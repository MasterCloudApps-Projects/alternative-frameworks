package es.codeurjc.test.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.quarkus.test.junit.main.QuarkusMainIntegrationTest;

import org.testcontainers.containers.BrowserWebDriverContainer;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

import java.io.File;

@Testcontainers
@QuarkusMainIntegrationTest
public class WikipediaTest {

	@Container
    public static BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
		.withCapabilities(DesiredCapabilities.chrome())
		.withRecordingMode(RECORD_ALL, new File("target"));

	private RemoteWebDriver driver;

	@BeforeEach
	public void setupTest() {
		driver = chrome.getWebDriver();
	}

	@AfterEach
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() throws InterruptedException {
		
		driver.get("https://wikipedia.org");
        WebElement searchInput = driver.findElement(By.name("search"));

        Thread.sleep(2000);
        
        searchInput.sendKeys("Rick Astley");
        searchInput.submit();

        Thread.sleep(2000);
        
        WebElement link = driver.findElement(By.linkText("Rickrolling"));
        link.click();
        
        Thread.sleep(2000);

        boolean memeFound = driver.findElements(By.cssSelector("p"))
                .stream()
                .anyMatch(element -> element.getText().contains("meme"));

        assertTrue(memeFound, "Rickrolling page should contain meme word");
	}

}







