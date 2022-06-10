package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/enlace")
public class EnlaceResource {

    @Inject
    Template enlace; 

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance enlace(@QueryParam("num") String num) {
        return enlace.data("num", num);
    }
}