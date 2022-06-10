import { Controller, Get, Query, Render} from '@nestjs/common';

@Controller()
export class GreetingController {
  
  constructor() {

  }

  @Get("/greeting")
  @Render('greeting.hbs')
  getHello(@Query() query) {
    return { name: query.userName };
  }
}
