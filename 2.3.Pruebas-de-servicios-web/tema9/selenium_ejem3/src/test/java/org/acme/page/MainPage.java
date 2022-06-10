package org.acme.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends Page {

	public MainPage(WebDriver driver) {
		super(driver);
	}

	public MainPage get() {
		get("https://wikipedia.org");
		return this;
	}
	
	public ArticlePage search(String searchText) {
	
		WebElement searchInput = driver.findElement(By.name("search"));

		searchInput.sendKeys(searchText);
		searchInput.submit();
		
		return new ArticlePage(this);
		
	}
}
