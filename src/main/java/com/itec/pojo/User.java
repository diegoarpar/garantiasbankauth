/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.security.Principal;

/**
 *
 * @author iTech-Pc
 */
public class User implements Principal{
    @JsonProperty
    private String user;
    @JsonProperty
    private String password;
    @JsonProperty
    private String token;
    @JsonProperty
    private String nombreCompleto;
    @JsonProperty
    private String autoization;

    public User(String user, String autoization, String password) {
        this.user=user;
        this.password=password;
        this.autoization=autoization;
    }

    public User() {
        
    }

    public String getAutoization() {
        return autoization;
    }

    public void setAutoization(String autoization) {
        this.autoization = autoization;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    
    
    
    
}
