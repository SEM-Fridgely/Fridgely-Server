package com.sem.fridgely.manager;

import com.sem.fridgely.models.Recipe.QueryResults;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
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

public class RecipeAPIManager {
    ApiSettings settings;
    QueryResults results;
    WebResource webResource;
    String searchResult;

    public RecipeAPIManager(ApiSettings settings) {
        this.settings = settings;

//        results  = new QueryResults();
    }

    public RecipeAPIManager callAPI() {

        ClientResponse response = createWebClient()
                .setQuery()
                .setIndex()
                .setCalories()
                .setDiet()
                .getWebResource()
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        //code to check response status code.
        InputStream inputStream = response.getEntityInputStream();
        System.out.println(inputStream.toString());
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())));
        try {
            this.searchResult = org.apache.commons.io.IOUtils.toString(reader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this;
    }

    public RecipeAPIManager createWebClient() {
        Client client = Client.create();
        this.webResource = client.resource(ApiSettings.API_ENDPOINT).queryParam("app_id", "3ef87764")
                .queryParam("app_key", "f6329aeb0ce6a806b529977877a9b5a4%20");
        return this;
    }

    private RecipeAPIManager setQuery() {
        System.out.println(settings.getQuery());
        if (settings.getQuery() != null) {
            this.webResource = getWebResource().queryParam("q", settings.getQuery());
        } else {
            this.webResource = getWebResource().queryParam("q", "test");
        }
        return this;
    }

    private RecipeAPIManager setIndex() {
        if (settings.getIndexFrom() != null && settings.getIndexTo() != null) {
            this.webResource = getWebResource().queryParam("from", settings.getIndexFrom().toString())
                    .queryParam("to", settings.getIndexTo().toString());
        }
        return this;
    }

    private RecipeAPIManager setCalories() {
        if (settings.getCalorieFrom() != null && settings.getCalorieTo() != null) {
//          queryParam("calories", "700-800")
            String cal = settings.getCalorieFrom().toString() + "-" + settings.getCalorieTo().toString();
            this.webResource = getWebResource().queryParam("calories", "700-800");
        }
        return this;
    }

    private RecipeAPIManager setDiet() {
        if (settings.getDietTags() != null) {
//          queryParam("diet", "low-fat")
            this.webResource = getWebResource().queryParam("calories", "low-fat");
        }
        return this;
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
                String id = "rec"+ (hit.getString("uri").hashCode());
                hit.put("id", id);
                res.add(hit);
            }
            return new JSONArray(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}