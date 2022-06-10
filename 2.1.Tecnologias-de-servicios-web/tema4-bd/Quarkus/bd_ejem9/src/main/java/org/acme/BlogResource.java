package org.acme;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.model.Blog;
import org.acme.model.Comment;

import io.quarkus.runtime.StartupEvent;

@Path("/")
public class BlogResource {

	@Inject
	BlogRepository blogRepository;

	@Inject
	CommentRepository commentRepository;

	@Transactional
	public void init(@Observes StartupEvent ev) {

		Blog blog = new Blog("New", "My new product");
		blog.getComments().add(new Comment("Cool", "Pepe"));
		blog.getComments().add(new Comment("Very cool", "Juan"));

		blogRepository.persist(blog);
	}

	@GET
	@Path("blogs")
	public List<Blog> getBlogs() {
		return blogRepository.listAll();
	}

	// Deleting a blog delete its associated comments
	@DELETE
	@Path("blogs/{id}")
	@Transactional
	public Blog deleteBlog(@PathParam("id") Long id) {
		Blog blog = blogRepository.findById(id);
		blogRepository.deleteById(id);
		return blog;
	}

	//A comment only can be deleted if it has no associated blog
	@DELETE
	@Path("comments/{id}")
	@Transactional
	public Comment deleteComment(@PathParam("id") Long id) {
		Comment comment = commentRepository.findById(id);
		commentRepository.deleteById(id);
		return comment;
	}

}
