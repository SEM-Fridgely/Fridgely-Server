package com.sem.fridgely.models;

import jdk.jfr.Timestamp;
import org.bson.Document;
import org.bson.types.Binary;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class ShoppingList {
    private String id, name, userid;
    private Timestamp createdOn, updatedOn, deletedOn;
    private Binary bItems;

    public ShoppingList(Document doc) {
        this.id = doc.getString("id");
        this.name = doc.getString("name");
        this.userid = doc.getString("userid");
        this.bItems = doc.get("items", org.bson.types.Binary.class);
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
            JSONObject js = new JSONObject().put("id", getId())
                    .put("name", getName())
                    .put("userid", getUserId())
                    .put("items", getItems());
            return js;
        } catch (Exception e) {
            return null;
        }
    }
}
