package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;

public class Transaccion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int numTransaccion;
	private String fecha;
	private double valor;
	private String nombreAnunciante;
	private String nombreComprador;
	private String nombreProducto;
	
	public Transaccion(){
		
	}

	public Transaccion(int numTransaccion, String fecha, double valor, String nombreAnunciante, String nombreComprador,
			String nombreProducto) {
		super();
		this.numTransaccion = numTransaccion;
		this.fecha = fecha;
		this.valor = valor;
		this.nombreAnunciante = nombreAnunciante;
		this.nombreComprador = nombreComprador;
		this.nombreProducto = nombreProducto;
	}

	public int getNumTransaccion() {
		return numTransaccion;
	}

	public void setNumTransaccion(int numTransaccion) {
		this.numTransaccion = numTransaccion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNombreAnunciante() {
		return nombreAnunciante;
	}

	public void setNombreAnunciante(String nombreAnunciante) {
		this.nombreAnunciante = nombreAnunciante;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	//Sobreescritura del método toString
		@Override
		public String toString() {
			return numTransaccion + "@@" + fecha + "@@" + valor + "@@" + nombreAnunciante + "@@" + nombreComprador + "@@" + nombreProducto +"\n";
		}
	
	
}
