package com.pantsoft.eppantsoft.util;

public class ClsCampo {
	private String nombre = null;
	private Tipo tipo = null;
	private boolean indexado = false;
	private boolean permiteNull = true;
	private int largoMinimo = 0;
	private int largoMaximo = 0;
	private boolean entidadGrande = false;
	private String valorDefault = null;
	private int posLlave = 0;
	private boolean sustituirNull = false;

	public enum Tipo {
		String, Boolean, Long, Double, Date, Rating, Text, Blob, ArrayString, ArrayLong, Email
	}

	public ClsCampo(String nombre, Tipo tipo, boolean indexado, boolean permiteNull, int largoMinimo, int largoMaximo, boolean entidadGrande, String valorDefault, int posLlave, boolean sustituirNull) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.indexado = indexado;
		this.permiteNull = permiteNull;
		this.largoMinimo = largoMinimo;
		this.largoMaximo = largoMaximo;
		this.entidadGrande = entidadGrande;
		this.valorDefault = valorDefault;
		this.posLlave = posLlave;
		this.sustituirNull = sustituirNull;
		// if (this.sustituirNull && valorDefault.equals("<SiNubeDefault>"))
		// throw new ExcepcionControlada("Error en definición del campo '" + this.nombre + "', no se puede sustituir null si el valor default es null");
	}

	public String getNombre() {
		return nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public boolean getIndexado() {
		return indexado;
	}

	public boolean getPermiteNull() {
		return permiteNull;
	}

	public int getLargoMinimo() {
		return largoMinimo;
	}

	public int getLargoMaximo() {
		return largoMaximo;
	}

	public boolean getEntidadGrande() {
		return entidadGrande;
	}

	public String getValorDefault() {
		return valorDefault;
	}

	public int getPosLlave() {
		return posLlave;
	}

	public boolean getSustituirNull() {
		return sustituirNull;
	}
}
