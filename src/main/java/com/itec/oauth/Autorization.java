/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.oauth;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.db.FactoryMongo;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.itec.services.ServicesRoles;
import com.itec.util.UTILS;
import com.mongodb.DBObject;
import io.dropwizard.auth.Authorizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 *
 * @author iTech-Pc
 */
public class Autorization implements Authorizer<User> {
   FactoryMongo f = ConfigurationAutentication.getFactoryMongo();
    HashMap<String, String> criterial = new HashMap<>();
    @Override
    public boolean authorize(User u, String role) {
        String [] rolesToSearch =role.split(",");         String autorization=u.getAutoization();         String tenant="";         String token="";        List<DBObject> roles = new ArrayList<>();
        criterial.clear();
        int length=autorization.split(",").length;
        if(length>1) {            tenant=autorization.split(",")[1];            criterial.put("tenant",tenant);        }
        if(length>0) {            token=autorization.split(",")[0];            criterial.put("token",token);        }
        List<DBObject> usersByToken = f.get(criterial, UTILS.COLLECTION_TOKEN);
        criterial.remove("token");
        for(DBObject us : usersByToken){
            criterial.put("tenant",tenant);
            criterial.put("user",us.get("user").toString());
            for(DBObject t:f.get(criterial,UTILS.COLLECTION_ROLE)){
                roles.add(t);
            }
        }
        for(int i=0;i<rolesToSearch.length;i++){
            for(int j=0;j<roles.size();j++){
                if(rolesToSearch[i].equals(roles.get(j).get("rol").toString())){
                    return true;
                }
            }
        }
        return false;
    }
}