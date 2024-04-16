package com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StytchOauthAuthenticationRequestToken extends AbstractAuthenticationToken {
    private final String stytchOauthToken;
    public StytchOauthAuthenticationRequestToken(String stytchOauthToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.stytchOauthToken = stytchOauthToken;
        setAuthenticated(false);
    }

    public StytchOauthAuthenticationRequestToken(String stytchOauthToken) {
        super(null);
        this.stytchOauthToken = stytchOauthToken;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return stytchOauthToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
