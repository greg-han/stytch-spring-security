package com.stytchspringsecurity.stytchspringsecurity;

import com.stytch.java.consumer.StytchClient;
import com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig.SpringContext;
import com.stytchspringsecurity.stytchspringsecurity.SecurityConfig.StytchConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class StytchspringsecurityApplication {
	public static void main(String[] args) {
		StytchClient.configure("projectid","projectsecret");
		SpringApplication.run(StytchspringsecurityApplication.class, args);
	}

}
