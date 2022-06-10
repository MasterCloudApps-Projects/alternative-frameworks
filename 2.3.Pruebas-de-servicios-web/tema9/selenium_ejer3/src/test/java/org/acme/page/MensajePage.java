package org.acme.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MensajePage extends Page {

	@FindBy(linkText = "Volver al tablón")
	private WebElement volverLink;
		
	public MensajePage(Page page) {
		super(page);
	}

	public TablonPage volverAlTablon() {
		
		volverLink.click();
		
		return new TablonPage(this);
	}

}
