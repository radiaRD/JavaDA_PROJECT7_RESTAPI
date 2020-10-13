package com.nnk.springboot.controllers;

import com.nnk.springboot.PasswordValidator;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
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
 * UserController includes CRUD methods for user.
 */
@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * find all users.
     *
     * @return the list of users.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        logger.info("Get users List");
        userService.home(model);
        return "user/list";
    }

    /**
     * Add new user.
     *
     * @return user/add URL.
     */
    @GetMapping("/user/add")
    public String addUser(User bid) {
        logger.info("Get user/add URL");
        return "user/add";
    }

    /**
     * Validate addition of a user.
     * Validate the password.
     *
     * @return user/list URL if password and user are validate.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {

            PasswordValidator passwordValidator = new PasswordValidator();
            if (!passwordValidator.validate(user.getPassword())) {
                logger.info("Password not validated, return URL user/add");
                return "user/add";
            } else {
                logger.info("Validate user add and redirect URL user/list");
                userService.validate(user, model);
                return "redirect:/user/list";
            }
        }
        logger.info("user add not validated, return URL user/add");
        return "user/add";
    }

    /**
     * Update the user.
     *
     * @param id id of the user.
     * @return user/update URL.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get a user with id :" + id);
        userService.showUpdateForm(id, model);
        return "user/update";
    }

    /**
     * Validate update user.
     * Validate the password.
     *
     * @param id id of the user.
     * @return user/list URL if password and user are validate.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Updated not validated, return to URL user/update");
            return "user/update";
        }
        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.validate(user.getPassword())) {
            logger.info("Password not validated, return to URL user/update");
            return "user/update";
        } else {
            logger.info("Validate user update with id : " + id + " and redirect URL user/list ");
            userService.updateUser(id, user, model);
            return "redirect:/user/list";
        }
    }

    /**
     * Delete the user.
     *
     * @param id id of the user.
     * @return user/list URL.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.info("Delete the user with id : " + id + " and redirect URL user/list");
        userService.deleteUser(id, model);
        return "redirect:/user/list";
    }
}
