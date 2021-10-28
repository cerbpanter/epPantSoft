package com.pantsoft.eppantsoft.serializable;

public class SerListaPreciosDet {
	private String empresa;
	private long temporada;
	private long idListaPrecios;
	private String modelo;
	private String referencia;
	private String talla;
	private String descripcion;
	private String tela;
	private String departamento;
	private double precosto;
	private double margen;
	private double precioVenta;
	private double precioVentaPublico;
	private boolean seleccion;
	private boolean valido;

	public SerListaPreciosDet() {
	}

	public SerListaPreciosDet(String empresa, long temporada, long idListaPrecios, String modelo, String referencia, String talla, String descripcion, String tela, String departamento, double precosto, double margen, double precioVenta, double precioVentaPublico, boolean seleccion, boolean valido) {
		setEmpresa(empresa);
		setTemporada(temporada);
		setIdListaPrecios(idListaPrecios);
		setModelo(modelo);
		setReferencia(referencia);
		setTalla(talla);
		setDescripcion(descripcion);
		setTela(tela);
		setDepartamento(departamento);
		setPrecosto(precosto);
		setMargen(margen);
		setPrecioVenta(precioVenta);
		setPrecioVentaPublico(precioVentaPublico);
		setSeleccion(seleccion);
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

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public double getPrecosto() {
		return precosto;
	}

	public void setPrecosto(double precosto) {
		this.precosto = precosto;
	}

	public double getMargen() {
		return margen;
	}

	public void setMargen(double margen) {
		this.margen = margen;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getPrecioVentaPublico() {
		return precioVentaPublico;
	}

	public void setPrecioVentaPublico(double precioVentaPublico) {
		this.precioVentaPublico = precioVentaPublico;
	}

	public boolean getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(boolean seleccion) {
		this.seleccion = seleccion;
	}

	public boolean getValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

}
