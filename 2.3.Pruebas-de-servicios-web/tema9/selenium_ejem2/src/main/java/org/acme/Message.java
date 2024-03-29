package org.acme;

import javax.ws.rs.FormParam;

public class Message {

	@FormParam("title")
	private String title;

	@FormParam("body")
	private String body;
	
	public Message() {
		
	}

	public Message(String title, String body) {
		super();
		this.title = title;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Message [title=" + title + ", body=" + body + "]";
	}

}
