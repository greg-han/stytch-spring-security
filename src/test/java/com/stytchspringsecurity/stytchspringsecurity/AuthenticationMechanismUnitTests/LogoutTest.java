package com.stytchspringsecurity.stytchspringsecurity.AuthenticationMechanismUnitTests;

import com.stytchspringsecurity.stytchspringsecurity.MockStytchSecurityContextFactory.WithMockStytchOauthResponseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
@SpringBootTest
@ActiveProfiles("test")
public class LogoutTest {

        private MockMvc mockMvc;
        @BeforeEach
        public void setup(WebApplicationContext wac) {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            mockMvc = MockMvcBuilders
                    .webAppContextSetup(wac)
                    .apply(springSecurity())
                    .build();
        }
        @Test
        @WithMockStytchOauthResponseToken
        public void logoutTest() throws Exception {
             mockMvc.perform(logout());
        }

}
