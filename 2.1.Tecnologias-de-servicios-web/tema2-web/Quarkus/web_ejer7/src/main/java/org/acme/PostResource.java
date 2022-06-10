package org.acme;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;

@Path("/")
public class PostResource {

    private static final String POSTS_FOLDER = "posts";

    @Inject
    PostService postService;

    @Inject
    UserSession userSession;

    @Inject
    ImageService imageService;

    @Inject
    HttpSession session;

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
    @Consumes("multipart/form-data")
    public TemplateInstance newPost(MultipartFormDataInput multipart) throws IOException {

        String user = multipart.getFormDataMap().get("user").get(0).getBodyAsString();
        String title = multipart.getFormDataMap().get("title").get(0).getBodyAsString();
        String text = multipart.getFormDataMap().get("text").get(0).getBodyAsString();
        
        Post post = new Post(user, title, text);

		postService.save(post);

		imageService.saveImage(POSTS_FOLDER, post.getId(), multipart);
		
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
    @Path("/post/{id}/image")	
	public Response downloadImage(@PathParam("id") int id) throws MalformedURLException {

		return imageService.createResponseFromImage(POSTS_FOLDER, id);		
	}

    @GET
    @Path("post/{id}/delete")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance deletePost(@PathParam("id") long id) throws IOException {

        postService.deleteById(id);
		imageService.deleteImage(POSTS_FOLDER, id);

        return deleted_post.instance();
    }

}