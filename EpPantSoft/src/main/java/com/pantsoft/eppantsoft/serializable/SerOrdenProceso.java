package com.pantsoft.eppantsoft.serializable;

public class SerOrdenProceso {
	private String empresa;
	private long folioOrdenProceso;
	private long folioOrden;
	private long orden;
	private long temporada;
	private int estatus;
	private long folioPedido;
	private long renglonPedido;
	private String modelo;
	private String referencia;
	private String tallas;
	private String proceso;
	private long folioMaquilero;
	private String maquilero;
	private long cantidadEntrada;
	private long cantidadSalida;
	private String observaciones;
	private String detalleEntrada;
	private String detalleSalida;
	private String usuario;
	private String bitacora;
	private boolean porRevisar;
	private String obsRevision;
	private String zonaHoraria;

	public SerOrdenProceso(String empresa, long folioOrden, long folioOrdenProceso, long orden, long temporada, int estatus, long folioPedido, long renglonPedido, String modelo, String referencia, String tallas, String proceso, String maquilero, long cantidadEntrada, long cantidadSalida, String observaciones, String detalleEntrada, String detalleSalida, boolean porRevisar, String obsRevision) {
		this.empresa = empresa;
		this.folioOrdenProceso = folioOrdenProceso;
		this.folioOrden = folioOrden;
		this.orden = orden;
		this.temporada = temporada;
		this.estatus = estatus;
		this.folioPedido = folioPedido;
		this.renglonPedido = renglonPedido;
		this.modelo = modelo;
		this.referencia = referencia;
		this.tallas = tallas;
		this.proceso = proceso;
		this.maquilero = maquilero;
		this.cantidadEntrada = cantidadEntrada;
		this.cantidadSalida = cantidadSalida;
		this.observaciones = observaciones;
		this.detalleEntrada = detalleEntrada;
		this.detalleSalida = detalleSalida;
		this.porRevisar = porRevisar;
		this.obsRevision = obsRevision;
	}

	public SerOrdenProceso() {
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioOrdenProceso() {
		return folioOrdenProceso;
	}

	public void setFolioOrdenProceso(long folioOrdenProceso) {
		this.folioOrdenProceso = folioOrdenProceso;
	}

	public long getFolioOrden() {
		return folioOrden;
	}

	public void setFolioOrden(long folioOrden) {
		this.folioOrden = folioOrden;
	}

	public long getOrden() {
		return orden;
	}

	public void setOrden(long orden) {
		this.orden = orden;
	}

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
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

	public String getTallas() {
		return tallas;
	}

	public void setTallas(String tallas) {
		this.tallas = tallas;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public long getFolioMaquilero() {
		return folioMaquilero;
	}

	public void setFolioMaquilero(long folioMaquilero) {
		this.folioMaquilero = folioMaquilero;
	}

	public String getMaquilero() {
		return maquilero;
	}

	public void setMaquilero(String maquilero) {
		this.maquilero = maquilero;
	}

	public long getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(long cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}

	public long getCantidadSalida() {
		return cantidadSalida;
	}

	public void setCantidadSalida(long cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getDetalleEntrada() {
		return detalleEntrada;
	}

	public void setDetalleEntrada(String detalleEntrada) {
		this.detalleEntrada = detalleEntrada;
	}

	public String getDetalleSalida() {
		return detalleSalida;
	}

	public void setDetalleSalida(String detalleSalida) {
		this.detalleSalida = detalleSalida;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getBitacora() {
		return bitacora;
	}

	public void setBitacora(String bitacora) {
		this.bitacora = bitacora;
	}

	public boolean getPorRevisar() {
		return porRevisar;
	}

	public void setPorRevisar(boolean porRevisar) {
		this.porRevisar = porRevisar;
	}

	public String getObsRevision() {
		return obsRevision;
	}

	public void setObsRevision(String obsRevision) {
		this.obsRevision = obsRevision;
	}

	public String getZonaHoraria() {
		return zonaHoraria;
	}

	public void setZonaHoraria(String zonaHoraria) {
		this.zonaHoraria = zonaHoraria;
	}

}
