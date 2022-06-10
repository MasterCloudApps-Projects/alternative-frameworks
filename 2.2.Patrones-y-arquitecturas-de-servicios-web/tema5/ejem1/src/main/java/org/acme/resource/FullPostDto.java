package org.acme.resource;

import java.util.ArrayList;
import java.util.List;

import org.acme.model.Comment;

public class FullPostDto {
	
	private long id = -1;

	private String title;

	private String content;

	private List<Comment> comments = new ArrayList<>();

	public FullPostDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	
}
