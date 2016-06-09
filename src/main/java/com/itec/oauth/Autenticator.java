/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.oauth;

import com.google.common.base.Optional;
import com.itec.db.DBFactoryMongo;
import com.itec.db.DBFactoryToken;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import javax.ws.rs.core.Response;
/**
 *
 * @author iTech-Pc
 */
public class Autenticator implements Authenticator<String, User>{
    DBFactoryMongo f = new DBFactoryMongo();
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        
        Token t = new Token();
        t.setToken(token);
       if (f.isValidToken(t)) {
            Response.status(Response.Status.ACCEPTED);
            return Optional.of(new User("diego",t,"123"));
        }else{
                Response.status(Response.Status.BAD_REQUEST);
                }
        return Optional.absent();
    }   
}