package com.sem.fridgely.manager;

import com.mongodb.BasicDBObject;
import com.sem.fridgely.models.User;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserManager extends Manager {

    public static UserManager _self;
    public static String FIELD_ID = "id", FIELD_USER_NAME = "username", FIELD_PASSWORD = "password", FIELD_EMAIL = "email";

    public UserManager() {
    }

    public static UserManager getInstance() {
        if (_self == null)
            _self = new UserManager();
        return _self;
    }

    public User createUser(User user) {
        String id = Base64.getEncoder().encodeToString(user.getEmail().getBytes());

        if (getUserById(id) == null) {
            Document newDoc = new Document()
                    .append(FIELD_ID, id)
                    .append(FIELD_USER_NAME, user.getUsername())
                    .append(FIELD_PASSWORD, user.getPassword())
                    .append(FIELD_EMAIL, user.getEmail());
            if (newDoc != null && id != null) {
                userCollection.insertOne(newDoc);
            }
        }
        return getUserById(id);
    }

    public ArrayList<User> getUserList() {
        List<User> userList = new ArrayList<User>();
        Iterable<Document> userDocs = userCollection.find();
        userDocs.forEach(doc -> userList.add(new User(doc)));
        return new ArrayList<>(userList);
    }

    public void updateUserPassword(String id, String password) {
        Bson filter = eq(FIELD_ID, id);
        Bson content = new Document("$set", new Document(FIELD_PASSWORD, password));
        this.userCollection.findOneAndUpdate(filter, content);
    }

    public ArrayList<User> getUserListSorted(String sortby) {
        ArrayList<User> userList = new ArrayList<User>();
        BasicDBObject sortParams = new BasicDBObject();
        sortParams.put(sortby, 1);
        Iterable<Document> userDocs = userCollection.find().sort(sortParams);
        userDocs.forEach(document -> userList.add(new User(document)));
        return new ArrayList<>(userList);
    }

    public User getUserById(String id) {
        Document doc = userCollection.find(new Document(FIELD_ID, id)).first();
        if (doc != null)
            return new User(doc);
        else
            return null;
    }

}
