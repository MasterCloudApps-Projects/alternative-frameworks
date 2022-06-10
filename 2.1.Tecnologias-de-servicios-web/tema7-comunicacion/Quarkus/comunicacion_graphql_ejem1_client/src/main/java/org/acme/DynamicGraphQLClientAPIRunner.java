package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;


import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Operation.operation;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.InputObject.inputObject;
import static io.smallrye.graphql.client.core.InputObjectField.prop;

import java.util.List;
import java.util.concurrent.ExecutionException;


@ApplicationScoped
public class DynamicGraphQLClientAPIRunner {

    @Inject
    @GraphQLClient("posts")
    DynamicGraphQLClient dynamicClient;
    
    public void init(@Observes StartupEvent ev) throws ExecutionException, InterruptedException {

		Post post = new Post("Antonio", "XXX", "000");
		Post newPost = new Post("Jose", "Alquilo", "Camion");
        
        getPosts();
        getPost();
		post = createPost(post);
		newPost = replacePost(post.getId(), newPost);
		deletePost(newPost.getId());

    }

    private void getPosts() throws ExecutionException, InterruptedException {

        Document query = document(
            operation(OperationType.QUERY,
                field("posts",
                field("id"), field("user"), field("title"), field("text")
                )
            )
        );
        
        List<Post> posts = dynamicClient.executeSync(query).getList(Post.class, "posts");

        System.out.println("---------");
		System.out.println("Get Posts");
		System.out.println("---------");

        for (Post p: posts)  {
            System.out.println(p);
        }

    }

    private void getPost() throws ExecutionException, InterruptedException {

        Document query = document(
            operation(OperationType.QUERY,
                field("post", List.of(arg("id", 1)),
                field("id"), field("user"), field("title"), field("text")
                )
            )
        );
        
        Post post = dynamicClient.executeSync(query).getObject(Post.class, "post");

        System.out.println("---------");
		System.out.println("Get Post with id 1");
		System.out.println("---------");

        System.out.println(post);

    }

	private Post createPost(Post p) throws ExecutionException, InterruptedException {

        Document query = document(
            operation(
                OperationType.MUTATION,
                field("createPost",
                        arg("post", 
                            inputObject(
                                prop("user", p.getUser()),
                                prop("title", p.getTitle()),
                                prop("text", p.getText())
                            )
                        )
                    ),
                field("", 
                    field("id"), field("user"), field("title"), field("text")
                )
            )
        );
        
        Post post = dynamicClient.executeSync(query).getObject(Post.class, "createPost");

        System.out.println("---------");
		System.out.println("Created Post");
		System.out.println("---------");

        System.out.println(post);

        return post;

    }

	private Post replacePost(long id, Post p) throws ExecutionException, InterruptedException {

        Document query = document(
            operation(
                OperationType.MUTATION,
                field("replacePost",
                        List.of(
                            arg("id", id), 
                            arg("post", 
                                inputObject(
                                    prop("user", p.getUser()),
                                    prop("title", p.getTitle()),
                                    prop("text", p.getText())
                                )
                            )
                        )
                    ),
                field("", 
                    field("id"), field("user"), field("title"), field("text")
                )
            )
        );
        
        Post post = dynamicClient.executeSync(query).getObject(Post.class, "replacePost");

        System.out.println("---------");
		System.out.println("Updated Post with id of previous post");
		System.out.println("---------");

        System.out.println(post);

        return post;

    }
    
	private void deletePost(long id) throws ExecutionException, InterruptedException {

        Document query = document(
            operation(
                OperationType.MUTATION,
                field("deletePost",arg("id", id)),
                field("", 
                    field("id"), field("user"), field("title"), field("text")
                )
            )
        );
        
        Post post = dynamicClient.executeSync(query).getObject(Post.class, "deletePost");

        System.out.println("---------");
        System.out.println("Deleted Post with id of previous post");
        System.out.println("---------");

        System.out.println(post);

    }
}
