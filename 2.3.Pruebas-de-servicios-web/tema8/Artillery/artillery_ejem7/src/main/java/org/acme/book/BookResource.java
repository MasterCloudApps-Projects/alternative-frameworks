package org.acme.book;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/books")
public class BookResource {

	private static final Logger log = LoggerFactory.getLogger(BookResource.class);

	@Inject
	BookRepository repository;

    @Context
    HttpRequest request;

	@Path("/")
    @GET
	@PermitAll
	public Collection<Book> getBooks() {
		return repository.listAll();
	}

	@Path("/{id}")
    @GET
	@PermitAll
	public Response getBook(@PathParam("id") long id) {

		log.info("Get book {}", id);
		
		if (repository.findByIdOptional(id).isPresent()) {
			Book book = repository.findById(id);
			return Response.ok().entity(book).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

    @Path("/")
    @POST
	@RolesAllowed({"USER"})
	@Transactional
	public Response createBook(Book book) throws URISyntaxException {
		
		repository.persist(book);

        URI location = new URI(request.getUri().getRequestUri() + String.valueOf(book.getId()));
		
		return Response.created(location).entity(book).build();
	}

    @Path("/{id}")
    @PUT
	@RolesAllowed({"USER"})
	@Transactional
	public Response updateBook(@PathParam("id")long id, Book updatedBook) {
		
		if (repository.findByIdOptional(id).isPresent()) {
			updatedBook.setId(id);
			repository.getEntityManager().merge(updatedBook);
			return Response.ok().entity(updatedBook).build();
		} else {
            return Response.status(Status.NOT_FOUND).build();
		}
	}

    @Path("/{id}")
    @DELETE
	@RolesAllowed({"ADMIN"})
	@Transactional
	public Response deleteBook(@PathParam("id")long id) {

		if (repository.findByIdOptional(id).isPresent()) {
			repository.deleteById(id);
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}