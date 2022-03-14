package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerAlmEntradaDet {
	private String empresa;
	private long folioAlmEntrada;
	private String almacen;
	private String modelo;
	private long temporada;
	private String color;
	private String talla;
	private String codigoDeBarras;
	private Date fechaAlmEntrada;
	private long dia;
	private long mes;
	private long anio;
	private long cantidad;

	public SerAlmEntradaDet() {
	}

	public SerAlmEntradaDet(String empresa, long folioAlmEntrada, String almacen, String modelo, long temporada, String color, String talla, String codigoDeBarras, Date fechaAlmEntrada, long dia, long mes, long anio, long cantidad) {
		setEmpresa(empresa);
		setFolioAlmEntrada(folioAlmEntrada);
		setAlmacen(almacen);
		setModelo(modelo);
		setTemporada(temporada);
		setColor(color);
		setTalla(talla);
		setCodigoDeBarras(codigoDeBarras);
		setFechaAlmEntrada(fechaAlmEntrada);
		setDia(dia);
		setMes(mes);
		setAnio(anio);
		setCantidad(cantidad);
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

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
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

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public long getAnio() {
		return anio;
	}

	public void setAnio(long anio) {
		this.anio = anio;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

}
