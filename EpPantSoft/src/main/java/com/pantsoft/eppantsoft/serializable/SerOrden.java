package com.pantsoft.eppantsoft.serializable;

public class SerOrden {
	private String empresa;
	private long folioOrden;
	private long temporada;
	private long folioPedido;
	private long renglonPedido;
	private String modelo;
	private String referencia;

	private SerOrdenProceso procesos[];

	public SerOrden(String empresa, long folioOrden, long temporada, long folioPedido, long renglonPedido, String modelo, String referencia) {
		this.empresa = empresa;
		this.folioOrden = folioOrden;
		this.temporada = temporada;
		this.folioPedido = folioPedido;
		this.renglonPedido = renglonPedido;
		this.modelo = modelo;
		this.referencia = referencia;
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

	public SerOrdenProceso[] getProcesos() {
		return procesos;
	}

	public void setProcesos(SerOrdenProceso[] procesos) {
		this.procesos = procesos;
	}

}
