package org.acme.page;

import org.openqa.selenium.By;

public class NuevoAnuncioPage extends Page {

	public NuevoAnuncioPage(Page page) {
		super(page);
	}

	public MensajePage crear(String userName, String subject, String comment) {
	    
		driver.findElement(By.name("nombre")).sendKeys(userName);
	    driver.findElement(By.name("asunto")).sendKeys(subject);
	    driver.findElement(By.name("comentario")).sendKeys(comment);
	    
	    driver.findElement(By.xpath("//input[@value='Enviar']")).click();
	    
	    return new MensajePage(this);
	}

}
