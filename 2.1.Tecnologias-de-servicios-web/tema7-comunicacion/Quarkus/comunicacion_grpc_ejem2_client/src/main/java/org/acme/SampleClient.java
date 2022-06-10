package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.acme.grpc.HelloRequest;
import org.acme.grpc.HelloResponse;
import org.acme.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class SampleClient {

	@GrpcClient("helloServer")
	HelloServiceBlockingStub client;
	
	
	public void init(@Observes StartupEvent ev) {
		
		HelloRequest request = HelloRequest.newBuilder()
				.setFirstName("Quarkus")
				.setLastName("gRPC")
	            .build();
	        
		HelloResponse response = client.hello(request);

	    System.out.println("Response received from server:\n" + response);
		
	}	
}
