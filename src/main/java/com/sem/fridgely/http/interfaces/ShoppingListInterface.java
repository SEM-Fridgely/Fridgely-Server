package com.sem.fridgely.http.interfaces;

import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.ShoppingListManager;
import com.sem.fridgely.models.ShoppingList;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/shoppingList")
public class ShoppingListInterface extends HttpInterface {
    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistGetAll() {
        return new ServiceResponse("Hello this is shopping list interface");
    }

    @GET
    @Path("/{shoppinglistid}")
    @Produces({MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistGetByUser(@PathParam("shoppinglistid") String shoppinglistid) {
        try {
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(shoppinglistid);
            if (shoppingList != null) {
                return new ServiceResponse(shoppingList);
            } else {
                return new ServiceResponse("not found");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @GET
    @Path("/user/{id}/all")
    @Produces({MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistAllGetByUser(@PathParam("id") String id) {
        try {
            return new ServiceResponse(ShoppingListManager.getInstance().getAllByUserId(id));
        } catch (Exception e) {
            return null;
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistDelete(@PathParam("id") String id) {
        try {
            ShoppingListManager.getInstance().delete(id);
            return new ServiceResponse("deleted");
        } catch (Exception e) {
            return new ServiceResponse("error");
        }
    }

    @POST
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistCreate(Object request, @PathParam("id") String id) {
        try {
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            String shoppinglistId = req.getString("id");
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(shoppinglistId);
            if (shoppingList != null) {
                return new ServiceResponse(shoppingList);
            }
            ShoppingListManager.getInstance().create(shoppinglistId, req.getString("name"), id);
            return new ServiceResponse(ShoppingListManager.getInstance().getById(shoppinglistId));
        } catch (Exception e) {

        }
        return null;
    }
}
