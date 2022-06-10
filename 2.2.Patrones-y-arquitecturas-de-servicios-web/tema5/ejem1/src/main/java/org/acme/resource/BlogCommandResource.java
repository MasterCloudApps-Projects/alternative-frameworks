package org.acme.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.acme.model.Comment;
import org.acme.model.Post;
import org.acme.service.BlogCommandService;
import org.jboss.resteasy.spi.HttpRequest;

@Path("/api/post")
public class BlogCommandResource {
	
	@Inject
	BlogCommandService blogService;


	@Context
	HttpRequest request;
	
	@POST
	public Response newPost(FullPostDto post) throws URISyntaxException {

		Post newPost = this.blogService.addPost(post);

		URI location = new URI(request.getUri().getRequestUri() + "/" + String.valueOf(newPost.getId()));

		return Response.created(location).entity(newPost).build();
	}
	
	@Path("/{postId}/comment")
	@POST	
	public Response newComment(@PathParam("postId") long postId, CommentDto comment) throws URISyntaxException {

		Comment newComment = this.blogService.addComment(postId, comment);

		URI location = new URI(request.getUri().getRequestUri() + "/" +  String.valueOf(newComment.getId()));
		
		return Response.created(location).entity(newComment).build();
	}

	@DELETE
	@Path("/{postId}/comment/{commentId}")
	public Response deleteComment(@PathParam("postId") long postId, @PathParam("commentId") long commentId) {
		return this.blogService.deleteComment(postId, commentId);
	}

}
