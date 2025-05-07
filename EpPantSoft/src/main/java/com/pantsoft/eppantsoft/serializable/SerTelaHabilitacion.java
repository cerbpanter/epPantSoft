package com.pantsoft.eppantsoft.serializable;

public class SerTelaHabilitacion {
	private String empresa;
	private String materia;
	private String tipo;
	private String precios;
	private double ancho;
	private String composicion1;
	private String composicion2;
	private String composicion3;
	private String composicion4;

	public SerTelaHabilitacion() {
	}

	public SerTelaHabilitacion(String empresa, String materia, String tipo, String precios, double ancho, String composicion1, String composicion2, String composicion3, String composicion4) {
		this.empresa = empresa;
		this.setMateria(materia);
		this.setTipo(tipo);
		this.setPrecios(precios);
		this.setAncho(ancho);
		this.setComposicion1(composicion1);
		this.setComposicion2(composicion2);
		this.setComposicion3(composicion3);
		this.setComposicion4(composicion4);
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

	public String getComposicion1() {
		return composicion1;
	}

	public void setComposicion1(String composicion1) {
		this.composicion1 = composicion1;
	}

	public String getComposicion2() {
		return composicion2;
	}

	public void setComposicion2(String composicion2) {
		this.composicion2 = composicion2;
	}

	public String getComposicion3() {
		return composicion3;
	}

	public void setComposicion3(String composicion3) {
		this.composicion3 = composicion3;
	}

	public String getComposicion4() {
		return composicion4;
	}

	public void setComposicion4(String composicion4) {
		this.composicion4 = composicion4;
	}

}
