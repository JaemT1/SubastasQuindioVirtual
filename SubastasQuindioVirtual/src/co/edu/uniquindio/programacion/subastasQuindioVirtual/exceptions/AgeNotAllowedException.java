package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el usuario es menor de edad o ingresa una edad no válida

public class AgeNotAllowedException extends Exception{

	private static final long serialVersionUID = 1L;

	public AgeNotAllowedException(String message) {
		super(message);
	}
}
