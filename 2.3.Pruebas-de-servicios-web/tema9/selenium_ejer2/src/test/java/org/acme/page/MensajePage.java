package org.acme.page;

import org.openqa.selenium.By;

public class MensajePage extends Page {

	public MensajePage(Page page) {
		super(page);
	}

	public TablonPage volverAlTablon() {
		driver.findElement(By.linkText("Volver al tablón")).click();
		
		return new TablonPage(this);
	}

}

