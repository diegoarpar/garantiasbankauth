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


    /*private MongoClient mongoClient = null;
    private DB database = null;
    private DBCursor curs;
*/


    public FactoryMongo() {
    }

    public MongoClient getMongoClient() {
        return ConfigurationAutentication.getMongoClient(ConfigurationAutentication.DATABASE_USER, ConfigurationAutentication.DATABASE_PASS, ConfigurationAutentication.DATABASE_SERVER_URL, ConfigurationAutentication.DATABASE_NAME);
    }

    public DB getDatabase() {

        return getMongoClient().getDB(ConfigurationAutentication.DATABASE_NAME);
    }


    public DBCollection getCollection(String name) {
        return getDatabase().getCollection(name);
    }

    public DBCollection getCollection(String collection,HashMap c){
        String tenant="";
        if(c.get("tenant")!=null)
        if(!c.get("tenant").toString().isEmpty()){
            tenant=c.get("tenant").toString();
            c.remove("tenant");
            collection=collection+"_"+tenant;
        }

        return getCollection(collection);
    }

    public void insert(HashMap c, String collection) {
        ConfigurationAutentication.dbm.insert(getCollection(collection,c),  getMongoClient(), c);
    }
    public void delete(HashMap c, String collection) {
        ConfigurationAutentication.dbm.remove(getCollection(collection,c),  getMongoClient(), c);
    }
    public List<DBObject> getAll(HashMap c, String collection) {
        DBMongo dbP = new DBMongo();
        return dbP.getAll(getCollection(collection,c),  getMongoClient(), c);
    }
    public List<DBObject> get(HashMap c, String collection) {
        return ConfigurationAutentication.dbm.get(getCollection(collection,c),  getMongoClient(), c);
    }



}
