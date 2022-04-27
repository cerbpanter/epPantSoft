package com.pantsoft.eppantsoft.serializable;

public class SerTelaHabilitacion {
	private String empresa;
	private String materia;
	private String tipo;
	private double precio;
	private double ancho;

	public SerTelaHabilitacion() {
	}

	public SerTelaHabilitacion(String empresa, String materia, String tipo, double precio, double ancho) {
		this.empresa = empresa;
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
