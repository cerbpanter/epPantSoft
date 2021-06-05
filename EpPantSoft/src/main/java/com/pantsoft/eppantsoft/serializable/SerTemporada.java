package com.pantsoft.eppantsoft.serializable;

public class SerTemporada {
	private String empresa;
	private long temporada;
	private String descripcion;

	public SerTemporada() {
	}

	public SerTemporada(String empresa, long temporada, String descripcion) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
