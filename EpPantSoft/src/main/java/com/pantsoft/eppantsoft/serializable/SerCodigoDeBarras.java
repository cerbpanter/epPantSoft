package com.pantsoft.eppantsoft.serializable;

public class SerCodigoDeBarras {
	private String empresa;
	private String modelo;
	private String color;
	private String talla;
	private String codigoDeBarras;
	private boolean aplicaMinimoMaximo;
	private Long minimo;
	private Long maximo;
	private Long loteMinimoMaximo;

	public SerCodigoDeBarras() {
	}

	public SerCodigoDeBarras(String empresa, String modelo, String color, String talla, String codigoDeBarras, boolean aplicaMinimoMaximo, Long minimo, Long maximo, Long loteMinimoMaximo) {
		setEmpresa(empresa);
		setModelo(modelo);
		setColor(color);
		setTalla(talla);
		setCodigoDeBarras(codigoDeBarras);
		setAplicaMinimoMaximo(aplicaMinimoMaximo);
		setMinimo(minimo);
		setMaximo(maximo);
		setLoteMinimoMaximo(loteMinimoMaximo);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public boolean getAplicaMinimoMaximo() {
		return aplicaMinimoMaximo;
	}

	public void setAplicaMinimoMaximo(boolean aplicaMinimoMaximo) {
		this.aplicaMinimoMaximo = aplicaMinimoMaximo;
	}

	public Long getMinimo() {
		return minimo;
	}

	public void setMinimo(Long minimo) {
		this.minimo = minimo;
	}

	public Long getMaximo() {
		return maximo;
	}

	public void setMaximo(Long maximo) {
		this.maximo = maximo;
	}

	public Long getLoteMinimoMaximo() {
		return loteMinimoMaximo;
	}

	public void setLoteMinimoMaximo(Long loteMinimoMaximo) {
		this.loteMinimoMaximo = loteMinimoMaximo;
	}

}
