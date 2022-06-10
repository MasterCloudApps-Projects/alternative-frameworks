package org.acme.jwt;


import java.util.Set;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class JwtUtils {

  @ConfigProperty(name = "jwtExpirationMs")
  int jwtExpirationMs;

  public String generateJwtToken(String username, Set<String> roles) {

    return Jwt.groups(roles) 
      .claim(Claims.preferred_username, username) 
      .expiresIn(jwtExpirationMs)
      .sign();

  }

}
