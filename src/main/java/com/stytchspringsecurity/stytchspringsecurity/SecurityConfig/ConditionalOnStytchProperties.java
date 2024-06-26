package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ConditionalOnProperty(
        name = {"stytch.config.projectid", "stytch.config.projectsecret"}

)

public @interface ConditionalOnStytchProperties {
}
