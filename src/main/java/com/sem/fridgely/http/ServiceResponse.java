package com.sem.fridgely.http;

import org.codehaus.jettison.json.JSONObject;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServiceResponse  {
    public boolean success = true;
    public Object data;
    public int httpStatusCode = 200;

    public static Response response200 (Object content) {
        JSONObject rs = new JSONObject();
        try {
            return  Response.ok(new JSONObject().put("data",content), MediaType.APPLICATION_JSON).build();
        }catch(Exception e){
            return  Response.status(400).entity("Error while response encoding").build();
        }

    }

}
