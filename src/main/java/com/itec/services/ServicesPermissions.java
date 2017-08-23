package com.itec.services;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.db.FactoryMongo;
import com.itec.util.UTILS;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

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
@Path("/autentication/permission")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesPermissions {

    private FactoryMongo f = ConfigurationAutentication.getFactoryMongo();
    private HashMap criterial= new HashMap<>();
    private ArrayList<HashMap<String, DBObject>> criterialList= new ArrayList<>();
    private  String postString="";

    @GET
    @Produces("application/json")
    @RolesAllowed("ADMIN,USER")
    public List<DBObject> get( @Context HttpServletRequest req)  {
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);
        criterial=UTILS.getTenant(req,criterial);
        return f.get(criterial,UTILS.COLLECTION_PERMISSION);
    }

    @POST
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public String insert(@Context HttpServletRequest req) throws IOException {
        postString= UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        for(HashMap o : criterialList){
            o=UTILS.getTenant(req,o);
            f.insert(o, UTILS.COLLECTION_PERMISSION);
        }
        return  "FIRMANDO";
    }
    @PUT
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public String update(@Context HttpServletRequest req) throws IOException  {
        criterial.clear();
        postString=UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        UTILS.getToken(req,criterial);
        UTILS.getTenant(req,criterial);
        BasicDBObject obj = (BasicDBObject) f.get(criterial,UTILS.COLLECTION_TOKEN).get(0);
        String user= (String) obj.get("user");
        criterial.clear();
        criterial.put("json", new BasicDBObject().append("user",user));
        criterial=UTILS.getTenant(req,criterial);
        f.delete(criterial,UTILS.COLLECTION_PERMISSION);
        criterial.clear();
        if(criterialList.size()>0) {
            user = (String)((BasicDBObject)(criterialList.get(0).get("json"))).get("user");
            for (HashMap o : criterialList) {
                o=UTILS.getTenant(req,o);
                f.insert(o, UTILS.COLLECTION_PERMISSION);
            }
        }
        return  "FIRMANDO";
    }
    @DELETE
    @Produces("application/json")
    @RolesAllowed({"ADMIN"})
    public String delete(@Context HttpServletRequest req,@PathParam("id") String id)throws IOException   {
        postString=UTILS.fillStringFromRequestPost(req);
        return  "FIRMANDO";
    }
    @GET
    @Produces("application/json")
    @Path("/getByUser")
    @RolesAllowed("ADMIN,USER")
    public List<DBObject> getByUser(@Context HttpServletRequest req)  {
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);

        return f.get(criterial,UTILS.COLLECTION_PERMISSION);
    }
}
