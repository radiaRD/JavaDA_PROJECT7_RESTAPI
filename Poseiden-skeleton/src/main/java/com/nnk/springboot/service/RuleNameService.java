package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * RuleNameService includes CRUD methods for ruleName.
 */
@Service
public class RuleNameService {
    private static final Logger logger = LogManager.getLogger(RuleNameService.class);

    @Autowired
    RuleNameRepository ruleNameRepository;

    /**
     * find all ruleName with ruleNameRepository.findAll.
     *
     * @return the list of ruleName.
     */
    public String home(Model model) {
        logger.info("Get  rule Name List");
        model.addAttribute("ruleName", ruleNameRepository.findAll());
        return "ruleName/list";
    }

    /**
     * create a new ruleName with ruleNameRepository.save.
     */
    public void validate(RuleName ruleName, Model model) {
        logger.info("Save a new rule");
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());

    }

    /**
     * find the ruleName by id with ruleNameRepository.findById.
     */
    public void showUpdateForm(Integer id, Model model) {
        logger.info("Find a rule with id :" + id);
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);

    }

    /**
     * find and update the ruleName by id .
     */
    public void updateRuleName(Integer id, RuleName ruleName,
                               Model model) {
        logger.info("Update the rule with id :" + id);
        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());
    }

    /**
     * find and delete the ruleName by id .
     */
    public void deleteRuleName(Integer id, Model model) {
        logger.info("delete the rule with id :" + id);
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());
    }
}
