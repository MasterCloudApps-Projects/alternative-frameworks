package org.acme;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PayloadDeserializer extends ObjectMapperDeserializer<Payload> {

    public PayloadDeserializer () {
        super(Payload.class);
    }
}