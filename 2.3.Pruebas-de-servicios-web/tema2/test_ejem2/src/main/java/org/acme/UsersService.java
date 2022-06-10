package org.acme;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsersService {

	private List<User> users = 
		Arrays.asList(new User("Pepe"));
	
	public List<User> getUsers() {
		return users;
	}
}
