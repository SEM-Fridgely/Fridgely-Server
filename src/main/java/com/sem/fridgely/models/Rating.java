package com.sem.fridgely.models;


import com.sem.fridgely.manager.RatingManager;
import org.codehaus.jettison.json.JSONObject;

import java.util.DoubleSummaryStatistics;

public class Rating {
    private String id;
    private Double aveRating;
    private Long count;

    public Rating(String id, DoubleSummaryStatistics summaryStatistics) {
        this.id = id;
        this.aveRating = summaryStatistics.getAverage();
        this.count = summaryStatistics.getCount();
    }

    public static JSONObject castRatingValueToNumber(Double rating) {
        try {
            JSONObject js = new JSONObject().put("value", rating.floatValue());
            return js;
        } catch (Exception e) {
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public Float getAveRating() {
        return aveRating.floatValue();
    }

    public Integer getCount() {
        return count.intValue();
    }

    public JSONObject getInJson() {
        try {
            JSONObject js = new JSONObject().put(RatingManager.FIELD_ID, getId())
                    .put("count", getCount())
                    .put("average", getAveRating());
            return js;
        } catch (Exception e) {
            return null;
        }
    }

}
