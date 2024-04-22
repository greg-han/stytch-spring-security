package com.stytchspringsecurity.stytchspringsecurity;

import com.stytch.java.consumer.StytchClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StytchspringsecurityApplication {
	public static void main(String[] args) {
		StytchClient.configure("projectid","projectsecret");
		SpringApplication.run(StytchspringsecurityApplication.class, args);
	}

}
