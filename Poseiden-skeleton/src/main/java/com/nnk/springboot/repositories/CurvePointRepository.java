package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    Logger logger = LogManager.getLogger(CurvePointRepository.class);

}
