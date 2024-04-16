package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//Replace with lombok
@Component
public class StytchConfigProperties {

    @Value("${stytch.config.projectid}")
    private String projectid;

    @Value("${stytch.config.projectsecret}")
    private String projectsecret;

    public StytchConfigProperties() {
    }
    public StytchConfigProperties(String projectid, String projectsecret) {
        this.projectid = projectid;
        this.projectsecret = projectsecret;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getProjectsecret() {
        return projectsecret;
    }

    public void setProjectsecret(String projectsecret) {
        this.projectsecret = projectsecret;
    }
}
