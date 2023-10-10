package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerBitacora {
	private String usuario;
	private Date fecha;
	private String descipcion;

	public SerBitacora() {
	}

	public SerBitacora(String usuario, Date fecha, String descripcion) {
		setUsuario(usuario);
		setFecha(fecha);
		setDescipcion(descripcion);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescipcion() {
		return descipcion;
	}

	public void setDescipcion(String descipcion) {
		this.descipcion = descipcion;
	}

}
