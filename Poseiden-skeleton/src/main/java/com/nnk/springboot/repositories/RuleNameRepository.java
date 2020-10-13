package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
    Logger logger = LogManager.getLogger(RuleNameRepository.class);

}
