package com.stytchspringsecurity.stytchspringsecurity.SessionFilters;

import java.io.IOException;

import com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods.StytchCookieProcessors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class StytchSessionFilter extends OncePerRequestFilter {
    private final String stytchSessionUrl;

    public StytchSessionFilter(String stytchSessionUrl) {
        this.stytchSessionUrl = stytchSessionUrl;
    }

    @Autowired
    StytchCookieProcessors stytchCookieProcessors;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Make API call to validate session
        boolean sessionValid = validateSession(request);
        if (!sessionValid) {
            // Clear security context if session is not valid
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionToken")) {
                    String sessionToken = cookie.getValue();
                    System.out.println("Cookie Value: " + sessionToken);
                    break;
                }
            }
        }
        return true;
    }
}