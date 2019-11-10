package com.sem.fridgely.manager;

import com.sem.fridgely.models.Recipe.QueryResults;
import com.sem.fridgely.models.Recipe.Recipe;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RecipeAPIManager extends Manager {
    public static String FIELD_ID = "_id", FIELD_LABEL = "label", FIELD_DETAIL = "detail";

    ApiSettings settings;
    WebResource webResource;
    Client webClient;
    String searchResult;

    public RecipeAPIManager(ApiSettings settings) {
        this.settings = settings;
        if ( this.webClient == null){
            this.webClient = Client.create();
        }
    }

    public RecipeAPIManager callAPI() {

        ClientResponse response = createWebClient()
                .getWebResource()
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        //code to check response status code.
        InputStream inputStream = response.getEntityInputStream();
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())));
        try {
            this.searchResult = org.apache.commons.io.IOUtils.toString(reader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this;
    }

    private RecipeAPIManager createWebClient() {
        this.webResource = webClient.resource(getResourcePath())
                .queryParam("app_id", "3ef87764")
                .queryParam("app_key", "f6329aeb0ce6a806b529977877a9b5a4%20");
        return this;
    }

    private String getResourcePath() {
        String resourcePath = ApiSettings.API_ENDPOINT + ApiSettings.QUERY_PREFIX + settings.getQuery();
        return resourcePath;
    }

    private WebResource getWebResource() {
        return this.webResource;
    }

    public JSONArray getSearchResult() {
        try {
            JSONObject result = new JSONObject(this.searchResult);
            JSONArray hits = result.getJSONArray("hits");
            List<JSONObject> res = new ArrayList<JSONObject>();
            for (int i = 0; i < hits.length(); i++) {
                JSONObject hit = new JSONObject(new JSONObject(hits.getString(i)).getString("recipe"));
                String id = "rec" + (hit.getString("uri").hashCode());
                hit.put("id", id);
                hit.put("rating", RatingManager.getInstance().getByRatingId(id).getAveRating());
                hit.put("raterNum", RatingManager.getInstance().getByRatingId(id).getCount());
                res.add(new JSONObject().put("recipe", hit));
                // DB process, should process in async way
                insertRecipeToDB(id, hit.getString("label"), hit);
            }
            return new JSONArray(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertRecipeToDB(String id, String label, JSONObject recipe) {
        Document newDoc = new Document()
                .append(FIELD_ID, id)
                .append(FIELD_LABEL, label)
                .append(FIELD_DETAIL, recipe.toString().getBytes());

        if (newDoc != null && id != null) {
            Bson filter = eq(FIELD_ID, id);
            long count = recipeCollection.countDocuments(filter);
            if (count == 0) {
                recipeCollection.insertOne(newDoc);
            } else {

                Bson content = new Document("$set", new Document(FIELD_LABEL, label)
                        .append(FIELD_DETAIL, recipe.toString().getBytes()));
                recipeCollection.findOneAndUpdate(filter, content);
            }
        }
    }

    public Recipe getRecipeById(String id) {
        Bson filter = eq(FIELD_ID, id);
        Document doc = recipeCollection.find(filter).first();
        return new Recipe(doc);
    }

}