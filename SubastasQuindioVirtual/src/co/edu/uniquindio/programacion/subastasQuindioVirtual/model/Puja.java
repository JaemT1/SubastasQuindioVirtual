package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;

public class Puja implements Serializable{

	private static final long serialVersionUID = -8486945425831717466L;
	
	double valor;
	Anuncio anuncio;
	Comprador comprador;
	int codigoPuja;
	
	public Puja() {
		super();
	}

	public Puja(double valor, Anuncio anuncio, Comprador comprador, int codigoPuja) {
		super();
		this.valor = valor;
		this.anuncio = anuncio;
		this.comprador = comprador;
		this.codigoPuja = codigoPuja;
	}
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	public Comprador getComprador() {
		return comprador;
	}
	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	public int getCodigoPuja() {
		return codigoPuja;
	}
	public void setCodigoPuja(int codigoPuja) {
		this.codigoPuja = codigoPuja;
	}	
}
