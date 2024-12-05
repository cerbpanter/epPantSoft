package com.pantsoft.eppantsoft.serializable;

public class SerPedidoDet {
	private String empresa;
	private long folioPedido;
	private long renglon;
	private long temporada;
	private String modelo;
	private String referencia;
	private double precio;
	private long cantidad;
	private String tallas;
	private String observaciones;
	private String detalle;
	private boolean revisado;
	private String marca;

	public SerPedidoDet(String empresa, long folioPedido, long renglon, long temporada, String modelo, String referencia, double precio, long cantidad, String tallas, String observaciones, String detalle, boolean revisado, String marca) {
		setEmpresa(empresa);
		setFolioPedido(folioPedido);
		setRenglon(renglon);
		setTemporada(temporada);
		setModelo(modelo);
		setReferencia(referencia);
		setPrecio(precio);
		setCantidad(cantidad);
		setTallas(tallas);
		setObservaciones(observaciones);
		setDetalle(detalle);
		setRevisado(revisado);
		setMarca(marca);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioPedido() {
		return folioPedido;
	}

	public void setFolioPedido(long folioPedido) {
		this.folioPedido = folioPedido;
	}

	public long getRenglon() {
		return renglon;
	}

	public void setRenglon(long renglon) {
		this.renglon = renglon;
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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public String getTallas() {
		return tallas;
	}

	public void setTallas(String tallas) {
		this.tallas = tallas;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public boolean getRevisado() {
		return revisado;
	}

	public void setRevisado(boolean revisado) {
		this.revisado = revisado;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

}
