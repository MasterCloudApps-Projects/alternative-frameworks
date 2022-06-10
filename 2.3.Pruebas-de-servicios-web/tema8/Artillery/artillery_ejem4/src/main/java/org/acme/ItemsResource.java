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

	private Map<Long, Item> items = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();

	@Context
    HttpRequest request;
	
	public ItemsResource(){
		long id = lastId.incrementAndGet();
		Item item = new Item();
		item.setDescription("Leche");
		item.setChecked(true);
		items.put(id, item);
	}

	@GET
	@Path("/")
	public Collection<Item> items() {
		return items.values();
	}

	@POST
	@Path("/")
	public Response nuevoItem(Item item) throws URISyntaxException {

		long id = lastId.incrementAndGet();
		item.setId(id);
		items.put(id, item);

		URI location = new URI(request.getUri().getRequestUri() + String.valueOf(item.getId()));

		return Response.created(location).entity(item).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateItem(@PathParam("id") long id, Item itemActualizado) {

		Item item = items.get(id);

		if (item != null) {

			itemActualizado.setId(id);
			items.put(id, itemActualizado);

			return Response.ok(itemActualizado).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/{id}")
	public Response getItem(@PathParam("id") long id) {

		Item item = items.get(id);

		if (item != null) {
			return Response.ok(item).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteItem(@PathParam("id") long id) {

		Item item = items.remove(id);

		if (item != null) {
			return Response.ok(item).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
