package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(get("/app/login")).andDo(print())
                .andExpect(view().name("login"));
    }


    @Test
   public void getAllUserArticles() throws Exception {
        this.mockMvc.perform(get("/app/secure/article-details")).andDo(print())
                .andExpect(view().name("user/list"));
    }

    @Test
    public void error() throws Exception {
        this.mockMvc.perform(get("/app/error")).andDo(print())
                .andExpect(view().name("403"));
    }

    @Test
    public void logout() throws Exception {
        this.mockMvc.perform(get("/app/logout")).andDo(print())
                .andExpect(view().name("logout"));
    }
}
