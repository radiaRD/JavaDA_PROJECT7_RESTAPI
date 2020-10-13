package com.nnk.springboot;

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
public class RatingIT {
    @Autowired
    private MockMvc mockMvc;

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
        this.mockMvc.perform(get("/rating/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("rating", Matchers.hasSize(1)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void addFormTest() throws Exception {
        this.mockMvc.perform(get("/rating/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    @Transactional
    public void validateTest() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "20")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/rating/list"));
        this.mockMvc.perform(get("/rating/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("rating", Matchers.hasSize(2)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void showUpdateFormListTest() throws Exception {
        this.mockMvc.perform(get("/rating/update/22")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", Matchers.hasProperty("orderNumber", is(22))));;
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void updateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/rating/update/22")
                .param("moodysRating", "MoodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "22")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/rating/list"));
        this.mockMvc.perform(get("/rating/update/22")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("rating", Matchers.hasProperty("moodysRating", is("MoodysRating"))));

    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    @Transactional
    void deleteTest() throws Exception {
        this.mockMvc.perform(get("/rating/delete/22")).andDo(print())
                .andExpect(redirectedUrl("/rating/list"));
        this.mockMvc.perform(get("/rating/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("rating", Matchers.hasSize(0)));
    }
}
