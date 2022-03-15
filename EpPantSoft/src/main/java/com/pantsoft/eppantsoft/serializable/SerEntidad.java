package com.pantsoft.eppantsoft.serializable;

public class SerEntidad {
	private String entidad;
	private boolean respaldar;
	private boolean endpoint;
	private boolean soloAdmin;
	private boolean validarSucursal;

	public SerEntidad() {
	}

	public SerEntidad(String entidad, boolean respaldar, boolean endpoint, boolean soloAdmin, boolean validarSucursal) {
		this.entidad = entidad;
		this.respaldar = respaldar;
		this.endpoint = endpoint;
		this.soloAdmin = soloAdmin;
		this.validarSucursal = validarSucursal;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public boolean getRespaldar() {
		return respaldar;
	}

	public void setRespaldar(boolean respaldar) {
		this.respaldar = respaldar;
	}

	public boolean getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(boolean endpoint) {
		this.endpoint = endpoint;
	}

	public boolean getSoloAdmin() {
		return soloAdmin;
	}

	public void setSoloAdmin(boolean soloAdmin) {
		this.soloAdmin = soloAdmin;
	}

	public boolean getValidarSucursal() {
		return validarSucursal;
	}

	public void setValidarSucursal(boolean validarSucursal) {
		this.validarSucursal = validarSucursal;
	}
}
