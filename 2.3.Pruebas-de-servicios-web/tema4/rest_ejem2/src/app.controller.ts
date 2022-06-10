import { Controller, Req, Body, Res, Get, Post, Put, Delete, Param, HttpStatus, HttpCode } from '@nestjs/common';
import { PostService } from './app.service';
import { PostDTO } from './app.post';

@Controller()
export class PostController {
  
  constructor(private readonly postService: PostService) {

  }

  @Get('/posts')
  getPosts() {
    return this.postService.getPosts();
  }

  @Post('/posts')
  @HttpCode(HttpStatus.CREATED)
  addPost(@Req() request, @Body() post: PostDTO, @Res() response) {
    post = this.postService.addPost(post)
    const location: string = request.protocol + '://' + request.get('Host') + request.originalUrl + '/' + post.id
    return response.location(location).send(post);
  }

  @Get('/posts/:id')
  getPost(@Param('id') id: string, @Res() response) {
    const post: PostDTO = this.postService.getPost(parseInt(id));
    if (post) {
      return response.status(HttpStatus.OK).send(post);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Put('/posts/:id')
  updateItem(@Param('id') id: string, @Body() post: PostDTO, @Res() response) {
    const auxPost: PostDTO = this.postService.getPost(parseInt(id));
    if (auxPost) {
      return response.status(HttpStatus.OK).send(this.postService.updatePost(parseInt(id), post));
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Delete('/posts/:id')
  deletePost(@Param('id') id: string, @Res() response) {
    const post: PostDTO = this.postService.getPost(parseInt(id));
    if (post) {
      return response.status(HttpStatus.OK).send(this.postService.deletePost(parseInt(id)));
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }
}
