package com.pantsoft.eppantsoft.serializable;

public class SerAlmEntradaDet {
	private String empresa;
	private long folioAlmEntrada;
	private String almacen;
	private long temporada;
	private String modelo;
	private long dia;
	private long mes;
	private long cantidad;
	private String detalle;

	public SerAlmEntradaDet() {
	}

	public SerAlmEntradaDet(String empresa, long folioAlmEntrada, String almacen, long temporada, String modelo, long dia, long mes, long cantidad, String detalle) {
		setEmpresa(empresa);
		setFolioAlmEntrada(folioAlmEntrada);
		setAlmacen(almacen);
		setTemporada(temporada);
		setModelo(modelo);
		setDia(dia);
		setMes(mes);
		setCantidad(cantidad);
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

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
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

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

}
