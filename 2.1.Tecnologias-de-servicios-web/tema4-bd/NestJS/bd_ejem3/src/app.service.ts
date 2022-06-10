import { connectToDatabase, getPostsCollection } from './app.db';
import { PostDTO } from './app.post';
import * as mongoDB from "mongodb";
import { Injectable } from '@nestjs/common';

@Injectable()
export class PostService {

  db : mongoDB.Db;
  posts: mongoDB.Collection;

  constructor() {
    this.db = connectToDatabase();
    this.posts = getPostsCollection(this.db);
    this.posts.insertOne(new PostDTO("Pepe", "Vendo moto", "Barata, barata"));
    this.posts.insertOne(new PostDTO("Juan", "Compro coche", "Pago, bien"));
  }

  async getPosts() {

    const allPosts = await this.posts.find().toArray();
    return this.toResponse(allPosts) as PostDTO [];
  }

  async addPost(post: PostDTO) {

    await this.posts.insertOne(post);
    return this.toResponse(post) as PostDTO;

  }

  async getPost(id: mongoDB.ObjectId) {

    const post = await this.posts.findOne({ _id: id });
    if (post) {
      return this.toResponse(post);
    }
  }

  async deletePost(id: mongoDB.ObjectId) {

    await this.posts.deleteOne({ _id: id });

  }

  async updatePost(id: mongoDB.ObjectId, post: PostDTO) {

    await this.posts.updateOne({ _id: id }, { $set: post });

  }

  toResponse(document) {

    if (document instanceof Array) {
        return document.map(elem => this.toResponse(elem));
    } else {
        let { _id, ...response } = document;
        response.id = document._id;
        return response;
    }
  }

}