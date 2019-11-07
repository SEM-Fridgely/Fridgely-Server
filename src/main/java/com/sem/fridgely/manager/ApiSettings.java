package com.sem.fridgely.manager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ApiSettings {
    public static final String API_ENDPOINT = "https://api.edamam.com/search";
    String query = null; // Required
    Integer indexFrom = null, indexTo = null, maxIngredient = null;
    String[] dietTags = null, healthTags = null, cuisineType = null, mealType = null, dishType = null, exclude = null;
    Integer calorieFrom = null, calorieTo = null, prepTimeFrom = null, prepTimeTo = null;
    JSONObject request = null;


    // Minimum settings
    public ApiSettings(String query) {
        this.query = query;
    }

    // Full settings
    public ApiSettings(String query, String specificRecipeUrl, int indexFrom, int indexTo, int maxIngredient, String[] dietTags, String[] healthTags, String[] cuisineType, String[] mealType, String[] dishType, int calorieFrom, int calorieTo, int prepTimeFrom, int prepTimeTo, String[] exclude) {
        this.query = query;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
        this.maxIngredient = maxIngredient;
        this.dietTags = dietTags;
        this.healthTags = healthTags;
        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.dishType = dishType;
        this.calorieFrom = calorieFrom;
        this.calorieTo = calorieTo;
        this.prepTimeFrom = prepTimeFrom;
        this.prepTimeTo = prepTimeTo;
        this.exclude = exclude;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    public Integer getIndexFrom() {
        return indexFrom;
    }

    public void setIndexFrom(int indexFrom) {
        this.indexFrom = indexFrom;
    }

    public Integer getIndexTo() {
        return indexTo;
    }

    public void setIndexTo(int indexTo) {
        this.indexTo = indexTo;
    }

    public int getMaxIngredient() {
        return maxIngredient;
    }

    public void setMaxIngredient(int maxIngredient) {
        this.maxIngredient = maxIngredient;
    }

    public String[] getDietTags() {
        return dietTags;
    }

    public void setDietTags(String[] dietTags) {
        this.dietTags = dietTags;
    }

    public String[] getHealthTags() {
        return healthTags;
    }

    public void setHealthTags(String[] healthTags) {
        this.healthTags = healthTags;
    }

    public String[] getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String[] cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String[] getMealType() {
        return mealType;
    }

    public void setMealType(String[] mealType) {
        this.mealType = mealType;
    }

    public String[] getDishType() {
        return dishType;
    }

    public void setDishType(String[] dishType) {
        this.dishType = dishType;
    }

    public Integer getCalorieFrom() {
        return calorieFrom;
    }

    public void setCalorieFrom(int calorieFrom) {
        this.calorieFrom = calorieFrom;
    }

    public Integer getCalorieTo() {
        return calorieTo;
    }

    public void setCalorieTo(int calorieTo) {
        this.calorieTo = calorieTo;
    }

    public Integer getPrepTimeFrom() {
        return prepTimeFrom;
    }

    public void setPrepTimeFrom(int prepTimeFrom) {
        this.prepTimeFrom = prepTimeFrom;
    }

    public Integer getPrepTimeTo() {
        return prepTimeTo;
    }

    public void setPrepTimeTo(int prepTimeTo) {
        this.prepTimeTo = prepTimeTo;
    }

    public String[] getExclude() {
        return exclude;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }

    public ApiSettings parseFilters(JSONObject request) {
        this.request = request;
        setIndexFrom();
        setIndexTo();
        setMaxIngredient();
        setCalorieFrom();
        setCalorieTo();
        return this;
    }

    private void setIndexFrom() {
        try {
            indexFrom = request.getInt("indexFrom");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setIndexTo() {
        try {
            indexTo = request.getInt("indexTo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCalorieFrom() {
        try {
            calorieFrom = request.getInt("calorieFrom");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCalorieTo() {
        try {
            calorieTo = request.getInt("calorieTo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMaxIngredient() {
        try {
            maxIngredient = request.getInt("maxIngredient");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDietTags() {
        try {
            maxIngredient = request.getInt("maxIngredient");
//            dietTags = request.getJSONArray("dietTags"); // Data type change
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setHealthTags() {
        try {
            maxIngredient = request.getInt("maxIngredient");
//            healthTags = request.getJSONArray("healthTags"); // Data type change
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCuisineType() {
        try {
            maxIngredient = request.getInt("maxIngredient");
//            cuisineType = request.getJSONArray("cuisineType"); // Data type change
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMealType() {
        try {
            maxIngredient = request.getInt("maxIngredient");
//            mealType = request.getJSONArray("mealType"); // Data type change
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
