package org.acme;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class WebAppTest {

	@ConfigProperty(name = "quarkus.http.port")
	int port;

	private WebDriver driver;

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
	public void test() throws InterruptedException {
		
		driver.get("http://localhost:"+(this.port+1)+"/");

		String newTitle = "MessageTitle";
		String newBody = "MessageBody";

		Thread.sleep(1000);		
		
		driver.findElement(By.id("title-input")).sendKeys(newTitle);
		driver.findElement(By.id("body-input")).sendKeys(newBody);

		Thread.sleep(1000);
		
		driver.findElement(By.id("submit")).click();

		Thread.sleep(1000);
		
		String title = driver.findElement(By.id("title")).getText();
		String body = driver.findElement(By.id("body")).getText();

		assertEquals(title, newTitle);
		assertEquals(body, newBody);

	}

}