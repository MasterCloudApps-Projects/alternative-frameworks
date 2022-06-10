package org.acme;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.acme.page.ArticlePage;
import org.acme.page.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.quarkus.test.junit.main.QuarkusMainIntegrationTest;

@QuarkusMainIntegrationTest
public class WikipediaTest {

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
		
		MainPage wikipedia = new MainPage(driver);
		
		ArticlePage article = wikipedia.get().search("Rick Astley");
		
		String content = article.getContextText();
		
		assertTrue(content.contains("Richard Paul Astley"));
	}
}