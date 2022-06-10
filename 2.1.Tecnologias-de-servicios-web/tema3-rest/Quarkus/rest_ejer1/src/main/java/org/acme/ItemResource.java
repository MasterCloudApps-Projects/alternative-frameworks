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


@Path("/items")
public class ItemResource {

	@Inject
	ItemService items;

	@Context
    HttpRequest request;

	@GET
	public Collection<Item> getItems() {
		return items.findAll();
	}

	@Path("/{id}")
	@GET
	public Response getItem(@PathParam("id") long id) {

		Item post = items.findById(id);

		if (post != null) {
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	public Response createItem(Item post) throws URISyntaxException {

		items.save(post);

		URI location = new URI(request.getUri().getRequestUri() + String.valueOf(post.getId()));

		return Response.created(location).entity(post).build();
	}

	@Path("/{id}")
	@PUT
	public Response replaceItem(@PathParam("id") long id, Item newItem) {

		Item post = items.findById(id);

		if (post != null) {

			newItem.setId(id);
			items.save(newItem);

			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Path("/{id}")
	@DELETE
	public Response deleteItem(@PathParam("id") long id) {

		Item post = items.findById(id);

		if (post != null) {
			items.deleteById(id);
			return Response.ok(post).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
