package org.acme;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@RegisterRestClient(baseUri = "https://www.googleapis.com/books/v1")
@ApplicationScoped
public interface BooksService {

	class BooksResponse {
		public List<Book> items;
	}
	
	class Book {
		public VolumeInfo volumeInfo;
	}
	
	class VolumeInfo {
		public String title;
	}

    @GET
	@Path("/volumes")
    public BooksResponse getByQuery(@QueryParam("q") String q);
}