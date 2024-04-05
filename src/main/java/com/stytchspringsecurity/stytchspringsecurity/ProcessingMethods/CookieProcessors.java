package com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods;

import jakarta.servlet.http.HttpServletRequest;

public interface CookieProcessors {
    String getSessionToken(HttpServletRequest request);

    String getProviderType(HttpServletRequest request);

    String getUserId(HttpServletRequest request);

    public interface CookieExtractor {
        String getSessionToken(HttpServletRequest request);
        String getProviderType(HttpServletRequest request);
        String getUserId(HttpServletRequest request);
    }
}
