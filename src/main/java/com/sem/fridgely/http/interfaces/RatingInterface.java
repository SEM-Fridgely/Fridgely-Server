package com.sem.fridgely.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.RatingManager;
import com.sem.fridgely.models.Rating;
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

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response ratingGetById(@PathParam("id") String id) {
        try {
            Double rating = RatingManager.getInstance().getByRatingId(id);
            if (rating != null) {
                return ServiceResponse.response200(rating);
            } else {
                return Response.noContent().build();
            }
        } catch (Exception e) {
            return null;
        }
    }

    @DELETE
    @Path("/user/{userid}/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response ratingDelete(@PathParam("userid") String uid, @PathParam("id") String id) {
        try {
            RatingManager.getInstance().deleteRating(id, uid);
            return Response.ok().entity("Deleted").build();
        } catch (Exception e) {
            return Response.status(400).entity("Failed to delete").build();
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
            Rating rating = RatingManager.getInstance().getByUserAndRatingId(userid, id);
            if (rating == null) {
                rating = RatingManager.getInstance().create(id, userid, req.getDouble("rating"));
            }
            return ServiceResponse.response200(rating.getInJson());
        } catch (Exception e) {
            return Response.status(400).entity(e.toString()).build();
        }
    }

    @PUT
    @Path("/user/{userid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response ratingUpdate(Object request, @PathParam("userid") String userid) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            String id = req.getString("id");
            Double value = req.getDouble("rating");
            Rating rating = RatingManager.getInstance().getByUserAndRatingId(userid, id);
            if (rating == null) {
                return ServiceResponse.response200("Invalid id");
            } else {
                rating.setRating(value);
                RatingManager.getInstance().updateRating(rating);
            }
            return ServiceResponse.response200(rating.getInJson());
        } catch (Exception e) {
            return Response.status(400).entity(e.toString()).build();
        }
    }
}
