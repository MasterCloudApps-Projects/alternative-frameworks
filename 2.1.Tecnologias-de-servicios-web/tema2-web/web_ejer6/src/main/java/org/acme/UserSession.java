package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserSession {

    private String user;
	private int numPosts;

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public int getNumPosts() {
		return this.numPosts;
	}

	public void incNumPosts() {
		this.numPosts++;
	}

}