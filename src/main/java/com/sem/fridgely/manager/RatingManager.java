package com.sem.fridgely.manager;

import com.sem.fridgely.models.Rating;
import com.sem.fridgely.models.UserRating;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.DoubleSummaryStatistics;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class RatingManager extends Manager {
    public static RatingManager _self;
    public static String FIELD_ID = "id", FIELD_RATING = "rating", FIELD_USERID = "userid";

    public static RatingManager getInstance() {
        if (_self == null) {
            return new RatingManager();
        }
        return _self;
    }

    public UserRating create(String id, String userid, Double rating) {
        this.ratingCollection.insertOne(new Document("id", id).append("userid", userid).append("rating", rating));
        return getByUserAndRatingId(userid, id);
    }

    public Rating getByRatingId(String id) {
        Iterable<Document> docs = this.ratingCollection.find(new Document(FIELD_ID, id));
        DoubleSummaryStatistics summaryStatistics = StreamSupport.stream(docs.spliterator(), true)
                .mapToDouble(doc -> doc.getDouble(FIELD_RATING)).summaryStatistics();
        return new Rating(id, summaryStatistics);
    }

    public UserRating getByUserAndRatingId(String userid, String id) {
        Document doc = this.ratingCollection.find(new Document(FIELD_ID, id).append(FIELD_USERID, userid)).first();
        if (doc != null)
            return new UserRating(doc);
        else
            return null;
    }

    public UserRating updateRating(UserRating userRating) {
        Bson filter = and(eq(FIELD_ID, userRating.getId()), eq(FIELD_USERID, userRating.getUserid()));
        Bson content = new Document("$set", new Document(FIELD_RATING, userRating.getRating()));
        Document doc = this.ratingCollection.findOneAndUpdate(filter, content);
        return new UserRating(doc);
    }

    public void deleteRating(String id, String userid) {
        Bson filter = and(eq(FIELD_ID, id), eq(FIELD_USERID, userid));
        Document doc = this.ratingCollection.findOneAndDelete(filter);
    }
}
