package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Logger logger = LogManager.getLogger(RatingRepository.class);


}
