package com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StytchSessionAuthenticationResponseToken extends AbstractAuthenticationToken {
    private final String stytchSessionToken;
    private final String userId;

    public StytchSessionAuthenticationResponseToken(String stytchSessionToken, String userId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.stytchSessionToken = stytchSessionToken;
        this.userId = userId;
        setAuthenticated(true);
    }

    public StytchSessionAuthenticationResponseToken(String stytchSessionToken, String userId) {
        super(null);
        this.stytchSessionToken = stytchSessionToken;
        this.userId = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return stytchSessionToken;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
