package com.sem.fridgely.http.interfaces;


import com.mongodb.client.MongoCollection;
import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.UserManager;
import com.sem.fridgely.models.User;
import org.bson.Document;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserHttpInterface extends HttpInterface {

    private MongoCollection<Document> userCollection = null;

    public UserHttpInterface() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postUsers(Object request) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            User newuser = new User(
                    null,
                    req.getString("username"),
                    req.getString("password"),
                    req.getString("email")
            );
            User user = UserManager.getInstance().createUser(newuser);
            return ServiceResponse.response200(user.getInJSON());
        } catch (Exception e) {
            throw handleException("POST users", e);
        }
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postUsersLogin(Object request) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            User user = UserManager.getInstance().userLogin(req.getString("email"), req.getString("password"));
            if (user != null) {
                return ServiceResponse.response200(user.getInJSON());
            } else {
                return ServiceResponse.response200(null);
            }
        } catch (Exception e) {
            throw handleException("POST users", e);
        }
    }

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSingleUser(@Context HttpHeaders headers, @PathParam("userId") String userId) {

        User user = UserManager.getInstance().getUserById(userId);

        if (user != null)
            return ServiceResponse.response200(user.getInJSON());
        else
            return ServiceResponse.response200(null);
    }

    @PUT
    @Path("/{userId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response patchUsers(@Context HttpHeaders headers, Object request, @PathParam("userId") String userId) {
        JSONObject json = null;
        try {
            json = new JSONObject(ow.writeValueAsString(request));

            UserManager.getInstance().updateUserPassword(userId, json.getString("password"));

        } catch (Exception e) {
            throw handleException("PATCH users/{userId}", e);
        }
        return ServiceResponse.response200("Update Successful");
    }
}
