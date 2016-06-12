/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.oauth;

import com.google.common.base.Optional;
import com.itec.db.FactoryMongo;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 *
 * @author iTech-Pc
 */
public class Autenticator implements Authenticator<String, User>{
    FactoryMongo fm = new FactoryMongo();
    HashMap<String, String> criterial = new HashMap<>();
    Token t = new Token();
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        t.setToken(token);
        criterial.clear();
        criterial.put("token",token);
       if (fm.isValidToken(criterial)) {
            Response.status(Response.Status.ACCEPTED);
            return Optional.of(new User("diego",t,"123"));
        }else{
                Response.status(Response.Status.BAD_REQUEST);
                }
        return Optional.absent();
    }   
}