package com.stytchspringsecurity.stytchspringsecurity.AuthenticationMechanismUnitTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthenticationMechanismTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @Test
    public void testAuthenticationProviderSupport() throws Exception {
        when(authenticationProvider.supports(StytchOauthAuthenticationRequestToken.class))
                .thenReturn(true);
        when(authenticationProvider.supports(StytchOauthAuthenticationResponseToken.class))
                .thenReturn(true);
    }
    @Test
    public void testAuthenticationSuccess() throws AuthenticationException {
                when(authenticationManager.authenticate(new StytchOauthAuthenticationRequestToken("abc123")))
                .thenReturn(new StytchOauthAuthenticationResponseToken("User123","abc123","provider"));

        Authentication authentication = authenticationManager.authenticate(new StytchOauthAuthenticationRequestToken("abc123"));

        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());
    }

}
