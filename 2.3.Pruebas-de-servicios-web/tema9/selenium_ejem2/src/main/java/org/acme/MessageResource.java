package org.acme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;


@Path("/")
public class MessageResource {

	@Inject
    Template index; 

	private List<Message> messages = Collections.synchronizedList(new ArrayList<>());

	@GET
	public TemplateInstance showMessages() {

		return index.data("messages", this.messages);
	}

	@POST
	public TemplateInstance newMessage(@BeanParam Message message) {

		messages.add(message);

		return index.data("messages", this.messages);
	}

}
