package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.util.ArrayList;

public class Anunciante extends Usuario {
	ArrayList<Anuncio> anuncios= new ArrayList<Anuncio>();
	
	public Anunciante() {
		
	}

	public Anunciante(ArrayList<Anuncio> anuncios, String contrasena, String nombre, int edad, String correo) {
		super(contrasena, nombre, edad, correo);
		this.anuncios = anuncios;
	}

	public ArrayList<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(ArrayList<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
		
}
