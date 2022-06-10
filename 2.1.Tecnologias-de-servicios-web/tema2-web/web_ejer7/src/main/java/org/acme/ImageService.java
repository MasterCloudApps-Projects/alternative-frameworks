package org.acme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@ApplicationScoped
public class ImageService {
    
    private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

	private Path createFilePath(long imageId, Path folder) {
		return folder.resolve("image-" + imageId + ".jpg");
	}
	
	public void saveImage(String folderName, long imageId, MultipartFormDataInput image) throws IOException {

		Path folder = FILES_FOLDER.resolve(folderName);

		Files.createDirectories(folder);
		
		Path newFile = createFilePath(imageId, folder);

		Map<String, List<InputPart>> uploadForm = image.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("image");

    
        for (InputPart inputPart : inputParts) {
    
            try {
           
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte [] bytes = IOUtils.toByteArray(inputStream);

                File file = new File(newFile.toString());

                file.createNewFile();                

                FileOutputStream fop = new FileOutputStream(file);

                fop.write(bytes);
                fop.flush();
                fop.close();
                            
            } catch (IOException e) {
                e.printStackTrace();
            }    
        }
	}

	public Response createResponseFromImage(String folderName, long imageId) throws MalformedURLException {

		return Response.ok().entity(FileUtils.getFile(folderName + System.getProperty("file.separator") + imageId)).build();	
	
	}

	public void deleteImage(String folderName, long imageId) throws IOException {

		Path folder = FILES_FOLDER.resolve(folderName);

		Path imageFile = createFilePath(imageId, folder);
		
		Files.deleteIfExists(imageFile);				
	}

}