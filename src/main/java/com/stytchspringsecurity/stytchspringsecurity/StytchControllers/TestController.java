package com.stytchspringsecurity.stytchspringsecurity.StytchControllers;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

    @GetMapping("/v1/test")
    public void testEndpoint(@NotNull HttpServletRequest request, Authentication authentication) {
       System.out.println("Thanks for being authenticated!");
       System.out.println(authentication);
    }
}
