package com.pantsoft.eppantsoft.serializable;

public class SerProceso {
	private String empresa;
	private String proceso;

	public SerProceso() {
	}

	public SerProceso(String empresa, String proceso) {
		this.empresa = empresa;
		this.setProceso(proceso);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

}
