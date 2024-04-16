package com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StytchOauthAuthenticationResponseToken extends AbstractAuthenticationToken{
        //consider encrypting this field.
        private final String userId;
        private final String sessionToken;
        private final String providerType;
        public StytchOauthAuthenticationResponseToken(String userId, String sessionToken, Collection<? extends GrantedAuthority> authorities, String provider) {
            super(authorities);
            this.sessionToken = sessionToken;
            this.userId = userId;
            this.providerType = provider;
            setAuthenticated(true);
        }

        public StytchOauthAuthenticationResponseToken(String userId, String sessionToken, String provider) {
            super(null);
            this.userId = userId;
            this.sessionToken = sessionToken;
            this.providerType = provider;
            setAuthenticated(true);
        }

    public StytchOauthAuthenticationResponseToken(Collection<? extends GrantedAuthority> authorities, String userId, String sessionToken, String provider) {
        super(authorities);
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.providerType = provider;
    }

        @Override
        public Object getCredentials() {
            return this.sessionToken;
        }

        @Override
        public Object getPrincipal() {
            return this.userId;
        }

        @Override
        public Object getDetails(){
            return this.providerType;
        }

}
