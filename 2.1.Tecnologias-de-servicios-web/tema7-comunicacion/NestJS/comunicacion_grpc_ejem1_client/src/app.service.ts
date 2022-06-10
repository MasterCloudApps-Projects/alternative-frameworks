import { Inject, Injectable, OnModuleInit } from '@nestjs/common';
import { ClientGrpc } from '@nestjs/microservices';
import { HelloRequest, HelloResponse, HelloService } from './interface.hello';

@Injectable()
export class GreetingService implements OnModuleInit {

  private helloService: HelloService;

  constructor(@Inject('hello') private client: ClientGrpc) {}

  onModuleInit() {
    this.helloService = this.client.getService<HelloService>('HelloService');
  }

  greeting(helloRequest: HelloRequest): HelloResponse {
    return this.helloService.hello(helloRequest);
  }
}