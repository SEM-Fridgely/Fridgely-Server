package com.sem.fridgely.utils;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {
    private static MongoDB _self;
    public static String COLLECTION_NAME_SHOPPING_LIST = "shoppinglist", COLLECTION_NAME_RATING="ratings", COLLECTION_USER="users", COLLECTION_RECIPE="recipes";
    private static MongoDatabase db;

    private MongoDB(){
        try {
            MongoClient mc = MongoClients.create();
            db = mc.getDatabase(Config.database);
            Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        } catch (Exception e) {
            AppLogger.error("From MongoPool creation ",e);
        }
    }
    public static MongoDB getInstance(){
        if (_self == null){
            _self = new MongoDB();
        }
        return _self;
    }
    public MongoCollection<Document> getCollection(String collectionName){
        return db.getCollection(collectionName);
    }
}
