package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerAlmEntrada {
	private String empresa;
	private long folioAlmEntrada;
	private String almacen;
	private long tipo;
	private String zonaHoraria;
	private Date fechaAlmEntrada;
	private String usuarioCreo;
	private String usuarioModifico;
	private String observaciones;
	private long folioOrdenProduccion;
	private long folioMaquilero;
	private String maquilero;
	private String detalle;
	private long folioAlmSalidaTraspaso;
	private String almacenTraspaso;
	private String serieFactura;
	private long folioFactura;
	private long folioCliente;
	private String cliente;
	private boolean tieneError;

	private SerAlmEntradaDet[] dbDetalle;

	public SerAlmEntrada() {
	}

	public SerAlmEntrada(String empresa, long folioAlmEntrada, String almacen, long tipo, String zonaHoraria, Date fechaAlmEntrada, String usuarioCreo, String usuarioModifico, String observaciones, long folioOrdenProduccion, long folioMaquilero, String maquilero, String detalle, long folioAlmSalidaTraspaso, String almacenTraspaso, String serieFactura, long folioFactura, long folioCliente, String cliente, boolean tieneError) {
		setEmpresa(empresa);
		setFolioAlmEntrada(folioAlmEntrada);
		setAlmacen(almacen);
		setTipo(tipo);
		setZonaHoraria(zonaHoraria);
		setFechaAlmEntrada(fechaAlmEntrada);
		setUsuarioCreo(usuarioCreo);
		setUsuarioModifico(usuarioModifico);
		setObservaciones(observaciones);
		setFolioOrdenProduccion(folioOrdenProduccion);
		setFolioMaquilero(folioMaquilero);
		setMaquilero(maquilero);
		setDetalle(detalle);
		setFolioAlmSalidaTraspaso(folioAlmSalidaTraspaso);
		setAlmacenTraspaso(almacenTraspaso);
		setSerieFactura(serieFactura);
		setFolioFactura(folioFactura);
		setFolioCliente(folioCliente);
		setCliente(cliente);
		setTieneError(tieneError);
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

	public String getZonaHoraria() {
		return zonaHoraria;
	}

	public void setZonaHoraria(String zonaHoraria) {
		this.zonaHoraria = zonaHoraria;
	}

	public Date getFechaAlmEntrada() {
		return fechaAlmEntrada;
	}

	public void setFechaAlmEntrada(Date fechaAlmEntrada) {
		this.fechaAlmEntrada = fechaAlmEntrada;
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

	public long getFolioAlmSalidaTraspaso() {
		return folioAlmSalidaTraspaso;
	}

	public void setFolioAlmSalidaTraspaso(long folioAlmSalidaTraspaso) {
		this.folioAlmSalidaTraspaso = folioAlmSalidaTraspaso;
	}

	public String getAlmacenTraspaso() {
		return almacenTraspaso;
	}

	public void setAlmacenTraspaso(String almacenTraspaso) {
		this.almacenTraspaso = almacenTraspaso;
	}

	public String getSerieFactura() {
		return serieFactura;
	}

	public void setSerieFactura(String serieFactura) {
		this.serieFactura = serieFactura;
	}

	public long getFolioFactura() {
		return folioFactura;
	}

	public void setFolioFactura(long folioFactura) {
		this.folioFactura = folioFactura;
	}

	public long getFolioCliente() {
		return folioCliente;
	}

	public void setFolioCliente(long folioCliente) {
		this.folioCliente = folioCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public boolean getTieneError() {
		return tieneError;
	}

	public void setTieneError(boolean tieneError) {
		this.tieneError = tieneError;
	}

}
