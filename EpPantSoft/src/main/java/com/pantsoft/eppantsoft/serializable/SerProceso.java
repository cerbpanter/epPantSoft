package com.pantsoft.eppantsoft.serializable;

public class SerProceso {
	private String empresa;
	private long temporada;
	private String proceso;

	public SerProceso() {
	}

	public SerProceso(String empresa, long temporada, String proceso) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.setProceso(proceso);
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

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

}
