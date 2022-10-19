package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;

public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 2723592590182269081L;
	
	//Declaración de atributos
	private String contrasena;
	private String userName;
	private int edad;
	private String correo;
	
	//Constructores
	public Usuario() {
		super();
	}

	public Usuario(String contrasena, String nombre, int edad, String correo) {
		super();
		this.contrasena = contrasena;
		this.userName = nombre;
		this.edad = edad;
		this.correo = correo;
	}

	//Getters y Setters
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNombre() {
		return userName;
	}
	public void setNombre(String nombre) {
		this.userName = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	//Sobreescritura del método toString
	@Override
	public String toString() {
		return contrasena + "@@" + userName + "@@" + edad + "@@" + correo + "\n";
	}
	
}
