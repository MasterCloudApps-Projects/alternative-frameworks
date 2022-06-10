import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Post } from './app.post';

@Injectable()
export class PostService {

  constructor(@InjectRepository(Post) private PostRepository: Repository<Post>) {

  }

  async addPost(post) {
    return await this.PostRepository.save(post);
  }
  
  async getPosts() {
    return await this.PostRepository.find();
  }

  async getPost(id: number) {
    return await this.PostRepository.findOneBy({ id });
  }

  async updatePost(id: number, post) {
    return await this.PostRepository.update(id, post);
  }

  async deletePost(id: number) {
    return await this.PostRepository.delete(id);
  }
  
}

