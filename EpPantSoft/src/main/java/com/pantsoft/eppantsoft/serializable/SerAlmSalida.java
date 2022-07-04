package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerAlmSalida {
	private String empresa;
	private long folioAlmSalida;
	private String almacen;
	private long tipo; // 1-Ajuste, 2-Factura. 3-Traspaso
	private String zonaHoraria;
	private Date fechaAlmSalida;
	private String usuarioCreo;
	private String usuarioModifico;
	private String observaciones;
	private String serieFactura;
	private long folioFactura;
	private long folioCliente;
	private String cliente;
	private String detalle;
	private long folioAlmEntradaTraspaso;
	private String almacenTraspaso;
	private boolean tieneError;
	private boolean validarInventario;

	private SerAlmSalidaDet[] dbDetalle;

	public SerAlmSalida() {
	}

	public SerAlmSalida(String empresa, long folioAlmSalida, String almacen, long tipo, String zonaHoraria, Date fechaAlmSalida, String usuarioCreo, String usuarioModifico, String observaciones, String serieFactura, long folioFactura, long folioCliente, String cliente, String detalle, long folioAlmEntradaTraspaso, String almacenTraspaso, boolean tieneError) {
		setEmpresa(empresa);
		setFolioAlmSalida(folioAlmSalida);
		setAlmacen(almacen);
		setTipo(tipo);
		setZonaHoraria(zonaHoraria);
		setFechaAlmSalida(fechaAlmSalida);
		setUsuarioCreo(usuarioCreo);
		setUsuarioModifico(usuarioModifico);
		setObservaciones(observaciones);
		setSerieFactura(serieFactura);
		setFolioFactura(folioFactura);
		setFolioCliente(folioCliente);
		setCliente(cliente);
		setDetalle(detalle);
		setFolioAlmEntradaTraspaso(folioAlmEntradaTraspaso);
		setAlmacenTraspaso(almacenTraspaso);
		setTieneError(tieneError);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioAlmSalida() {
		return folioAlmSalida;
	}

	public void setFolioAlmSalida(long folioAlmSalida) {
		this.folioAlmSalida = folioAlmSalida;
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

	public Date getFechaAlmSalida() {
		return fechaAlmSalida;
	}

	public void setFechaAlmSalida(Date fechaAlmSalida) {
		this.fechaAlmSalida = fechaAlmSalida;
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public SerAlmSalidaDet[] getDbDetalle() {
		return dbDetalle;
	}

	public void setDbDetalle(SerAlmSalidaDet[] dbDetalle) {
		this.dbDetalle = dbDetalle;
	}

	public long getFolioAlmEntradaTraspaso() {
		return folioAlmEntradaTraspaso;
	}

	public void setFolioAlmEntradaTraspaso(long folioAlmEntradaTraspaso) {
		this.folioAlmEntradaTraspaso = folioAlmEntradaTraspaso;
	}

	public String getAlmacenTraspaso() {
		return almacenTraspaso;
	}

	public void setAlmacenTraspaso(String almacenTraspaso) {
		this.almacenTraspaso = almacenTraspaso;
	}

	public boolean getTieneError() {
		return tieneError;
	}

	public void setTieneError(boolean tieneError) {
		this.tieneError = tieneError;
	}

	public boolean getValidarInventario() {
		return validarInventario;
	}

	public void setValidarInventario(boolean validarInventario) {
		this.validarInventario = validarInventario;
	}

}
