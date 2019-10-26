package com.sem.fridgely.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RecipeAPI {
    ApiSettings settings;
    QueryResults results;

    public RecipeAPI(ApiSettings settings){
        this.settings = settings;
//        results  = new QueryResults();
    }

    public String callAPI() {
        String query = this.settings.getQuery();
        Client client = Client.create();
        WebResource webResource = client.resource(ApiSettings.API_ENDPOINT);
        ClientResponse response = webResource.queryParam("app_id","3ef87764")
                .queryParam("app_key","f6329aeb0ce6a806b529977877a9b5a4%20")
                .queryParam("q",query)
                .queryParam("from","0")
                .queryParam("to","2")
                .queryParam("calories","700-800")
                .queryParam("diet","low-fat").type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        //code to check response status code.

        InputStream inputStream = response.getEntityInputStream();

        Reader reader  = new BufferedReader(new InputStreamReader(inputStream,Charset.forName(StandardCharsets.UTF_8.name())));
        try{
            String test=  org.apache.commons.io.IOUtils.toString(reader);
            return test;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "Hello from recipe API with query ";
        }
    }
}