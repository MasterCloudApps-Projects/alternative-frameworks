package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Comment;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment>{

}
