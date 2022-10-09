package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el usuario quiere tener un username ya tomado

public class AlreadyTakenUsernameException extends Exception{

	private static final long serialVersionUID = 1L;

	public AlreadyTakenUsernameException(String message) {
		super(message);
	}
}
