/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

/**
 *
 * @author iTech-Pc
 */
import com.itec.pojo.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
public class DBFactoryProduct {
    
    
    private MongoClient mongoClient =null;
    private DB database =null ;
    private DBCursor curs;
    public DBCollection getCollection(){
        System.out.println("GET MONGO");
        if(mongoClient==null){
            mongoClient = new MongoClient(new MongoClientURI("mongodb://usuario_reportes:setroper_oirausu@localhost:27017/?authSource=reportes&authMechanism=MONGODB-CR"));
        }
        System.out.println("GET DB");
        if(database==null){
            database=mongoClient.getDB("reportes");
            //database.authenticate("usuario_reportes", "setroper_oirausu".toCharArray());
            
                                
        }
        System.out.println("GET COLLECTION");
        return database.getCollection("digitalizacion_productos");
    }
    
    public List<Product> getProduct(){
        List<Product> products= new ArrayList<Product>();
        Product p;
        DBCollection collection = getCollection();
        //BasicDBObject searchQuery2  = new BasicDBObject().append("", "");
        BasicDBObject searchQuery2  = new BasicDBObject();
        curs=collection.find(searchQuery2);
        while(curs.hasNext()) {
                DBObject o = curs.next();
                p = new Product();
                p.setDescription((String)o.get("description")) ; 
                p.setName((String)o.get("name")) ; 
                p.setCod((String)o.get("cod")) ;
                products.add(p);
                
            }
        mongoClient.close();
        
    return products;
    }
    public String insertProduct(Product p){
    DBCollection collection = getCollection();
    BasicDBObject document = new BasicDBObject();
    document.append("cod", p.getCod());
    document.append("description", p.getDescription());
    document.append("name", p.getName());
    document.append("hijo", new BasicDBObject("country",p.getName())
                                    .append("state", p.getName())
                                    .append("city", p.getName())
            );
    collection.insert(document);
    System.out.println("INSERTADO");
    mongoClient.close();
    return "Insertado";
    }
}
