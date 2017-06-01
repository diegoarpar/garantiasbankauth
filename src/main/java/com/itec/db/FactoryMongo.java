/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.itec.configuration.ConfigurationAutentication;
import com.mongodb.*;

import java.util.HashMap;
import java.util.List;

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
        mongoClient=ConfigurationAutentication.getMongoClient(user,pass,url,dataBase               );
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

        return getCollection(collection, ConfigurationAutentication.DATABASE_USER, ConfigurationAutentication.DATABASE_PASS, ConfigurationAutentication.DATABASE_SERVER_URL, ConfigurationAutentication.DATABASE_NAME);
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
