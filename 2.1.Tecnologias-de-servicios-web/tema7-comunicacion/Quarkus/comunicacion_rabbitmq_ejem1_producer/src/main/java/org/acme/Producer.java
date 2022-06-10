package org.acme;

import java.util.concurrent.ExecutionException;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class Producer {

    @Channel("payload")
    Emitter<Payload> payloadEmitter;

    @Scheduled(every="1s")
    public void produce() throws InterruptedException, ExecutionException {
        Payload payload = new Payload("payload");
        payloadEmitter.send(payload);
        System.out.println("Producer:" + payload); 
    }

}