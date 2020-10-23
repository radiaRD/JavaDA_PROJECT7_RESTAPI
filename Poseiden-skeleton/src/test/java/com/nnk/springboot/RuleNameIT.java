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
@Transactional
public class RuleNameIT {

    private static int counter = 0;

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
        this.mockMvc.perform(get("/ruleName/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleName", Matchers.hasSize(0)));;
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void addFormTest() throws Exception {
        this.mockMvc.perform(get("/ruleName/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void validateTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/ruleName/list"));
        this.mockMvc.perform(get("/ruleName/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("ruleName", Matchers.hasSize(1)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void showUpdateFormListTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .contentType("text/html;charset=UTF-8"));
        this.mockMvc.perform(get("/ruleName/update/"+counter)).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
        .andExpect(model().attribute("ruleName", Matchers.hasProperty("id", is(counter))));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void updateTest() throws Exception {
        counter = counter + 1;
        mockMvc.perform(MockMvcRequestBuilders
                .post("/ruleName/update/1")
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "Sql")
                .param("sqlPart", "Sql Part")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/ruleName/list"));
        this.mockMvc.perform(get("/ruleName/update/"+counter)).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("sqlStr", is("Sql"))));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void deleteTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .contentType("text/html;charset=UTF-8"));
        this.mockMvc.perform(get("/ruleName/delete/"+counter)).andDo(print())
                .andExpect(redirectedUrl("/ruleName/list"));
        this.mockMvc.perform(get("/ruleName/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("ruleName", Matchers.hasSize(0)));
    }

}
