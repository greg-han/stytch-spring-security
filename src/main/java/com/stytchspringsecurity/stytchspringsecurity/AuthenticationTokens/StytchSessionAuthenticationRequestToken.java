package com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StytchSessionAuthenticationRequestToken extends AbstractAuthenticationToken {

    private final String stytchSessionToken;

    public StytchSessionAuthenticationRequestToken(String stytchSessionToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.stytchSessionToken = stytchSessionToken;
        setAuthenticated(false);

    }

    public StytchSessionAuthenticationRequestToken(String stytchSessionToken) {
        super(null);
        this.stytchSessionToken = stytchSessionToken;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return stytchSessionToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
