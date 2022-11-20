package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SubastasQuindio implements Serializable{

	private static final long serialVersionUID = 67227836544102263L;
	
	//Atributos
	ArrayList<Anuncio> anuncios= new ArrayList<Anuncio>();
	ArrayList<Usuario> usuarios= new ArrayList<Usuario>();
	int cantidadPujas = 0;
	int cantidadTransacciones = 0;
	
	//Getters y Setters
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
	public int getCantidadPujas() {
		return cantidadPujas;
	}
	public void setCantidadPujas(int cantidadPujas) {
		this.cantidadPujas = cantidadPujas;
	}
	public int getCantidadTransacciones() {
		return cantidadTransacciones;
	}
	public void setCantidadTransacciones(int cantidadTransacciones) {
		this.cantidadTransacciones = cantidadTransacciones;
	}
	
}
