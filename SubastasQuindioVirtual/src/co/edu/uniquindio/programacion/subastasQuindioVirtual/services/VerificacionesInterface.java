package co.edu.uniquindio.programacion.subastasQuindioVirtual.services;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.exceptions.WrongDateException;

public interface VerificacionesInterface {
	public boolean esNumero(String esNumero);
	public boolean verificarCampoVacio();
	public boolean verificarFechas() throws WrongDateException;
}
