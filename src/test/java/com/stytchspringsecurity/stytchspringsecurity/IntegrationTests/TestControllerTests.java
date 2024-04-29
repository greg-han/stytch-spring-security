package com.stytchspringsecurity.stytchspringsecurity.IntegrationTests;

import com.stytchspringsecurity.stytchspringsecurity.MockStytchSecurityContextFactory.WithMockStytchOauthResponseToken;
import com.stytchspringsecurity.stytchspringsecurity.TestSecurityConfig.TestSecurityConfig;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class TestControllerTests {
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
    public void authenticatedEndpointTest() throws Exception {
        mockMvc.perform(get("/v1/test")
                        .cookie(new Cookie("sessionToken","abc123")))
                .andExpect(status().isOk());
    }

}
