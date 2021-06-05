package com.pantsoft.eppantsoft.serializable;

public class SerVista {
	private String empresa;
	private String vista;
	private long[] permisos;

	public SerVista() {
	}

	public SerVista(String empresa, String vista, long[] permisos) {
		this.empresa = empresa;
		this.vista = vista;
		this.permisos = permisos;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public long[] getPermisos() {
		return permisos;
	}

	public void setPermisos(long[] permisos) {
		this.permisos = permisos;
	}
}
