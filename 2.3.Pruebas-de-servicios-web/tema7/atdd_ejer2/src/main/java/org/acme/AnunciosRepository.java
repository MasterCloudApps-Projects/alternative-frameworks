package org.acme;

import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AnunciosRepository implements PanacheRepository<Anuncio> {
	
}