package com.pantsoft.eppantsoft.serializable;

public class SerEntidadCampo {
	private String entidad;
	private String campo;
	private String tipo;
	private boolean indexado;
	private boolean permiteNull;
	private long largoMinimo;
	private long largoMaximo;
	private boolean entidadGrande;
	private String valorDefault;
	private long posLlave;
	private boolean sustituirNull;
	private String descripcion;

	public SerEntidadCampo() {
	}

	public SerEntidadCampo(String entidad, String campo, String tipo, boolean indexado, boolean permiteNull, long largoMinimo, long largoMaximo, boolean entidadGrande, String valorDefault, long posLlave, boolean sustituirNull) {
		this.entidad = entidad;
		this.setCampo(campo);
		this.setTipo(tipo);
		this.setIndexado(indexado);
		this.setPermiteNull(permiteNull);
		this.setLargoMinimo(largoMinimo);
		this.setLargoMaximo(largoMaximo);
		this.setEntidadGrande(entidadGrande);
		this.setValorDefault(valorDefault);
		this.setPosLlave(posLlave);
		this.setSustituirNull(sustituirNull);
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean getIndexado() {
		return indexado;
	}

	public void setIndexado(boolean indexado) {
		this.indexado = indexado;
	}

	public boolean getPermiteNull() {
		return permiteNull;
	}

	public void setPermiteNull(boolean permiteNull) {
		this.permiteNull = permiteNull;
	}

	public long getLargoMinimo() {
		return largoMinimo;
	}

	public void setLargoMinimo(long largoMinimo) {
		this.largoMinimo = largoMinimo;
	}

	public long getLargoMaximo() {
		return largoMaximo;
	}

	public void setLargoMaximo(long largoMaximo) {
		this.largoMaximo = largoMaximo;
	}

	public boolean getEntidadGrande() {
		return entidadGrande;
	}

	public void setEntidadGrande(boolean entidadGrande) {
		this.entidadGrande = entidadGrande;
	}

	public String getValorDefault() {
		return valorDefault;
	}

	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}

	public long getPosLlave() {
		return posLlave;
	}

	public void setPosLlave(long posLlave) {
		this.posLlave = posLlave;
	}

	public boolean getSustituirNull() {
		return sustituirNull;
	}

	public void setSustituirNull(boolean sustituirNull) {
		this.sustituirNull = sustituirNull;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
