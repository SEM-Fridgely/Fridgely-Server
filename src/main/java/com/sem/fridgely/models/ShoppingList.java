package com.sem.fridgely.models;

import com.sem.fridgely.manager.ShoppingListManager;
import org.bson.Document;
import org.bson.types.Binary;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;

public class ShoppingList {

    private String id, name, userid;
    private Date createdOn, updatedOn, deletedOn;
    private Binary bItems;

    public ShoppingList(Document doc) {
        this.id = doc.getString(ShoppingListManager.FIELD_ID);
        this.name = doc.getString(ShoppingListManager.FIELD_NAME);
        this.userid = doc.getString(ShoppingListManager.FIELD_USERID);
        try {
            this.bItems = doc.get(ShoppingListManager.FIELD_ITEMS, org.bson.types.Binary.class);
        } catch (Exception e) {
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userid;
    }

    public JSONArray getItems() {
        try {
            return new JSONArray(new String(bItems.getData()));
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public JSONObject getInJson() {
        try {
            JSONObject js = new JSONObject().put(ShoppingListManager.FIELD_ID, getId())
                    .put(ShoppingListManager.FIELD_NAME, getName())
                    .put(ShoppingListManager.FIELD_USERID, getUserId())
                    .put(ShoppingListManager.FIELD_ITEMS, getItems());
            return js;
        } catch (Exception e) {
            return null;
        }
    }
}
