package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando se quiere exceder la cantidad limite de anuncios

public class AdvertisementLimitedAmountException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public AdvertisementLimitedAmountException(String message) {
		super(message);
	}
}
