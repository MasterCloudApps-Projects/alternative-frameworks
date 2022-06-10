package org.acme;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Usuario {

	private String info;

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}