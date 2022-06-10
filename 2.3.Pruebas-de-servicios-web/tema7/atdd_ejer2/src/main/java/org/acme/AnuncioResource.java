package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.spi.HttpRequest;


@Path("/anuncios")
public class AnuncioResource {

	@javax.inject.Inject
	AnunciosRepository repository;

	@Context
	HttpRequest request;

	@POST
	@Path("/")
	@Transactional
	public Response nuevoAnuncio(Anuncio anuncio) throws URISyntaxException {

		repository.persist(anuncio);
		
		URI location = new URI(request.getUri().getRequestUri() + String.valueOf(anuncio.getId()));

		return Response.created(location).entity(anuncio).build();		
	}


	@GET
	@Path("/{id}")
	public Response verAnuncio(@PathParam("id") long id) {
		Optional<Anuncio> anuncio =  repository.findByIdOptional(id);
		if(anuncio.isPresent()){
			return Response.ok(anuncio.get()).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	public Response borrarAnuncio(@PathParam("id") long id) {
		
		Optional<Anuncio> anuncioToDelete = repository.findByIdOptional(id);
		if (anuncioToDelete.isPresent()) {
			repository.delete(anuncioToDelete.get());
			return Response.ok(anuncioToDelete.get()).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}