package org.acme.manager;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.acme.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient(baseUri = "http://localhost:8080/users")
@ApplicationScoped
public interface UserService {
   
    @GET
    @Path("/")
    public Response getUsers();

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") long id);

    @POST
    @Path("/")
    public Response postUser(@Valid  User user);

    @PUT
    @Path("/{id}")
    public Response putUser(@PathParam("id") long id, @Valid User user);

}