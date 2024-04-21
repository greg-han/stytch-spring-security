package com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class StytchCookieProcessors implements CookieProcessors {

    @Override
    public String getSessionToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionToken")) {
                    String sessionToken = cookie.getValue();
                    return sessionToken;
                }
            }
        }
        return null;
    }

    @Override
    public String getProviderType(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("providerType")) {
                    String providerType = cookie.getValue();
                    return providerType;
                }
            }
        }
        return null;
    }

}
