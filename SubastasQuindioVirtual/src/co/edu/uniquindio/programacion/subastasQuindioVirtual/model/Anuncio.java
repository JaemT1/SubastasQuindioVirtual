package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Anuncio implements Serializable{

	private static final long serialVersionUID = -5448981009687680450L;
	
	//Declaracion de atributos
	String tipoProducto;
	int tiempoLimite;
	String nombreProducto;
	String descripcion;
	String rutaFoto;
	String nombreAnunciante;
	String fechaPublicacion;
	String fechaFinPublicacion;
	double valorInicial;
	boolean estado;
	ArrayList<Puja> pujas= new ArrayList<Puja>();
	
	//Constructores
	public Anuncio() {
	}
	
	public Anuncio(String tipoProducto, int tiempoLimite, String nombreProducto, String descripcion, String foto,
			String nombreAnunciante, String fechaPublicacion, String fechaFinPublicacion, double valorInicial,
			boolean estado, ArrayList<Puja> pujas) {
		super();
		this.tipoProducto = tipoProducto;
		this.tiempoLimite = tiempoLimite;
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.rutaFoto = foto;
		this.nombreAnunciante = nombreAnunciante;
		this.fechaPublicacion = fechaPublicacion;
		this.fechaFinPublicacion = fechaFinPublicacion;
		this.valorInicial = valorInicial;
		this.estado = estado;
		this.pujas = pujas;
	}

	//Getters y Setters
	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public int getTiempoLimite() {
		return tiempoLimite;
	}

	public void setTiempoLimite(int tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return rutaFoto;
	}

	public void setFoto(String foto) {
		this.rutaFoto = foto;
	}

	public String getNombreAnunciante() {
		return nombreAnunciante;
	}

	public void setNombreAnunciante(String nombreAnunciante) {
		this.nombreAnunciante = nombreAnunciante;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getFechaFinPublicacion() {
		return fechaFinPublicacion;
	}

	public void setFechaFinPublicacion(String fechaFinPublicacion) {
		this.fechaFinPublicacion = fechaFinPublicacion;
	}

	public double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public ArrayList<Puja> getPujas() {
		return pujas;
	}

	public void setPujas(ArrayList<Puja> pujas) {
		this.pujas = pujas;
	}

	//To String
	@Override
	public String toString() {
		return tipoProducto + "@@" + nombreProducto + "@@" + descripcion + "@@" + rutaFoto + "@@" + nombreAnunciante
				+ "@@" + fechaPublicacion + "@@" + fechaFinPublicacion+ "@@" + valorInicial;
	}
}
