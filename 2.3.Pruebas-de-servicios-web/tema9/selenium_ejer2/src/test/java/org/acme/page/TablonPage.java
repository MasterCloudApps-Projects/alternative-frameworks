package org.acme.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TablonPage extends Page {

	public TablonPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl);		
	}
	
	public TablonPage(Page page) {
		super(page);
	}

	public NuevoAnuncioPage nuevoAnuncio() {
		driver.findElement(By.linkText("Nuevo anuncio")).click();
		return new NuevoAnuncioPage(this);
	}

	public boolean estaAnuncio(String userName, String subject) {
		return driver.findElements(By.linkText(userName+" "+subject)).size() > 0;
	}

	public AnuncioPage verAnuncio(String userName, String subject) {
		driver.findElement(By.linkText(userName+" "+subject)).click();
		return new AnuncioPage(this);
	}

	public TablonPage get() {
		
		driver.get(this.baseUrl);
		
		return this;
	}

}
