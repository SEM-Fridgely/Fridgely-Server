package com.sem.fridgely.http.interfaces;

import com.sem.fridgely.http.ServiceResponse;
import com.sem.fridgely.manager.ApiSettings;
import com.sem.fridgely.manager.RecipeAPI;
import com.sem.fridgely.manager.ShoppingListManager;
import com.sem.fridgely.models.ShoppingList;
import org.json.JSONObject;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("/shoppingList")
public class ShoppingListInterface extends  HttpInterface{
    @GET
    @Path("")
    @Produces({ MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistGetName() {
        return new ServiceResponse("Hello this is shopping list interface");
    }
    @POST
    @Path("")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public ServiceResponse shoppinglistCreate(Object request){
        try{
            JSONObject req = new JSONObject(ow.writeValueAsString(request));
            String id = req.getString("id");
            ShoppingList shoppingList = ShoppingListManager.getInstance().getById(id);
            if(shoppingList != null){ return new ServiceResponse(shoppingList);}
            ShoppingListManager.getInstance().create(id,req.getString("name"));
            return new ServiceResponse(ShoppingListManager.getInstance().getById(id));
        }catch(Exception e) {

        }
        return null;
    }
}
