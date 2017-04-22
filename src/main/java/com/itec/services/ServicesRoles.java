package com.itec.services;

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

    private FactoryMongo f = new FactoryMongo();
    private HashMap criterial= new HashMap<>();
    private ArrayList<HashMap<String, DBObject>> criterialList= new ArrayList<>();
    private  String postString="";

    @GET
    @Produces("application/json")
    @PermitAll
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

    @POST
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public String insert(@Context HttpServletRequest req) throws IOException {
        postString= UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        for(HashMap o : criterialList){
            o=UTILS.getTenant(req,o);
            f.insert(o, UTILS.COLLECTION_ROLE);
        }
        return  "FIRMANDO";
    }
    @PUT
    @Produces("application/json")
    @PermitAll
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
    @PermitAll
    public String delete(@Context HttpServletRequest req,@PathParam("id") String id)throws IOException   {
        postString=UTILS.fillStringFromRequestPost(req);
        return  "FIRMANDO";
    }
}
