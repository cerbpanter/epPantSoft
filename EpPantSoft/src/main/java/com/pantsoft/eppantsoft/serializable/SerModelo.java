package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerModelo {
	private String empresa;
	private long temporada;
	private String modelo;
	private String referencia;
	private String copiaDe;
	private String departamento;
	private String descripcionSeccion;
	private String descripcion2;
	private String tela;
	private String talla;
	private String observaciones;
	private double costura;
	private double otros;
	private double precosto;
	private double costo;
	private Date fecha;
	private boolean ok;
	private boolean cortado;
	private boolean esPantSoft;

	private SerModeloHabilitacion[] habilitaciones;
	private SerModeloProceso[] procesos;

	public SerModelo() {
	}

	public SerModelo(String empresa, long temporada, String modelo, String referencia, String copiaDe, String departamento, String descripcionSeccion, String descripcion2, String tela, String talla, String observaciones, double costura, double otros, double precosto, double costo, Date fecha, boolean ok, boolean cortado, boolean esPantSoft) {
		setEmpresa(empresa);
		setTemporada(temporada);
		setModelo(modelo);
		setReferencia(referencia);
		setCopiaDe(copiaDe);
		setDepartamento(departamento);
		setDescripcionSeccion(descripcionSeccion);
		setDescripcion2(descripcion2);
		setTela(tela);
		setTalla(talla);
		setObservaciones(observaciones);
		setCostura(costura);
		setOtros(otros);
		setPrecosto(precosto);
		setCosto(costo);
		setFecha(fecha);
		setOk(ok);
		setCortado(cortado);
		setEsPantSoft(esPantSoft);
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

	public String getCopiaDe() {
		return copiaDe;
	}

	public void setCopiaDe(String copiaDe) {
		this.copiaDe = copiaDe;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDescripcionSeccion() {
		return descripcionSeccion;
	}

	public void setDescripcionSeccion(String descripcionSeccion) {
		this.descripcionSeccion = descripcionSeccion;
	}

	public String getDescripcion2() {
		return descripcion2;
	}

	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public double getCostura() {
		return costura;
	}

	public void setCostura(double costura) {
		this.costura = costura;
	}

	public double getOtros() {
		return otros;
	}

	public void setOtros(double otros) {
		this.otros = otros;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean getOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public boolean getCortado() {
		return cortado;
	}

	public void setCortado(boolean cortado) {
		this.cortado = cortado;
	}

	public SerModeloHabilitacion[] getHabilitaciones() {
		return habilitaciones;
	}

	public void setHabilitaciones(SerModeloHabilitacion[] habilitaciones) {
		this.habilitaciones = habilitaciones;
	}

	public SerModeloProceso[] getProcesos() {
		return procesos;
	}

	public void setProcesos(SerModeloProceso[] procesos) {
		this.procesos = procesos;
	}

	public boolean getEsPantSoft() {
		return esPantSoft;
	}

	public void setEsPantSoft(boolean esPantSoft) {
		this.esPantSoft = esPantSoft;
	}

}
