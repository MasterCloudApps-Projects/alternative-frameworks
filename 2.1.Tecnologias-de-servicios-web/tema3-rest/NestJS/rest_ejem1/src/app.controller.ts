import { Controller, Get } from '@nestjs/common';

@Controller()
export class PostController {
  
  constructor() {

  }

  @Get('/posts/1')
  getPost() {
    return {  id: 1,
      user: 'Pepe',
      title: 'Vendo moto',
      text: 'Barata, barata' };
  }
}
