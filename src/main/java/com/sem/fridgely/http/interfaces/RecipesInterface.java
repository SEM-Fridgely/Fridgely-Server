package com.sem.fridgely.http.interfaces;

import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.ApiSettings;
import com.sem.fridgely.manager.RecipeAPIManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Path("")
public class RecipesInterface extends HttpInterface {
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String start() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("version", "0.0.1");
            obj.put("date", String.join(" ", LocalDateTime.now().toString().split("T")));
            obj.put("info", "Welcome to the library");
        } catch (Exception e) {
            System.out.println("Could not set version");
        }
        return "Hello world";
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response recipeGetAll(@QueryParam("q") String query) {
        if (query != null) {
            ApiSettings recipeApiSetting = new ApiSettings(query);
            RecipeAPIManager recipeAPIManager = new RecipeAPIManager(recipeApiSetting);
            JSONArray results = recipeAPIManager.callAPI().getSearchResult();
            return ServiceResponse.response200(results);
        }
        return ServiceResponse.response200("results");
    }

    @POST
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response recipePostAll(Object request, @QueryParam("q") String query) {
        if (query != null) {
            try {
                JSONObject req = new JSONObject(ow.writeValueAsString(request));
                ApiSettings recipeApiSetting = new ApiSettings(query).parseFilters(req);
                JSONArray results = new RecipeAPIManager(recipeApiSetting).callAPI().getSearchResult();
                return ServiceResponse.response200(results);
            } catch (Exception e) {
                return Response.status(500).entity("Error").build();
            }
        }
        return Response.status(404).entity("query is missing").build();
    }
}
