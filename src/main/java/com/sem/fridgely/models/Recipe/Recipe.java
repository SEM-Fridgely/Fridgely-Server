package com.sem.fridgely.models.Recipe;

import com.sem.fridgely.manager.RecipeAPIManager;
import org.bson.Document;
import org.bson.types.Binary;
import org.codehaus.jettison.json.JSONObject;

public class Recipe {
    String id, label;
    private Binary bItems;

    public Recipe(Document doc) {
        this.id = doc.getString(RecipeAPIManager.FIELD_ID);
        this.label = doc.getString(RecipeAPIManager.FIELD_LABEL);
        try {
            this.bItems = doc.get(RecipeAPIManager.FIELD_DETAIL, org.bson.types.Binary.class);
        } catch (Exception e) {
        }
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public JSONObject getInJson() {
        try {
            return new JSONObject(new String(bItems.getData())); // contains whole bunch of recipe object
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
