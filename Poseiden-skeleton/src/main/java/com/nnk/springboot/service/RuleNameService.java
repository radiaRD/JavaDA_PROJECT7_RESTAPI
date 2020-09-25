package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepository;

    public String home(Model model) {
        model.addAttribute("ruleName", ruleNameRepository.findAll());
        return "ruleName/list";
    }

    public void validate(RuleName ruleName, Model model) {
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());

    }

    public void showUpdateForm(Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleName.setName(ruleName.getName());
        ruleName.setDescription(ruleName.getDescription());
        ruleName.setJson("");
        ruleName.setTemplate("");
        ruleName.setSqlStr("");
        ruleName.setSqlPart("");
        model.addAttribute("ruleName", ruleName);

    }

    public void updateRuleName(Integer id, RuleName ruleName,
                               Model model) {
        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());
    }

    public void deleteRuleName(Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());
    }
}
