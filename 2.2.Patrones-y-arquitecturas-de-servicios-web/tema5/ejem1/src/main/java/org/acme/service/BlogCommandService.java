package org.acme.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.model.Comment;
import org.acme.model.Post;
import org.acme.repository.CommentRepository;
import org.acme.repository.PostRepository;
import org.acme.resource.CommentDto;
import org.acme.resource.FullPostDto;
import org.modelmapper.ModelMapper;


@ApplicationScoped
public class BlogCommandService {

	@Inject
	PostRepository postRepository;
	
	@Inject
	CommentRepository commentRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Transactional
	public Post addPost(FullPostDto post) {
		Post newPost = convertFullPostDtoToEntity(post);
		postRepository.persist(newPost);
		return newPost;
	}

	@Transactional
	public Comment addComment(long postId, CommentDto comment) {
		Post post = postRepository.findById(postId);
		
		if (post == null) {
			throw new EntityNotFoundException();
		}
		
		Comment savedComment = convertCommentDtoToEntity(comment);		
		commentRepository.persist(savedComment);
		
		post.getComments().add(savedComment);
		
		postRepository.persist(post);
		return savedComment;
	}

	@Transactional
	public Response deleteComment(long postId, long commentId) {
		
		Post post = postRepository.findById(postId);
		
		if (post == null) {
			return Response.status(Status.NOT_FOUND).entity("Post Not Found").build();
		}

		Comment comment = commentRepository.findById(commentId);

		if (comment == null) {
			return Response.status(Status.NOT_FOUND).entity("Comment Not Found").build();
		}
		
		post.deleteComment(comment);
		postRepository.persist(post);
		commentRepository.deleteById(commentId);

		return Response.status(Status.NO_CONTENT).build();

	}

	private Post convertFullPostDtoToEntity(FullPostDto post) {
		return new Post(post.getTitle(), post.getContent());
	}

	private Comment convertCommentDtoToEntity(CommentDto comment) {
		return modelMapper.map(comment, Comment.class);
	}


}
