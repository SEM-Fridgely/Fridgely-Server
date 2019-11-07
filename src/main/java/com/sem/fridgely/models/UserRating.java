package com.sem.fridgely.models;


import com.sem.fridgely.manager.RatingManager;
import org.bson.Document;
import org.codehaus.jettison.json.JSONObject;

public class UserRating {
    private String id, userid;
    private Double rating;

    public UserRating(Document doc) {
        this.id = doc.getString("id");
        this.userid = doc.getString("userid");
        this.rating = doc.getDouble("rating");
    }

    public String getId() {
        return this.id;
    }

    public String getUserid() {
        return this.userid;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public JSONObject getInJson() {
        try {
            JSONObject js = new JSONObject().put(RatingManager.FIELD_ID, getId())
                    .put(RatingManager.FIELD_USERID, getUserid())
                    .put(RatingManager.FIELD_RATING, getRating());
            return js;
        } catch (Exception e) {
            return null;
        }
    }
}
