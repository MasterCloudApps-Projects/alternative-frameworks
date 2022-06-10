package org.acme;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.quarkus.runtime.StartupEvent;

@Path("/anuncios")
public class AnunciosResource {

	@Inject
	AnunciosRepository repository;

	@Transactional
	public void init(@Observes StartupEvent ev) {
		repository.persist(new Anuncio("Pepe", "Hola caracola", "XXXX"));
		repository.persist(new Anuncio("Juan", "Hola caracola", "XXXX"));
	}

	@GET
	@Path("/")
	public List<Anuncio> getAnuncios() {
		return repository.listAll();
	}

	@POST
	@Path("/")
	@Transactional
	public Anuncio nuevoAnuncio(Anuncio anuncio) {
		repository.persist(anuncio);
		return anuncio;
	}

	@GET
	@Path("/{id}")
	public Anuncio verAnuncio(@PathParam("id") long id) {
		return repository.findById(id);
	}
}