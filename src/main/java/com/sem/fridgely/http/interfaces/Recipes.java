package com.sem.fridgely.http.interfaces;

import com.sem.fridgely.manager.ApiSettings;
import com.sem.fridgely.manager.RecipeAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("")
public class Recipes extends HttpInterface {
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
    @Produces({MediaType.TEXT_PLAIN})
    public String recipeGetAll(@QueryParam("q") String query) {
        String specificRecipeUrl = "https://api.edamam.com/search";

        if(query!=null) {
           ApiSettings recipeApiSetting = new ApiSettings(query, specificRecipeUrl);
           RecipeAPI recipeAPI = new RecipeAPI(recipeApiSetting);
           String results =    recipeAPI.callAPI();
           return results;
        }
        return "Hello world without query";
    }
}
