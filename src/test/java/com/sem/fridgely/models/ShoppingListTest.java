package com.sem.fridgely.models;

import com.sem.fridgely.manager.ShoppingListManager;
import org.bson.Document;
import org.bson.types.Binary;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoppingListTest {

    @Test
    public void testGetInJASON() throws JSONException {
        // Arrange
        JSONArray items = new JSONArray().put(new JSONObject()
                .put("id", "123")
                .put("qty", 10)
                .put("unit", "g"));
        Document doc = new Document(ShoppingListManager.FIELD_ID, "123")
                .append(ShoppingListManager.FIELD_NAME, "test list")
                .append(ShoppingListManager.FIELD_USERID, "u123")
                .append(ShoppingListManager.FIELD_ITEMS,new Binary(items.toString().getBytes()));

        JSONObject expected = new JSONObject().put("id", "123")
                .put("name", "test list")
                .put("userid", "u123").put("items", items);

        // Action
        ShoppingList undertest = new ShoppingList(doc);
        JSONObject actual = undertest.getInJson();

        // Assert
        assertEquals(expected.toString(),actual.toString());

    }
}