package org.acme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateGlobal;

@Path("/")
public class DefaultResource {


	@Inject
    Template page;

	@Inject
    Template basic_template;

	@Inject
    Template list_template;

	@Inject
    Template list_obj_template;

	@TemplateGlobal
    static String userName() {
        return "Juan";
    }

    @GET
	@Path("page")
	public TemplateInstance page() {

		return page.data("userName", DefaultResource.userName());
	}
	
	@GET
	@Path("basic")
	public TemplateInstance basic() {

		return basic_template.data("name", "World", "hello", true);
	}

	@GET
	@Path("list")
	public TemplateInstance iteration() {

		return list_template.data("colors", Arrays.asList("Red", "Blue", "Green"));
	}

	@GET
	@Path("list_objects")
	public TemplateInstance iterationObj() {

		List<Person> people = new ArrayList<>();

		people.add(new Person("Pepe","Pérez"));
		people.add(new Person("Juan","González"));
		people.add(new Person("Ramón","Lucas"));

		return list_obj_template.data("people", people);
	}
}