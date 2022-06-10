package org.acme;

import java.util.UUID;

public class Payload {

    public UUID id;
    public String value;

    public Payload() { 

    }

    public Payload(String value) {
        this.id = UUID.randomUUID();
        this.value = value;
    }

    public Payload(UUID id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Payload [id=" + id + ", value=" + value + "]";
    }
    
}
