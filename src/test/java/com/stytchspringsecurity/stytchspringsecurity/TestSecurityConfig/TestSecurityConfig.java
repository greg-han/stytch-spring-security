package com.stytchspringsecurity.stytchspringsecurity.TestSecurityConfig;

import com.stytchspringsecurity.stytchspringsecurity.MockTestClasses.MockStytchSessionFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationRequestProvider;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters.StytchAuthenticationFilter;
import com.stytchspringsecurity.stytchspringsecurity.LogoutHandlers.StytchLogoutHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.filter.CorsFilter;

@TestConfiguration
@EnableWebSecurity
@ActiveProfiles("test")
public class TestSecurityConfig {

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            StytchOauthAuthenticationRequestProvider stytchOauthRequestProvider) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(stytchOauthRequestProvider);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        http.securityContext(securityContext -> securityContext.requireExplicitSave(false));

        http.addFilterAfter(
                new StytchAuthenticationFilter("/authenticate", authManager), CorsFilter.class);
        http.addFilterAfter(
                new MockStytchSessionFilter("/"), LogoutFilter.class);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/v1/bitbucketAuth").permitAll()
                .anyRequest().authenticated()
        );

        http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.logout(logout ->
                logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                        .addLogoutHandler(new StytchLogoutHandler())
        );

        return http.build();

    }
}
