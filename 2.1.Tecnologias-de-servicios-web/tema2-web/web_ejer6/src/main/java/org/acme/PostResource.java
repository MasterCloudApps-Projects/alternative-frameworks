package org.acme;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
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
    UserSession userSession;

    @Inject HttpSession session;

    @Inject
    Template index;

    @Inject
    Template new_post;

    @Inject
    Template saved_post;

    @Inject
    Template show_post;

    @Inject
    Template deleted_post;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance showPosts() {
        return index.data("posts", postService.findAll()).data("welcome", session.isNew());
    }

    @GET
    @Path("post/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newPostForm() {
        return new_post.data("user", userSession.getUser());
    }

    @POST
    @Path("post/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newPost(@BeanParam Post post) {

		postService.save(post);
		
		userSession.setUser(post.getUser());
		userSession.incNumPosts();
		
        return saved_post.data("numPosts", userSession.getNumPosts());
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