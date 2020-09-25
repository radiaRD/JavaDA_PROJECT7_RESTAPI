package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public String home(Model model) {
        model.addAttribute("rating", ratingRepository.findAll());
        return "rating/list";
    }

    public void validate(Rating rating, Model model) {
        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
    }

    public void showUpdateForm(Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        rating.setOrderNumber(0);
        model.addAttribute("rating", rating);
    }


    public void updateRating(Integer id, Rating rating,
                             Model model) {
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
    }

    public void deleteRating(Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("rating", ratingRepository.findAll());
    }

}
