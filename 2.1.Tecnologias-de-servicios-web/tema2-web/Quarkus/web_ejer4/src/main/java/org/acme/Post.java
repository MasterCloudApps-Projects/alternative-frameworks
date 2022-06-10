package org.acme;

import javax.ws.rs.FormParam;

public class Post {

	@FormParam("user")
	private String user;

	@FormParam("title")
	private String title;

	@FormParam("text")
	private String text;

	public Post() {

	}

	public Post(String user, String title, String text) {
		this.user = user;
		this.title = title;
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Post [user=" + user + ", title=" + title + ", text=" + text + "]";
	}

}