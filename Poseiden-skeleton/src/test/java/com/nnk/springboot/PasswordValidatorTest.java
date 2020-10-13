package com.nnk.springboot;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordValidatorTest {

    @Test
    public void ValidPasswordTest() {
        PasswordValidator passwordValidator = new PasswordValidator();
        String password = "Password1@";

        boolean valid = passwordValidator.validate(password);
        System.out.println("Password is valid : " + password + " , " + valid);
        Assert.assertTrue(valid);

    }

    @Test
    public void InValidPasswordTest() {

        PasswordValidator passwordValidator = new PasswordValidator();
        String password = "Password@";
        boolean valid = passwordValidator.validate(password);
        System.out.println("Password is valid : " + password + " , " + valid);
        Assert.assertFalse(valid);
    }

}
