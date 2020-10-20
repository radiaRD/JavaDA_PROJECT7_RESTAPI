package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * LoginController includes user login and redirect methods if authentication is successful or not.
 */
@Controller
@RequestMapping("app")
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    UserRepository userRepository;

    /**
     * User login.
     *
     * @return login URL.
     */
    @GetMapping("login")
    public ModelAndView login() {
        logger.info("Get URL login");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Authentication is successful.
     *
     * @return secure/article-details URL.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        logger.info("Redirect after success authentication to URL app/secure/article-details ");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Authentication is unsuccessful.
     *
     * @return error URL.
     */
    @GetMapping("error")
    public ModelAndView error() {
        logger.info("Redirect after unsuccessful authentication to URL app/error");
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    /**
     * User log out.
     *
     * @return logout URL.
     */
    @GetMapping("logout")
    public ModelAndView logout() {
        logger.info("User log out");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("logout");
        return mav;
    }

}
