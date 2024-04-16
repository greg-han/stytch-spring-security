package com.stytchspringsecurity.stytchspringsecurity.AuthenticationProviders;

import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.sessions.AuthenticateRequest;
import com.stytch.java.consumer.models.sessions.AuthenticateResponse;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchOauthAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchSessionAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchSessionAuthenticationResponseToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class StytchSessionAuthenticationRequestProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticateRequest authenticateRequest = new AuthenticateRequest((String) authentication.getCredentials());
        StytchSessionAuthenticationResponseToken stytchSessionAuthenticationResponseToken;
        String sessionToken;
        try {
            StytchResult<AuthenticateResponse> authenticateResponse = StytchClient.sessions.authenticateCompletable(authenticateRequest).get();
            if (authenticateResponse instanceof StytchResult.Error) {
                var exception = ((StytchResult.Error) authenticateResponse).getException();
                System.out.println(exception.getReason());
                stytchSessionAuthenticationResponseToken = new StytchSessionAuthenticationResponseToken(null, null, null);
                stytchSessionAuthenticationResponseToken.setAuthenticated(false);
                return stytchSessionAuthenticationResponseToken;
            } else {
                sessionToken = ((StytchResult.Success<AuthenticateResponse>) authenticateResponse).getValue().getSessionToken();
                System.out.println("Session");
                System.out.println(authenticateResponse);
                stytchSessionAuthenticationResponseToken = new StytchSessionAuthenticationResponseToken(sessionToken, null, null);
                return stytchSessionAuthenticationResponseToken;
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(StytchSessionAuthenticationRequestToken.class);
    }

}
