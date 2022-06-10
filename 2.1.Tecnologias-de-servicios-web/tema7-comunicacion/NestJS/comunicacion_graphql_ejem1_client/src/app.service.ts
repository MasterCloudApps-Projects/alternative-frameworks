import { InjectGraphQLClient } from '@golevelup/nestjs-graphql-request';
import { GraphQLClient } from 'graphql-request';
import { Injectable } from '@nestjs/common';
import { PostDTO } from './app.post';
import { addPostOperation, deletePostOperation, getPostOperation, getPostsOperation, updatePostOperation } from './app.operations';

@Injectable()
export class PostService {

  constructor(@InjectGraphQLClient() private readonly client: GraphQLClient) {

  }

  async addPost(post : PostDTO){
    const newPost = await this.client.request(addPostOperation, { post: post })
    return newPost["createPost"] as PostDTO;
  }
    
  async getPosts()  {
    const posts = await this.client.request(getPostsOperation);
    return posts["posts"] as PostDTO;
  }
  
  async getPost(id: string) {
    const post = await this.client.request(getPostOperation, { "id": parseInt(id) });
    return post["post"] as PostDTO;
  }
  
  async updatePost(id: string, post : PostDTO) {
    const replacePost = await this.client.request(updatePostOperation, { "id": parseInt(id) , post: post}) as PostDTO;
    return replacePost["replacePost"] as PostDTO;
  }

  async deletePost(id: string) {
    const deletePost = await this.client.request(deletePostOperation, { "id": parseInt(id) }) as PostDTO;
    return deletePost["deletePost"] as PostDTO;
  }

}