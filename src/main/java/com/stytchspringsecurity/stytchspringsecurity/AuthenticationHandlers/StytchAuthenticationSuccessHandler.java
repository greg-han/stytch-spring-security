package com.stytchspringsecurity.stytchspringsecurity.AuthenticationHandlers;

import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StytchAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        StytchOauthAuthenticationResponseToken resToken = (StytchOauthAuthenticationResponseToken) authentication;
    }
/*
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("user", authUser);
        session.setAttribute("username", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());


        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        //since we have created our custom success handler, its up to us, to where
        //we will redirect the user after successfully login
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
//        String requestUrl = savedRequest.getRedirectUrl();
        response.sendRedirect(savedRequest.getRedirectUrl().isEmpty() ? "/" : savedRequest.getRedirectUrl()); //requestUrl!=null?requestUrl:
    }

 */
}