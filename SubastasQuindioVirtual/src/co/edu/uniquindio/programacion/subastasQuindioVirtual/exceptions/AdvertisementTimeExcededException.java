package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el tiempo de un anuncio ha terminado

public class AdvertisementTimeExcededException extends Exception{

	private static final long serialVersionUID = 1L;

	public AdvertisementTimeExcededException(String message) {
		super(message);
	}
}
