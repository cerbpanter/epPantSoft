package com.pantsoft.eppantsoft.serializable;

public class SerInvModeloDet {
	private String empresa;
	private String almacen;
	private String modelo;
	private String color;
	private String talla;
	private String codigoDeBarras;
	private long cantidad;

	public SerInvModeloDet() {
	}

	public SerInvModeloDet(String empresa, String almacen, String modelo, String color, String talla, String codigoDeBarras, long cantidad) {
		setEmpresa(empresa);
		setAlmacen(almacen);
		setModelo(modelo);
		setColor(color);
		setTalla(talla);
		setCodigoDeBarras(codigoDeBarras);
		setCantidad(cantidad);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

}
