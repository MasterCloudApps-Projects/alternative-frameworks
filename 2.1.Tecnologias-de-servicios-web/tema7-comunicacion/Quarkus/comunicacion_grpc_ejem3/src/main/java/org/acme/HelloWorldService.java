package org.acme;

import java.util.concurrent.atomic.AtomicInteger;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HelloWorldService implements HelloService {

    AtomicInteger counter = new AtomicInteger();

    @Override
    public Uni<HelloResponse> hello(HelloRequest request) {
        int count = counter.incrementAndGet();
        String name = request.getName();
        return Uni.createFrom().item("Hello " + name)
                .map(res -> HelloResponse.newBuilder().setMessage(res).setCount(count).build());
    }
}
