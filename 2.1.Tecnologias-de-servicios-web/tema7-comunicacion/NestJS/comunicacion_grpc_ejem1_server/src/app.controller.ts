import { Metadata, ServerUnaryCall } from '@grpc/grpc-js';
import { Controller } from '@nestjs/common';
import { GrpcMethod } from '@nestjs/microservices';
import { HelloRequest, HelloResponse } from './interface.hello';


@Controller()
export class HelloController {

  @GrpcMethod('HelloService', 'hello')
  greeting(request: HelloRequest, metadata: Metadata, call: ServerUnaryCall<any, any>): HelloResponse {
    return new HelloResponse("Hello, " + request.firstName + " " + request.lastName);
  }
}