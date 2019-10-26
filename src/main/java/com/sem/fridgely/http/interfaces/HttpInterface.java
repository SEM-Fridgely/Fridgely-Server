package com.sem.fridgely.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.WebApplicationException;

public class HttpInterface extends ResourceConfig {
    protected ObjectWriter ow;

    public HttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    protected WebApplicationException handleException(String message, Exception e) {
//        if(e instanceof JSONException)
//            return new HttpBadRequestException(-1, "Bad request data provided: " + e.getMessage());
//        if (e instanceof AppException)
//            return ((AppException) e).getHttpException();
//        AppLogger.error(message, e);
//        return new HttpInternalServerException(-1);
        return null;
    }
}
