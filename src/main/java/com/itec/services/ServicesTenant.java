package com.itec.services;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.db.FactoryMongo;
import com.itec.util.UTILS;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.eclipse.jetty.server.Request;

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
@Path("/autentication/tenant")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesTenant {

    private FactoryMongo f = ConfigurationAutentication.getFactoryMongo();
    private HashMap<String, DBObject> criterial= new HashMap<>();
    private ArrayList<HashMap<String, DBObject>> criterialList= new ArrayList<>();
    private  String postString="";

    @GET
    @Produces("application/json")

    public List<DBObject> get(@Context HttpServletRequest req)  {
        criterial.clear();
        criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);
        System.out.println(criterial.toString());
        //criterial.put("origin", new BasicDBObject().append("origin",((Request) req).getHttpFields().get("host")));
        return f.get(criterial,UTILS.COLLECTION_TENANT);
    }

    @POST
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public String insert(@Context HttpServletRequest req) throws IOException {
        postString= UTILS.fillStringFromRequestPost(req);
        criterialList=UTILS.fillCriterialListFromDBOBject((BasicDBList) JSON.parse(postString.toString()),criterial, criterialList);
        for(HashMap o : criterialList){
            o=UTILS.getTenant(req,o);
            //f.insert(o, UTILS.COLLECTION_TOKEN);
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
