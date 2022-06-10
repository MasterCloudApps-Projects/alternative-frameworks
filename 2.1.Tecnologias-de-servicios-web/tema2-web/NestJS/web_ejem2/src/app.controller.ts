import { Controller, Get, Render} from '@nestjs/common';

@Controller()
export class GreetingController {
  
  constructor() {

  }

  @Get()
  @Render('index.hbs')
  getHello() {
    return { name: 'World!'};
  }
}
