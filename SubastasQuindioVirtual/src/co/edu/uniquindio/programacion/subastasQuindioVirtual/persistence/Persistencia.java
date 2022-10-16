package co.edu.uniquindio.programacion.subastasQuindioVirtual.persistence;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;



public class Persistencia {
	
	//Declaración de rutas
	public static final String RUTA_ARCHIVO_ANUNCIANTES = "C:\\td\\persistencia\\archivos\\archivoAnunciantes.txt";
	public static final String RUTA_ARCHIVO_COMPRADORES = "C:\\td\\persistencia\\archivos\\archivoCompradores.txt";
	public static final String RUTA_ARCHIVO_PRODUCTOS = "C:\\td\\persistencia\\archivos\\archivoProductos.txt";
	public static final String RUTA_ARCHIVO_LOG = "C:\\td\\persistencia\\log\\subastasQuindioLog.txt";
	
	/**
	 * Método que carga todos los anunciantes guardados en el archivo .txt
	 * @return retorna un array con todos los anunciantes
	 * @throws IOException
	 */
	public static ArrayList<Usuario> cargarAnunciantes() throws IOException{
		ArrayList<String> anuncianteTxt = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ANUNCIANTES);
		ArrayList<Usuario> anunciantes = new ArrayList<Usuario>();
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		for (String anuncianteAux : anuncianteTxt) {
			String[] anuncianteSeparado = anuncianteAux.split("@@");
			String contrasena = anuncianteSeparado[0];
			String nombre = anuncianteSeparado[1];
			int edad = Integer.parseInt(anuncianteSeparado[2]);
			String correo = anuncianteSeparado[3];
			Usuario anunciante = new Anunciante(anuncios, contrasena, nombre, edad, correo);
			anunciantes.add(anunciante);
		}
		return anunciantes;
	}

	/**
	 * Método que guarda en el archivo log los registros de operaciones 
	 * @param mensaje Mensaje de para el registro
	 * @param nivel Nivel de registro
	 * @param accion Acción que se realizó para registrar en el log
	 * @throws IOException
	 */
	public static void guardarLog(String mensaje, int nivel, String accion) throws IOException{
		ArchivoUtil.guardarRegistroLog(mensaje, nivel, accion, RUTA_ARCHIVO_LOG);
	}

	/**
     * Método que guarda el anunciante en el archivo de texto
     * @param anunciante anunciante a guardar
     */
	public static void guardarAnunciante(Anunciante anunciante) {
		String contenido = "";
		contenido += anunciante.toString();
		try {
			ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIANTES, contenido, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
