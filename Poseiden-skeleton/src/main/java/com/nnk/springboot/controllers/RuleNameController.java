package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
 * RuleNameController includes CRUD methods for rule.
 */
@Controller
public class RuleNameController {
    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    RuleNameService ruleNameService;

    /**
     * find all rule names.
     *
     * @return the list of rule names.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        logger.info("Get rule List");
        ruleNameService.home(model);
        return "ruleName/list";
    }

    /**
     * Add new rule name.
     *
     * @return ruleName/add URL.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        logger.info("Get ruleName/add URL");
        return "ruleName/add";
    }

    /**
     * Validate addition of rule name.
     *
     * @return ruleName/list URL if rule name is validate.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Validate rule add and redirect URL ruleName/list");
            ruleNameService.validate(ruleName, model);
            return "redirect:/ruleName/list";
        }
        logger.info("rule add not validated, return URL ruleName/add");
        return "ruleName/add";
    }

    /**
     * Update the rule name.
     *
     * @param id id of the rule name.
     * @return ruleName/update URL.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get a rule with id :" + id);
        ruleNameService.showUpdateForm(id, model);
        return "ruleName/update";
    }

    /**
     * Validate update rule name.
     *
     * @param id id of the rule name.
     * @return ruleName/list URL if rule name is validate.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Updated not validated, return to URL ruleName/update");
            return "ruleName/update";
        }
        logger.info("Validate rule update with id : " + id + " and redirect URL ruleName/list ");
        ruleNameService.updateRuleName(id, ruleName, model);
        return "redirect:/ruleName/list";
    }

    /**
     * Delete the rule name.
     *
     * @param id id of rule name.
     * @return ruleName/list URL.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Delete the rule with id : " + id + " and redirect URL ruleName/list");
        ruleNameService.deleteRuleName(id, model);
        return "redirect:/ruleName/list";
    }
}
