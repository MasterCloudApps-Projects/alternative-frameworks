package org.acme.page;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePage extends Page {

	public ArticlePage(Page page) {
		super(page);
	}

	public String getContextText() {
		
		WebElement text = wait.until(
			presenceOfElementLocated(By.id("mw-content-text")));
		        
		return text.getText();
	}	
}
