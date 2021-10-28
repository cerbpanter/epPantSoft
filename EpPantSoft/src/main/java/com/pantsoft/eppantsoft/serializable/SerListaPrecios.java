package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerListaPrecios {
	private String empresa;
	private long temporada;
	private long idListaPrecios;
	private String descripcion;
	private Date fechaGenerada;
	private boolean modoBloqueo;
	private double descuento;
	private boolean autoGenerar;
	private double pvp;
	private String usuarioPropietario;
	private String filtros;
	private boolean valido;

	public SerListaPrecios() {
	}

	public SerListaPrecios(String empresa, long temporada, long idListaPrecios, String descripcion, Date fechaGenerada, boolean modoBloqueo, double descuento, boolean autoGenerar, double pvp, String usuarioPropietario, String filtros, boolean valido) {
		setEmpresa(empresa);
		setTemporada(temporada);
		setIdListaPrecios(idListaPrecios);
		setDescripcion(descripcion);
		setFechaGenerada(fechaGenerada);
		setModoBloqueo(modoBloqueo);
		setDescuento(descuento);
		setAutoGenerar(autoGenerar);
		setPvp(pvp);
		setUsuarioPropietario(usuarioPropietario);
		setFiltros(filtros);
		setValido(valido);
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

	public long getIdListaPrecios() {
		return idListaPrecios;
	}

	public void setIdListaPrecios(long idListaPrecios) {
		this.idListaPrecios = idListaPrecios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaGenerada() {
		return fechaGenerada;
	}

	public void setFechaGenerada(Date fechaGenerada) {
		this.fechaGenerada = fechaGenerada;
	}

	public boolean getModoBloqueo() {
		return modoBloqueo;
	}

	public void setModoBloqueo(boolean modoBloqueo) {
		this.modoBloqueo = modoBloqueo;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public boolean getAutoGenerar() {
		return autoGenerar;
	}

	public void setAutoGenerar(boolean autoGenerar) {
		this.autoGenerar = autoGenerar;
	}

	public double getPvp() {
		return pvp;
	}

	public void setPvp(double pvp) {
		this.pvp = pvp;
	}

	public String getUsuarioPropietario() {
		return usuarioPropietario;
	}

	public void setUsuarioPropietario(String usuarioPropietario) {
		this.usuarioPropietario = usuarioPropietario;
	}

	public String getFiltros() {
		return filtros;
	}

	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}

	public boolean getValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

}
