package com.sem.fridgely.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.RatingManager;
import com.sem.fridgely.models.Rating;
import com.sem.fridgely.models.UserRating;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ratings")
public class RatingInterface extends ResourceConfig {


    protected ObjectWriter ow;

    public RatingInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response ratingRoot() {
        JSONObject obj = new JSONObject();
        try {
            return Response.ok("hello this is rating page").build();
        } catch (Exception e) {
            return Response.status(404).entity("Service is not available now, please try later").build();
        }
    }

    @POST
    @Path("/user/{userid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response ratingCreate(Object request, @PathParam("userid") String userid) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            String id = req.getString("id");
            Double value = req.getDouble("rating");
            UserRating userRating = RatingManager.getInstance().getByUserAndRecipeId(userid, id);
            if (userRating == null) {
                userRating =  RatingManager.getInstance().create(id,userid,value);
            } else {
                userRating.setRating(value);
                userRating =  RatingManager.getInstance().updateRating(userRating);
            }
            return ServiceResponse.response200(userRating.getInJson());
        } catch (Exception e) {
            return Response.status(400).entity(e.toString()).build();
        }
    }
}
