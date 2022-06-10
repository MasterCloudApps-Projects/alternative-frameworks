package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

	public int getNumUsers() {
		return 5;
	}
}
