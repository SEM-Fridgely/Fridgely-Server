package com.sem.fridgely.manager;

import com.mongodb.client.MongoCollection;
import com.sem.fridgely.utils.MongoDB;
import org.bson.Document;

public class Manager {
    protected MongoCollection<Document> shoppingListCollection, ratingCollection, userCollection, recipeCollection;

    public Manager() {
        this.shoppingListCollection = MongoDB.getInstance().getCollection(MongoDB.COLLECTION_NAME_SHOPPING_LIST);
        this.ratingCollection = MongoDB.getInstance().getCollection(MongoDB.COLLECTION_NAME_RATING);
        this.userCollection = MongoDB.getInstance().getCollection(MongoDB.COLLECTION_USER);
        this.recipeCollection = MongoDB.getInstance().getCollection(MongoDB.COLLECTION_RECIPE);
    }
//    protected AppException handleException(String message, Exception e){
//        AppLogger.error(message, e);
//        if((e instanceof AppException) && !(e instanceof AppInternalServerException)){
//            return (AppException) e;
//        }
//        return (AppInternalServerException) e;
//    }
}