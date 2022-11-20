package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando algun array es vacío 

public class EmptyArrayException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public EmptyArrayException(String message) {
		super(message);
	}
}
