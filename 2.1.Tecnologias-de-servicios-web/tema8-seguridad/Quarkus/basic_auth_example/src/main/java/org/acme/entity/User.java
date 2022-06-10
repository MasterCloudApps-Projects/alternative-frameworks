package org.acme.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@Entity
@Table(name = "user",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username")
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  private String email;

  @NotBlank
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles;

  public User(String username, String email, String password, Set<String> roles) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }
}
