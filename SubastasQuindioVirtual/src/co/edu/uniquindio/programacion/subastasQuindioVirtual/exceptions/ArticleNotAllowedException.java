package co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions;

//Excepción usada cuando el tipo de articulo seleccionado no está permitido 

public class ArticleNotAllowedException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ArticleNotAllowedException(String message) {
		super(message);
	}
}
