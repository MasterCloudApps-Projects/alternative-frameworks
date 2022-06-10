package org.acme;

import java.util.List;

import org.eclipse.microprofile.graphql.Mutation;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;

@GraphQLClientApi(configKey = "posts")
public interface PostsClientApi {

    List<Post> getPosts();

    Post getPost(long id);

    @Mutation
    Post createPost(Post post);

    @Mutation
    Post replacePost(long id, Post post);

    @Mutation
    Post deletePost(long id);
}