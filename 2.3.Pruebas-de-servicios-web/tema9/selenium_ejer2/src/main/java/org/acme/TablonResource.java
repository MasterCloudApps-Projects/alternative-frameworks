package org.acme;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class TablonResource {

	@Inject
	Usuario usuario;

	@Inject
	HttpSession session;

	@Inject
	Template tablon;

	@Inject
	Template nuevo_anuncio;

	@Inject
	Template anuncio_guardado;

	@Inject
	Template ver_anuncio;

	@Inject
	Template anuncio_borrado;

	private ConcurrentMap<Long, Anuncio> anuncios = new ConcurrentHashMap<>();
	private AtomicLong nextId = new AtomicLong();

	public TablonResource() {
		addAnuncio(new Anuncio("Pepe", "Hola caracola", "XXXX"));
		addAnuncio(new Anuncio("Juan", "Hola caracola", "XXXX"));
	}

	private void addAnuncio(Anuncio anuncio) {
		Long id = nextId.getAndIncrement();
		anuncio.setId(id);
		anuncios.put(id, anuncio);
	}

	@GET
	public TemplateInstance tablon() {

		return tablon.data("anuncios", anuncios.values()).data("bienvenida", session.isNew());
	}

	@POST
	@Path("anuncio/nuevo")
	public TemplateInstance nuevoAnuncio(@BeanParam Anuncio anuncio) {

		addAnuncio(anuncio);

		usuario.setNombre(anuncio.getNombre());
		usuario.incAnuncios();

		return anuncio_guardado.instance();

	}

	@GET
	@Path("/anuncio/nuevo_form")
	public TemplateInstance nuevoAnuncioForm() {

		return nuevo_anuncio.data("nombre", usuario.getNombre()).data("num_anuncios", usuario.getNumAnuncios());

	}

	@GET
	@Path("anuncio/{id}")
	public TemplateInstance nuevoAnuncio(@PathParam("id") Long id) {

		Anuncio anuncio = anuncios.get(id);

		return ver_anuncio.data("anuncio", anuncio);

	}
	
	@GET
	@Path("anuncio/{id}/borrar")
	public TemplateInstance borrarAnuncio(@PathParam("id") Long id) {

		Anuncio anuncio = anuncios.remove(id);

		return anuncio_borrado.data("anuncio", anuncio);
	}
}