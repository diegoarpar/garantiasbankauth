/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.services;

import com.itec.db.FactoryMongo;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.itec.util.UTILS;
import com.mongodb.DBObject;
import org.eclipse.jetty.server.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author iTech-Pc
 */
@Path("/autentication/users")
@Produces(MediaType.APPLICATION_JSON)
public class Services {
    FactoryMongo fm = new FactoryMongo();
    HashMap<String, String> criterial= new HashMap<>();

    @POST
    @Produces("application/json")
    @Path("/users")
    public String insertUser(@QueryParam("name") String name, @QueryParam("password") String password, @QueryParam("fullName") String fullName,@QueryParam("password2") String password2 ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        User p = new User();
                p.setUser(name);
                p.setPassword(UTILS.hash256(password));
                p.setNombreCompleto(fullName);
                if(password2.equals("DIEGOPADILLAFIRMA")){
                    //fm.insertUser(p);
                     return  "FIRMANDOOK";
                }
        return  "FIRMANDO";
    }

    @GET
    @Produces("application/json")
    @Path("/getPermissionByUser")
    public List<DBObject> getPermission(@QueryParam("user") String user) throws NoSuchAlgorithmException {
        criterial.clear();
        criterial.put("user",user);
        return fm.get(criterial,UTILS.COLLECTION_PERMISSION);
    }

}
