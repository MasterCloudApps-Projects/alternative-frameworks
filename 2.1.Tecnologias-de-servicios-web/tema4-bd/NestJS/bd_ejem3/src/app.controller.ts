import { Controller, Req, Body, Res, Get, Post, Put, Delete, Param, HttpStatus, HttpCode } from '@nestjs/common';
import { PostService } from './app.service';
import { PostDTO } from './app.post';
import { ObjectId } from 'mongodb';

@Controller()
export class PostController {

  constructor(private readonly postService: PostService) {
    
  }

  @Get('/posts')
  async getPosts() {
    return this.postService.getPosts();
  }

  @Post('/posts')
  @HttpCode(HttpStatus.CREATED)
  async addPost(@Req() request, @Body() post: PostDTO, @Res() response) {
    post = await this.postService.addPost(post);
    const location: string = request.protocol + '://' + request.get('Host') + request.originalUrl + '/' + post.id
    return response.location(location).send(post);
  }

  @Get('/posts/:id')
  async getPost(@Param('id') id: string, @Res() response) {
    if(!ObjectId.isValid(id)){
      return response.status(HttpStatus.BAD_REQUEST).send();
    }
    const post = await this.postService.getPost(new ObjectId(id));
    if (post) {
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Put('/posts/:id')
  async updateItem(@Param('id') id: string, @Body() post: PostDTO, @Res() response) {
    if(!ObjectId.isValid(id)){
      return response.status(HttpStatus.BAD_REQUEST).send();
    }
    const auxPost = await this.postService.getPost(new ObjectId(id));
    if (auxPost) {
      await this.postService.updatePost(new ObjectId(id), post);
      post.id = id.toString();
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Delete('/posts/:id')
  async deletePost(@Param('id') id: string, @Res() response) {
    if(!ObjectId.isValid(id)){
      return response.status(HttpStatus.BAD_REQUEST).send();
    }
    const post = await this.postService.getPost(new ObjectId(id));
    if (post) {
      await this.postService.deletePost(new ObjectId(id));
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }
}
