package org.acme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@ApplicationScoped
public class ImageService {

	private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

	private Path createFilePath(long imageId, Path folder) {
		return folder.resolve("image-" + imageId + ".jpg");
	}
	
	public void saveImage(String folderName, long imageId, MultipartFormDataInput multipart) throws IOException {

		Path folder = FILES_FOLDER.resolve(folderName);

		Files.createDirectories(folder);
		
		Path newFile = createFilePath(imageId, folder);

		InputStream inputStream = multipart.getParts().get(0).getBody(InputStream.class,null);
		
		inputStream.transferTo(new FileOutputStream(new File(newFile.toString())));

	}

	public Response createResponseFromImage(String folderName, long imageId) {

		Path folder = FILES_FOLDER.resolve(folderName);
		
		Path imagePath = createFilePath(imageId, folder);
		
		File file = imagePath.toFile();
		
		if(!Files.exists(imagePath)) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").entity(file).build();
		}		
	}

	public void deleteImage(String folderName, long imageId) throws IOException {

		Path folder = FILES_FOLDER.resolve(folderName);

		Path imageFile = createFilePath(imageId, folder);
		
		Files.deleteIfExists(imageFile);				
	}

}