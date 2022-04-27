package com.pantsoft.eppantsoft.serializable;

public class SerTalla {
	private String empresa;
	private String talla;
	private long orden;

	public SerTalla() {
	}

	public SerTalla(String empresa, String talla, long orden) {
		this.empresa = empresa;
		this.setTalla(talla);
		this.setOrden(orden);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public long getOrden() {
		return orden;
	}

	public void setOrden(long orden) {
		this.orden = orden;
	}

}
