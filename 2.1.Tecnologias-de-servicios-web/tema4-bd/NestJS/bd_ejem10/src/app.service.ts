import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/sequelize';
import { Post } from './app.post';

@Injectable()
export class PostService {
  constructor(@InjectModel(Post) private readonly postModel: typeof Post) {

  }

  async addPost(post) {
    return await this.postModel.create(post);
  }
  
  async getPosts() {
    return await this.postModel.findAll();
  }

  async getPost(id: number) {
    return await this.postModel.findByPk(id);
  }

  async updatePost(id: number, post) {
    return await (await this.postModel.findByPk(id)).update(post);
  }

  async deletePost(id: number) {
    return await (await this.postModel.findByPk(id)).destroy();
  }
  
}

