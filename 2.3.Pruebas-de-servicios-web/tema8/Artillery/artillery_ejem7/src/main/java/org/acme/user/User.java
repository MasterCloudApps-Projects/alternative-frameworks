package org.acme.user;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.elytron.security.common.BcryptUtil;

@Entity
@Table(name = "user",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username")
    })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	@JsonIgnore
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles;

	private boolean logged;

	public User() {
	}

	public User(String name, String password, String... roles) {
		this.username = name;
		this.password = BcryptUtil.bcryptHash(password);
		this.roles = Set.of(roles);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public boolean isLogged() {
		return this.logged;
	}

	public void setLooged(boolean logged) {
		this.logged = logged;
	}

}