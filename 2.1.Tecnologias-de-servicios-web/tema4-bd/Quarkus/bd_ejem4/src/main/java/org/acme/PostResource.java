package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.enterprise.event.Observes;
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

import io.quarkus.runtime.StartupEvent;

@Path("/posts")
public class PostResource {

	@Inject
	PostRepository posts;

	@Context
    HttpRequest request;

	/* PostConstruct requieres manually Transaction
	   https://stackoverflow.com/questions/73000578/using-transaction-in-postconstruct
	*/
	@Transactional
	public void init(@Observes StartupEvent ev) {
		posts.persist(new Post("Pepe", "Vendo moto", "Barata, barata"));
		posts.persist(new Post("Juan", "Compro coche", "Pago bien"));
	}
	
	@GET
	public Collection<Post> getPosts() {
		return posts.listAll();
	}

	@GET
	@Path("/{id}")
	public Response getPost(@PathParam("id") long id) {

		Post  post = posts.findById(id);

		if (post != null) {
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Transactional
	public Response createPost(Post post) throws URISyntaxException {

		posts.persist(post);

		URI location = new URI(request.getUri().getRequestUri() + post.getId().toString());

		return Response.created(location).entity(post).build();
	}

	@PUT
	@Path("/{id}")
	@Transactional
	public Response replacePost(@PathParam("id") long id, Post newPost) {

		if (posts.findById(id) != null) {

			newPost.setId(id);
			posts.getEntityManager().merge(newPost);

			/* 
			 * If there are two different objects, they must be merged or the required fields must be copied one by one 
			 * from the non-living entity to the living entity of the Hibernate session to be updated
			 */

			return Response.ok(newPost).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	public Response deletePost(@PathParam("id") long id) {

		Post  post = posts.findById(id);

		if (post != null) {
			posts.deleteById(id);
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
