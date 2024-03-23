package com.stytchspringsecurity.stytchspringsecurity.SecurityConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

//Replace with lombok
@ConfigurationProperties(prefix="stytch.config")
public class StytchConfigProperties {
    private String projectid;
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

    public String getprojectsecret() {
        return projectsecret;
    }

    public void setProjectsecret(String projectsecret) {
        this.projectsecret = projectsecret;
    }
}
