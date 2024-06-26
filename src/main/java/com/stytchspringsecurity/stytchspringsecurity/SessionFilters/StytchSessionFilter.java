package com.stytchspringsecurity.stytchspringsecurity.SessionFilters;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.stytch.java.common.StytchException;
import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.sessions.AuthenticateRequest;
import com.stytch.java.consumer.models.sessions.AuthenticateResponse;
import com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig.SpringContext;
import com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods.StytchCookieProcessors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class StytchSessionFilter extends OncePerRequestFilter {
    private final String stytchSessionUrl;

    public StytchSessionFilter(String stytchSessionUrl) {
        this.stytchSessionUrl = stytchSessionUrl;
    }
    StytchCookieProcessors stytchCookieProcessors = SpringContext.getBean(StytchCookieProcessors.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println(securityContext);
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
            Cookie StytchUserCookie = new Cookie("userID",null);
            Cookie StytchSessionTokenCookie = new Cookie("sessionToken", null);
            Cookie StytchProviderTypeCookie = new Cookie("providerType", null);

            StytchUserCookie.setMaxAge(0); // Setting the maxAge to 0 deletes the cookie
            StytchUserCookie.setPath("/"); // Set the path to match the cookie's original path
            response.addCookie(StytchUserCookie);

            StytchSessionTokenCookie.setMaxAge(0);
            StytchSessionTokenCookie.setPath("/");
            response.addCookie(StytchSessionTokenCookie);

            StytchProviderTypeCookie.setMaxAge(0);
            StytchProviderTypeCookie.setPath("/");
            response.addCookie(StytchProviderTypeCookie);
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateSession(HttpServletRequest request) throws ExecutionException, InterruptedException, StytchException {
        String sessionToken = stytchCookieProcessors.getSessionToken(request);
        AuthenticateRequest stytchAuthenticateRequest;
        if (sessionToken != null) {
            stytchAuthenticateRequest = new AuthenticateRequest(sessionToken);
            try {
                StytchResult<AuthenticateResponse> stytchAuthenticateResponse = StytchClient.sessions.authenticateCompletable(stytchAuthenticateRequest).get();
                int value;
                if (stytchAuthenticateResponse instanceof StytchResult.Error) {
                    var exception = ((StytchResult.Error) stytchAuthenticateResponse).getException();
                    System.out.println(exception.getReason());
                    return false;
                }
                System.out.println("Session Response");
                System.out.println(stytchAuthenticateResponse);
                System.out.println("Session Token: " + sessionToken);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(sessionToken == null){
            return false;
        }
        return true;
    }

}