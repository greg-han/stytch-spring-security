package com.stytchspringsecurity.stytchspringsecurity.MockStytchSecurityContextFactory;

import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockStytchOauthSecurityContextFactory implements WithSecurityContextFactory<WithMockStytchOauthResponseToken> {
    @Override
    public SecurityContext createSecurityContext(WithMockStytchOauthResponseToken customToken) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new StytchOauthAuthenticationResponseToken(customToken.userId(), customToken.sessionToken(), customToken.provider());
        context.setAuthentication(auth);
        return context;
    }
}
