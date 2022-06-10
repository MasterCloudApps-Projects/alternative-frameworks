package org.acme.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.service.BlogQueryService;

@Path("/api/post")
public class BlogQueryResource {
	
	@Inject
	BlogQueryService blogService;
	
	@GET
	public List<BasicPostDto> listPosts() {
		return blogService.getPostsList();
	}
	
	@GET
	@Path("/{id}")
	public Response getPost(@PathParam("id") long id) {
		FullPostDto post = this.blogService.getPost(id);
		if (post == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(post).build();
	}

}
