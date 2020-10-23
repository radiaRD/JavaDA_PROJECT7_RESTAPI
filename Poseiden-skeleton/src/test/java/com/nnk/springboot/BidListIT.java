package com.nnk.springboot;


import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.hamcrest.Matchers;
import org.junit.Before;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BidListIT {

    private static int counter = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BidListService bidListService;

    @Autowired
    BidListRepository bidListRepository;

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
    public void homeBidListTest() throws Exception {
        this.mockMvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidList", Matchers.hasSize(0)));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    public void addBidFormTest() throws Exception {
        this.mockMvc.perform(get("/bidList/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    public void validateBidListTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/bidList/validate")
                .param("account", "Account Test1")
                .param("type", "Type test1")
                .param("bidQuantity", "20")
                .param("askQuantity", "15")
                .param("bid", "10")
                .param("ask", "11")
                .param("benchmark", "Benchmark")
                .param("bidListDate", "2020-10-03 21:04:40")
                .param("commentary", "Commentary")
                .param("serucity", "Serucity")
                .param("status", "Status")
                .param("trader", "Trader")
                .param("book", "Book")
                .param("creationName", "CreationName")
                .param("creationDate", "2020-02-10 21:10:40")
                .param("revisionName", "2020-10-03 21:30:40")
                .param("dealName", "DealName")
                .param("dealType", "DealType")
                .param("sourceListId", "SourceListId")
                .param("side", "Side")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/bidList/list"));
        this.mockMvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("bidList", Matchers.hasSize(1)));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    void showUpdateFormBidListTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/bidList/validate")
                .param("account", "Account Test1")
                .param("type", "Type test1")
                .param("bidQuantity", "20")
                .param("askQuantity", "15")
                .param("bid", "10")
                .param("ask", "11")
                .param("benchmark", "Benchmark")
                .param("bidListDate", "2020-10-03 21:04:40")
                .param("commentary", "Commentary")
                .param("serucity", "Serucity")
                .param("status", "Status")
                .param("trader", "Trader")
                .param("book", "Book")
                .param("creationName", "CreationName")
                .param("creationDate", "2020-02-10 21:10:40")
                .param("revisionName", "2020-10-03 21:30:40")
                .param("dealName", "DealName")
                .param("dealType", "DealType")
                .param("sourceListId", "SourceListId")
                .param("side", "Side")
                .contentType("text/html;charset=UTF-8"));
        this.mockMvc.perform(get("/bidList/update/" + counter)).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("bidListId", is(counter))));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    public void updateBidTest() throws Exception {
        counter = counter + 1;
        mockMvc.perform(MockMvcRequestBuilders
                .post("/bidList/update/1")
                .param("account", "Account Test1")
                .param("type", "Type test1")
                .param("bidQuantity", "20.0")
                .param("askQuantity", "15")
                .param("bid", "10")
                .param("ask", "11")
                .param("benchmark", "Benchmark")
                .param("bidListDate", "2020-10-03 21:04:40")
                .param("commentary", "Commentary")
                .param("serucity", "Serucity")
                .param("status", "Status")
                .param("trader", "Trader")
                .param("book", "Book")
                .param("creationName", "CreationName")
                .param("creationDate", "2020-02-10 21:10:40")
                .param("revisionName", "2020-10-03 21:30:40")
                .param("dealName", "DealName")
                .param("dealType", "DealType")
                .param("sourceListId", "SourceListId")
                .param("side", "Side")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/bidList/list"));
        this.mockMvc.perform(get("/bidList/update/" + counter)).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("bidList", Matchers.hasProperty("bidQuantity", is(20.0))));
    }

    @WithMockUser(value = "admin", password = "admin")
    @Test
    void deleteBidTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/bidList/validate")
                .param("account", "Account Test1")
                .param("type", "Type test1")
                .param("bidQuantity", "20")
                .param("askQuantity", "15")
                .param("bid", "10")
                .param("ask", "11")
                .param("benchmark", "Benchmark")
                .param("bidListDate", "2020-10-03 21:04:40")
                .param("commentary", "Commentary")
                .param("serucity", "Serucity")
                .param("status", "Status")
                .param("trader", "Trader")
                .param("book", "Book")
                .param("creationName", "CreationName")
                .param("creationDate", "2020-02-10 21:10:40")
                .param("revisionName", "2020-10-03 21:30:40")
                .param("dealName", "DealName")
                .param("dealType", "DealType")
                .param("sourceListId", "SourceListId")
                .param("side", "Side")
                .contentType("text/html;charset=UTF-8"));
        this.mockMvc.perform(get("/bidList/delete/" + counter)).andDo(print())
                .andExpect(redirectedUrl("/bidList/list"));
        this.mockMvc.perform(get("/bidList/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("bidList", Matchers.hasSize(0)));
    }

}
