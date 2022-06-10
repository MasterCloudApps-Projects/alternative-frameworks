package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.acme.HelloServiceGrpc.HelloServiceBlockingStub;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

@Path("/hello")
public class HelloWorldEndpoint {

    @GrpcClient("hello")
    HelloServiceBlockingStub blockingHelloService;

    @GrpcClient("hello")
    HelloService helloService;

    @GET
    @Path("/blocking/{name}")
    public String helloBlocking(String name) {
        HelloResponse reply = blockingHelloService.hello(HelloRequest.newBuilder().setName(name).build());
        return generateResponse(reply);
    }

    @GET
    @Path("/reactive/{name}")
    public Uni<String> helloMutiny(String name) {
        return helloService.hello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform((reply) -> generateResponse(reply));
    }

    public String generateResponse(HelloResponse reply) {
        return String.format("%s! HelloWorldService has been called %d number of times.", reply.getMessage(), reply.getCount());
    }

}