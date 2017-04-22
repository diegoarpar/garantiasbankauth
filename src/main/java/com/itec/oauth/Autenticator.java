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
import com.itec.util.UTILS;
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
    @Override
    public Optional<User> authenticate(String autorization) throws AuthenticationException {
        String token="";
        String tenant="";
        criterial.clear();
        int length=autorization.split(",").length;
        if(length>1) {
            tenant=autorization.split(",")[1];
            criterial.put("tenant",tenant);
        }
        if(length>0) {
            token=autorization.split(",")[0];
            criterial.put("token",token);
        }

       if (fm.get(criterial, UTILS.COLLECTION_TOKEN).size()>0) {

            Response.status(Response.Status.ACCEPTED);
            return Optional.of(new User("diego",autorization,"123"));
        }
        Response.status(Response.Status.BAD_REQUEST);
        return Optional.absent();
    }   
}