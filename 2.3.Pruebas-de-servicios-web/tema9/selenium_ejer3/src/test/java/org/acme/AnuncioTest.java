package org.acme;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.acme.page.AnuncioPage;
import org.acme.page.MensajePage;
import org.acme.page.NuevoAnuncioPage;
import org.acme.page.TablonPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AnuncioTest {

	@ConfigProperty(name = "quarkus.http.port")
    int port;

	private WebDriver driver;

	private String userName = "User_" + Double.toString(Math.random());

	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setupTest() {
		driver = new ChromeDriver();
	}

	@AfterEach
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void createAnuncio() {

		TablonPage tablon = new TablonPage(driver, "http://localhost:"+(port+1));

		NuevoAnuncioPage nuevoAnuncio = tablon.get().nuevoAnuncio();		
		
		MensajePage creado = nuevoAnuncio.crear(userName, "Subject", "Comment");
		
		tablon = creado.volverAlTablon();
		
		assertTrue(tablon.estaAnuncio(userName, "Subject"));
		
	}

	
	@Test
	public void borrarAnuncio() {
		
		TablonPage tablon = new TablonPage(driver, "http://localhost:"+(port+1));

		NuevoAnuncioPage nuevoAnuncio = tablon.get().nuevoAnuncio();		
		
		MensajePage creado = nuevoAnuncio.crear(userName, "Subject", "Comment");
		
		tablon = creado.volverAlTablon();
		
		AnuncioPage anuncio = tablon.get().verAnuncio(userName, "Subject");
		
		MensajePage borrado = anuncio.borrar();
		
		tablon = borrado.volverAlTablon();
		
		assertFalse(tablon.estaAnuncio(userName, "Subject"));
	}
	
}
