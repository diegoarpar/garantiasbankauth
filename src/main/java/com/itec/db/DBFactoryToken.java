/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iTech-Pc
 */
public class DBFactoryToken {
   
    
    public List<Token> getToken(DBCollection collection,DBCursor curs, MongoClient mongoClient){
        List<Token> tokens= new ArrayList<Token>();
        Token u;

        //BasicDBObject searchQuery2  = new BasicDBObject().append("", "");
        BasicDBObject searchQuery2  = new BasicDBObject();
        curs=collection.find(searchQuery2);
        while(curs.hasNext()) {
                DBObject o = curs.next();
                u = new Token();
                u.setToken((String)o.get("token")); 
                tokens.add(u);
                
            }
        
    return tokens;
    }
    public String insertToken(DBCollection collection,DBCursor curs, MongoClient mongoClient,Token t, User u){
        
        BasicDBObject document = new BasicDBObject();
        document.append("token", t.getToken());
        document.append("user", new BasicDBObject("name",u.getName())
                                        .append("password", u.getPassword())
                                        .append("nombreCompleto", u.getNombreCompleto())
                );
        collection.insert(document);
        System.out.println("INSERTADO");
    return "Insertado";
    }
    public  Boolean isValidToken(DBCollection collection,DBCursor curs, MongoClient mongoClient,Token token){
        Boolean respuesta=false;
        List<Token> tokens= new ArrayList<Token>();
        Token t;
        
        BasicDBObject searchQuery2  = new BasicDBObject().append("token", token.getToken());
         t = new Token();
        curs=collection.find(searchQuery2);
        while(curs.hasNext()) {
                DBObject o = curs.next();
                t = new Token();
                t.setToken((String)o.get("token")); 
            }
        System.out.println("\n\ntoken a verificar"+token.getToken()+"\n\n");
        if(t.getToken().length()>2){
            respuesta=true;
        }
        
        return respuesta;
    }
}
