package org.acme.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.entity.User;
import org.acme.jwt.JwtUtils;
import org.acme.request.LoginRequest;
import org.acme.service.UserService;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@Path("/login")
public class LoginResource {
   
  @Inject
  UserService userService;

  @Inject
  JwtUtils jwtUtils;

  @ConfigProperty(name = "jwtExpirationMs")
  int jwtExpirationMs;

  @POST
  public Response login(@Valid LoginRequest loginRequest) {
    if (userService.login(loginRequest)) {
      User user = userService.getUser(loginRequest.getUsername());
      List<String> roles = user.getRoles().stream().map(r -> r.getName()).toList();
      String authJWT = jwtUtils.generateJwtToken(user.getUsername(), roles, jwtExpirationMs);
      String refreshedJWt = jwtUtils.generateJwtToken(user.getUsername(), roles, jwtExpirationMs * 2);
      return Response.ok().header("justAuthenticatedToken", authJWT)
        .header("refreshedToken", refreshedJWt).build();
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }

}

