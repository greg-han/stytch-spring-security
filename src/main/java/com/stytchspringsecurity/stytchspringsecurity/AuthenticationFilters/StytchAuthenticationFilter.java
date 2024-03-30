package com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters;

import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
public class StytchAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public StytchAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager){
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
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = getToken(request.getQueryString());
        StytchOauthAuthenticationRequestToken stytchOauthAuthenticationToken = new StytchOauthAuthenticationRequestToken(token);
        //It may be better to delegate this to the authenticationManager
        StytchOauthAuthenticationResponseToken stytchOauthResponseToken = (StytchOauthAuthenticationResponseToken) this.getAuthenticationManager().authenticate(stytchOauthAuthenticationToken);
        return stytchOauthResponseToken;
    }


}