/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.services;

import com.itec.db.FactoryMongo;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.mongodb.DBObject;

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
@Path("/insert-database")
@Produces(MediaType.APPLICATION_JSON)
public class Services {
    FactoryMongo fm = new FactoryMongo();
    HashMap<String, String> criterial= new HashMap<>();

    @POST
    @Produces("application/json")
    @Path("/insertUser")
    public String insertUser(@QueryParam("name") String name, @QueryParam("password") String password, @QueryParam("fullName") String fullName,@QueryParam("password2") String password2 ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        User p = new User();
                p.setUser(name);
                p.setPassword(fm.hash256(password));
                System.out.println(fm.hash256(password));
                p.setNombreCompleto(fullName);
                if(password2.equals("DIEGOPADILLAFIRMA")){
                    fm.insertUser(p);
                     return  "FIRMANDOOK";
                }
        return  "FIRMANDO";
    }
    //String token = UUID.randomUUID().toString();
    @GET
    @Produces("application/json")
    @Path("/getToken")
    public Token getToken(@QueryParam("user") String user, @QueryParam("password") String password) throws NoSuchAlgorithmException {
        Token t = new Token();
        String token = UUID.randomUUID().toString();
        criterial.clear();
        criterial.put("pass",password);
        criterial.put("user",user);
        User u = new User ();
        if(fm.isValidUser(criterial)){
            DBObject obj=fm.getUser(criterial).get(0);
            u.setUser(obj.get("user").toString());
            u.setToken(token);
            u.setNombreCompleto(obj.get("completeName").toString());
            t.setUser(u);
            t.setToken(token);
            fm.insertToken(t);
            return t;
        }
         t=null;
        return t;
    }
    @GET
    @Produces("application/json")
    @Path("/getPermissionByUser")
    public List<DBObject> getPermission(@QueryParam("user") String user) throws NoSuchAlgorithmException {
        criterial.clear();
        criterial.put("user",user);
        return fm.getPermission(criterial);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getPermissionByUser")
    @PermitAll
    public String insertPermission(@Context HttpServletRequest req) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
        fm.insertPermission(stringBuilder.toString());
        return  "FIRMANDO";
    }
    @GET
    @Produces("application/json")
    @Path("/getRoles")
    public List<DBObject> getRoles() throws NoSuchAlgorithmException {
        criterial.clear();
        return fm.getRoles(criterial);
    }
    @GET
    @Produces("application/json")
    @Path("/getUsers")
    public List<DBObject> getUsers() throws NoSuchAlgorithmException {
        criterial.clear();
        return fm.getUsers(criterial);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getUsers")

    public String insertUser(@Context HttpServletRequest req) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
        fm.insertUser(stringBuilder.toString());
        return  "FIRMANDO";
    }
    @GET
    @Produces("application/json")
    @Path("/isValidToken")
    public Boolean isValidToken(@QueryParam("token") String token) throws NoSuchAlgorithmException {
        criterial.clear();
        criterial.put("token",token);
        return fm.isValidToken(criterial);

    }
    private void fillCriterialFromString( String queryString){
        criterial.clear();
        if(queryString!=null)
            for (String split : queryString.split("&")) {
                if (split.split("=").length == 2) {
                    criterial.put(split.split("=")[0], split.split("=")[1]);
                }
            }

    }
}
