package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.util.ArrayList;

public class Anunciante extends Usuario {
	ArrayList<Anuncio> anuncios= new ArrayList<Anuncio>();

	public Anunciante(ArrayList<Anuncio> anuncios) {
		super();
		this.anuncios = anuncios;
	}

	public ArrayList<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(ArrayList<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
		
}
