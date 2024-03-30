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
    String projectid = stytchConfigProperties.getProjectid();
    String projectsecret = stytchConfigProperties.getprojectsecret();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = null;
        StytchOauthAuthenticationResponseToken stytchOauthAuthenticationResponseToken = null;
        StytchClient.configure(projectid,projectsecret);
        AuthenticateRequest authenticateRequest = new AuthenticateRequest((String) authentication.getCredentials());
        StytchResult<AuthenticateResponse> authenticateResponse;
        try {
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
            //This might be a good place to log the UID of the request
            System.out.println(((StytchResult.Success<?>) authenticateResponse).getValue());
            userId = ((StytchResult.Success<AuthenticateResponse>) authenticateResponse).getValue().getUserId();
            stytchOauthAuthenticationResponseToken= new StytchOauthAuthenticationResponseToken(userId);
            return stytchOauthAuthenticationResponseToken;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(StytchOauthAuthenticationRequestToken.class);
    }
}
