package com.nnk.springboot;

import com.nnk.springboot.config.CustomSuccessHandler;
import com.nnk.springboot.service.BidListService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomSuccessHandlerTests {
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

    @Test
    void unauthorizedAccessTest() throws Exception {
        this.mockMvc.perform(get("/user/list")).andDo(print())
                .andExpect(redirectedUrl("http://localhost/app/login"));
    }


    @Test
    public void onAuthenticationSuccessUserTest() throws Exception {
        MockHttpServletRequestBuilder securedResourceAccess = get("/user/list");
        MvcResult unauthenticatedResult = mockMvc.perform(securedResourceAccess)
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpSession session = (MockHttpSession) unauthenticatedResult.getRequest()
                .getSession();
        String loginUrl = unauthenticatedResult.getResponse()
                .getRedirectedUrl();
        mockMvc.perform(post(loginUrl).param("username", "user")
                .param("password", "Password@1")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("secure/article-details"))
                .andReturn();
        mockMvc.perform(securedResourceAccess.session(session))
                .andExpect(status().isOk());

    }

    @Test
    public void onAuthenticationSuccessAdminTest() throws Exception {
        MockHttpServletRequestBuilder securedResourceAccess = get("/user/list");
        MvcResult unauthenticatedResult = mockMvc.perform(securedResourceAccess)
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpSession session = (MockHttpSession) unauthenticatedResult.getRequest()
                .getSession();
        String loginUrl = unauthenticatedResult.getResponse()
                .getRedirectedUrl();
        mockMvc.perform(post(loginUrl).param("username", "admin")
                .param("password", "admin")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/home"))
                .andReturn();
        mockMvc.perform(securedResourceAccess.session(session))
                .andExpect(status().isOk());
    }

}
