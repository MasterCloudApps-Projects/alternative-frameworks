package org.acme.jwt;

import java.util.Set;

import lombok.Data;

@Data
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private String username;
  private Set<String> roles;

  public JwtResponse(String accessToken, String username, Set<String> roles) {
    this.token = accessToken;
    this.username = username;
    this.roles = roles;
  }
}
