package org.acme;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
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
    public TemplateInstance guardarAnuncio(@FormParam("nombre") String nombre, @FormParam("asunto") String asunto,
            @FormParam("comentario") String comentario) {
        return anuncio.data("nombre", nombre,"asunto", asunto,"comentario", comentario);
    }
}