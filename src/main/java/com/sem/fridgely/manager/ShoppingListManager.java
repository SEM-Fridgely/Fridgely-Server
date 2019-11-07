package com.sem.fridgely.manager;

import com.sem.fridgely.models.ShoppingList;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.codehaus.jettison.json.JSONArray;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ShoppingListManager extends Manager {
    public static ShoppingListManager _self;
    public static String FIELD_ID = "id", FIELD_NAME = "name", FIELD_USERID = "userid", FIELD_ITEMS = "items";

    public static ShoppingListManager getInstance() {
        if (_self == null) {
            return new ShoppingListManager();
        }
        return _self;
    }

    public ShoppingList create(String name, String userId, JSONArray items) {
        String id = Base64.getEncoder().encodeToString((userId + new Date().toString()).getBytes());
        this.shoppingListCollection.insertOne(new Document(FIELD_ID, id).append(FIELD_NAME, name)
                .append(FIELD_USERID, userId)
                .append(FIELD_ITEMS, items.toString().getBytes()));
        return getById(id);
    }

    public void delete(String id) {
        this.shoppingListCollection.deleteOne(new Document(FIELD_ID, id));
    }

    public void update(ShoppingList shoppinglist) {
        Bson filter = eq(FIELD_ID, shoppinglist.getId());
        Bson content = new Document("$set", new Document(FIELD_NAME, shoppinglist.getName()));
        Document doc = this.shoppingListCollection.findOneAndUpdate(filter, content);
    }

    public ShoppingList getById(String id) {
        Document doc = this.shoppingListCollection.find(new Document(FIELD_ID, id)).first();
        if (doc != null)
            return new ShoppingList(doc);
        else
            return null;
    }

    public ShoppingList getByUserId(String userid, String id) {
        Document doc = this.shoppingListCollection.find(new Document(FIELD_USERID, userid).append(FIELD_ID, id)).first();
        if (doc != null)
            return new ShoppingList(doc);
        else
            return null;
    }

    public List<ShoppingList> getAllByUserId(String userid) {
        List<ShoppingList> sList = new ArrayList<ShoppingList>();
        Iterable<Document> docs = this.shoppingListCollection.find(new Document(FIELD_USERID, userid));
        if (docs != null) {
            docs.forEach(doc -> sList.add(new ShoppingList(doc)));
            return sList;
        } else {
            return null;
        }
    }

    public ShoppingList updateItems(String id, String name, JSONArray items) {
        List<ShoppingList> sl = new ArrayList<ShoppingList>();
        Bson filter = eq(FIELD_ID, id);
        Bson content = new Document("$set", new Document(FIELD_ITEMS, items.toString().getBytes()).append(FIELD_NAME, name));
        this.shoppingListCollection.findOneAndUpdate(filter, content);
        return getById(id);
    }

}
