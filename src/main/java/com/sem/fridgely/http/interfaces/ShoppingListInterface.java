package com.sem.fridgely.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sem.fridgely.manager.ShoppingListManager;
import com.sem.fridgely.models.ShoppingList;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/shoppingList")
public class ShoppingListInterface {
    protected ObjectWriter ow;

    public ShoppingListInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppingListRoot() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("version", "0.0.1");
            obj.put("date", String.join(" ", LocalDateTime.now().toString().split("T")));
            obj.put("info", "Hello from Shopping List Interface : )");
            return Response.ok(new JSONObject().put("data", obj), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(404).entity("Service is not available now, please try later").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response shoppinglistGetByUser(@PathParam("id") String shoppinglistid) {
        try {
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(shoppinglistid);
            if (shoppingList != null) {
                return Response.ok(new JSONObject().put("data", shoppingList.getInJson()), MediaType.APPLICATION_JSON_TYPE).build();
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
            List<JSONObject> ls = ShoppingListManager.getInstance().getAllByUserId(id).stream().map(ShoppingList::getInJson).collect(toList());
            return Response.ok(new JSONObject().put("data", new JSONArray(ls)), MediaType.APPLICATION_JSON).build();
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
            String id = req.getString("id");
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(id);
            if (shoppingList != null) {
                return Response.ok(new JSONObject().put("data", shoppingList.getInJson()), MediaType.APPLICATION_JSON).build();
            } else {
                shoppingList = ShoppingListManager.getInstance().create(id, req.getString("name"), userid, req.getJSONArray("items"));
                return Response.ok(shoppingList.getInJson(), MediaType.APPLICATION_JSON).build();
            }
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
            return Response.ok(new JSONObject().put("data", updated.getInJson()), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {

        }
        return null;
    }
}
