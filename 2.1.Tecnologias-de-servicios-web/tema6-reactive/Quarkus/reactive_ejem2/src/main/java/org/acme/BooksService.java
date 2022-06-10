package org.acme;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@RegisterRestClient(baseUri = "https://www.googleapis.com/books/v1")
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
    public Uni<BooksResponse> getByQuery(@QueryParam("q") String q);
}