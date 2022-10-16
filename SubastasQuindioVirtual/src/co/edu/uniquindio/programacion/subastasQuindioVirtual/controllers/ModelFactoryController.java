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
	SubastasQuindio aplicacionSubastas = new SubastasQuindio();
	Main gestorVentanas = new Main();

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
        //Siempre se debe verificar si la raiz del recurso es null
        if(aplicacionSubastas == null) {
            System.out.println("La aplicación de subastas está vacía");
        }
    }
    
    /**
     * Método que carga los estudiantes desde el archivo .txt
     */
    public void cargarAnunciantes() {
    	try {
			this.aplicacionSubastas.setUsuarios(Persistencia.cargarAnunciantes());
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
}
