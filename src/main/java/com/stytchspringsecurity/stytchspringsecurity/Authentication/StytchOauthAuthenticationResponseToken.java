package com.stytchspringsecurity.stytchspringsecurity.Authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StytchOauthAuthenticationResponseToken extends AbstractAuthenticationToken{
        //consider encrypting this field.
        private final String userId;
        private final String sessionToken;
        public StytchOauthAuthenticationResponseToken(String userId, String sessionToken, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.sessionToken = sessionToken;
            this.userId = userId;
            setAuthenticated(true);
        }

        public StytchOauthAuthenticationResponseToken(String userId, String sessionToken) {
            super(null);
            this.userId = userId;
            this.sessionToken = sessionToken;
            setAuthenticated(true);
        }

    public StytchOauthAuthenticationResponseToken(Collection<? extends GrantedAuthority> authorities, String userId, String sessionToken) {
        super(authorities);
        this.userId = userId;
        this.sessionToken = sessionToken;
    }

        @Override
        public Object getCredentials() {
            return this.sessionToken;
        }

        @Override
        public Object getPrincipal() {
            return this.userId;
        }

}
