package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationRequestProvider;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters.StytchAuthenticationFilter;
import com.stytchspringsecurity.stytchspringsecurity.SessionFilters.StytchSessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            StytchOauthAuthenticationRequestProvider stytchOauthRequestProvider) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(stytchOauthRequestProvider);
        authenticationManagerBuilder.authenticationEventPublisher(authenticationEventPublisher());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        http.addFilterBefore(
                new StytchSessionFilter("blank"), BasicAuthenticationFilter.class);
        http.addFilterBefore(
                new StytchAuthenticationFilter("/authenticate", authManager), UsernamePasswordAuthenticationFilter.class);
        //This allows stytch to handle the session entirely.
        http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER));

        return http.build();

    }
}