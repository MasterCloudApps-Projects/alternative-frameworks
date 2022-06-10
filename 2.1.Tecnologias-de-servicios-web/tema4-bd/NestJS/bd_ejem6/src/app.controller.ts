import { Controller, Req, Body, Res, Get, Post, Put, Delete, Param, HttpStatus, HttpCode } from '@nestjs/common';
import { PostService } from './app.service';
import { PostDTO } from './app.postdto';
import { Types } from 'mongoose';

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
    const newPost = await this.postService.addPost(post);
    const location: string = request.protocol + '://' + request.get('Host') + request.originalUrl + '/' + newPost.id
    return response.location(location).send(newPost);
  }

  @Get('/posts/:id')
  async getPost(@Param('id') id: string, @Res() response) {
    if(!Types.ObjectId.isValid(id)){
      return response.status(HttpStatus.BAD_REQUEST).send();
    }
    const post = await this.postService.getPost(id);
    if (post) {
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Put('/posts/:id')
  async updateItem(@Param('id') id: string, @Body() post: PostDTO, @Res() response) {
    if(!Types.ObjectId.isValid(id)){
      return response.status(HttpStatus.BAD_REQUEST).send();
    }
    const auxPost = await this.postService.getPost(id);
    if (auxPost) {
      await this.postService.updatePost(id, post);
      post.id = id.toString();
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Delete('/posts/:id')
  async deletePost(@Param('id') id: string, @Res() response) {
    if(!Types.ObjectId.isValid(id)){
      return response.status(HttpStatus.BAD_REQUEST).send();
    }
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
