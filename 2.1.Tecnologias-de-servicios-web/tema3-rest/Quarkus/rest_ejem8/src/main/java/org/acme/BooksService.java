package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RegisterRestClient(baseUri = "https://www.googleapis.com/books/v1")
@ApplicationScoped
public interface BooksService {

    @GET
	@Path("/volumes")
    public ObjectNode getByQuery(@QueryParam("q") String q);
}