package com.sem.fridgely.manager;

import com.sem.fridgely.models.ShoppingList;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class ShoppingListManager extends Manager{
    public static ShoppingListManager _self;

    public static ShoppingListManager getInstance(){
        if(_self ==null ){
            return new ShoppingListManager();
        }
        return _self;
    }

    public String create(String id, String name, String userId){
        this.shoppingListCollection.insertOne(new Document("id", id).append("name", name).append("userid",userId));
        return id;
    }

    public void delete(String id){
        this.shoppingListCollection.deleteOne(new Document("id", id));
    }

    public void update(ShoppingList shoppinglist){
        Bson filter = eq("id", shoppinglist.getId());
        Bson content = new Document("$set", new Document("name", shoppinglist.getName()));
        Document doc = this.shoppingListCollection.findOneAndUpdate(filter,content);
    }
    public ShoppingList getById(String id){
        Document doc = this.shoppingListCollection.find(new Document("id",id)).first();
        if(doc != null)
            return new ShoppingList(doc);
        else
            return null;
    }
    public ShoppingList getByUserId(String userid, String id){
        Document doc = this.shoppingListCollection.find(new Document("userid",userid).append("id",id)).first();
        if(doc != null)
            return new ShoppingList(doc);
        else
            return null;
    }
    public List<ShoppingList> getAllByUserId(String userid){
        List<ShoppingList> sList = new ArrayList<ShoppingList>();
        Iterable<Document> docs =  this.shoppingListCollection.find(new Document("userid",userid));
        if(docs != null){
            docs.forEach(doc->sList.add(new ShoppingList(doc)));
            return sList;
        }
        else{
            return null;}
    }
    public void addItems(){}
    public void updateItems(ShoppingList shoppinglist){
        Bson filter = eq("id", shoppinglist.getId());
        Bson content = new Document("$set", new Document("name", shoppinglist.getName()));
        Document doc = this.shoppingListCollection.findOneAndUpdate(filter,content);
    }
    public void deleteItems(){}

}
