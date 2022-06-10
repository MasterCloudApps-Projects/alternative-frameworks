import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { Injectable } from '@nestjs/common';
import { PostDTO } from './app.post';

@Injectable()
export class PostService {

  private BASE_URL = "http://localhost:3000/posts";

  constructor(private readonly httpService: HttpService) {

  }

  async addPost(post : PostDTO){
    const newPost = await firstValueFrom(this.httpService.post(this.BASE_URL, post));
    return newPost.data as PostDTO;
  }

  async getPosts() {
    const posts = await firstValueFrom(this.httpService.get(this.BASE_URL));
    return posts.data as PostDTO[];
  }

  async getPost(id: number) {
    try {
      const post = await firstValueFrom(this.httpService.get(this.BASE_URL  + "/" + id));
      return post.data as PostDTO;
    }
    catch (error) {
      // Not Found, Do Nothing
    }
  }

  async updatePost(id: number, post: PostDTO) {
      const updatePost = await firstValueFrom(this.httpService.put(this.BASE_URL  + "/" + id, post));
      return updatePost.data as PostDTO;
  }

  async deletePost(id: number) {
    const post = await firstValueFrom(this.httpService.delete(this.BASE_URL  + "/" + id));
    return post.data as PostDTO;
  }

}