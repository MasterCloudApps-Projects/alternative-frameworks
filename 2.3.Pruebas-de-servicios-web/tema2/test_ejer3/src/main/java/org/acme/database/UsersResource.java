package org.acme.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.User;
import org.jboss.resteasy.spi.HttpRequest;

@Path("/users")
public class UsersResource {

	@Context
	HttpRequest request;

	private Map<Long, User> users = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();

	@Path("/")
	@GET
	public Response items() {
		return Response.ok().entity(users.values()).build();
	}


	@POST
	@Path("/")
	public Response createUser(@Valid User user) throws URISyntaxException {

		long id = lastId.incrementAndGet();
		user.setId(id);
		users.put(id, user);

		URI location = new URI(request.getUri().getRequestUri() + String.valueOf(user.getId()));

		return Response.created(location).entity(user).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateUser(@PathParam("id") long id, User updatedUser) {

		User user = users.get(id);

		if (user != null) {

			updatedUser.setId(id);
			users.put(id, updatedUser);

			return Response.ok().entity(updatedUser).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/{id}")
	public Response getUser(@PathParam("id") long id) {

		User user = users.get(id);

		if (user != null) {
			return Response.ok().entity(user).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id")  long id) {

		User user = users.remove(id);

		if (user != null) {
			return Response.ok().entity(user).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}