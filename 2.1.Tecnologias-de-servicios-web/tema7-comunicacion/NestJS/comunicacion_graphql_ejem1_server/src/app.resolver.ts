import { Args, Int, Mutation, Query, Resolver } from '@nestjs/graphql';
import { Post, PostInput } from './app.post';
import { PostService } from './app.service';


@Resolver(of => Post)
export class PostResolver {
  
  constructor(private readonly postService: PostService) {

  }

  @Query(returns => [Post], { name: 'posts', nullable: false })
  async getPosts() {
    return await this.postService.getPosts();
  }

  @Mutation(returns => Post, { name: 'createPost'})
  async addPost(@Args({ name: 'post' }) post: PostInput) {
    return await this.postService.addPost(post);
  }

  @Query(returns => Post, { name: 'post', nullable: true })
  async getPost(@Args({ name: 'id', type: () => Int }) id: number) {
    return await this.postService.getPost(id);
  }

  @Mutation(returns => Post, { name: 'replacePost'})
  async updatePost(@Args({ name: 'id', type: () => Int }) id: number, @Args({ name: 'post' }) post: PostInput) {
    const postFound = await this.postService.getPost(id);
    if (postFound) {
      return await this.postService.updatePost(id, post)
    }
  }

  @Mutation(returns => Post, { name: 'deletePost'})
  async deletePost(@Args({ name: 'id', type: () => Int }) id: number) {
    return await this.postService.deletePost(id);
  }
}
