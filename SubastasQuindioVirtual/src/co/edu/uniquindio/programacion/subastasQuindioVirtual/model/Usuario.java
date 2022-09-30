package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

public class Usuario {
	String contrasena;
	String nombre;
	int edad;
	
	
	public Usuario() {
		super();
	}


	public Usuario(String contrasena, String nombre, int edad) {
		super();
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.edad = edad;
	}
	
	
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	
}
