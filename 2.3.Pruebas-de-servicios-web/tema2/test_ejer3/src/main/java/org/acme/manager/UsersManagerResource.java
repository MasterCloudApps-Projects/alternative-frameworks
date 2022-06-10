package org.acme.manager;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.acme.User;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.spi.HttpRequest;


@Path("/manager/users")
public class UsersManagerResource {

	@RestClient
	@Inject
	UserService userService;

	@Context
	HttpRequest request;

	@Path("/")
	@GET
	public Response users() {
		return userService.getUsers();
	}

	@GET
	@Path("/{id}")
	public Response getUser(@PathParam("id") long id) {

		return userService.getUser(id);
	}

	@POST
	@Path("/")
	public Response createUser(@Valid User user) {

		return userService.postUser(user);
	}

	@PUT
	@Path("/{id}")
	public Response updateUser(@PathParam("id") long id, @Valid  User user) {

		return userService.putUser(id, user);
	}

}
