/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author iTech-Pc
 */
public class DBFactoryMongo {
    private static final String MONGO_DB= "reportes";
    private static final String MONGO_COLLECTION_PRODUCTS= "digitalizacion_productos";
    private static final String MONGO_COLLECTION_DIGITALIZACION= "digitalizacion_digitalizacion";
    private static final String MONGO_COLLECTION_TELEFONICA= "telefonica_report";
    private static final String MONGO_COLLECTION_CATEGORY= "digitalizacion_category";
    private static final String MONGO_COLLECTION_USERS= "digitalizacion_user";
    private static final String MONGO_COLLECTION_TOKEN= "digitalizacion_token";
    private MongoClient mongoClient =null;
    private DB database =null ;
    private DBCursor curs;

    public DBFactoryMongo() {
    }
   
    
    public DBCollection getCollection(String name){
        if(mongoClient==null){
            //mongoClient = new MongoClient(new MongoClientURI("mongodb://"+MONGO_SERVER));
            //mongoClient = new MongoClient(new MongoClientURI("mongodb://certi:Certi123@10.130.186.221:27017/?authSource=reportestelefonica&authMechanism=MONGODB-CR"));
            mongoClient = new MongoClient(new MongoClientURI("mongodb://swd_db:swd_db@localhost:27017/?authSource=SWD_DB&authMechanism=MONGODB-CR"));
        }
        if(database==null){
            //database=mongoClient.getDB("reportestelefonica");
            database=mongoClient.getDB("SWD_DB");
        }
        switch (name){
            case MONGO_COLLECTION_PRODUCTS:
                    return database.getCollection(MONGO_COLLECTION_PRODUCTS);
            case MONGO_COLLECTION_CATEGORY:
                    return database.getCollection(MONGO_COLLECTION_CATEGORY); 
            case MONGO_COLLECTION_TOKEN:
                    return database.getCollection(MONGO_COLLECTION_TOKEN);
            case MONGO_COLLECTION_DIGITALIZACION:
                    return database.getCollection(MONGO_COLLECTION_DIGITALIZACION);
             case MONGO_COLLECTION_TELEFONICA:
                    return database.getCollection(MONGO_COLLECTION_TELEFONICA);        
            default: 
                     break;
        }
        
        return database.getCollection(name);
        }
        
        
        
       
        public Boolean isValidToken(Token token){
            DBFactoryToken dbP= new DBFactoryToken();
            return dbP.isValidToken(getCollection(MONGO_COLLECTION_TOKEN), curs, mongoClient,token);
            
        }
        public String insertToken(Token token, User u){
            DBFactoryToken dbP= new DBFactoryToken();
            return dbP.insertToken(getCollection(MONGO_COLLECTION_TOKEN), curs, mongoClient,token,u);
            
        }

    public String insertUser(User p) throws NoSuchAlgorithmException, UnsupportedEncodingException {
         DBFactoryUser dbP= new DBFactoryUser();
            return dbP.insertUser(getCollection(MONGO_COLLECTION_TOKEN), curs, mongoClient,p);
    }

    public User isValidUser(User u) {
       DBFactoryUser dbP= new DBFactoryUser();
            return dbP.isValidUser(getCollection(MONGO_COLLECTION_TOKEN), curs, mongoClient,u);
    }
}
