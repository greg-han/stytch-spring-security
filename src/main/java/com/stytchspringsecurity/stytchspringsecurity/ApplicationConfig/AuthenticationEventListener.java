package com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            // Handle successful authentication event
            System.out.println("Successful authentication event occurred.");
        } else if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            // Handle authentication failure due to bad credentials
            System.out.println("Authentication failure due to bad credentials.");
        } else if (event instanceof AuthenticationFailureDisabledEvent) {
            // Handle authentication failure due to disabled user account
            System.out.println("Authentication failure due to disabled account.");
        } else if (event instanceof AuthenticationFailureExpiredEvent) {
            // Handle authentication failure due to expired credentials
            System.out.println("Authentication failure due to expired credentials.");
        } else if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
            // Handle authentication failure due to credentials expired
            System.out.println("Authentication failure due to credentials expired.");
        } else if (event instanceof AuthenticationFailureLockedEvent) {
            // Handle authentication failure due to locked user account
            System.out.println("Authentication failure due to locked account.");
        }
    }
}