package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import com.stytch.java.consumer.models.oauth.AuthenticateResponse;
import com.stytch.java.consumer.models.oauth.AuthenticateRequest;
import com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig.SpringContext;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationRequestToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@GregsAnnotationLol
@Configurable
public class StytchAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    StytchAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager){
        super(defaultFilterProcessesUrl, authenticationManager);
    }
    private AuthenticationManager authenticationManager;
    public static String getToken(String input) {
        int tokenIndex = input.indexOf("token=");
        if (tokenIndex == -1 || tokenIndex + 6 >= input.length()) {
            return "";
        }
        return input.substring(tokenIndex + 6);
    }
    StytchConfigProperties stytchConfigProperties = SpringContext.getBean(StytchConfigProperties.class);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("I'm trying!");
        String token = getToken(request.getQueryString());
        StytchOauthAuthenticationRequestToken stytchOauthAuthenticationToken = new StytchOauthAuthenticationRequestToken(token);

        System.out.println("I'm trying again!");
        return this.getAuthenticationManager().authenticate(stytchOauthAuthenticationToken);
    }


}