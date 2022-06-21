package com.pantsoft.eppantsoft.serializable;

public class SerParametro {
	private String empresa;
	private String parametro;
	private String valor;

	public SerParametro() {
	}

	public SerParametro(String empresa, String parametro, String valor) {
		this.empresa = empresa;
		this.setParametro(parametro);
		this.setValor(valor);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
