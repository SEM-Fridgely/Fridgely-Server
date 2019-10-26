package com.sem.fridgely.http;

public class ServiceResponse {
 public boolean success = true;
    public Object data;
    public int httpStatusCode = 200;
    public ServiceResponse(Object data){
        this.data = data;
    }

}
