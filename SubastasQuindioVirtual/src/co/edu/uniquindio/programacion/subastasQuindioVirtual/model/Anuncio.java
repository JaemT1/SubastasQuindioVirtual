package co.edu.uniquindio.programacion.subastasQuindioVirtual.model;


import java.util.ArrayList;
import java.util.Date;

import javafx.scene.image.Image;

public class Anuncio {
	TipoProducto tipoProducto;
	int tiempoLimite;
	String nombreProducto;
	String descripcion;
	Image foto;
	String nombreAnunciante;
	Date fechaPublicacion;
	Date fechaFinPublicacion;
	double valorInicial;
	boolean estado;
	ArrayList<Puja> pujas= new ArrayList<Puja>();
	
	public Anuncio(TipoProducto tipoProducto, int tiempoLimite, String nombreProducto, String descripcion, Image foto,
			String nombreAnunciante, Date fechaPublicacion, Date fechaFinPublicacion, double valorInicial,
			boolean estado, ArrayList<Puja> pujas) {
		super();
		this.tipoProducto = tipoProducto;
		this.tiempoLimite = tiempoLimite;
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.foto = foto;
		this.nombreAnunciante = nombreAnunciante;
		this.fechaPublicacion = fechaPublicacion;
		this.fechaFinPublicacion = fechaFinPublicacion;
		this.valorInicial = valorInicial;
		this.estado = estado;
		this.pujas = pujas;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
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

	public Image getFoto() {
		return foto;
	}

	public void setFoto(Image foto) {
		this.foto = foto;
	}

	public String getNombreAnunciante() {
		return nombreAnunciante;
	}

	public void setNombreAnunciante(String nombreAnunciante) {
		this.nombreAnunciante = nombreAnunciante;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Date getFechaFinPublicacion() {
		return fechaFinPublicacion;
	}

	public void setFechaFinPublicacion(Date fechaFinPublicacion) {
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
	
}
