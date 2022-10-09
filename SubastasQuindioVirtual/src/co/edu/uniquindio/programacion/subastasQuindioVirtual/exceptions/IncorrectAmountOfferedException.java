package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando la oferta hecha a un anuncio es menor a su valor inicial

public class IncorrectAmountOfferedException extends Exception{

	private static final long serialVersionUID = 1L;

	public IncorrectAmountOfferedException(String message) {
		super(message);
	}
}
