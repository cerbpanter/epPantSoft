package com.pantsoft.eppantsoft.serializable;

public class SerUsuario {
	private String empresa;
	private String usuario;
	private String password;
	private long[] permisos;

	public SerUsuario() {
	}

	public SerUsuario(String empresa, String usuario, String password, long[] permisos) {
		this.empresa = empresa;
		this.usuario = usuario;
		this.password = password;
		this.permisos = permisos;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long[] getPermisos() {
		return permisos;
	}

	public void setPermisos(long[] permisos) {
		this.permisos = permisos;
	}
}
