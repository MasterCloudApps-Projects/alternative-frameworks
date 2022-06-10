package org.acme;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/anuncio")
public class UsersResource {

	@Inject
	UsersService usersService;

	@GET
	public List<User> getAnuncio() {
		return usersService.getUsers();
	}
}