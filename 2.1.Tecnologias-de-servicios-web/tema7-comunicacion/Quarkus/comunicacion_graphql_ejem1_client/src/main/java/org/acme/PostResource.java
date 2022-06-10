package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
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
	PostsClientApi postsClientApi;

	@Context
    HttpRequest request;

    @GET
    @Path("/")
	public Response getPosts() {
		return Response.ok(postsClientApi.getPosts()).build();
	}

    @GET
	@Path("/{id}")
	public Response getPost(@PathParam("id") long id) {

		Post post = postsClientApi.getPost(id);

		if (post != null) {
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

    @POST
	@Path("/")
	public Response createPost(Post post) throws URISyntaxException {

		post = postsClientApi.createPost(post);

		URI location = new URI(request.getUri().getRequestUri() + post.getId().toString());

		return Response.created(location).entity(post).build();
	}

    @PUT
	@Path("/{id}")
	public Response replacePost(@PathParam("id") long id, Post newPost) {
		return Response.ok(postsClientApi.replacePost(id, newPost)).build();
	}

	@DELETE
    @Path("/{id}")
	public Response deletePost(@PathParam("id") long id) {
		return Response.ok(postsClientApi.deletePost(id)).build();
	}

}