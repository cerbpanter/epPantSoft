package com.pantsoft.eppantsoft.serializable;

public class SerAlmacen {
	private String empresa;
	private String almacen;

	public SerAlmacen() {
	}

	public SerAlmacen(String empresa, String almacen) {
		this.empresa = empresa;
		this.setAlmacen(almacen);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getAlmacen() {
		return almacen;
	}

	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}

}
