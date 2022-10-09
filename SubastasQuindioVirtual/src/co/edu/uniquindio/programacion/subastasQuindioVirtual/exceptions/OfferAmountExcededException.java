package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando la cantidad de anuncios limite es superada

public class OfferAmountExcededException extends Exception{

	private static final long serialVersionUID = 1L;

	public OfferAmountExcededException(String message) {
		super(message);
	}
}
