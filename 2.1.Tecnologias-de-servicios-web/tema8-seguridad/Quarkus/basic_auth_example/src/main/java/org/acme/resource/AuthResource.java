package org.acme.resource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.entity.User;
import org.acme.entity.ERole;
import org.acme.repository.UserRepository;
import org.acme.request.SignupRequest;

import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Path("/api/auth")
public class AuthResource {
    
  @Inject
  UserRepository userRepository;


  @POST
  @Path("/signup")
  @Transactional
  public Response registerUser(@Valid SignupRequest signUpRequest) {
    if (userRepository.find("username", signUpRequest.getUsername()).firstResultOptional().isPresent()) {
      return Response
          .status(Status.BAD_REQUEST)
          .entity("Error: Username is already taken!").build();
    }

    List<String> roles = Arrays.asList(ERole.values()).stream().map(e -> e.toString()).toList();

    if (validateRoles(roles, signUpRequest.getRoles())) {

      User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        BcryptUtil.bcryptHash(signUpRequest.getPassword()), signUpRequest.getRoles());

      userRepository.persist(user);

      return Response.ok("User registered successfully!").build();
    }

    return Response.status(Status.BAD_REQUEST).entity("Error: A Role is not found.").build();

  }

  private static boolean validateRoles(List<String> roles, Set<String> inputRoles){
    return inputRoles.stream().allMatch(e -> roles.contains(e));
  }
}
