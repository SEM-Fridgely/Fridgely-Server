package mocks;

import org.bson.BsonBinary;
import org.bson.Document;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class ShoppingListMock {

    public static Document getShopplingList() {
        JSONArray items = new JSONArray();
        try {
            items.put(new JSONObject().put("id", "I123").put("name", "ITEM TEST 3").put("qty", 10).put("unit", "g"))
                    .put(new JSONObject().put("id", "I124").put("name", "ITEM TEST 2").put("qty", 6).put("unit", "g"));

        } catch (Exception err) {

        }

        return new Document("id", "sl003")
                .append("name", "Dan3's shopping list")
                .append("userid", "u234").append("items", new BsonBinary(items.toString().getBytes()));
    }

    public static JSONObject getShopplingListExpected() {
        JSONArray items = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
            items.put(new JSONObject().put("id", "I123").put("name", "ITEM TEST 3").put("qty", 10).put("unit", "g"))
                    .put(new JSONObject().put("id", "I124").put("name", "ITEM TEST 2").put("qty", 6).put("unit", "g"));

            obj.append("data", new JSONObject().append("id", "sl003")
                    .append("name", "Dan3's shopping list")
                    .append("userid", "u234").append("items", items));
        } catch (Exception err) {

        }
        return obj;
    }
}
