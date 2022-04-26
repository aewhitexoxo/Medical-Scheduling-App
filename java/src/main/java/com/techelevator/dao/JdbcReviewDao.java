package com.techelevator.dao;

import com.techelevator.model.Office;
import com.techelevator.model.Review;
import com.techelevator.model.UserNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createReview(Review review) {
        String sql = "INSERT INTO review (rating, review_desc, office_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, review.getRating(),review.getReviewDesc(),review.getOfficeId());


    }

    @Override
    public Review getReviewById(Long reviewId) {
        String sql = "SELECT * FROM review WHERE review_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reviewId);
        if(results.next()) {
            return mapRowToReview(results);
        } else {
            throw new UserNotFoundException();
        }
    }



    @Override
    public List<Review> displayAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "select * from review";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Review review = mapRowToReview(results);
            reviews.add(review);
        }

        return reviews;
    }

    @Override
    public void respondToReview(Review review) {
        String sql = "UPDATE review SET response = ? WHERE review_id = ?";
        jdbcTemplate.update(sql,review.getResponse(),review.getReviewId());
    }

    @Override
    public List<Review> getReviewByOfficeId(Long officeId) {
        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT * FROM review WHERE office_id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,officeId);
        while(rowSet.next()) {

            Review review = mapRowToReview(rowSet);
            reviews.add(review);
        }
        return reviews;
    }

    private Review mapRowToReview(SqlRowSet results) {
        Review review = new Review();
        review.setReviewId(results.getLong("review_id"));
        review.setRating(results.getInt("rating"));
        review.setReviewDesc(results.getString("review_desc"));
        review.setOfficeId(results.getLong("office_id"));
        review.setResponse(results.getString("response"));

        return review;
    }
}
