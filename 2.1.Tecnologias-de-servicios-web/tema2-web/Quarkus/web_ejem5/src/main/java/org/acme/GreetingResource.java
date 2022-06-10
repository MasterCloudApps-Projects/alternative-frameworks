package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/greeting")
public class GreetingResource {

    @Inject
    UserService userService;

    @Inject
    Template greeting;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return greeting.data("name", userService.getNumUsers() + " users");  
    }
}