package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;

public class Puja implements Serializable{

	private static final long serialVersionUID = -8486945425831717466L;
	
	double valor;
	String nombreAnunciante;
	String nombreProducto;
	String nombreComprador;
	int codigoPuja;
	
	public Puja() {
		super();
	}

	public Puja(double valor, String nombreAnunciante, String nombreProducto, String nombreComprador, int codigoPuja) {
		super();
		this.valor = valor;
		this.nombreAnunciante = nombreAnunciante;
		this.nombreProducto = nombreProducto;
		this.nombreComprador = nombreComprador;
		this.codigoPuja = codigoPuja;
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

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getNombreComprador() {
		return nombreComprador;
	}

	public void setNombreComprador(String nombreComprador) {
		this.nombreComprador = nombreComprador;
	}

	public int getCodigoPuja() {
		return codigoPuja;
	}

	public void setCodigoPuja(int codigoPuja) {
		this.codigoPuja = codigoPuja;
	}

	@Override
	public String toString() {
		return valor + "@@" + nombreAnunciante + "@@" + nombreProducto + "@@" + nombreComprador + "@@" + codigoPuja + "\n";
	}	
}
