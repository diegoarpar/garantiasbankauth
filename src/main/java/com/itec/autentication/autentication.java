/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.autentication;

import com.itec.configuration.ConfigurationAutentication;
import com.itec.oauth.Autenticator;
import com.itec.oauth.Autorization;
import com.itec.pojo.User;
import com.itec.services.*;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Environment;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 *
 * @author iTech-Pc
 */
public class autentication extends  Application<ConfigurationAutentication>{
     public static void main (String[] args)  throws Exception{
         if(args.length > 0) new autentication().run(args);
         else {
             new autentication().run(new String[] { "server","./src/main/java/com/itec/autentication/configAuth.yml" });
         }
    }
    

     @Override
    public void run(ConfigurationAutentication t, Environment e) throws Exception {
            t.getTemplate();
            t.getDefaultName();
        FilterRegistration.Dynamic filter = e.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
                                    filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, e.getApplicationContext().getContextPath() + "*");
                                    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
                                    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
                                    filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization, Date,Accept-Encoding");
                                    filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

         e.jersey().register(new AuthDynamicFeature(
                 new OAuthCredentialAuthFilter.Builder<User>()
                         .setAuthenticator(new Autenticator())
                         .setAuthorizer(new Autorization())
                         .setPrefix("Bearer")
                         .buildAuthFilter()));

        e.jersey().register(RolesAllowedDynamicFeature.class);

        e.jersey().register(Services.class);
        e.jersey().register(ServicesTenant.class);
        e.jersey().register(ServicesToken.class);
        e.jersey().register(ServicesUsers.class);
        e.jersey().register(ServicesPermissions.class);
        e.jersey().register(ServicesRoles.class);
        e.jersey().register(ServicesParametricos.class);
        e.jersey().register(GZipEncoder.class);
        e.jersey().register(EncodingFilter.class);

        

        
    }
}
