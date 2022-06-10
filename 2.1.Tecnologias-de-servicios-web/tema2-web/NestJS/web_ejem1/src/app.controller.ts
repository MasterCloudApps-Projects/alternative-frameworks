import { Controller, Get } from '@nestjs/common';

@Controller()
export class GreetingController {
  
  constructor() {

  }

  @Get()
  getHello(): string {
    return 'Hello World!';
  }
}
