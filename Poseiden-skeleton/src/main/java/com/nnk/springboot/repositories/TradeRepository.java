package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
    Logger logger = LogManager.getLogger(TradeRepository.class);

}
