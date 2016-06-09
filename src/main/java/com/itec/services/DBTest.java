/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.services;

import com.itec.db.DBFactoryMongo;
import com.itec.db.DBFactoryProduct;
import com.itec.db.DBFactoryUser;
import com.itec.pojo.Product;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author iTech-Pc
 */
@Path("/insert-database")
@Produces(MediaType.APPLICATION_JSON)
public class DBTest {
    
    private DBFactoryMongo f = new DBFactoryMongo();;
    @POST
    public String insertProduct(@QueryParam("name") String name, @QueryParam("cod") String cod, @QueryParam("desc") String desc ) {
        DBFactoryProduct db = new DBFactoryProduct();
        Product p = new Product();
                p.setCod(cod);
                p.setDescription(desc);
                p.setName(name);
                
                db.insertProduct(p);
        return  "FIRMANDO";
    }
    @POST
    @Produces("application/json")
    @Path("/insertUser")
    public String insertUser(@QueryParam("name") String name, @QueryParam("password") String password, @QueryParam("fullName") String fullName,@QueryParam("password2") String password2 ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        User p = new User();
                p.setUser(name);
                p.setPassword(password);
                p.setNombreCompleto(fullName);
                if(password2.equals("CERTICAMARADIEGOPADILLA")){
                    f.insertUser(p);
                     return  "FIRMANDOOK";
                }
        return  "FIRMANDO";
    }
    @GET
    @Produces("application/json")
    @Path("/getAll")
    @RolesAllowed("Admin")
    public List<Product> getProduct() {
        DBFactoryProduct db = new DBFactoryProduct();
        return db.getProduct();
    }
    @GET
    @Produces("application/json")
    @Path("/getToken")
    public Token getToken(@QueryParam("user") String user, @QueryParam("password") String password) throws NoSuchAlgorithmException {
         
         String token = UUID.randomUUID().toString();
                token = DBFactoryUser.hash256(token).toString();
         User u = new User();
              u.setUser(user);
              u.setPassword(password);
         Token t = new Token();
         t.setToken(token);
         
         if((u=f.isValidUser(u))!=null){
             f.insertToken(t, u);
             return t;
         }
         t=null;
        return t;
    }
}
