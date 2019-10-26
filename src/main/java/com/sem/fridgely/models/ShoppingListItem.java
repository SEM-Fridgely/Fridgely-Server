package com.sem.fridgely.models;


import org.bson.Document;

public class ShoppingListItem {
    private String id, name, qty, unit;

    public  ShoppingListItem(Document doc){
        this.id = doc.getString("id");
        this.name = doc.getString("name");
        this.qty = doc.getString("qty");
        this.unit = doc.getString("unit");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQty() {
        return qty;
    }

    public String getUnit() {
        return unit;
    }
}
