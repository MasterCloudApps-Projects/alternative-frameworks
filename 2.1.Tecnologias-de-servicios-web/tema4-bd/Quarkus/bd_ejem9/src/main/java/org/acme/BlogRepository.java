package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Blog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {
	
}