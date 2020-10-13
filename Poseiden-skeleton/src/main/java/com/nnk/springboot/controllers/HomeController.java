package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController includes application home and admin home.
 */
@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger(HomeController.class);

    /**
     * Application home.
     *
     * @return home.
     */
    @RequestMapping("/")
    public String home(Model model) {
        logger.info("Get URL home");
        return "home";
    }

    /**
     * Admin home.
     *
     * @return admin/home URL.
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        logger.info("Get URL admin/home");
        return "redirect:/bidList/list";
    }


}
