package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Comprador extends Usuario implements Serializable{

	private static final long serialVersionUID = 8586936108288247513L;
	
	ArrayList<Puja> pujas= new ArrayList<Puja>();
	
	public Comprador() {
		
	}
	
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
