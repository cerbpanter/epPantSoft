package com.pantsoft.eppantsoft.serializable;

public class SerUsuario {
	private String empresa;
	private String usuario;
	private String password;
	private long[] permisos;
	private String[] talleres;
	private String sesion;
	private long vigencia;
	private String[] almacenes;
	private long[] tiposEntrada;
	private long[] tiposSalida;

	public SerUsuario() {
	}

	public SerUsuario(String empresa, String usuario, String password, long[] permisos, String[] talleres, String sesion, long vigencia) {
		this.empresa = empresa;
		this.usuario = usuario;
		this.password = password;
		this.permisos = permisos;
		this.setTalleres(talleres);
		this.setSesion(sesion);
		this.setVigencia(vigencia);
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

	public String[] getTalleres() {
		return talleres;
	}

	public void setTalleres(String[] talleres) {
		this.talleres = talleres;
	}

	public String getSesion() {
		return sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}

	public long getVigencia() {
		return vigencia;
	}

	public void setVigencia(long vigencia) {
		this.vigencia = vigencia;
	}

	public String[] getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(String[] almacenes) {
		this.almacenes = almacenes;
	}

	public long[] getTiposEntrada() {
		return tiposEntrada;
	}

	public void setTiposEntrada(long[] tiposEntrada) {
		this.tiposEntrada = tiposEntrada;
	}

	public long[] getTiposSalida() {
		return tiposSalida;
	}

	public void setTiposSalida(long[] tiposSalida) {
		this.tiposSalida = tiposSalida;
	}
}
