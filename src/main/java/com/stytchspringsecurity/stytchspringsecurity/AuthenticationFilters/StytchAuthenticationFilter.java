package com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters;

import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationHandlers.StytchAuthenticationSuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;
//
public class StytchAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private SecurityContextRepository securityContextRepository;
    //I am passing int he auth manager, just to set the authenticationSuccessHandler
    public StytchAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager){
        super(defaultFilterProcessesUrl, authenticationManager);
       // setAuthenticationSuccessHandler(new StytchAuthenticationSuccessHandler());
        //setSecurityContextRepository(super.s);
    }
    private AuthenticationManager authenticationManager;
    public static String getToken(String input) {
        int tokenIndex = input.indexOf("token=");
        if (tokenIndex == -1 || tokenIndex + 6 >= input.length()) {
            return "";
        }
        return input.substring(tokenIndex + 6);
    }

    /*
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        this.attemptAuthentication(request,response);
        // On success keep going on the chain
        this.setAuthenticationSuccessHandler((req, res, authentication) -> chain.doFilter(req, res));
        //this.setAuthenticationFailureHandler((req, res, exception) -> res.setStatus(HttpStatus.UNAUTHORIZED.value()));
        super.doFilter(request, response, chain);
    }
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = getToken(request.getQueryString());
        StytchOauthAuthenticationRequestToken stytchOauthAuthenticationToken = new StytchOauthAuthenticationRequestToken(token);
        //It may be better to delegate this to the authenticationManager
         StytchOauthAuthenticationResponseToken stytchOauthResponseToken = (StytchOauthAuthenticationResponseToken) this.getAuthenticationManager().authenticate(stytchOauthAuthenticationToken);
        //StytchOauthAuthenticationResponseToken stytchOauthResponseToken = (StytchOauthAuthenticationResponseToken) authManager.authenticate(stytchOauthAuthenticationToken);
        Cookie StytchUserCookie = new Cookie("userID",(String)stytchOauthResponseToken.getPrincipal());
        Cookie StytchSessionTokenCookie = new Cookie("sessionToken", (String)stytchOauthResponseToken.getCredentials());
        Cookie StytchProviderTypeCookie = new Cookie("providerType", (String)stytchOauthResponseToken.getDetails());

        //Consider making these cookies never disappear.
        StytchUserCookie.setMaxAge(600);
        StytchUserCookie.setPath("/");
        response.addCookie(StytchUserCookie);

        StytchSessionTokenCookie.setMaxAge(600);
        StytchSessionTokenCookie.setPath("/");
        response.addCookie(StytchSessionTokenCookie);

        StytchProviderTypeCookie.setMaxAge(600);
        StytchProviderTypeCookie.setPath("/");
        response.addCookie(StytchProviderTypeCookie);
        return stytchOauthResponseToken;
    }

}