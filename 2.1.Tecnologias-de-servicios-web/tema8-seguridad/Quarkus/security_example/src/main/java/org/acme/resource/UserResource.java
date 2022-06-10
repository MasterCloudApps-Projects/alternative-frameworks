package org.acme.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.acme.entity.Role;
import org.acme.model.RoleDTO;
import org.acme.model.UserDTO;
import org.acme.model.UserResponseDTO;
import org.acme.request.RoleToUserForm;
import org.acme.service.UserService;


@Path("/api")
public class UserResource {
   
  @Inject
  UserService userService;

  @GET
  @Path("/users")
  public Response getUsers() {
    return Response.ok(userService.getUsers()).build();
  }

  @POST
  @Path("/user/save")
  public Response saveUser(UserDTO user) {
    UserResponseDTO userResponseDTO = userService.saveUser(user);
    return Response.ok().entity(userResponseDTO).build();
  }

  @POST
  @Path("/role/save")
  public Response saveRole(RoleDTO role) {
    Role newRole = userService.saveRole(role);
    return Response.ok().entity(newRole).build();
  }

  @POST
  @Path("/role/enroll")
  public Response addRoleToUser(RoleToUserForm roleToUserForm) {
    return userService.addRoleToUser(roleToUserForm);
  }

}

