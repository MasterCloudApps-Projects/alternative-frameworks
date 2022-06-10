package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.acme.BooksService.Book;
import org.acme.BooksService.BooksResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class LibrosAutoresResource {

	@Inject
    @RestClient
    BooksService booksService;


	@Path("booktitles")
	@GET
	public List<String> getBooks(@QueryParam("query") String query) {

		BooksResponse data = booksService.getByQuery(query);

		List<String> bookTitles = new ArrayList<String>();

		for (Book book : data.items) {
			bookTitles.add(book.volumeInfo.title);
		}

		return bookTitles;
	}
}
