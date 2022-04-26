package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Review;
import com.techelevator.model.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin
public class ReviewController {

    private ReviewDao reviewDao;
    private UserDao userDao;


    public ReviewController(ReviewDao reviewDao, UserDao userDao) {

        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @RequestMapping(path= "/reviews", method=RequestMethod.GET)
    public List<Review> getAllReviews(){
        return reviewDao.displayAllReviews();
    }

    @RequestMapping(path= "/reviews/{reviewId}", method=RequestMethod.GET)
    public Review getReviewById(@PathVariable Long reviewId) {
       return reviewDao.getReviewById(reviewId);
    }

    @RequestMapping(path = "/reviews", method=RequestMethod.POST)
    public void createReview(@RequestBody Review review){
        reviewDao.createReview(review);
    }

    @RequestMapping(path= "/reviews/{reviewId}", method=RequestMethod.PUT)
    public void respondToReview(@RequestBody Review review){
        reviewDao.respondToReview(review);
    }

    @RequestMapping(path="/office/{officeId}/reviews", method = RequestMethod.GET)
    public List<Review> getReviewByOfficeId(@PathVariable Long officeId){
        return reviewDao.getReviewByOfficeId(officeId);
    }
}
