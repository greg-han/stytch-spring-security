package com.stytchspringsecurity.stytchspringsecurity.AuthenticationHandlers;

import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StytchAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println("Whoah someones in here!");
        System.out.println(authentication.getCredentials());
        //Consider using a separate cookie for name : value schema instead.
        Cookie StytchUserCookie = new Cookie("userID",(String)authentication.getPrincipal());
        Cookie StytchSessionTokenCookie = new Cookie("sessionToken", (String)authentication.getCredentials());
        //Configure this to be configureable, and to match with the stytch session timeout.
        //You may want to authenticate the token with Stytch.
        StytchUserCookie.setMaxAge(7200);
        StytchUserCookie.setPath("/");
        StytchSessionTokenCookie.setMaxAge(7200);
        StytchSessionTokenCookie.setPath("/");
        response.addCookie(StytchUserCookie);
        response.addCookie(StytchSessionTokenCookie);
        response.sendRedirect("/");
    }
}