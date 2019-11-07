package com.sem.fridgely.models;


import com.sem.fridgely.manager.UserManager;
import org.bson.Document;
import org.codehaus.jettison.json.JSONObject;

public class User {

    String id = null;
    String username = null;
    String password = null;
    String email = null;

    public User(String id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Document doc) {
        this.id = doc.getString(UserManager.FIELD_ID);
        this.username = doc.getString(UserManager.FIELD_USER_NAME);
        this.password = doc.getString(UserManager.FIELD_PASSWORD);
        this.email = doc.getString(UserManager.FIELD_EMAIL);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public JSONObject getInJSON() {
        try {
            JSONObject js = new JSONObject().put(UserManager.FIELD_ID, getId())
                    .put(UserManager.FIELD_USER_NAME, getUsername())
                    .put(UserManager.FIELD_EMAIL, getEmail());
            return js;
        } catch (Exception e) {
            return null;
        }
    }
}
