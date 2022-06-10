package org.acme;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.acme.BooksService.BooksResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.mutiny.Uni;


@Path("/")
public class BookResource {

    @Inject
    @RestClient
    BooksService booksService;

    @GET
    @Path("/booktitles")
    public Response getBookTitles(@QueryParam("query") String query) throws InterruptedException, ExecutionException {

        Uni<BooksResponse> data = booksService.getByQuery(query);

        List<String> books = data.subscribe().asCompletionStage().get().items.stream().map(e -> e.volumeInfo.title).toList();
      
        return Response.ok(books).build();
        
    }

}