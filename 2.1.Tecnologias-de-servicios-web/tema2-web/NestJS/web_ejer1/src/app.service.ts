import { Injectable } from '@nestjs/common';
import { PostDTO } from './app.post';

@Injectable()
export class PostService {

  posts: Map<number, PostDTO>;
  nextId: number;

  constructor() {
    this.posts = new Map<number, PostDTO>();
    this.nextId = 1;
    this.addPost(new PostDTO("Pepe","Vendo moto", "Barata, barata"));
    this.addPost(new PostDTO("Juan","Compro coche", "Pago, bien"));
  }

  addPost(post : PostDTO) : PostDTO {
    post.id = this.nextId;
    this.nextId++;
    this.posts.set(post.id, post);
    return post;
  }

  deletePost(id: number) : PostDTO {
    const post : PostDTO = this.posts.get(id);
    this.posts.delete(id);
    return post;
  }

  getPosts() : PostDTO[] {
    return [...this.posts.values()];
  }

  getPost(id: number) : PostDTO {
    return this.posts.get(id);
  }

}