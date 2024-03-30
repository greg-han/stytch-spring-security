package com.stytchspringsecurity.stytchspringsecurity.Authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StytchOauthAuthenticationResponseToken extends AbstractAuthenticationToken{
        private final String userId;
        public StytchOauthAuthenticationResponseToken(String userId, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.userId = userId;
            setAuthenticated(false);
        }

        public StytchOauthAuthenticationResponseToken(String userId) {
            super(null);
            this.userId = userId;
            setAuthenticated(false);
        }

    public StytchOauthAuthenticationResponseToken(Collection<? extends GrantedAuthority> authorities, String userId) {
        super(authorities);
        this.userId = userId;
    }

    @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return this.userId;
        }

}
