package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * RatingController includes CRUD methods for rating.
 */
@Controller
public class RatingController {
    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    RatingService ratingService;

    /**
     * find all rating.
     *
     * @return the list of rating.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        logger.info("Get rating List");
        ratingService.home(model);
        return "rating/list";
    }

    /**
     * Add new rating.
     *
     * @return rating/add URL.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("Get rating/add URL");
        return "rating/add";
    }

    /**
     * Validate addition of rating.
     *
     * @return rating/list URL if rating is validate.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Validate rate add and redirect URL rating/list");
            ratingService.validate(rating, model);
            return "redirect:/rating/list";
        }
        logger.info("rate add not validated, return URL rating/add");
        return "rating/add";
    }


    /**
     * Update the rating.
     *
     * @param id id of the rating.
     * @return rating/update URL.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get a rate with id :" + id);
        ratingService.showUpdateForm(id, model);
        return "rating/update";
    }

    /**
     * Validate update rating.
     *
     * @param id id of the rating.
     * @return rating/list URL if rating is validate.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Updated not validated, return to URL rating/update");
            return "rating/update";
        }
        logger.info("Validate rate update with id : " + id + " and redirect URL rating/list ");
        ratingService.updateRating(id, rating, model);
        return "redirect:/rating/list";
    }

    /**
     * Delete the rating.
     *
     * @param id id of rating.
     * @return rating/list URL.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Delete the rate with id : " + id + " and redirect URL rating/list");
        ratingService.deleteRating(id, model);
        return "redirect:/rating/list";
    }
}
