package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.inject.Inject;
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


@Path("/posts")
public class PostResource {

	@Inject
	PostService posts;

	@Context
    HttpRequest request;

	@GET
	public Collection<Post> getPosts() {
		return posts.findAll();
	}

	@Path("/{id}")
	@GET
	public Response getPost(@PathParam("id") long id) {

		Post post = posts.findById(id);

		if (post != null) {
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	public Response createPost(Post post) throws URISyntaxException {

		posts.save(post);

		URI location = new URI(request.getUri().getRequestUri() + post.getId().toString());

		return Response.created(location).entity(post).build();
	}

	@Path("/{id}")
	@PUT
	public Response replacePost(@PathParam("id") long id, Post newPost) {

		Post post = posts.findById(id);

		if (post != null) {

			newPost.setId(id);
			posts.save(newPost);

			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Path("/{id}")
	@DELETE
	public Response deletePost(@PathParam("id") long id) {

		Post post = posts.findById(id);

		if (post != null) {
			posts.deleteById(id);
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
