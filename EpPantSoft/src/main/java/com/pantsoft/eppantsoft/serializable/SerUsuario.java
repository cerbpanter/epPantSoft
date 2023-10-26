package com.pantsoft.eppantsoft.serializable;

public class SerUsuario {
	private String empresa;
	private String usuario;
	private String password;
	private long[] permisos;
	private String[] talleres;
	private String[] clientes;
	private String sesion;
	private long vigencia;
	private String[] almacenes;
	private long[] tiposEntrada;
	private long[] tiposSalida;
	private boolean diseno;
	private boolean trazo;

	public SerUsuario() {
	}

	public SerUsuario(String empresa, String usuario, String password, long[] permisos, String[] talleres, String[] clientes, String sesion, long vigencia, boolean diseno, boolean trazo) {
		this.empresa = empresa;
		this.usuario = usuario;
		this.password = password;
		this.permisos = permisos;
		this.setTalleres(talleres);
		this.setClientes(clientes);
		this.setSesion(sesion);
		this.setVigencia(vigencia);
		this.setDiseno(diseno);
		this.setTrazo(trazo);
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

	public String[] getClientes() {
		return clientes;
	}

	public void setClientes(String[] clientes) {
		this.clientes = clientes;
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

	public boolean getDiseno() {
		return diseno;
	}

	public void setDiseno(boolean diseno) {
		this.diseno = diseno;
	}

	public boolean getTrazo() {
		return trazo;
	}

	public void setTrazo(boolean trazo) {
		this.trazo = trazo;
	}

}
