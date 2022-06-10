package org.acme;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.model.Player;
import org.acme.model.Team;
import org.hibernate.Hibernate;

import io.quarkus.runtime.StartupEvent;


@Path("/")
public class TeamResource {

	@Inject
	TeamRepository teamRepository;
	
	@Inject
	PlayerRepository playerRepository;

	@Transactional
	public void init(@Observes StartupEvent ev) {

		Player p1 = new Player("Torres", 10);
		Player p2 = new Player("Iniesta", 10);
		
		playerRepository.persist(p1);
		playerRepository.persist(p2);
		
		Team team = new Team("Selección", 1);
		
		team.getPlayers().add(p1);
		team.getPlayers().add(p2);

		teamRepository.persist(team);
	}

	@GET
	@Path("teams")
	public List<Team> getTeams() {
		return teamRepository.listAll();
	}
	
	@GET
	@Path("players")
	public List<Player> getPlayers() {
		return playerRepository.listAll();
	}
	
	//Deleting a team doesn't delete its associated players
	@DELETE
	@Path("teams/{id}")
	@Transactional
	public Team deleteTeam(@PathParam("id") Long id) {
		Team team = teamRepository.findById(id);
		//Force loading players from database to be returned as JSON
		Hibernate.initialize(team.getPlayers());		
		teamRepository.deleteById(id);
		return team;
	}
	
	//A player only can be deleted if it has no associated team
	@DELETE
	@Path("players/{id}")
	@Transactional
	public Player deleteProject(@PathParam("id")  Long id) {
		Player player = playerRepository.findById(id);
		playerRepository.deleteById(id);
		return player;
	}
}
