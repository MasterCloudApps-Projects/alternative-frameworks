package org.acme;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AnuncioTest {

	@ConfigProperty(name = "quarkus.http.port")
    int port;

	WebDriver driver;
	
	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}
	
	@AfterEach
	public void teardown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void createAnuncio() throws InterruptedException {
		driver.get("http://localhost:"+(this.port+1)+"/");
		
		driver.findElement(By.linkText("Nuevo anuncio")).click();
		
		driver.findElement(By.name("nombre")).sendKeys("Michel");
		driver.findElement(By.name("asunto")).sendKeys("Vendo moto Selenium");
		driver.findElement(By.name("comentario")).sendKeys("Un comentario muy largo...");
		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		driver.findElement(By.linkText("Volver al tablón")).click();
		
		assertNotNull(driver.findElement(By.partialLinkText("Selenium")));
	}

}
