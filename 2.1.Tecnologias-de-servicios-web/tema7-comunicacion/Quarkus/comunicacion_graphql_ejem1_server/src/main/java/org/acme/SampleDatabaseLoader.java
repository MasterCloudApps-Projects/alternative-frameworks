package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
public class SampleDatabaseLoader {

	@Inject 
	PostRepository posts;
	
	@Transactional
	public void init(@Observes StartupEvent ev) {
		posts.persist(new Post("Pepe", "Vendo moto", "Barata, barata"));
		posts.persist(new Post("Juan", "Compro coche", "Pago bien"));
	}
}
