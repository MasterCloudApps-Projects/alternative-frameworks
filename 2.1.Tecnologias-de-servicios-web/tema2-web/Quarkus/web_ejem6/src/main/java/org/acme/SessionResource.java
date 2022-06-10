package org.acme;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/")
public class SessionResource {

    @Inject
    Template formulario; 

    @Inject
    Template datos;

    @Inject
    HttpSession session;
   
    private String infoCompartida;

    @POST
    @Path("procesarFormulario")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance procesarFormulario(@FormParam("info") String info) {

        session.setAttribute("infoUsuario", info);
        infoCompartida = info;

        return formulario.instance();  
    }

    @GET
    @Path("mostrarDatos")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance mostrarDatos() {

        String infoUsuario = (String) session.getAttribute("infoUsuario");
	
		return datos.data("infoCompartida", infoCompartida, "infoUsuario", infoUsuario);
    }
}