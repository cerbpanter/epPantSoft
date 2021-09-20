package com.pantsoft.eppantsoft.serializable;

public class SerTemporada {
	private String empresa;
	private long temporada;
	private String descripcion;
	private long temporadaSql;

	public SerTemporada() {
	}

	public SerTemporada(String empresa, long temporada, String descripcion, long temporadaSql) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.descripcion = descripcion;
		this.temporadaSql = temporadaSql;
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

	public long getTemporadaSql() {
		return temporadaSql;
	}

	public void setTemporadaSql(long temporadaSql) {
		this.temporadaSql = temporadaSql;
	}
}
