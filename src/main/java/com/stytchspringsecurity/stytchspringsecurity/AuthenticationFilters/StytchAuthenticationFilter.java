package com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters;

import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchOauthAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchOauthAuthenticationResponseToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;
public class StytchAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private SecurityContextRepository securityContextRepository;
    //I am passing in the auth manager, just to set the authenticationSuccessHandler
    public StytchAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager){
        super(defaultFilterProcessesUrl, authenticationManager);
       // setAuthenticationSuccessHandler(new StytchAuthenticationSuccessHandler());
    }
    private AuthenticationManager authenticationManager;

    //I'm thinking about replacing this with a regex.
    public static String getToken(String input) {
        int tokenIndex = input.indexOf("token=");
        if (tokenIndex == -1 || tokenIndex + 6 >= input.length()) {
            return "";
        }
        return input.substring(tokenIndex + 6);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = getToken(request.getQueryString());
        StytchOauthAuthenticationRequestToken stytchOauthAuthenticationToken = new StytchOauthAuthenticationRequestToken(token);
        StytchOauthAuthenticationResponseToken stytchOauthResponseToken = (StytchOauthAuthenticationResponseToken) this.getAuthenticationManager().authenticate(stytchOauthAuthenticationToken);
        Cookie StytchUserCookie = new Cookie("userID",(String)stytchOauthResponseToken.getPrincipal());
        Cookie StytchSessionTokenCookie = new Cookie("sessionToken", (String)stytchOauthResponseToken.getCredentials());
        Cookie StytchProviderTypeCookie = new Cookie("providerType", (String)stytchOauthResponseToken.getDetails());

        //Consider making these cookies never disappear.
        StytchUserCookie.setMaxAge(Integer.MAX_VALUE);
        StytchUserCookie.setPath("/");
        response.addCookie(StytchUserCookie);

        StytchSessionTokenCookie.setMaxAge(Integer.MAX_VALUE);
        StytchSessionTokenCookie.setPath("/");
        response.addCookie(StytchSessionTokenCookie);

        StytchProviderTypeCookie.setMaxAge(Integer.MAX_VALUE);
        StytchProviderTypeCookie.setPath("/");
        response.addCookie(StytchProviderTypeCookie);

        return stytchOauthResponseToken;
    }

}