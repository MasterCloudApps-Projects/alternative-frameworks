import { Controller, Render, Body, Get, Post, Param } from '@nestjs/common';
import { PostService } from './app.service';
import { PostDTO } from './app.post';

@Controller()
export class PostController {
  
  constructor(private readonly postService: PostService) {

  }

  @Get()
  @Render('index.hbs')
  getPosts() {
    return { posts: this.postService.getPosts() };
  }

  @Post('/post/new')
  @Render('saved_post.hbs')
  addPost(@Body() post: PostDTO): PostDTO {
    return this.postService.addPost(post);
  }

  @Get('/post/:id')
  @Render('show_post.hbs')
  getPost(@Param('id') id: string) {
    return { post: this.postService.getPost(parseInt(id)) };
  }

  @Get('/post/:id/delete')
  @Render('deleted_post.hbs')
  deletePost(@Param('id') id: string) {
    this.postService.deletePost(parseInt(id));
  }
}
