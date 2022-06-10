package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Player;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PlayerRepository implements PanacheRepository<Player> {
	
}