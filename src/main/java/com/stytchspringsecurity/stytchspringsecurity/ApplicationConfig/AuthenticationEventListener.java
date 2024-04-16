package com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        //Although the app logs out, this event does't display.
        if (event instanceof LogoutSuccessEvent) {
            System.out.println("You've logged out!");
        }
        if (event instanceof AuthenticationSuccessEvent) {
            System.out.println("Successful authentication event occurred.");
            Authentication stytchSessionAuthToken = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(stytchSessionAuthToken);
        }
    }
}