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
    public String insertUser(DBCollection collection,DBCursor curs,MongoClient mongoClient, User c){

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
            it.remove();
        }
        curs=collection.find(searchQuery2);

        while(curs.hasNext()) {
                DBObject o = curs.next();
                data.add(o);
            }
    return data;
    }


    public String insert(DBCollection collection,DBCursor curs,MongoClient mongoClient, String c){

        BasicDBObject object = (BasicDBObject) JSON.parse(c);
        collection.insert(object);

        return "Insertado";
    }


}
