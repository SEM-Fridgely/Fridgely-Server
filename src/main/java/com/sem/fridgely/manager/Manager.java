package com.sem.fridgely.manager;

import com.mongodb.client.MongoCollection;
import com.sem.fridgely.utils.AppLogger;
import com.sem.fridgely.utils.MongoDB;
import org.bson.Document;

public class Manager {
    protected MongoCollection<Document> shoppingListCollection;

    public Manager(){
        this.shoppingListCollection = MongoDB.getInstance().getCollection(MongoDB.COLLECTION_NAME_SHOPPING_LIST);
    }
//    protected AppException handleException(String message, Exception e){
//        AppLogger.error(message, e);
//        if((e instanceof AppException) && !(e instanceof AppInternalServerException)){
//            return (AppException) e;
//        }
//        return (AppInternalServerException) e;
//    }
}