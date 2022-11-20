package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Anunciante extends Usuario implements Serializable{

	private static final long serialVersionUID = 7032391086599142701L;
	
	//Atributos
	ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
	ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
	
	//Constructores
	public Anunciante() {
		
	}

	public Anunciante(ArrayList<Anuncio> anuncios, String contrasena, String nombre, int edad, String correo, ArrayList<Transaccion> transacciones) {
		super(contrasena, nombre, edad, correo);
		this.anuncios = anuncios;
		this.transacciones = transacciones;
	}

	//Getters y Setters
	public ArrayList<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(ArrayList<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public ArrayList<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(ArrayList<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}
}
