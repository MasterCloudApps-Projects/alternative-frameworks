package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Team;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {
	
}