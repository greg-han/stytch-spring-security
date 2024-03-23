package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import com.stytch.java.consumer.api.oauth.OAuth;
import com.stytch.java.consumer.models.oauth.AuthenticateResponse;
import com.stytch.java.consumer.models.oauth.AuthenticateRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

@GregsAnnotationLol
public class StytchAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    protected StytchAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Autowired
    private StytchConfigProperties stytchConfigProperties;

    @Async
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("I'm trying!");
        String thing = request.getQueryString();
        StytchClient.configure("project-test-bb161e22-475d-4596-9d50-e4b98259f421","secret-test-OyXRTvgImdIJ-9jve6X3lpyWOD5S71yL8xo=");
        AuthenticateRequest authenticateRequest = new AuthenticateRequest("BME-EMcGnIkFbPwXV1ZocFsOZhgkPGruwpFGoxlW3ZEs");
        //CompletableFuture<StytchResult<AuthenticateResponse>> authenticateResponse = StytchClient.oauth.authenticateCompletable(authenticateRequest).get;
        //StytchResult<AuthenticateResponse> authenticateResponse = StytchClient.oauth.authenticate(authenticateRequest).get();

        System.out.println("I'm trying again!");
        return null;
    }


}