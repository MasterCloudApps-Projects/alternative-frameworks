package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RegisterRestClient(baseUri = "https://www.googleapis.com/books/v1")
@ApplicationScoped
public interface BookService {

    @GET
	@Path("/volumes")
    public ObjectNode getByQuery(@QueryParam("q") String q);
}