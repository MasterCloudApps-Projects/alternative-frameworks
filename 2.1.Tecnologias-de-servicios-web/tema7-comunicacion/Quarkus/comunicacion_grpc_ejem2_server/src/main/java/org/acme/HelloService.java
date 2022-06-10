package org.acme;

import org.acme.grpc.HelloRequest;
import org.acme.grpc.HelloResponse;
import org.acme.grpc.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class HelloService extends HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, 
    		StreamObserver<HelloResponse> responseObserver) {
    	
        System.out.println("Request received from client:\n" + request);

        String greeting = new StringBuilder().append("Hello, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        HelloResponse response = HelloResponse.newBuilder()
            .setGreeting(greeting)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
