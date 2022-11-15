package co.edu.uniquindio.programacion.subastasQuindioVirtual.persistence;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Anunciante;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Comprador;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Puja;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.SubastasQuindio;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Transaccion;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.Usuario;

public class Persistencia implements Serializable{
	
	private static final long serialVersionUID = -715139514634395675L;
	
	//Declaración de rutas
	public static final String RUTA_ARCHIVO_ANUNCIANTES = "C:\\td\\persistencia\\archivos\\archivoAnunciantes.txt";
	public static final String RUTA_ARCHIVO_COMPRADORES = "C:\\td\\persistencia\\archivos\\archivoCompradores.txt";
	public static final String RUTA_ARCHIVO_TRANSACCIONES = "C:\\td\\persistencia\\archivos\\archivoTransaccion.txt";
	public static final String RUTA_ARCHIVO_PUJAS = "C:\\td\\persistencia\\archivos\\archivoPujas.txt";
	public static final String RUTA_ARCHIVO_LOG = "C:\\td\\persistencia\\log\\subastasQuindioLog.txt";
	public static final String RUTA_ARCHIVO_TIPOS_PRODUCTOS_PROPERTIES = "C:\\td\\persistencia\\archivos\\tiposProductos.properties";
	public static final String RUTA_ARCHIVO_MODEL_XML = "C:\\td\\persistencia\\model.xml";
	public static final String RUTA_ARCHIVO_MODEL_DAT = "C:\\td\\persistencia\\model.dat";
	
	/**
	 * Método que carga todos los anunciantes guardados en el archivo .txt
	 * @return retorna un array con todos los anunciantes
	 * @throws IOException
	 */
	/*public static ArrayList<Usuario> cargarAnunciantes() throws IOException{
		ArrayList<String> anuncianteTxt = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ANUNCIANTES);
		ArrayList<Usuario> anunciantes = new ArrayList<Usuario>();
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		for (String anuncianteAux : anuncianteTxt) {
			String[] compradorSeparado = anuncianteAux.split("@@");
			String contrasena = compradorSeparado[0];
			String nombre = compradorSeparado[1];
			int edad = Integer.parseInt(compradorSeparado[2]);
			String correo = compradorSeparado[3];
			Usuario anunciante = new Anunciante(anuncios, contrasena, nombre, edad, correo);
			anunciantes.add(anunciante);
		}
		return anunciantes;
	}*/
	
	public static ArrayList<Usuario> cargarCompradores() throws IOException{
		ArrayList<String> compradorTxt = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_COMPRADORES);
		ArrayList<Usuario> compradores = new ArrayList<Usuario>();
		ArrayList<Puja> pujas = new ArrayList<Puja>();
		for (String compradorAux : compradorTxt) {
			String[] compradorSeparado = compradorAux.split("@@");
			String contrasena = compradorSeparado[0];
			String nombre = compradorSeparado[1];
			int edad = Integer.parseInt(compradorSeparado[2]);
			String correo = compradorSeparado[3];
			Usuario comprador = new Comprador(pujas, contrasena, nombre, edad, correo);
			compradores.add(comprador);
		}
		return compradores;
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
	
	/**
	 * M�todo que guarda el anunciante en el archivo de texto
	 * @param comprador
	 */
	public static void guardarComprador(Comprador comprador) {
		String contenido = "";
		contenido += comprador.toString();
		try {
			ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_COMPRADORES, contenido, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que carga los tipos de productos desde un archivo properties
	 * @return retorna un array de String con los tipos de productos
	 */
	public static String[] cargarTiposProductos() {
		String[] tiposProductos = new String[5];
		tiposProductos = ArchivoUtil.leerArchivoProperties(RUTA_ARCHIVO_TIPOS_PRODUCTOS_PROPERTIES);
		return tiposProductos;
	}
	
	/**
	 * Serializa el modelo en xml
	 * @param aplicacionSubastas
	 */
	public static void serializarModeloXml(SubastasQuindio aplicacionSubastas) {
		ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODEL_XML, aplicacionSubastas);	
	}
	
	/**
	 * Carga los datos del modelo desde el archivo .xml
	 * @return un objeto SubastasQuindio
	 */
	public static SubastasQuindio cargarDatosModelo() {
		SubastasQuindio aplicacionSubastasQuindio = null;
		try {
			aplicacionSubastasQuindio = (SubastasQuindio)ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODEL_XML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aplicacionSubastasQuindio;
	}
	
	/**
	 * Serializa el modelo en binario
	 * @param aplicacionSubastas
	 */
	public static void serializarModeloBinario(SubastasQuindio aplicacionSubastas) throws Exception {
		ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODEL_DAT, aplicacionSubastas);	
	}
	
	/**
	 * Carga los datos del modelo desde el archivo .dat
	 * @return un objeto SubastasQuindio
	 */
	public static SubastasQuindio cargarDatosModeloBinario() throws Exception {
		SubastasQuindio aplicacionSubastasQuindio = null;
		try {
			aplicacionSubastasQuindio = (SubastasQuindio)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODEL_DAT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aplicacionSubastasQuindio;
	}
	
	/**
	 * Metodo que guarda la transaccion
	 * @param transaccion
	 */
	public static void guardarTransaccion(Transaccion transaccion) {
		String contenido = "";
		contenido += transaccion.toString();
		try {
			ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_TRANSACCIONES, contenido, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que guarda la puja
	 * @param puja
	 */
	public static void guardarPuja(Puja puja) {
		String contenido = "";
		contenido += puja.toString();
		try {
			ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PUJAS, contenido, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
