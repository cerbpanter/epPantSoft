package com.pantsoft.eppantsoft.serializable;

public class SerTallas {
	private String empresa;
	private String talla;
	private String tallas;

	public SerTallas() {
	}

	public SerTallas(String empresa, String talla, String tallas) {
		this.empresa = empresa;
		this.setTalla(talla);
		this.setTallas(tallas);
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

	public String getTallas() {
		return tallas;
	}

	public void setTallas(String tallas) {
		this.tallas = tallas;
	}

}
