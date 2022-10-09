package co.edu.uniquindio.programacion.subastasQuindioVirtual.services;

import java.util.ArrayList;

public interface OperacionesCrudInterface {
	//Crear
	public <T> void crear(T objetoACrear);
	//Leer
	public <T> ArrayList<T> leer(T objetoALeer);
	//Actualizar
	public <T> void actualizar(T objeto_a_actualizar); 
	//Eliminar
	public <T> void eliminar(T objetoAEliminar);
}
