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
public class TradeIT {

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
        this.mockMvc.perform(get("/trade/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
         .andExpect(model().attribute("trade", Matchers.hasSize(0)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void addFormTest() throws Exception {
        this.mockMvc.perform(get("/trade/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void validateTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/trade/validate")
                .param("account", "Trade Account Update")
                .param("type", "Type")
                .param("buyQuantity", "10.0")
                .param("sellQuantity","20.0")
                .param("buyPrice","15.0")
                .param("sellPrice","16d")
                .param("benchmark","Benchmark")
                .param("tradeDate","2020-10-03 21:04:40")
                .param("security","Security")
                .param("status","Status")
                .param("trader","Trader")
                .param("book","Book")
                .param("creationName","CreationName")
                .param( "creationDate","2020-10-03 21:04:40")
                .param("revisionName","RevisionName")
                .param("revisionDate","2020-10-03 21:10:40")
                .param("dealName","DealName")
                .param("dealType","DealType")
                .param("sourceListId","SourceListId")
                .param("side","Side")

                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/trade/list"));
        this.mockMvc.perform(get("/trade/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("trade", Matchers.hasSize(1)));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void showUpdateFormListTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/trade/validate")
                .param("account", "Trade Account Update")
                .param("type", "Type")
                .param("buyQuantity", "10.0")
                .param("sellQuantity","20.0")
                .param("buyPrice","15.0")
                .param("sellPrice","16d")
                .param("benchmark","Benchmark")
                .param("tradeDate","2020-10-03 21:04:40")
                .param("security","Security")
                .param("status","Status")
                .param("trader","Trader")
                .param("book","Book")
                .param("creationName","CreationName")
                .param( "creationDate","2020-10-03 21:04:40")
                .param("revisionName","RevisionName")
                .param("revisionDate","2020-10-03 21:10:40")
                .param("dealName","DealName")
                .param("dealType","DealType")
                .param("sourceListId","SourceListId")
                .param("side","Side")

                .contentType("text/html;charset=UTF-8"));
        this.mockMvc.perform(get("/trade/update/"+counter)).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", Matchers.hasProperty("tradeId", is(counter))));
    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    public void updateTest() throws Exception {
        counter = counter + 1;
        mockMvc.perform(MockMvcRequestBuilders
                .post("/trade/update/1")
                .param("account", "Trade Account Update")
                .param("type", "type")
                .param("buyQuantity", "15.0")
                .param("sellQuantity","20.0")
                .param("buyPrice","15.0")
                .param("sellPrice","16d")
                .param("benchmark","Benchmark")
                .param("tradeDate","2020-10-03 21:04:40")
                .param("security","Security")
                .param("status","Status")
                .param("trader","Trader")
                .param("book","Book")
                .param("creationName","CreationName")
                .param( "creationDate","2020-10-03 21:04:40")
                .param("revisionName","RevisionName")
                .param("revisionDate","2020-10-03 21:10:40")
                .param("dealName","DealName")
                .param("dealType","DealType")
                .param("sourceListId","SourceListId")
                .param("side","Side")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(redirectedUrl("/trade/list"));
        this.mockMvc.perform(get("/trade/update/"+counter)).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("trade", Matchers.hasProperty("type", is("type"))));

    }

    @WithMockUser(value = "user1", password = "Password1@")
    @Test
    void deleteTest() throws Exception {
        counter = counter + 1;
        this.mockMvc.perform(post("/trade/validate")
                .param("account", "Trade Account Update")
                .param("type", "Type")
                .param("buyQuantity", "10.0")
                .param("sellQuantity","20.0")
                .param("buyPrice","15.0")
                .param("sellPrice","16d")
                .param("benchmark","Benchmark")
                .param("tradeDate","2020-10-03 21:04:40")
                .param("security","Security")
                .param("status","Status")
                .param("trader","Trader")
                .param("book","Book")
                .param("creationName","CreationName")
                .param( "creationDate","2020-10-03 21:04:40")
                .param("revisionName","RevisionName")
                .param("revisionDate","2020-10-03 21:10:40")
                .param("dealName","DealName")
                .param("dealType","DealType")
                .param("sourceListId","SourceListId")
                .param("side","Side")

                .contentType("text/html;charset=UTF-8"));
        this.mockMvc.perform(get("/trade/delete/"+counter)).andDo(print())
                .andExpect(redirectedUrl("/trade/list"));
        this.mockMvc.perform(get("/trade/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(model().attribute("trade", Matchers.hasSize(0)));
    }

}
