package org.acme.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AnuncioPage extends Page {

	@FindBy(linkText = "Borrar anuncio")
	private WebElement borrarLink; 
	
	public AnuncioPage(Page page) {
		super(page);
	}

	public MensajePage borrar() {
		
		borrarLink.click();
		
		return new MensajePage(this);
	}

}