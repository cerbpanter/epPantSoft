package com.pantsoft.eppantsoft.serializable;

public class SerModeloProceso {
	private String empresa;
	private long temporada;
	private String modelo;
	private String referencia;
	private String proceso;
	private double precosto;
	private double costo;

	public SerModeloProceso() {
	}

	public SerModeloProceso(String empresa, long temporada, String modelo, String referencia, String proceso, double precosto, double costo) {
		setEmpresa(empresa);
		setTemporada(temporada);
		setModelo(modelo);
		setReferencia(referencia);
		setProceso(proceso);
		setPrecosto(precosto);
		setCosto(costo);
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

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public double getPrecosto() {
		return precosto;
	}

	public void setPrecosto(double precosto) {
		this.precosto = precosto;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

}
