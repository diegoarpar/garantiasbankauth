/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.autentication;

import com.itec.configuration.ConfigurationExample;
import com.itec.services.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

/**
 *
 * @author iTech-Pc
 */
public class autentication extends  Application<ConfigurationExample>{
     public static void main (String[] args)  throws Exception{
         if(args.length > 0) new autentication().run(args);
         else {
             new autentication().run(new String[] { "server","./src/main/java/com/itec/autentication/config.yml" });
         }
    }
    

     @Override
    public void run(ConfigurationExample t, Environment e) throws Exception {
            t.getTemplate();
            t.getDefaultName();
        FilterRegistration.Dynamic filter = e.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
                                    filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, e.getApplicationContext().getContextPath() + "*");
                                    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
                                    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
                                    filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization, Date");
                                    filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        final Services db = new Services();
        final ServicesPermissions p = new ServicesPermissions();
        final ServicesTenant te = new ServicesTenant();
        final ServicesToken k = new ServicesToken();
        final ServicesUsers u = new ServicesUsers();
        e.jersey().register(db);
        e.jersey().register(te);
        e.jersey().register(k);
        e.jersey().register(u);
        e.jersey().register(p);

        

        
    }
}
