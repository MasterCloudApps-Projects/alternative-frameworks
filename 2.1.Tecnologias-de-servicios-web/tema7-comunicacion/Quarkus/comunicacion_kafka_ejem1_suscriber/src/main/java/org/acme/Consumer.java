package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;


@ApplicationScoped
public class Consumer {

    @Incoming("payload-requests")
    @Outgoing("payload")
    public Payload process(Payload payload) throws InterruptedException {
        System.out.println(payload); 
        return payload;
    }
}