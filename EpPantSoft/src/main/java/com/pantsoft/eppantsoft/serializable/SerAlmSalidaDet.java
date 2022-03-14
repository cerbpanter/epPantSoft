package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerAlmSalidaDet {
	private String empresa;
	private long folioAlmSalida;
	private String almacen;
	private String modelo;
	private long temporada;
	private String color;
	private String talla;
	private String codigoDeBarras;
	private Date fechaAlmSalida;
	private long dia;
	private long mes;
	private long anio;
	private long cantidad;

	public SerAlmSalidaDet() {
	}

	public SerAlmSalidaDet(String empresa, long folioAlmSalida, String almacen, String modelo, long temporada, String color, String talla, String codigoDeBarras, Date fechaAlmSalida, long dia, long mes, long anio, long cantidad) {
		setEmpresa(empresa);
		setFolioAlmSalida(folioAlmSalida);
		setAlmacen(almacen);
		setModelo(modelo);
		setTemporada(temporada);
		setColor(color);
		setTalla(talla);
		setCodigoDeBarras(codigoDeBarras);
		setFechaAlmSalida(fechaAlmSalida);
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
