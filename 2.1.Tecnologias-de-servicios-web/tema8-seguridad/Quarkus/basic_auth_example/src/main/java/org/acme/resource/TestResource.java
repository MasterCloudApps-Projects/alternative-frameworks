package org.acme.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/api/test")
public class TestResource {

  @GET
  @Path("/all")
  @PermitAll
  public String allAccess() {
    return "Public Content.";
  }

  @GET
  @Path("/user")
  @RolesAllowed({"USER", "MODERATOR", "ADMIN"}) 
  public String userAccess() {
    return "User Content.";
  }

  @GET
  @Path("/mod")
  @RolesAllowed({"MODERATOR"}) 
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GET
  @Path("/admin")
  @RolesAllowed({"ADMIN"}) 
  public String adminAccess() {
    return "Admin Board.";
  }
}
