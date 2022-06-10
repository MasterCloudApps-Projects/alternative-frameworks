package org.acme;

import javax.ws.rs.FormParam;

public class Anuncio {

	@FormParam("nombre")
	private String nombre;

	@FormParam("asunto")
	private String asunto;

	@FormParam("comentario")
	private String comentario;

	public Anuncio() {
		
	}

	public Anuncio(String nombre, String asunto, String comentario) {
		this.nombre = nombre;
		this.asunto = asunto;
		this.comentario = comentario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}