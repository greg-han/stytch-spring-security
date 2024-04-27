package com.stytchspringsecurity.stytchspringsecurity.MockStytchSecurityContextFactory;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockStytchOauthSecurityContextFactory.class)
public @interface WithMockStytchOauthResponseToken {
    String userId() default "user-123";
    String sessionToken() default "123abc";
    String provider() default "Oauth";

}
