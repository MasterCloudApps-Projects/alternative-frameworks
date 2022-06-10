package org.acme;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/guardaranuncio")
public class AnuncioResource {

    @Inject
    Template anuncio; 

    @POST
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance guardarAnuncio(@BeanParam Anuncio anun) {
        return anuncio.data(anun);
    }
}