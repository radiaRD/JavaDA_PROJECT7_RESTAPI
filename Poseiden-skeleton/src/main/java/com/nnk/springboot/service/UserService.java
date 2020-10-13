package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.UserDetail;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


/**
 * UserService includes CRUD and user authentication methods.
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    DaoAuthenticationProvider authProvider;

    /**
     * find all users with userRepository.findAll.
     *
     * @return the list of users.
     */
    public String home(Model model) {
        logger.info("Get users List");
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * encrypt the password with BCryptPasswordEncoder.
     * create a new user with userRepository.save.
     */
    public void validate(User user, Model model) {
        logger.info("Save a new user");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
    }

    /**
     * find the user by id with userRepository.findById.
     */
    public void showUpdateForm(Integer id, Model model) {
        logger.info("Find a user with id :" + id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
    }

    /**
     * find and update the user by id .
     */
    public void updateUser(Integer id, User user, Model model) {
        logger.info("Update the user with id :" + id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
    }

    /**
     * find and delete the user by id .
     */
    public void deleteUser(Integer id, Model model) {
        logger.info("Delete the user with id :" + id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());

    }

    /**
     * user authentication with Spring Security UserDetailsService .
     *
     * @param username find user by username.
     * @return object UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Get the user by username: "+ username);
        User user = userRepository.findByUsername(username);
        UserDetail userDetail = new UserDetail(user);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
       return new UserDetail(user);}


}
