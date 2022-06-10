package org.acme;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class Usuario {

	private String nombre = "";
	private int numAnuncios;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void incAnuncios() {
		this.numAnuncios++;
	}

	public int getNumAnuncios() {
		return numAnuncios;
	}

	public void setNumAnuncios(int numAnuncios) {
		this.numAnuncios = numAnuncios;
	}

}