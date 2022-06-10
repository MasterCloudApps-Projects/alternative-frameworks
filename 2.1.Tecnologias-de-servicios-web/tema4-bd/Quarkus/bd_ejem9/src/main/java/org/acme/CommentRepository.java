package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment> {
	
}