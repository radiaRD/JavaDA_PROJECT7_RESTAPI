package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * RatingService includes CRUD methods for rating.
 */
@Service
public class RatingService {
    private static final Logger logger = LogManager.getLogger(RatingService.class);
    @Autowired
    RatingRepository ratingRepository;

    /**
     * find all rating with ratingRepository.findAll.
     *
     * @return the list of rating.
     */
    public String home(Model model) {
        logger.info("Get  rating List");
        model.addAttribute("rating", ratingRepository.findAll());
        return "rating/list";
    }

    /**
     * create a new rating with ratingRepository.save.
     */
    public void validate(Rating rating, Model model) {
        logger.info("Save a new rate");
        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
    }

    /**
     * find the rating by id with ratingRepository.findById.
     */
    public void showUpdateForm(Integer id, Model model) {
        logger.info("Find a rate with id :" + id);
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
    }

    /**
     * find and update the rating by id .
     */
    public void updateRating(Integer id, Rating rating,
                             Model model) {
        logger.info("Update the rate with id :" + id);
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
    }

    /**
     * find and delete the rating by id .
     */
    public void deleteRating(Integer id, Model model) {
        logger.info("delete the rate with id :" + id);
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("rating", ratingRepository.findAll());
    }

}
