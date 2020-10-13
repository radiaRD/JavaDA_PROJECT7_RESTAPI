package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.transaction.Transactional;


import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@SpringBootTest
@AutoConfigureMockMvc
public class UserIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService userService;


    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void homeTest() throws Exception {
        this.mockMvc.perform(get("/user/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", Matchers.hasSize(2)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void addFormTest() throws Exception {
        this.mockMvc.perform(get("/user/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }


    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    @Transactional
    public void validateTest() throws Exception {
        User user = new User("username test", "Password@1", "fullnam", "user");
        this.mockMvc.perform(post("/user/validate")
                .param("username", "username test")
                .param("password", "Password@1")
                .param("fullname", "fullnam")
                .param("role", "USER")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/user/list"));
        this.mockMvc.perform(get("/user/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("users", Matchers.hasSize(3)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void showUpdateFormListTest() throws Exception {
        this.mockMvc.perform(get("/user/update/89")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", Matchers.hasProperty("username", is("user"))));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void updateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/update/89")
                .param("username", "user")
                .param("password", "Password@1")
                .param("fullname", "fullname")
                .param("role", "USER")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/user/list"));
        this.mockMvc.perform(get("/user/update/89")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("user", Matchers.hasProperty("fullname", is("fullname"))));

    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    @Transactional
    void deleteTest() throws Exception {
        this.mockMvc.perform(get("/user/delete/89")).andDo(print())
                .andExpect(redirectedUrl("/user/list"));
        this.mockMvc.perform(get("/user/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("users", Matchers.hasSize(1)));
    }
}
