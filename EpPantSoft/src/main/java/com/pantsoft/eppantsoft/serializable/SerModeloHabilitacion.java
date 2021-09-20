package com.pantsoft.eppantsoft.serializable;

public class SerModeloHabilitacion {
	private String empresa;
	private long temporada;
	private String modelo;
	private String referencia;
	private String materia;
	private double consumo;
	private double consumoReal;
	private double trazo;

	public SerModeloHabilitacion() {
	}

	public SerModeloHabilitacion(String empresa, long temporada, String modelo, String referencia, String materia, double consumo, double consumoReal, double trazo) {
		setEmpresa(empresa);
		setTemporada(temporada);
		setModelo(modelo);
		setReferencia(referencia);
		setMateria(materia);
		setConsumo(consumo);
		setConsumoReal(consumoReal);
		setTrazo(trazo);
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

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public double getConsumoReal() {
		return consumoReal;
	}

	public void setConsumoReal(double consumoReal) {
		this.consumoReal = consumoReal;
	}

	public double getTrazo() {
		return trazo;
	}

	public void setTrazo(double trazo) {
		this.trazo = trazo;
	}

}
