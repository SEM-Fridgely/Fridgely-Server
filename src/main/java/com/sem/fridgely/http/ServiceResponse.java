package com.sem.fridgely.http;

import org.codehaus.jettison.json.JSONObject;

public class ServiceResponse {
    public boolean success = true;
    public Object data;
    public int httpStatusCode = 200;

    public ServiceResponse(Object content) {
       this.data = content;
    }
}
