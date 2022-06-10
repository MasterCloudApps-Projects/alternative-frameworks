package org.acme.page;

import org.openqa.selenium.By;

public class AnuncioPage extends Page {

	public AnuncioPage(Page page) {
		super(page);
	}

	public MensajePage borrar() {
		
		driver.findElement(By.linkText("Borrar anuncio")).click();
		
		return new MensajePage(this);
	}

}

