package com.itec.services;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.db.FactoryMongo;
import com.itec.pojo.User;
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
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by iTech on 17/04/2017.
 */
@Path("/autentication/token")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesToken {

    private FactoryMongo f = ConfigurationAutentication.getFactoryMongo();
    private HashMap<String, DBObject> criterial= new HashMap<>();
    private ArrayList<HashMap<String, DBObject>> criterialList= new ArrayList<>();
    private  String postString="";

    @GET
    @Produces("application/json")
    @PermitAll
    @RolesAllowed("ADMIN,USER")
    public Boolean get( @Context HttpServletRequest req)  {
        //criterial=UTILS.fillCriterialFromString(req.getQueryString(),criterial);
        //criterial=UTILS.getTenant(req,criterial);
        //return f.isValidToken(criterial);
        return true;
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
