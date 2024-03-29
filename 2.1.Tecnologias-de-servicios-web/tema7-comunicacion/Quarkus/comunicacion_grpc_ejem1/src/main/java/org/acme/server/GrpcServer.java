package org.acme.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class GrpcServer {

    public static void main(String ... args) throws IOException, InterruptedException {

        System.out.println("Starting server...");

        Server server = ServerBuilder.forPort(9090)
          .addService(new HelloService()).build();
       
        server.start();
        
        System.out.println("Server started!");
        
        server.awaitTermination();
     }
}