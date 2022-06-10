package org.acme;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class BooksServiceTest {

	@Inject
	@InjectMock
	@RestClient
	BookService bookService;

	private static ObjectNode jsonResponse;

	@BeforeAll
	public static void setup() throws JsonMappingException, JsonProcessingException {
        jsonResponse = (ObjectNode) new ObjectMapper().readTree("""
			{
			  "kind": "books#volumes",
			  "totalItems": 502,
			  "items": [
			   {
				"kind": "books#volume",
				"id": "USaAQ0hHQWIC",
				"etag": "bPrwJYXD7aM",
				"selfLink": "https://www.googleapis.com/books/v1/volumes/USaAQ0hHQWIC",
				"volumeInfo": {
				 "title": "Java a Tope: J2me (java 2 Micro Edition).",
				 "publisher": "Sergio Gálvez Rojas",
				 "publishedDate": "2003",
				 "industryIdentifiers": [
				{
				 "type": "ISBN_13",
				 "identifier": "9788468847047"
				},
				{
				 "type": "ISBN_10",
				 "identifier": "8468847046"
				}
				 ],
				 "readingModes": {
				"text": false,
				"image": true
				 },
				 "pageCount": 188,
				 "printType": "BOOK",
				 "averageRating": 4.0,
				 "ratingsCount": 9,
				 "maturityRating": "NOT_MATURE",
				 "allowAnonLogging": false,
				 "contentVersion": "1.1.0.0.preview.1",
				 "imageLinks": {
				"smallThumbnail": "http://books.google.com/books/content?id=USaAQ0hHQWIC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				"thumbnail": "http://books.google.com/books/content?id=USaAQ0hHQWIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
				 },
				 "language": "es",
				 "previewLink": "http://books.google.es/books?id=USaAQ0hHQWIC&printsec=frontcover&dq=intitle:Java&hl=&cd=1&source=gbs_api",
				 "infoLink": "http://books.google.es/books?id=USaAQ0hHQWIC&dq=intitle:Java&hl=&source=gbs_api",
				 "canonicalVolumeLink": "https://books.google.com/books/about/Java_a_Tope_J2me_java_2_Micro_Edition.html?hl=&id=USaAQ0hHQWIC"
				},
				"saleInfo": {
				 "country": "ES",
				 "saleability": "NOT_FOR_SALE",
				 "isEbook": false
				},
				"accessInfo": {
				 "country": "ES",
				 "viewability": "ALL_PAGES",
				 "embeddable": true,
				 "publicDomain": false,
				 "textToSpeechPermission": "ALLOWED",
				 "epub": {
				"isAvailable": false
				 },
				 "pdf": {
				"isAvailable": false
				 },
				 "webReaderLink": "http://play.google.com/books/reader?id=USaAQ0hHQWIC&hl=&printsec=frontcover&source=gbs_api",
				 "accessViewStatus": "SAMPLE",
				 "quoteSharingAllowed": false
				}
			   },
			   {
				"kind": "books#volume",
				"id": "29zE8HTdJ1QC",
				"etag": "swhA3SUn5CY",
				"selfLink": "https://www.googleapis.com/books/v1/volumes/29zE8HTdJ1QC",
				"volumeInfo": {
				 "title": "Introduccion Al Desarrollo de Programas Con Java",
				 "publisher": "UNAM",
				 "publishedDate": "2007",
				 "industryIdentifiers": [
				{
				 "type": "ISBN_10",
				 "identifier": "9703243177"
				},
				{
				 "type": "ISBN_13",
				 "identifier": "9789703243174"
				}
				 ],
				 "readingModes": {
				"text": false,
				"image": true
				 },
				 "pageCount": 253,
				 "printType": "BOOK",
				 "averageRating": 4.5,
				 "ratingsCount": 2,
				 "maturityRating": "NOT_MATURE",
				 "allowAnonLogging": false,
				 "contentVersion": "0.0.1.0.preview.1",
				 "imageLinks": {
				"smallThumbnail": "http://books.google.com/books/content?id=29zE8HTdJ1QC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				"thumbnail": "http://books.google.com/books/content?id=29zE8HTdJ1QC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
				 },
				 "language": "es",
				 "previewLink": "http://books.google.es/books?id=29zE8HTdJ1QC&printsec=frontcover&dq=intitle:Java&hl=&cd=2&source=gbs_api",
				 "infoLink": "http://books.google.es/books?id=29zE8HTdJ1QC&dq=intitle:Java&hl=&source=gbs_api",
				 "canonicalVolumeLink": "https://books.google.com/books/about/Introduccion_Al_Desarrollo_de_Programas.html?hl=&id=29zE8HTdJ1QC"
				},
				"saleInfo": {
				 "country": "ES",
				 "saleability": "NOT_FOR_SALE",
				 "isEbook": false
				},
				"accessInfo": {
				 "country": "ES",
				 "viewability": "PARTIAL",
				 "embeddable": true,
				 "publicDomain": false,
				 "textToSpeechPermission": "ALLOWED",
				 "epub": {
				"isAvailable": false
				 },
				 "pdf": {
				"isAvailable": false
				 },
				 "webReaderLink": "http://play.google.com/books/reader?id=29zE8HTdJ1QC&hl=&printsec=frontcover&source=gbs_api",
				 "accessViewStatus": "SAMPLE",
				 "quoteSharingAllowed": false
				}
			   }]}
			""");
    }

	@Test
	@TestHTTPEndpoint(BooksResource.class) 
	public void bookServiceTest() throws Exception {

		when(bookService.getByQuery("Java")).thenReturn(jsonResponse);

		List<String> response = 
			given()
			.when().queryParam("query", "Java").get()
			.then()
			.statusCode(Status.OK.getStatusCode())
			.extract()
			.body()
			.jsonPath().getList(".", String.class);
  
		assertEquals(response.size(), 2);
		assertEquals(response.get(0),"Java a Tope: J2me (java 2 Micro Edition).");
		assertEquals(response.get(1),"Introduccion Al Desarrollo de Programas Con Java");
  
	  }
}