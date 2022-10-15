package co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers;

import java.io.IOException;
import java.io.Serializable;

import co.edu.uniquindio.programacion.subastasQuindioVirtual.application.*;
import co.edu.uniquindio.programacion.subastasQuindioVirtual.model.*;

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
    
  
}
