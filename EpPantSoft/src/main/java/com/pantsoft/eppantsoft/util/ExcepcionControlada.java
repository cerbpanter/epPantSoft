package com.pantsoft.eppantsoft.util;

public class ExcepcionControlada extends Exception {
	private static final long serialVersionUID = 1L;
	private int tipo;
	private String valor;

	public ExcepcionControlada(String message) {
		super(message);
		this.tipo = 0;
		this.valor = "";
	}

	public ExcepcionControlada(String message, int tipo, String valor) {
		super(message);
		this.tipo = tipo;
		this.valor = valor;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
