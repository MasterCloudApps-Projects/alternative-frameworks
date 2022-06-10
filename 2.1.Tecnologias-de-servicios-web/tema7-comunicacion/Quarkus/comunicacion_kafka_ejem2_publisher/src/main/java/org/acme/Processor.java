package org.acme;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;


@Path("/payload")
public class Processor {

    @Channel("payload-requests")
    Emitter<Payload> payloadEmitter;

    @Channel("payload")
    Multi<Payload> payloadReciever;

    @POST
    @Path("/")
    public void send (@FormParam("payload") String payload) {
        payloadEmitter.send(new Payload(payload));
	}

    @GET
    @Path("/")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Blocking
    public Response recieve() throws InterruptedException, ExecutionException {
        return Response.ok().entity(payloadReciever.toUni().await().atMost(Duration.ofSeconds(60))).build();
	}
    
}
