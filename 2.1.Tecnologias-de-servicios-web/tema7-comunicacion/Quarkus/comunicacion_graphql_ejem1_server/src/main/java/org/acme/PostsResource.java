package org.acme;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import io.vertx.ext.web.handler.HttpException;


@GraphQLApi
@ApplicationScoped
public class PostsResource {

	@Inject
	PostRepository posts;

	@Query
	public Collection<Post> posts() {
		return posts.listAll();
	}

	@Query
	public Post post(long id) {
		return posts.findById(id);
	}

	@Mutation
	@Transactional
	public Post createPost(Post post) {

		posts.persist(post);

		return post;
	}

	@Mutation
	@Transactional
	public Post replacePost(long id, Post post) {

		Post existingPost =  posts.findById(id);

		if (existingPost != null) {
			post.setId(id);
			posts.getEntityManager().merge(post);
			return post;
		}
	
		throw new HttpException(Status.NOT_FOUND.getStatusCode(), "Post with " + id + " not found");
	}

	@Mutation
	@Transactional
	public Post deletePost(long id) {

		Post post = posts.findById(id);

		if (post != null) {
			posts.delete(post);
			return post;
		}

		throw new HttpException(Status.NOT_FOUND.getStatusCode(), "Post with " + id + " not found");
		
	}
}

