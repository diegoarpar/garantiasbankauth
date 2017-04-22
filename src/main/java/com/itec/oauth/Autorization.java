/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.oauth;

import com.itec.db.FactoryMongo;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import io.dropwizard.auth.Authorizer;
import java.util.UUID;


/**
 *
 * @author iTech-Pc
 */
public class Autorization implements Authorizer<User> {
   FactoryMongo f = new FactoryMongo();

    @Override
    public boolean authorize(User u, String role) {
         String token = UUID.randomUUID().toString();
         Token t = new Token();
         t.setToken(token);
         /*if((u=f.isValidUser(u))!=null){
             f.insertToken(t, u);
             return true;
         }*/
        return true;
    }
}