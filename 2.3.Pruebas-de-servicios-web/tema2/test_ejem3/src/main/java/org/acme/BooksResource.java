package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/booktitles")
public class BooksResource {

	@Inject
    @RestClient
    BookService bookService;

	@GET
	public List<String> getBooks(@QueryParam("query") String query) {

		ObjectNode data = bookService.getByQuery(query);

		List<String> bookTitles = new ArrayList<String>();

		ArrayNode items = (ArrayNode) data.get("items");
		for (int i = 0; i < items.size(); i++) {
			JsonNode item = items.get(i);
			String bookTitle = item.get("volumeInfo").get("title").asText();
			bookTitles.add(bookTitle);
		}

		return bookTitles;
	}
}
