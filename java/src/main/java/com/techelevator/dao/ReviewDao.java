package com.techelevator.dao;


import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {

     void createReview(Review review);

     Review getReviewById(Long reviewId);

     List<Review> displayAllReviews();

     void respondToReview(Review review);

     List<Review> getReviewByOfficeId(Long officeId);


}
