/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.itec.pojo.User;
import com.itec.pojo.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iTech-Pc
 */
public class DBFactoryUser {
    
    
    
    public List<User> getUser(DBCollection collection,DBCursor curs, MongoClient mongoClient){
        List<User> users= new ArrayList<User>();
        User u;

        //BasicDBObject searchQuery2  = new BasicDBObject().append("", "");
        BasicDBObject searchQuery2  = new BasicDBObject();
        curs=collection.find(searchQuery2);
        while(curs.hasNext()) {
                DBObject o = curs.next();
                u = new User();
                u.setUser((String)o.get("user")); 
                u.setPassword((String)o.get("password")) ;
                u.setNombreCompleto((String)o.get("nombreCompleto"));
                users.add(u);
            }
        
    return users;
    }
    public String insertUser(DBCollection collection,DBCursor curs, MongoClient mongoClient, User u) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    
    BasicDBObject document = new BasicDBObject();
    document.append("user", u.getUser());
    document.append("password", hash2562(u.getPassword()));
    document.append("nombreCompleto", u.getNombreCompleto());
    collection.insert(document);
    System.out.println("INSERTADO");
    
    return "Insertado";
    }
    public User isValidUser(DBCollection collection,DBCursor curs, MongoClient mongoClient, User ui){
        Boolean respuesta=false;
        List<User> users= new ArrayList<User>();
        User u;

        BasicDBObject searchQuery2  = new BasicDBObject().append("user", ui.getUser());
         u = null;
        curs=collection.find(searchQuery2);
        System.out.println("\n\nusuario a verificar"+ui.getName()+"---"+ui.getPassword()+"\n\n");
        while(curs.hasNext()) {
                DBObject o = curs.next();
                u = new User();
                u.setUser((String)o.get("user")); 
                u.setPassword((String)o.get("password")) ; 
                u.setNombreCompleto((String)o.get("nombreCompleto")) ;
                users.add(u);
                
            }
        if(u!=null){
            if(u.getPassword().equals(ui.getPassword())){
                respuesta=true;
            }
        }
        
        
        return u;
    }
    public static String hash2562(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return Hashing.sha256()
        .hashString(password, Charsets.UTF_8)
        .toString();
    }
    
    public static byte[] hash256(String password) throws NoSuchAlgorithmException {
    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
    byte[] passBytes = password.getBytes();
    byte[] passHash = sha256.digest(passBytes);
    return passHash;
}
    
}
