package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Post;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post>{

}
