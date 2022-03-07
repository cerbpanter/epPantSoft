package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerAlmEntrada {
	private String empresa;
	private long folioAlmEntrada;
	private String almacen;
	private long tipo;
	private Date fechaAlmEntrada;
	private long dia;
	private long mes;
	private String usuarioCreo;
	private String usuarioModifico;
	private String observaciones;
	private long folioOrdenProduccion;
	private long folioMaquilero;
	private String maquilero;
	private String detalle;

	private SerAlmEntradaDet[] dbDetalle;

	public SerAlmEntrada() {
	}

	public SerAlmEntrada(String empresa, long folioAlmEntrada, String almacen, long tipo, Date fechaAlmEntrada, long dia, long mes, String usuarioCreo, String usuarioModifico, String observaciones, long folioOrdenProduccion, long folioMaquilero, String maquilero, String detalle) {
		setEmpresa(empresa);
		setFolioAlmEntrada(folioAlmEntrada);
		setAlmacen(almacen);
		setTipo(tipo);
		setFechaAlmEntrada(fechaAlmEntrada);
		setDia(dia);
		setMes(mes);
		setUsuarioCreo(usuarioCreo);
		setUsuarioModifico(usuarioModifico);
		setObservaciones(observaciones);
		setFolioOrdenProduccion(folioOrdenProduccion);
		setFolioMaquilero(folioMaquilero);
		setMaquilero(maquilero);
		setDetalle(detalle);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioAlmEntrada() {
		return folioAlmEntrada;
	}

	public void setFolioAlmEntrada(long folioAlmEntrada) {
		this.folioAlmEntrada = folioAlmEntrada;
	}

	public String getAlmacen() {
		return almacen;
	}

	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}

	public long getTipo() {
		return tipo;
	}

	public void setTipo(long tipo) {
		this.tipo = tipo;
	}

	public Date getFechaAlmEntrada() {
		return fechaAlmEntrada;
	}

	public void setFechaAlmEntrada(Date fechaAlmEntrada) {
		this.fechaAlmEntrada = fechaAlmEntrada;
	}

	public long getDia() {
		return dia;
	}

	public void setDia(long dia) {
		this.dia = dia;
	}

	public long getMes() {
		return mes;
	}

	public void setMes(long mes) {
		this.mes = mes;
	}

	public String getUsuarioCreo() {
		return usuarioCreo;
	}

	public void setUsuarioCreo(String usuarioCreo) {
		this.usuarioCreo = usuarioCreo;
	}

	public String getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(String usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public long getFolioOrdenProduccion() {
		return folioOrdenProduccion;
	}

	public void setFolioOrdenProduccion(long folioOrdenProduccion) {
		this.folioOrdenProduccion = folioOrdenProduccion;
	}

	public long getFolioMaquilero() {
		return folioMaquilero;
	}

	public void setFolioMaquilero(long folioMaquilero) {
		this.folioMaquilero = folioMaquilero;
	}

	public String getMaquilero() {
		return maquilero;
	}

	public void setMaquilero(String maquilero) {
		this.maquilero = maquilero;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public SerAlmEntradaDet[] getDbDetalle() {
		return dbDetalle;
	}

	public void setDbDetalle(SerAlmEntradaDet[] dbDetalle) {
		this.dbDetalle = dbDetalle;
	}

}
