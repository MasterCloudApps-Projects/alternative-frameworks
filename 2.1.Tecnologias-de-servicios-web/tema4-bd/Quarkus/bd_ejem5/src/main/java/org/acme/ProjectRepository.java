package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Project;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {
	
}