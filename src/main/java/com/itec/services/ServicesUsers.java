package com.itec.services;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.db.FactoryMongo;
import com.itec.pojo.Token;
import com.itec.pojo.User;
import com.itec.util.UTILS;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by iTech on 17/04/2017.
 */
@Path("/autentication/users")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesUsers {

    private FactoryMongo f = ConfigurationAutentication.getFactoryMongo();
    private HashMap criterial= new HashMap<>();
    private ArrayList<HashMap<String, DBObject>> criterialList= new ArrayList<>();
    private  String postString="";

    @GET
    @Produces("application/json")
    @PermitAll
    public List<DBObject> get( @Context HttpServletRequest req)  {
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);

        criterial=UTILS.getTenant(req,criterial);
        return f.get(criterial,UTILS.COLLECTION_USER);

    }
    @GET
    @Produces("application/json")
    @Path("/logIn")
    public Token LogIn(@QueryParam("user") String user, @QueryParam("pass") String pass, @QueryParam("tenant") String tenant)  {
        Token t = new Token();
        User u = new User ();
        criterial.clear();
        criterial.put("user",user);
        criterial.put("pass",pass);
        criterial.put("tenant",tenant);
        if(f.get(criterial,UTILS.COLLECTION_USER).size()>0){
            String token = UUID.randomUUID().toString();
            t.setToken(token);
            DBObject temp = new BasicDBObject();
            temp.put("user",user);
            temp.put("token",token);
            criterial.clear();
            criterial.put("tenant",tenant);
            criterial.put("json",temp);
            f.insert(criterial,UTILS.COLLECTION_TOKEN);
            t.setUser(u);
            t.setToken(token);
            u.setUser(user);
            return t;
        }
        return null;

    }
    @GET
    @Produces("application/json")
    @Path("/getByToken")
    @RolesAllowed("ADMIN,USER")
    public List<DBObject> getByToken( @Context HttpServletRequest req)  {
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);

        criterial=UTILS.getTenant(req,criterial);
        return f.get(criterial,UTILS.COLLECTION_TOKEN);

    }
    @GET
    @Produces("application/json")
    @Path("/getAll")
    @RolesAllowed("ADMIN")
    public List<DBObject> getAll(@Context HttpServletRequest req)  {
        criterial=UTILS.getTenant(req,criterial);
        List<DBObject> temp=f.getAll(criterial,UTILS.COLLECTION_USER);
        for(DBObject o : temp){
            o.removeField("pass");
        }
        return temp;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public String insert(@Context HttpServletRequest req) throws IOException {
        postString= UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        for(HashMap o : criterialList){
            o=UTILS.getTenant(req,o);
            f.insert(o, UTILS.COLLECTION_USER);
        }
        return  "FIRMANDO";
    }
    @PUT
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public String update(@Context HttpServletRequest req) throws IOException  {
        postString=UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        for(HashMap o : criterialList){
            o=UTILS.getTenant(req,o);
            //f.insert(o, UTILS.COLLECTION_TOKEN);
        }
        return  "FIRMANDO";
    }
    @DELETE
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public String delete(@Context HttpServletRequest req,@PathParam("id") String id)throws IOException   {
        postString=UTILS.fillStringFromRequestPost(req);
        return  "FIRMANDO";
    }
}
