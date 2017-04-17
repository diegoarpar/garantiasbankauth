/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import java.util.*;

/**
 *
 * @author iTech-Pc
 */

public class DBMongo {
    public String insert(DBCollection collection,DBCursor curs,MongoClient mongoClient, User c){

        BasicDBObject object = new BasicDBObject();
        object.append("user",c.getUser());
        object.append("pass",c.getPassword());
        object.append("completeName",c.getNombreCompleto());
        collection.insert(object);

    return "Insertado";
    }
    public String insertToken(DBCollection collection,DBCursor curs,MongoClient mongoClient, Token c){

        BasicDBObject object = new BasicDBObject();
        object.append("user",c.getUser().getUser());
        object.append("pass",c.getUser().getPassword());
        object.append("completeName",c.getUser().getNombreCompleto());
        object.append("token",c.getToken());
        object.append("date",new Date());
        collection.insert(object);

        return "Insertado";
    }
    public String updateUser(DBCollection collection,DBCursor curs,MongoClient mongoClient, String  c){
        BasicDBObject object = (BasicDBObject)JSON.parse(c);
        BasicDBObject searchQuery2  = new BasicDBObject().append("_id",object.get("_id"));
        collection.update(searchQuery2,object);
    return "actualizado";
    }
    public List<DBObject> getCriterial(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){
        List<DBObject> data= new ArrayList<>();
        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            searchQuery2.append(pair.getKey().toString(),pair.getValue()!=null?pair.getValue().toString().equals("null")?null:pair.getValue().toString().equals("true")?true:pair.getValue().toString():null);
        }
        curs=collection.find(searchQuery2);

        while(curs.hasNext()) {
                DBObject o = curs.next();
                data.add(o);
            }
    return data;
    }



    public void insertCiterial(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){

        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            searchQuery2.append(pair.getKey().toString(),pair.getValue()!=null?pair.getValue().toString().equals("null")?null:pair.getValue().toString().equals("true")?true:pair.getValue().toString():null);
        }
        collection.insert(searchQuery2);

    }


    public void insert(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){
        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            //searchQuery2.append(pair.getKey().toString(),pair.getValue()!=null?pair.getValue().toString().equals("null")?null:pair.getValue().toString().equals("true")?true:pair.getValue().toString():null);
            searchQuery2=(BasicDBObject)JSON.parse(pair.getValue().toString());
        }

        collection.insert(searchQuery2);

    }
    public void update(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){
        BasicDBObject _id  = new BasicDBObject();
        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            searchQuery2=(BasicDBObject)JSON.parse(pair.getValue().toString());
        }
        _id=(BasicDBObject)JSON.parse(searchQuery2.get("_id").toString());
        searchQuery2.remove("_id");
        ObjectId o =new ObjectId((int)_id.get("timestamp"), (int)_id.get("machineIdentifier"), (short)(int)_id.get("processIdentifier"), (int)_id.get("counter"));
        //searchQuery2.remove("_id");
        collection.update(new BasicDBObject("_id", o),searchQuery2);

    }
    public String remove(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){

        List<DBObject> data= new ArrayList<>();
        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            //searchQuery2.append(pair.getKey().toString(),pair.getValue().toString().equals("null")?null:pair.getValue().toString().equals("true")?true:pair.getValue().toString());

            //it.remove();
            searchQuery2=(BasicDBObject)JSON.parse(pair.getValue().toString());
        }

        //BasicDBObject searchQuery2  = new BasicDBObject();
        collection.remove(searchQuery2);

        return "eliminado";
    }

    public List<DBObject> get(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){
        List<DBObject> data= new ArrayList<>();
        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            try{
                searchQuery2=(BasicDBObject)JSON.parse(pair.getValue().toString());
            }catch (Exception e){

                searchQuery2.append(pair.getKey().toString(),pair.getValue().toString().equals("null")?null:pair.getValue().toString().equals("true")?true:pair.getValue().toString());
            }
            //it.remove();
        }

        //BasicDBObject searchQuery2  = new BasicDBObject();
        curs=collection.find(searchQuery2);

        while(curs.hasNext()) {
            DBObject o = curs.next();
            data.add(o);
        }
        return data;
    }
    public List<DBObject> getAll(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){
        List<DBObject> data= new ArrayList<>();

        curs=collection.find();

        while(curs.hasNext()) {
            DBObject o = curs.next();
            data.add(o);
        }
        return data;
    }
}
