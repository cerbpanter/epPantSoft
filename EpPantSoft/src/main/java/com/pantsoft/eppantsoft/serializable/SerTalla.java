package com.pantsoft.eppantsoft.serializable;

public class SerTalla {
	private String empresa;
	private long temporada;
	private String talla;

	public SerTalla() {
	}

	public SerTalla(String empresa, long temporada, String talla) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.setTalla(talla);
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

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

}
