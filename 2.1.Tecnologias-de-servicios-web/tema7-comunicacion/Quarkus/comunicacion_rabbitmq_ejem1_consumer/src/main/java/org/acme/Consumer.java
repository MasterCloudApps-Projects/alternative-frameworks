package org.acme;

import java.util.concurrent.ExecutionException;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;


@ApplicationScoped
public class Consumer {

    @Incoming("payload")
    @Blocking
    public void process(JsonObject json) throws InterruptedException, ExecutionException {
        Payload payload = json.mapTo(Payload.class);
        System.out.println("Consumer:" + payload); 
    }
}