package org.acme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class ImageResource {

    private static final java.nio.file.Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
    
    @Inject
    Template uploaded_image; 

    @Inject
    Template view_image;

    private String imageName;
  
    @POST
    @Path("upload_image")
    @Consumes("multipart/form-data")
    public TemplateInstance procesarFormulario(MultipartFormDataInput multipart) throws IOException {
        
    
		this.imageName = multipart.getFormDataMap().get("imageName").get(0).getBodyAsString();

		Files.createDirectories(IMAGES_FOLDER);
    
		java.nio.file.Path newFile = IMAGES_FOLDER.resolve("image.jpg");
			          
		InputStream inputStream = multipart.getFormDataMap().get("image").get(0).getBody(InputStream.class,null);
		
		inputStream.transferTo(new FileOutputStream(new File(newFile.toString())));

        return uploaded_image.instance();
    
    }

	@GET
    @Path("image")
	public TemplateInstance viewImage() {

		return view_image.data("imageName", this.imageName);
	}

    @GET
    @Path("/download_image")	
	public Response downloadImage() {

		java.nio.file.Path imagePath = IMAGES_FOLDER.resolve("image.jpg");
		
		return Response.ok().entity(FileUtils.getFile(imagePath.toString())).build();	
	}
}