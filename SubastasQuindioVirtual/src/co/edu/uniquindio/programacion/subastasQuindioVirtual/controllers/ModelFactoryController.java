package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.persistence.*;

public class ModelFactoryController implements Serializable{

	private static final long serialVersionUID = -4040905587180317945L;
	
	//Declaración de atributos
	private static ModelFactoryController instance;
	
	//Quien almacena todo el modelo
	SubastasQuindio aplicacionSubastas = new SubastasQuindio();
	
	//Quien gestiona las views
	Main gestorVentanas = new Main();
	
	String[] tiposProductos = new String[5];
	
	//Almacena el usuario, en este caso, el anunciante que ha iniciado sesión
	Anunciante anuncianteSesionIniciada = new Anunciante();
	
	//Almacena el usuario, en este caso, el comprador que ha iniciado sesión
	Comprador compradorSesionIniciada = new Comprador();
	
	//Nombre del anuncio a modificar
	String nombreAnuncioAModificar = ""; 
    //------------------------------  SINGLETON ------------------------------------------------
    /**
     * Método que devuelve la instancia del singleton 
     * y verifica si existe la instancia, si no la crea
     * @return instance
     */
    public static ModelFactoryController getInstance(){
        if(instance == null){
            instance = new ModelFactoryController();
        }
        return instance;
    }
    
    /**
     * Método constructor
     */
    private ModelFactoryController(){
    	cargarDatosModelo();
        //Siempre se debe verificar si la raiz del recurso es null
        if(aplicacionSubastas == null) {
            System.out.println("La aplicación de subastas está vacía");
        }
    }
    
    /**
     * Datos de prueba
     * @throws Exception
     */
    public void cargarDatosIniciales() {
    	ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
    	ArrayList<Puja> pujas = new ArrayList<Puja>();
    	Anunciante anunciante = new Anunciante(anuncios, "holi", "Juan Tunubala", 19, "juantunubala@gmail.com");
    	Comprador comprador = new Comprador(pujas, "puedeser", "Jose Antonio", 41, "joseant@gmail.com");
    	Anuncio anuncio = new Anuncio("Tecnologico", 68, "La roca chiquita", "Skin limitada de la roca chiquita", "C:\\td\\persistencia\\imagenesProductos\\laroca.jpeg", "Juan Tunubala", "2022-10-23", "2022-12-30", 78000, true, pujas);
    	anunciante.getAnuncios().add(anuncio);
    	aplicacionSubastas.getAnuncios().add(anuncio);
    	aplicacionSubastas.getUsuarios().add(anunciante);
    	aplicacionSubastas.getUsuarios().add(comprador);
    	try {
			serializarModeloBinario();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	serializarModeloXml();
    	guardarAnunciante(anunciante);
    	guardarComprador(comprador);
    }

    /**
     * Método que carga los usuarios desde el archivo .txt
     */
    public void cargarAnunciantes() {
    	try {
			this.aplicacionSubastas.setUsuarios(Persistencia.cargarAnunciantes());
			cargarCompradores();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * M�todo que carga los compradores desde el archivo .txt
     */
    public void cargarCompradores() {
    	try {
    		ArrayList<Usuario> usuarios = Persistencia.cargarCompradores();
    		for (Usuario usuario : usuarios) {
				this.aplicacionSubastas.getUsuarios().add(usuario);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Método que guarda un registro en un archivo log
     * @param mensaje el mensaje a guardar
     * @param nivel el nivel del registro
     * @param accion la acción que se realizaba
     */
    public void guardarLog(String mensaje, int nivel, String accion) {
    	try {
			Persistencia.guardarLog(mensaje, nivel, accion);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Método que guarda el anunciante en el archivo de texto
     * @param anunciante anunciante a guardar
     */
	public void guardarAnunciante(Anunciante anunciante) {
		Persistencia.guardarAnunciante(anunciante);
		
	}   
	
	/**
	 * M�todo que guarda el comprador en el archivo de texto
	 * @param comprador
	 */
	public void guardarComprador(Comprador comprador) {
		Persistencia.guardarComprador(comprador);
		
	}   
	
	/**
	 * Metodo que carga los tipos de productos desde un archivo properties
	 */
	public void cargarTiposProductos() {
		tiposProductos = Persistencia.cargarTiposProductos();
	}
	
	/**
	 * Serializa el modelo en xml
	 */
	public void serializarModeloXml() {
		Persistencia.serializarModeloXml(aplicacionSubastas);
	}
	
	/**
	 * Carga los datos del modelo desde el archivo .xml
	 */
	public void cargarDatosModelo() {
		aplicacionSubastas = Persistencia.cargarDatosModelo();
		
	}
	

	/**
	 * Serializa el modelo en binario
	 * @throws Exception 
	 */
	public void serializarModeloBinario() throws Exception {
		Persistencia.serializarModeloBinario(aplicacionSubastas);
	}
	
	/**
	 * Carga los datos del modelo desde el archivo binario
	 * @throws Exception 
	 */
	public void cargarDatosModeloBinario() throws Exception {
		aplicacionSubastas = Persistencia.cargarDatosModeloBinario();
		
	}
}
