package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el tiempo de un anuncio ha terminado

public class WrongDateException extends Exception{

	private static final long serialVersionUID = 1L;

	public WrongDateException(String message) {
		super(message);
	}
}
