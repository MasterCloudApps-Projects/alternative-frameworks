package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/anuncio")
public class AnuncioResource {

	@GET
	public Anuncio getAnuncio() {
		return new Anuncio("Pepe","Vendo moto","...");
	}

}