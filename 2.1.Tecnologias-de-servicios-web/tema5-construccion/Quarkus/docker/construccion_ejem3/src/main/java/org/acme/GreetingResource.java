package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/greeting")
public class GreetingResource {

	@Inject
    Template greeting_template;

	@GET
	public TemplateInstance greeting() {

		return greeting_template.data("name", "Mundo");
	}
	

}
