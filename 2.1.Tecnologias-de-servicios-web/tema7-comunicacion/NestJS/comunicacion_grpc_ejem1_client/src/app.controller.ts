import { Controller, Post, Body } from '@nestjs/common';
import { GreetingService } from './app.service';
import { HelloRequest } from './interface.hello';


@Controller()
export class GreetingController {

  constructor(private readonly greetingService: GreetingService) {
    
  }

  @Post('/greeting')
  async greeting(@Body() helloRequest: HelloRequest) {
    return this.greetingService.greeting(helloRequest);
  }

}