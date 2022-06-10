package org.acme;

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

    @Inject
    PostService postService;

    @Inject
    Template index;

    @Inject
    Template saved_post;

    @Inject
    Template show_post;

    @Inject
    Template deleted_post;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showPosts() {
        return index.data("posts", postService.findAll());
    }

    @POST
    @Path("post/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newPost(@BeanParam Post post) {
        postService.save(post);
        return saved_post.instance();
    }

    @GET
    @Path("post/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showPost(@PathParam("id") long id) {
		Post post = postService.findById(id);
        return show_post.data("post", post);
    }

    @GET
    @Path("post/{id}/delete")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance deletePost(@PathParam("id") long id) {
		postService.deleteById(id);
        return deleted_post.instance();
    }

}