package com.pantsoft.eppantsoft.serializable;

import java.util.ArrayList;
import java.util.Date;

public class SerAlmSalida {
	private String empresa;
	private long folioAlmSalida;
	private String almacen;
	private long tipo;
	private Date fechaAlmSalida;
	private long dia;
	private long mes;
	private String usuarioCreo;
	private String usuarioModifico;
	private String observaciones;
	private ArrayList<String> facturas;
	private long folioCliente;
	private String cliente;
	private String detalle;

	private SerAlmSalidaDet[] dbDetalle;

	public SerAlmSalida() {
	}

	public SerAlmSalida(String empresa, long folioAlmSalida, String almacen, long tipo, Date fechaAlmSalida, long dia, long mes, String usuarioCreo, String usuarioModifico, String observaciones, ArrayList<String> facturas, long folioCliente, String cliente, String detalle) {
		setEmpresa(empresa);
		setFolioAlmSalida(folioAlmSalida);
		setAlmacen(almacen);
		setTipo(tipo);
		setFechaAlmSalida(fechaAlmSalida);
		setDia(dia);
		setMes(mes);
		setUsuarioCreo(usuarioCreo);
		setUsuarioModifico(usuarioModifico);
		setObservaciones(observaciones);
		setFacturas(facturas);
		setFolioCliente(folioCliente);
		setCliente(cliente);
		setDetalle(detalle);
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

	public Date getFechaAlmSalida() {
		return fechaAlmSalida;
	}

	public void setFechaAlmSalida(Date fechaAlmSalida) {
		this.fechaAlmSalida = fechaAlmSalida;
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

	public ArrayList<String> getFacturas() {
		return facturas;
	}

	public void setFacturas(ArrayList<String> facturas) {
		this.facturas = facturas;
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

}