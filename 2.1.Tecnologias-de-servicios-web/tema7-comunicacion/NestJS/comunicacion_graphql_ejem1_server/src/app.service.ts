import { Injectable } from '@nestjs/common';
import { Post, PostInput } from './app.post';

@Injectable()
export class PostService {

  posts: Map<number, Post>;
  nextId: number;

  constructor() {
    this.posts = new Map<number, Post>();
    this.nextId = 1;
    this.addPost(new PostInput("Pepe", "Vendo moto", "Barata, barata"));
    this.addPost(new PostInput("Juan", "Compro coche", "Pago, bien"));
  }

  addPost(inputPost : PostInput){
    const post = new Post(this.nextId, inputPost.user, inputPost.title, inputPost.text);
    this.posts.set(post.id, post);
    this.nextId++
    return post;
  }
  
  deletePost(id: number) : Post{
    let post: Post = this.getPost(id)
    this.posts.delete(id);
    return post;
  }
  
  getPosts() : Post[] {
    return [...this.posts.values()];
  }
  
  getPost(id: number) : Post {
    return this.posts.get(id);
  }
  
  updatePost(id: number, inputPost : PostInput) : Post {
    const post = new Post(id, inputPost.user, inputPost.title, inputPost.text);
    this.posts.set(id, post);
    return post;
  }

}