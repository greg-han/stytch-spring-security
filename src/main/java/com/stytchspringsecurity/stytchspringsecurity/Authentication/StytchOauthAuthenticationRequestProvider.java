package com.stytchspringsecurity.stytchspringsecurity.Authentication;

import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.oauth.AuthenticateRequest;
import com.stytch.java.consumer.models.oauth.AuthenticateResponse;
import com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig.SpringContext;
import com.stytchspringsecurity.stytchspringsecurity.SecurityConfig.StytchConfigProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class StytchOauthAuthenticationRequestProvider implements AuthenticationProvider {
    StytchConfigProperties stytchConfigProperties = SpringContext.getBean(StytchConfigProperties.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String projectid = stytchConfigProperties.getProjectid();
        String projectsecret = stytchConfigProperties.getprojectsecret();
        StytchClient.configure(projectid,projectsecret);
        AuthenticateRequest authenticateRequest = new AuthenticateRequest((String) authentication.getCredentials());
        StytchResult<AuthenticateResponse> authenticateResponse;
        try {
            //This part needs to be put in a custom Authentication Provider
            authenticateResponse = StytchClient.oauth.authenticateCompletable(authenticateRequest).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (authenticateResponse instanceof StytchResult.Error) {
            var exception = ((StytchResult.Error) authenticateResponse).getException();
            System.out.println(exception.getReason());
        } else {
            System.out.println(((StytchResult.Success<?>) authenticateResponse).getValue());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(StytchOauthAuthenticationRequestToken.class);
    }
}
