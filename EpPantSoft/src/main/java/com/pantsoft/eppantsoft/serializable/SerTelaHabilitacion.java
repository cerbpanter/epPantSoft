package com.pantsoft.eppantsoft.serializable;

public class SerTelaHabilitacion {
	private String empresa;
	private long temporada;
	private String materia;
	private String tipo;
	private double precio;
	private double ancho;

	public SerTelaHabilitacion() {
	}

	public SerTelaHabilitacion(String empresa, long temporada, String materia, String tipo, double precio, double ancho) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.setMateria(materia);
		this.setTipo(tipo);
		this.setPrecio(precio);
		this.setAncho(ancho);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

}
