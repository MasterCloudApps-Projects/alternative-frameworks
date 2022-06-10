package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/")
public class PostResource {

    private List<Post> posts = new ArrayList<>();

    @Inject
    Template index;

    @Inject
    Template saved_post;

    @Inject
    Template show_post;

    @Inject
    Template deleted_post;

    public PostResource() {
        posts.add(new Post("Pepe", "Vendo moto", "Barata, barata"));
		posts.add(new Post("Juan", "Compro coche", "Pago bien"));
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showPosts() {
        return index.data("posts", posts);
    }

    @POST
    @Path("post/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newPost(@BeanParam Post post) {
        posts.add(post);
        return saved_post.instance();
    }

    @GET
    @Path("post/{numPost}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showPost(@PathParam("numPost") int numPost) {
		Post post = posts.get(numPost - 1);
        return show_post.data("post", post).data("numPost", numPost);
    }

    @GET
    @Path("post/{numPost}/delete")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance deletePost(@PathParam("numPost") int numPost) {
		posts.remove(numPost - 1);
        return deleted_post.instance();
    }

}