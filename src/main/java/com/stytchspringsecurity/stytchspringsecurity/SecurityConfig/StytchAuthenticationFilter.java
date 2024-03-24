package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import com.stytch.java.consumer.api.oauth.OAuth;
import com.stytch.java.consumer.models.oauth.AuthenticateResponse;
import com.stytch.java.consumer.models.oauth.AuthenticateRequest;
import com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig.SpringContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.stytchspringsecurity.stytchspringsecurity.SecurityConfig.StytchConfigProperties;

@GregsAnnotationLol
@Configurable
public class StytchAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    protected StytchAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public static String getToken(String input) {
        // Find the index of "token="
        int tokenIndex = input.indexOf("token=");

        // If "token=" is not found or it's at the end of the string, return an empty string
        if (tokenIndex == -1 || tokenIndex + 6 >= input.length()) {
            return "";
        }

        // Extract the substring after "token="
        return input.substring(tokenIndex + 6);
    }
    StytchConfigProperties stytchConfigProperties = SpringContext.getBean(StytchConfigProperties.class);

    @Async
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("I'm trying!");
        String token = getToken(request.getQueryString());
        String projectid = stytchConfigProperties.getProjectid();
        String projectsecret = stytchConfigProperties.getprojectsecret();
        StytchClient.configure(projectid,projectsecret);
        AuthenticateRequest authenticateRequest = new AuthenticateRequest(token);
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
            System.out.println(((StytchResult.Success<?>) authenticateResponse).getValue());
        }
//        StytchResult<AuthenticateResponse> authenticateResponse = StytchClient.oauth.authenticate(authenticateRequest);

        System.out.println("I'm trying again!");
        return null;
    }


}