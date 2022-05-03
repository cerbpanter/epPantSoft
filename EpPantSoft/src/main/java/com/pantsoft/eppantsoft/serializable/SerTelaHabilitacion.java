package com.pantsoft.eppantsoft.serializable;

public class SerTelaHabilitacion {
	private String empresa;
	private String materia;
	private String tipo;
	private String precios;
	private double ancho;

	public SerTelaHabilitacion() {
	}

	public SerTelaHabilitacion(String empresa, String materia, String tipo, String precios, double ancho) {
		this.empresa = empresa;
		this.setMateria(materia);
		this.setTipo(tipo);
		this.setPrecios(precios);
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

	public String getPrecios() {
		return precios;
	}

	public void setPrecios(String precios) {
		this.precios = precios;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

}
