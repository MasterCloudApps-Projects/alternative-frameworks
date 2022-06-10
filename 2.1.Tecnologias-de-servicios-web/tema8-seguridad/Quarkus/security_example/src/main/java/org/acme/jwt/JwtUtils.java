package org.acme.jwt;


import java.util.Collection;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class JwtUtils {

  public String generateJwtToken(String username, Collection<String> roles, int expirationMs) {
    return Jwt.groups(new HashSet<>(roles)) 
      .claim(Claims.preferred_username, username) 
      .expiresIn(expirationMs)
      .sign();
  }

}
