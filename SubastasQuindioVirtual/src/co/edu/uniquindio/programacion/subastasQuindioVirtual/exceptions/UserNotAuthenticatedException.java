package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el usuario no está autenticado

public class UserNotAuthenticatedException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserNotAuthenticatedException(String message) {
		super(message);
	}
}
