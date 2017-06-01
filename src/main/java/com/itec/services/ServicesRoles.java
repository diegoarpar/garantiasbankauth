package com.itec.services;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.db.FactoryMongo;
import com.itec.util.UTILS;
import com.mongodb.BasicDBList;
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

/**
 * Created by iTech on 17/04/2017.
 */
@Path("/autentication/roles")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesRoles {

    private FactoryMongo f = ConfigurationAutentication.getFactoryMongo();
    private HashMap criterial= new HashMap<>();
    private ArrayList<HashMap<String, DBObject>> criterialList= new ArrayList<>();
    private  String postString="";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public List<DBObject> get(@Context HttpServletRequest req)  {
        List<DBObject> roles = new ArrayList<>();
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);
        criterial=UTILS.getTenant(req,criterial);
        criterial=UTILS.getToken(req,criterial);
        List<DBObject> usersByToken = f.get(criterial,UTILS.COLLECTION_TOKEN);
        criterial.remove("token");

        for(DBObject u : usersByToken){
            criterial=UTILS.getTenant(req,criterial);
            criterial.put("user",u.get("user").toString());
            for(DBObject t:f.get(criterial,UTILS.COLLECTION_ROLE)){
                roles.add(t);
            }
        }

        return roles;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    @Path("/outToken")
    public List<DBObject> getOutToken(@Context HttpServletRequest req)  {
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);
        criterial=UTILS.getTenant(req,criterial);

        return f.get(criterial,UTILS.COLLECTION_ROLE);
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public String insert(@Context HttpServletRequest req) throws IOException {
        postString= UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        if(criterialList.size()>0) {
            DBObject objetToFind= (DBObject) JSON.parse(criterialList.get(0).get("json").toString());
            HashMap objectToDelete = new HashMap();
            HashMap objectToFind= new HashMap();
            objectToFind.put("user",objetToFind.get("user"));
            objectToFind = UTILS.getTenant(req, objectToFind);
            for (DBObject o : f.get(objectToFind,UTILS.COLLECTION_ROLE)) {
                objectToDelete = UTILS.getTenant(req, objectToDelete);
                objectToDelete.put("json",o);
                f.delete(objectToDelete, UTILS.COLLECTION_ROLE);
            }
        }
        for(HashMap o : criterialList){
            o=UTILS.getTenant(req,o);
            f.insert(o, UTILS.COLLECTION_ROLE);
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
