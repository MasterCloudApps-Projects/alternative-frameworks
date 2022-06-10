package org.acme.client;

import org.acme.grpc.HelloRequest;
import org.acme.grpc.HelloResponse;
import org.acme.grpc.HelloServiceGrpc;
import org.acme.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    
	public static void main(String[] args) throws InterruptedException {
        
    	ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
            .usePlaintext()
            .build();

        HelloServiceBlockingStub client = 
        		HelloServiceGrpc.newBlockingStub(channel);

        HelloRequest request = HelloRequest.newBuilder()
            .setFirstName("Quarkus")
            .setLastName("gRPC")
            .build();
        
		HelloResponse response = client.hello(request);

        System.out.println("Response received from server:\n" + response);

        channel.shutdown();
    }
}
