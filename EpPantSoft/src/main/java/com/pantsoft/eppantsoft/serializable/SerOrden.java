package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerOrden {
	private String empresa;
	private long folioOrden;
	private long temporada;
	private long folioPedido;
	private long renglonPedido;
	private String modelo;
	private String referencia;
	private boolean revisado;
	// Diseño
	private String usuarioDiseno;
	private Date fechaDiseno;
	private boolean disenoTerminado;
	private String carpetaTrazo;
	private long piezasMolde;
	private long prioridadDiseno;
	// Trazo
	private String usuarioTrazo;
	private Date fechaTrazo;
	private boolean trazoTerminado;
	private String distribucion;
	private double largoTrazoTela;
	private double largoTrazoBies;
	private String trazos;
	private long prioridadTrazo;

	private SerOrdenProceso procesos[];

	public SerOrden(String empresa, long folioOrden, long temporada, long folioPedido, long renglonPedido, String modelo, String referencia, boolean revisado, String usuarioDiseno, Date fechaDiseno, boolean disenoTerminado, String usuarioTrazo, Date fechaTrazo, boolean trazoTerminado) {
		this.empresa = empresa;
		this.folioOrden = folioOrden;
		this.temporada = temporada;
		this.folioPedido = folioPedido;
		this.renglonPedido = renglonPedido;
		this.modelo = modelo;
		this.referencia = referencia;
		this.usuarioDiseno = usuarioDiseno;
		this.fechaDiseno = fechaDiseno;
		this.disenoTerminado = disenoTerminado;
		this.usuarioTrazo = usuarioTrazo;
		this.fechaTrazo = fechaTrazo;
		this.trazoTerminado = trazoTerminado;
		this.revisado = revisado;
	}

	public SerOrden() {
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioOrden() {
		return folioOrden;
	}

	public void setFolioOrden(long folioOrden) {
		this.folioOrden = folioOrden;
	}

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public long getFolioPedido() {
		return folioPedido;
	}

	public void setFolioPedido(long folioPedido) {
		this.folioPedido = folioPedido;
	}

	public long getRenglonPedido() {
		return renglonPedido;
	}

	public void setRenglonPedido(long renglonPedido) {
		this.renglonPedido = renglonPedido;
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

	public boolean getRevisado() {
		return revisado;
	}

	public void setRevisado(boolean revisado) {
		this.revisado = revisado;
	}

	public SerOrdenProceso[] getProcesos() {
		return procesos;
	}

	public void setProcesos(SerOrdenProceso[] procesos) {
		this.procesos = procesos;
	}

	public String getUsuarioDiseno() {
		return usuarioDiseno;
	}

	public void setUsuarioDiseno(String usuarioDiseno) {
		this.usuarioDiseno = usuarioDiseno;
	}

	public Date getFechaDiseno() {
		return fechaDiseno;
	}

	public void setFechaDiseno(Date fechaDiseno) {
		this.fechaDiseno = fechaDiseno;
	}

	public boolean getDisenoTerminado() {
		return disenoTerminado;
	}

	public void setDisenoTerminado(boolean disenoTerminado) {
		this.disenoTerminado = disenoTerminado;
	}

	public String getCarpetaTrazo() {
		return carpetaTrazo;
	}

	public void setCarpetaTrazo(String carpetaTrazo) {
		this.carpetaTrazo = carpetaTrazo;
	}

	public long getPiezasMolde() {
		return piezasMolde;
	}

	public void setPiezasMolde(long piezasMolde) {
		this.piezasMolde = piezasMolde;
	}

	public long getPrioridadDiseno() {
		return prioridadDiseno;
	}

	public void setPrioridadDiseno(long prioridadDiseno) {
		this.prioridadDiseno = prioridadDiseno;
	}

	public String getUsuarioTrazo() {
		return usuarioTrazo;
	}

	public void setUsuarioTrazo(String usuarioTrazo) {
		this.usuarioTrazo = usuarioTrazo;
	}

	public Date getFechaTrazo() {
		return fechaTrazo;
	}

	public void setFechaTrazo(Date fechaTrazo) {
		this.fechaTrazo = fechaTrazo;
	}

	public boolean getTrazoTerminado() {
		return trazoTerminado;
	}

	public void setTrazoTerminado(boolean trazoTerminado) {
		this.trazoTerminado = trazoTerminado;
	}

	public String getDistribucion() {
		return distribucion;
	}

	public void setDistribucion(String distribucion) {
		this.distribucion = distribucion;
	}

	public double getLargoTrazoTela() {
		return largoTrazoTela;
	}

	public void setLargoTrazoTela(double largoTrazoTela) {
		this.largoTrazoTela = largoTrazoTela;
	}

	public double getLargoTrazoBies() {
		return largoTrazoBies;
	}

	public void setLargoTrazoBies(double largoTrazoBies) {
		this.largoTrazoBies = largoTrazoBies;
	}

	public long getPrioridadTrazo() {
		return prioridadTrazo;
	}

	public void setPrioridadTrazo(long prioridadTrazo) {
		this.prioridadTrazo = prioridadTrazo;
	}

	public String getTrazos() {
		return trazos;
	}

	public void setTrazos(String trazos) {
		this.trazos = trazos;
	}

}
