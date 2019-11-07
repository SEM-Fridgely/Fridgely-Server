package com.sem.fridgely.http.interfaces;

import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.ShoppingListManager;
import com.sem.fridgely.models.ShoppingList;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/shoppingList")
public class ShoppingListInterface extends HttpInterface {

    public ShoppingListInterface() {

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppingListRoot() {
        JSONObject obj = new JSONObject();
        try {
            return Response.ok("hello this is shopping list page").build();
        } catch (Exception e) {
            return Response.status(404).entity("Service is not available now, please try later").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppinglistGetById(@PathParam("id") String shoppinglistid) {
        try {
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(shoppinglistid);
            if (shoppingList != null) {
                return ServiceResponse.response200(shoppingList.getInJson());
            } else {
                return Response.noContent().build();
            }
        } catch (Exception e) {
            return null;
        }
    }

    @GET
    @Path("/user/{userid}/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppinglistAllGetByUser(@PathParam("userid") String id) {
        try {
            List<JSONObject> ls = ShoppingListManager.getInstance().getAllByUserId(id).stream()
                    .map(ShoppingList::getInJson).collect(toList());
            return ServiceResponse.response200(new JSONArray(ls));
        } catch (Exception e) {
            return null;
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppinglistDelete(@PathParam("id") String id) {
        try {
            ShoppingListManager.getInstance().delete(id);
            return Response.ok().entity("Deleted").build();
        } catch (Exception e) {
            return Response.status(400).entity("Failed to delete").build();
        }
    }

    @POST
    @Path("/user/{userid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppinglistCreate(Object request, @PathParam("userid") String userid) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            ShoppingList shoppingList = ShoppingListManager.getInstance().create(req.getString("name"), userid, req.getJSONArray("items"));
            return ServiceResponse.response200(shoppingList.getInJson());
        } catch (Exception e) {
            return Response.status(400).entity(e.toString()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppinglistPut(Object request, @PathParam("id") String id) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(id);
            if (shoppingList == null) {
                return Response.noContent().entity("Not found").build();
            }
            ShoppingList updated = ShoppingListManager.getInstance().updateItems(id, req.getString("name"), req.getJSONArray("items"));
            return ServiceResponse.response200(updated.getInJson());
        } catch (Exception e) {

        }
        return null;
    }
}
