package com.stytchspringsecurity.stytchspringsecurity.AuthenticationHandlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class StytchAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//public class StytchAuthenticationSuccessHandler{

//@Value("${stytch.session.duration}")
    int duration = 600;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        //securityContextRepository.saveContext(securityContext, httpServletRequest, httpServletResponse);
        System.out.println(securityContext);
        //All values in here can be accessed from the securitycontext.
        System.out.println("Whoah someones in here!");
        System.out.println(authentication.getCredentials());

        //consider encrypting the userid
        Cookie StytchUserCookie = new Cookie("userID",(String)authentication.getPrincipal());
        Cookie StytchSessionTokenCookie = new Cookie("sessionToken", (String)authentication.getCredentials());
        Cookie StytchProviderTypeCookie = new Cookie("providerType", (String)authentication.getDetails());

        //Consider making these cookies never disappear.
        StytchUserCookie.setMaxAge(duration);
        StytchUserCookie.setPath("/");
        response.addCookie(StytchUserCookie);

        StytchSessionTokenCookie.setMaxAge(duration);
        StytchSessionTokenCookie.setPath("/");
        response.addCookie(StytchSessionTokenCookie);

        StytchProviderTypeCookie.setMaxAge(duration);
        StytchProviderTypeCookie.setPath("/");
        response.addCookie(StytchProviderTypeCookie);
        //super(onAuthenticationSuccess(request,response,chain,authentication));
        response.sendRedirect("/*");
    }
}