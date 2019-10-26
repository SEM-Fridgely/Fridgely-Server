package com.sem.fridgely.manager;

import java.util.ArrayList;

public class Recipe {
    String uri;
    String label;// Recipe title
    ArrayList<String> image;
    String url;
    ArrayList<String> tags;
    int yield;// Serving Size
    int calories;
    float rating;
    int raterNum;
    ArrayList<String> ingredients;
    ArrayList<DietLabels> dietLabels;
    ArrayList<HealthLabels> healthLabels;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRaterNum() {
        return raterNum;
    }

    public void setRaterNum(int raterNum) {
        this.raterNum = raterNum;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<DietLabels> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(ArrayList<DietLabels> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public ArrayList<HealthLabels> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(ArrayList<HealthLabels> healthLabels) {
        this.healthLabels = healthLabels;
    }
}
