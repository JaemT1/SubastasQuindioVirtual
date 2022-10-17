package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.util.ArrayList;

public class Comprador extends Usuario{
	ArrayList<Puja> pujas= new ArrayList<Puja>();
	
	

	public Comprador(ArrayList<Puja> pujas,String contrasena,String nombre,int edad,String correo) {
		super(contrasena, nombre, edad, correo);
		this.pujas = pujas;
	}

	public ArrayList<Puja> getPujas() {
		return pujas;
	}

	public void setPujas(ArrayList<Puja> pujas) {
		this.pujas = pujas;
	}
	
	
}
