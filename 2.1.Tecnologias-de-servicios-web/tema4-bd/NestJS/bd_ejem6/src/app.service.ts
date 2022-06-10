import { Injectable } from "@nestjs/common";
import { InjectModel } from "@nestjs/mongoose";
import { Model, ObjectId } from 'mongoose';
import { Post, PostDocument } from "./app.post";
import { PostDTO } from "./app.postdto";

@Injectable()
export class PostService {
  
  constructor(@InjectModel(Post.name) private postModel: Model<PostDocument>) {

  }

  async addPost(post: PostDTO) {
    const createdPost = new this.postModel(post);
    const newPost = await createdPost.save();
    return this.toResponse(newPost);
  }

  async getPosts() {
    const posts = await this.postModel.find().exec();
    return this.toResponse(posts);
  }

  async getPost(id: string) {
    const post = await this.postModel.findById(id);
    if (post) {
      return await this.toResponse(post) as PostDTO;
    }
    
  }

  async deletePost(id: string) {
    const post = await this.postModel.findById(id);
    if (post) {
      return await this.postModel.findByIdAndDelete(id);
    }
  }

  async updatePost(id: string, post: PostDTO) {
    const foundPost = await this.postModel.findById(id);
    if (foundPost) {
      return await this.postModel.updateOne({ _id: { $eq: id } }, { $set: post });
    }
  }

  toResponse(document) {
    if (document instanceof Array) {
        return document.map(elem => this.toResponse(elem));
    } else {
        let response = document.toObject({ versionKey: false });
        response.id = response._id.toString();
        delete response._id;
        return response;
    }
  }

}