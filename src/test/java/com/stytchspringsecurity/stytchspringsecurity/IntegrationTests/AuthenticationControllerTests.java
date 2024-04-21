package com.stytchspringsecurity.stytchspringsecurity.IntegrationTests;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class AuthenticationControllerTests {

        MockMvc mockMvc;

        @BeforeEach
        void setup(WebApplicationContext wac) {
                this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
                Cookie tokenCookie = new Cookie("sessionToken", "123abc");
        }

        @Test
        void bitbucketAuthController() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/bitbucketAuth"))
                        .andExpect(status().is3xxRedirection());
        }




}
