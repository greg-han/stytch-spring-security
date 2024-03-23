package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class EnableLogging implements WebSecurityCustomizer {

    @Override
    public void customize(WebSecurity web){
        web.debug(true);
    }

}