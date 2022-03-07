package com.pantsoft.eppantsoft.serializable;

public class SerCodigoDeBarras {
	private String empresa;
	private long temporada;
	private String modelo;
	private String color;
	private String talla;
	private String codigoDeBarras;

	public SerCodigoDeBarras() {
	}

	public SerCodigoDeBarras(String empresa, long temporada, String modelo, String color, String talla, String codigoDeBarras) {
		setEmpresa(empresa);
		setTemporada(temporada);
		setModelo(modelo);
		setColor(color);
		setTalla(talla);
		setCodigoDeBarras(codigoDeBarras);
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

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

}
