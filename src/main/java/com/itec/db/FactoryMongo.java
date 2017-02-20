/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.itec.configuration.ConfigurationExample;
import com.mongodb.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import com.itec.pojo.User;
import com.itec.pojo.Token;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 *
 * @author iTech-Pc
 */
public class FactoryMongo {
    private static final String COLLECTION_TOKEN = "token";
    private static final String COLLECTION_TENANT = "tenant";
    private static final String COLLECTION_USER = "users";
    private static final String COLLECTION_ROLE = "role";
    private static final String COLLECTION_PERMISSION = "permission";
    private static final String USER_PASS_USERS = "certiusers:certi123";
    private static final String URL_USERS = "localhost";
    private static final String DATA_BASE_USERS = "usersdb";
    private MongoClient mongoClient = null;
    private DB database = null;
    private DBCursor curs;

    DBMongo dbP = new DBMongo();

    public FactoryMongo() {
    }

    public MongoClient getMongoClient(String user, String pass, String url, String dataBase) {
        if (mongoClient == null) {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://" + user + ":" + pass + "@" + url + ":27017/?authSource=" + dataBase + "&authMechanism=MONGODB-CR"));
        }
        return mongoClient;
    }

    public DB getDatabase(String dataBase) {
        database = mongoClient.getDB(dataBase);
        return database;
    }


    public DBCollection getCollection(String name, String user, String pass, String url, String dataBase) {
        getMongoClient(user, pass, url, dataBase);
        getDatabase(dataBase);


        return database.getCollection(name);
    }

    public DBCollection getCollection(String collection,HashMap c){
        String tenant="";
        if(c.get("tenant")!=null)
        if(!c.get("tenant").toString().isEmpty()){
            tenant=c.get("tenant").toString();
            c.remove("tenant");
            collection=collection+"_"+tenant;
        }

        return getCollection(collection, ConfigurationExample.DATABASE_USER,ConfigurationExample.DATABASE_PASS,ConfigurationExample.DATABASE_SERVER_URL,ConfigurationExample.DATABASE_NAME);
    }

    public List<DBObject> getUser(HashMap c) {
        return dbP.getCriterial(getCollection(COLLECTION_USER,c), curs, mongoClient, c);
    }

    public Boolean isValidToken(HashMap c){
        return dbP.getCriterial(getCollection(COLLECTION_TOKEN,c), curs, mongoClient, c).size()>0;
    }
    public void insertToken(HashMap c){
         dbP.insertCiterial(getCollection(COLLECTION_TOKEN,c), curs, mongoClient, c);
    }
    public Boolean isValidUser(HashMap c){
        return dbP.getCriterial(getCollection(COLLECTION_USER,c), curs, mongoClient, c).size()>0;
    }
    public List<DBObject> getPermission(HashMap c) {
        return dbP.getCriterial(getCollection(COLLECTION_PERMISSION,c), curs, mongoClient, c);
    }

    public List<DBObject> getRoles(HashMap c) {
        return dbP.getCriterial(getCollection(COLLECTION_ROLE,c), curs, mongoClient, c);
    }
    public List<DBObject> getUsers(HashMap c) {
        return dbP.getCriterial(getCollection(COLLECTION_USER,c), curs, mongoClient, c);
    }

    public List<DBObject> getTenant(HashMap c) {
        return dbP.getCriterial(getCollection(COLLECTION_TENANT,c), curs, mongoClient, c);
    }
    public String hash256(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Hashing.sha256()
                .hashString(password, Charsets.UTF_8)
                .toString();
    }
    public static byte[] hash256D(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passBytes = password.getBytes();
        byte[] passHash = sha256.digest(passBytes);
        return passHash;
    }

}
