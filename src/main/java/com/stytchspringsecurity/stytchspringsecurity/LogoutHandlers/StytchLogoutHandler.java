package com.stytchspringsecurity.stytchspringsecurity.LogoutHandlers;

import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.sessions.RevokeRequest;
import com.stytch.java.consumer.models.sessions.RevokeResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class StytchLogoutHandler implements LogoutHandler {

    public StytchLogoutHandler() {
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        String sessionToken  = (String) authentication.getCredentials();
        RevokeRequest revokeRequest = new RevokeRequest(sessionToken);
        StytchResult<RevokeResponse> revokeResponse;
        try {
            revokeResponse = StytchClient.sessions.revokeCompletable(revokeRequest).get();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (revokeResponse instanceof StytchResult.Error) {
            var exception = ((StytchResult.Error) revokeResponse).getException();
            System.out.println(exception.getReason());
        }
        HttpSession session = request.getSession();
        session.invalidate();

        SecurityContextHolder.clearContext();

        Cookie StytchSessionTokenCookie = new Cookie("sessionToken", null);
        Cookie StytchProviderTypeCookie = new Cookie("providerType", null);


        StytchSessionTokenCookie.setMaxAge(0);
        StytchSessionTokenCookie.setPath("/");
        response.addCookie(StytchSessionTokenCookie);

        StytchProviderTypeCookie.setMaxAge(0);
        StytchProviderTypeCookie.setPath("/");
        response.addCookie(StytchProviderTypeCookie);

    }

}