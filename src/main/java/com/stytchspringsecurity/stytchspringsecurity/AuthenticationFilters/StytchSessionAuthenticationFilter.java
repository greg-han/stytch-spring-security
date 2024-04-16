package com.stytchspringsecurity.stytchspringsecurity.AuthenticationFilters;
import com.stytch.java.b2b.models.discoveryorganizations.CreateResponse;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationHandlers.StytchAuthenticationSuccessHandler;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchSessionAuthenticationRequestToken;
import com.stytchspringsecurity.stytchspringsecurity.AuthenticationTokens.StytchSessionAuthenticationResponseToken;
import com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods.StytchCookieProcessors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class StytchSessionAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

        private SecurityContextRepository securityContextRepository;
        //I am passing in the auth manager, just to set the authenticationSuccessHandler
        public StytchSessionAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager){
            super(defaultFilterProcessesUrl, authenticationManager);
            // setAuthenticationSuccessHandler(new StytchAuthenticationSuccessHandler());
        }
        private AuthenticationManager authenticationManager;
        StytchSessionAuthenticationResponseToken stytchSessionAuthenticationResponseToken;

    public String getSessionToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionToken")) {
                    String sessionToken = cookie.getValue();
                    return sessionToken;
                }
            }
        }
        return null;
    }
        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
            String sessionToken = getSessionToken(request);
            if(sessionToken != null) {
                // String sessionToken = stytchCookieProcessors.getSessionToken(request);
                StytchSessionAuthenticationRequestToken stytchSessionAuthenticationToken = new StytchSessionAuthenticationRequestToken(sessionToken);
                stytchSessionAuthenticationResponseToken = (StytchSessionAuthenticationResponseToken) this.getAuthenticationManager().authenticate(stytchSessionAuthenticationToken);
                Cookie StytchSessionTokenCookie = new Cookie("sessionToken", (String) stytchSessionAuthenticationResponseToken.getCredentials());
                //Consider making these cookies never disappear.
                if (!stytchSessionAuthenticationResponseToken.isAuthenticated()) {
                    //HttpSession session = request.getSession();
                    //session.invalidate();
                    Cookie stytchSessionTokenCookie = new Cookie("sessionToken", null);
                    stytchSessionTokenCookie.setMaxAge(0);
                    stytchSessionTokenCookie.setPath("/");
                    response.addCookie(stytchSessionTokenCookie);
                }
            }
            if(sessionToken == null){
                stytchSessionAuthenticationResponseToken = new StytchSessionAuthenticationResponseToken(null,null);
                stytchSessionAuthenticationResponseToken.setAuthenticated(false);
            }

            return stytchSessionAuthenticationResponseToken;
        }


}
