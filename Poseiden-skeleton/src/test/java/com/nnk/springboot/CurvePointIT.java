package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
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

import java.sql.Timestamp;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointIT {

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

    @WithMockUser(value = "admin", password = "admin")
    @Test
    void homeTest() throws Exception {
        this.mockMvc.perform(get("/curvePoint/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoint", Matchers.hasSize(1)));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    public void addFormTest() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    @Transactional
    public void validateTest() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")

                .param("curveId", "21")
                .param("asOfDate","2020-10-03 21:04:40")
                .param("term", "20.0")
                .param("value", "20.0")
                .param("creationDate","2020-10-03 21:04:40")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/curvePoint/list"));
        this.mockMvc.perform(get("/curvePoint/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("curvePoint", Matchers.hasSize(2)));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    void showUpdateFormListTest() throws Exception {
        this.mockMvc.perform(get("/curvePoint/update/20")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("curveId", is(20))));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    public void updateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/curvePoint/update/20")
                .param("curveId", "20")
                .param("asOfDate","2020-10-03 21:04:40")
                .param("term", "20.1")
                .param("value", "30.0")
                .param("creationDate","2020-10-03 21:04:40")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/curvePoint/list"));
        this.mockMvc.perform(get("/curvePoint/update/20")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("term", is(20.1))));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    @Transactional
    void deleteTest() throws Exception {
        this.mockMvc.perform(get("/curvePoint/delete/20")).andDo(print())
                .andExpect(redirectedUrl("/curvePoint/list"));
        this.mockMvc.perform(get("/curvePoint/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("curvePoint", Matchers.hasSize(0)));
    }
}
