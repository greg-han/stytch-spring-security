package com.stytchspringsecurity.stytchspringsecurity.MockTestClasses;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.stytch.java.common.StytchException;
import com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig.SpringContext;
import com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods.StytchCookieProcessors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class MockStytchSessionFilter extends OncePerRequestFilter {
    private final String stytchSessionUrl;
    StytchCookieProcessors stytchCookieProcessors = SpringContext.getBean(StytchCookieProcessors.class);

    public MockStytchSessionFilter(String stytchSessionUrl) {
        this.stytchSessionUrl = stytchSessionUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean sessionValid = false;
        try {
            sessionValid = validateSession(request);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (StytchException e) {
            throw new RuntimeException(e);
        }

        if (!sessionValid) {
            HttpSession session = request.getSession();
            session.invalidate();
            //May need to clear securitycontextrepository as well.
            SecurityContextHolder.clearContext();
            Cookie sessionToken = new Cookie("sessionToken", null);
            sessionToken.setMaxAge(0);
            sessionToken.setPath("/");
            response.addCookie(sessionToken);
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateSession(HttpServletRequest request) throws ExecutionException, InterruptedException, StytchException {
        String sessionToken = stytchCookieProcessors.getSessionToken(request);
        if (sessionToken != null) {
            return true;
        }
        if (sessionToken == null) {
            return false;
        }

        return false;
    }
}


