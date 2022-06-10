package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/enlace")
public class EnlaceResource {

    @Inject
    Template enlace; 

    @GET
    @Path("/{num}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance enlace(@PathParam("num") String num) {
        return enlace.data("num", num);
    }
}