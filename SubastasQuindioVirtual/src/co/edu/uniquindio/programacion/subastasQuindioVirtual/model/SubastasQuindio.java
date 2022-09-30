package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.util.ArrayList;

public class SubastasQuindio {
	ArrayList<Anuncio> anuncios= new ArrayList<Anuncio>();
	ArrayList<Usuario> usuarios= new ArrayList<Usuario>();
	
	public ArrayList<Anuncio> getAnuncios() {
		return anuncios;
	}
	public void setAnuncios(ArrayList<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
