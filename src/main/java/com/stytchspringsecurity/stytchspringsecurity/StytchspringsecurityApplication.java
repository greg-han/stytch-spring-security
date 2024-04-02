package com.stytchspringsecurity.stytchspringsecurity;

import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.oauth.AttachResponse;
import com.stytch.java.consumer.models.oauth.AuthenticateResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class StytchspringsecurityApplication {
	public static void main(String[] args) {
		StytchClient.configure("project-test-bb161e22-475d-4596-9d50-e4b98259f421","secret-test-OyXRTvgImdIJ-9jve6X3lpyWOD5S71yL8xo=");
		SpringApplication.run(StytchspringsecurityApplication.class, args);
	}

}
