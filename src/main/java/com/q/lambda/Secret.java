package com.q.lambda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public class Secret {

    private String username;
    private String password;
    
    public Secret() {
    }
    
    public Secret(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Secret [username=" + username + ", password=" + password + "]";
    }
}
