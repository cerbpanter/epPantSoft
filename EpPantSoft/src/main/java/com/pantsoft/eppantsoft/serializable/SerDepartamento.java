package com.pantsoft.eppantsoft.serializable;

public class SerDepartamento {
	private String empresa;
	private String departamento;

	public SerDepartamento() {
	}

	public SerDepartamento(String empresa, String departamento) {
		this.empresa = empresa;
		this.setDepartamento(departamento);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

}
