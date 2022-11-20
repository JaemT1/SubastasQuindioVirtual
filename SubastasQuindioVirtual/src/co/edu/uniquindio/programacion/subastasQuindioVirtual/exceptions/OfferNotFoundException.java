package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el tipo de articulo seleccionado no está permitido 

public class OfferNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public OfferNotFoundException(String message) {
		super(message);
	}
}
