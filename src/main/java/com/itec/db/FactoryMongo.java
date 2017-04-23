/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.itec.configuration.ConfigurationExample;
import com.itec.util.UTILS;
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

    public void insert(HashMap c, String collection) {
        dbP.insert(getCollection(collection,c), curs, mongoClient, c);
    }
    public void delete(HashMap c, String collection) {
        dbP.remove(getCollection(collection,c), curs, mongoClient, c);
    }
    public List<DBObject> getAll(HashMap c, String collection) {
        return dbP.getAll(getCollection(collection,c), curs, mongoClient, c);
    }
    public List<DBObject> get(HashMap c, String collection) {
        return dbP.get(getCollection(collection,c), curs, mongoClient, c);
    }



}
