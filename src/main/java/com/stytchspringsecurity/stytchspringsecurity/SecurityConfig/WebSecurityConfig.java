package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters.StytchSessionAuthenticationFilter;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationProviders.StytchOauthAuthenticationRequestProvider;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters.StytchAuthenticationFilter;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationProviders.StytchSessionAuthenticationRequestProvider;
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
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.*;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

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
            StytchOauthAuthenticationRequestProvider stytchOauthRequestProvider, StytchSessionAuthenticationRequestProvider stytchSessionAuthenticationRequestProvider) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(stytchOauthRequestProvider);
        authenticationManagerBuilder.authenticationProvider(stytchSessionAuthenticationRequestProvider);
        authenticationManagerBuilder.authenticationEventPublisher(authenticationEventPublisher());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        http.securityContext( securityContext -> securityContext.requireExplicitSave(false));

        http.addFilterAfter(
                new StytchAuthenticationFilter("/authenticate", authManager), CorsFilter.class);
        http.addFilterBefore(
               new StytchSessionAuthenticationFilter("/", authManager), StytchAuthenticationFilter.class);
       // http.addFilterAfter(
        //        new StytchSessionFilter("/"), LogoutFilter.class);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/v1/bitbucketAuth").permitAll()
                .anyRequest().authenticated()
        );

        http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                //httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.logout(logout ->
                logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                        .addLogoutHandler(new CookieClearingLogoutHandler("userID","sessionToken","providerType"))

        );

        return http.build();

    }

}