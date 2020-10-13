package com.nnk.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PasswordValidator includes the method of validating password with regular expression.
 */
public class PasswordValidator {
    private static final Logger logger = LogManager.getLogger(PasswordValidator.class);
    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password) {
        logger.info("Validate password : "+ matcher);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
