import { Controller, Req, Body, Res, Get, Post, Put, Delete, Param, HttpStatus, HttpCode } from '@nestjs/common';
import { PostService } from './app.service';
import { PostDTO } from './app.post';

@Controller()
export class PostController {
  
  constructor(private readonly postService: PostService) {

  }

  @Get('/posts')
  async getPosts() {
    return await this.postService.getPosts();
  }

  @Post('/posts')
  @HttpCode(HttpStatus.CREATED)
  async addPost(@Req() request, @Body() post: PostDTO, @Res() response) {
    post = await this.postService.addPost(post)
    const location: string = request.protocol + '://' + request.get('Host') + request.originalUrl + '/' + post.id
    return response.location(location).send(post);
  }

  @Get('/posts/:id')
  async getPost(@Param('id') id: number, @Res() response) {
    const post: PostDTO = await this.postService.getPost(id);
    if (post) {
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Put('/posts/:id')
  async updateItem(@Param('id') id: number, @Body() post: PostDTO, @Res() response) {
    const auxPost: PostDTO = await this.postService.getPost(id);
    if (auxPost) {
      return response.status(HttpStatus.OK).send(await this.postService.updatePost(id, post));
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Delete('/posts/:id')
  async deletePost(@Param('id') id: number, @Res() response) {
    const post = await this.postService.getPost(id);
    if (post) {
      await this.postService.deletePost(id);
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }
}
