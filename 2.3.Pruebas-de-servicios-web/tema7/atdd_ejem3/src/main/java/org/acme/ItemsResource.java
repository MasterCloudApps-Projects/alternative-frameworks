package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
public class ItemsResource {

	@Context
	HttpRequest request;

	private Map<Long, Item> items = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();

	@Path("/")
	@GET
	public Collection<Item> items() {
		return items.values();
	}

	@Path("/")
	@POST
	public Response nuevoItem(Item item) throws URISyntaxException {

		long id = lastId.incrementAndGet();
		item.setId(id);
		items.put(id, item);

		URI location = new URI(request.getUri().getRequestUri() + String.valueOf(item.getId()));

		return Response.created(location).entity(item).build();
	}

	@Path("/{id}")
	@PUT
	public Response actulizaItem(@PathParam("id") long id, Item itemActualizado) throws URISyntaxException {

		Item item = items.get(id);

		if (item != null) {

			itemActualizado.setId(id);
			items.put(id, itemActualizado);

			return Response.ok(itemActualizado).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Path("/{id}")
	@GET
	public Response getItem(@PathParam("id") long id) {

		Item item = items.get(id);

		if (item != null) {
			return Response.ok(item).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Path("/{id}")
	@DELETE
	public Response borraItem(@PathParam("id") long id) {

		Item item = items.remove(id);

		if (item != null) {
			return Response.ok(item).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
