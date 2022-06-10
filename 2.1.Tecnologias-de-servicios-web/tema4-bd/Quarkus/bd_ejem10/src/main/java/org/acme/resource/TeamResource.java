package org.acme.resource;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.model.Player;
import org.acme.model.Team;
import org.acme.repository.PlayerRepository;
import org.acme.repository.TeamRepository;

import io.quarkus.runtime.StartupEvent;


@Path("/")
public class TeamResource {

	@Inject
	TeamRepository teamRepository;
	
	@Inject
	PlayerRepository playerRepository;

	@Transactional
	public void init(@Observes StartupEvent ev) {

		Team team = new Team("Selección", 1);
		
		teamRepository.persist(team);
		
		Player p1 = new Player("Torres", 10);
		Player p2 = new Player("Iniesta", 10);
		
		p1.setTeam(team);
		p2.setTeam(team);
		
		playerRepository.persist(p1);
		playerRepository.persist(p2);
		
	}

	@GET
	@Path("teams")
	public List<Team> getTeams() throws Exception {
		return teamRepository.listAll();
	}
	
	@GET
	@Path("/players/{id}")
	public Player getPlayer(@PathParam("id") long id) throws Exception {
		return playerRepository.findById(id);
	}
}
