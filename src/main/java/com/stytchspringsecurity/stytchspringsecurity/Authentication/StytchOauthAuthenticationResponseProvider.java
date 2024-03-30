package com.stytchspringsecurity.stytchspringsecurity.Authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class StytchOauthAuthenticationResponseProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(StytchOauthAuthenticationResponseToken.class);
    }
}
