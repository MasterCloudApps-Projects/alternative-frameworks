package org.acme.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NuevoAnuncioPage extends Page {

	@FindBy(name = "nombre")
	private WebElement nombreInput;
	
	@FindBy(name = "asunto")
	private WebElement asuntoInput;
	
	@FindBy(name = "comentario")
	private WebElement comentarioInput;
	
	@FindBy(xpath = "//input[@value='Enviar']")
	private WebElement enviarButton;
	
	public NuevoAnuncioPage(Page page) {
		super(page);
	}

	public MensajePage crear(String userName, String subject, String comment) {
	    
		nombreInput.sendKeys(userName);
	    asuntoInput.sendKeys(subject);
	    comentarioInput.sendKeys(comment);
	    
	    enviarButton.click();
	    
	    return new MensajePage(this);
	}

}