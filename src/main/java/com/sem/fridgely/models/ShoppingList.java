package com.sem.fridgely.models;

import jdk.jfr.Timestamp;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;

public class ShoppingList {
    private String id,name, userid;
    private Timestamp createdOn,updatedOn,deletedOn;
//    List<HashMap<String,String>> items;
    public ShoppingList(Document doc){
        this.id = doc.getString("id");
        this.name = doc.getString("name");
        this.userid = doc.getString("userid");
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getUserId(){
        return userid;
    }
}
