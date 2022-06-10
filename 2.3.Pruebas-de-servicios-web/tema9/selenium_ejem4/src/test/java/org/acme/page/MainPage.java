package org.acme.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {

	@FindBy(name = "search")
	WebElement searchInput;
	
	public MainPage(WebDriver driver) {
		super(driver);
	       
        PageFactory.initElements(driver, this);
	}

	public MainPage get() {
		get("https://wikipedia.org");
		return this;
	}
	
	public ArticlePage search(String searchText) {
	
		searchInput.sendKeys(searchText);
		searchInput.submit();
		
		return new ArticlePage(this);
		
	}
}
