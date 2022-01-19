package com.pantsoft.eppantsoft.serializable;

public class SerColor {
	private String empresa;
	private long temporada;
	private String color;

	public SerColor() {
	}

	public SerColor(String empresa, long temporada, String color) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.setColor(color);
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
