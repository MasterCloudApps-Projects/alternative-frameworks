package org.acme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@javax.ws.rs.Path("/")
public class ImageResource {

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
    
    @Inject
    Template uploaded_image; 

    @Inject
    Template view_image;

    private String imagePath;

    private String imageName;

    private static final String EXTENSION = ".jpg";
  
    @POST
    @javax.ws.rs.Path("upload_image")
    @Consumes("multipart/form-data")
    public TemplateInstance procesarFormulario(MultipartFormDataInput input) throws IOException {
    
            
        Files.createDirectories(IMAGES_FOLDER);

        this.imageName = input.getFormDataMap().get("imageName").get(0).getBodyAsString();
        this.imagePath = IMAGES_FOLDER + System.getProperty("file.separator") + imageName + EXTENSION;
		            
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("image");

    
        for (InputPart inputPart : inputParts) {
    
            try {
           
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte [] bytes = IOUtils.toByteArray(inputStream);

                File file = new File(imagePath);

                file.createNewFile();                

                FileOutputStream fop = new FileOutputStream(file);

                fop.write(bytes);
                fop.flush();
                fop.close();
                            
            } catch (IOException e) {
                e.printStackTrace();
            }    
        }
    
        return uploaded_image.instance();
    
    }


	@GET
    @javax.ws.rs.Path("image")
	public TemplateInstance viewImage() {

		return view_image.data("imageName", this.imageName);
	}

    @GET
    @javax.ws.rs.Path("/download_image")	
	public Response downloadImage() {

		return Response.ok().entity(FileUtils.getFile(imagePath)).build();	
	}
}