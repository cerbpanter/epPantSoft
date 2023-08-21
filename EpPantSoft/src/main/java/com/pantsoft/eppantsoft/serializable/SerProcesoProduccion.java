package com.pantsoft.eppantsoft.serializable;

public class SerProcesoProduccion {
	private String empresa;
	private long folioProcesoProduccion;
	private long temporada;
	private int estatus;
	private long folioPedido;
	private long renglon;
	private long folioProcesoOrigen;
	private long folioProcesoDestino;
	private String modelo;
	private String referencia;
	private String tallas;
	private String proceso;
	private String maquilero;
	private long cantidadEntrada;
	private long cantidadSalida;
	private String observaciones;
	private String detalleEntrada;
	private String detalleSalida;

	public SerProcesoProduccion(String empresa, long folioProcesoProduccion, long temporada, int estatus, long folioPedido, long renglon, long folioProcesoOrigen, long folioProcesoDestino, String modelo, String referencia, String tallas, String proceso, String maquilero, long cantidadEntrada, long cantidadSalida, String observaciones, String detalleEntrada, String detalleSalida) {
		this.empresa = empresa;
		this.folioProcesoProduccion = folioProcesoProduccion;
		this.temporada = temporada;
		this.estatus = estatus;
		this.folioPedido = folioPedido;
		this.renglon = renglon;
		this.folioProcesoOrigen = folioProcesoOrigen;
		this.folioProcesoDestino = folioProcesoDestino;
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
	}

	public SerProcesoProduccion() {
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioProcesoProduccion() {
		return folioProcesoProduccion;
	}

	public void setFolioProcesoProduccion(long folioProcesoProduccion) {
		this.folioProcesoProduccion = folioProcesoProduccion;
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

	public long getRenglon() {
		return renglon;
	}

	public void setRenglon(long renglon) {
		this.renglon = renglon;
	}

	public long getFolioProcesoOrigen() {
		return folioProcesoOrigen;
	}

	public void setFolioProcesoOrigen(long folioProcesoOrigen) {
		this.folioProcesoOrigen = folioProcesoOrigen;
	}

	public long getFolioProcesoDestino() {
		return folioProcesoDestino;
	}

	public void setFolioProcesoDestino(long folioProcesoDestino) {
		this.folioProcesoDestino = folioProcesoDestino;
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

}
