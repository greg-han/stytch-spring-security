plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.freefair.lombok") version "8.6"
}

group = "com.stytchspringsecurity"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.stytch.java:sdk:1.2.0")
	implementation("org.springframework.security:spring-security-oauth2-core:6.2.2")
	implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
	implementation("org.springframework.security:spring-security-oauth2-client:6.2.2")



	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
