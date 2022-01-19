package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerProduccion {
	private String empresa;
	private long temporada;
	private long numOrden;
	private String maquileroCorte;
	private String cliente;
	private String departamento;
	private String descripcion;
	private int estatus;
	private Date fechaProgramada;
	private String modelo;
	private String referencia;
	private long cantidad;
	private long corteSobreTela;
	private long cantidadCorte;
	private long cantidadEntrega;
	private long faltanteMaquilero;
	private long faltanteCorte;
	private String taller;
	private double precio;
	private double costura;
	private double total;
	private double precioFaltante;
	private double totalPorPagar;
	private Date fechaSalida;
	private Date fechaEntrega;
	private String proceso1;
	private String tallerProceso1;
	private double precioProceso1;
	private String proceso2;
	private String tallerProceso2;
	private double precioProceso2;
	private double consumo1;
	private double mtsSolicitados1;
	private double mtsEnviados1;
	private double mtsDevolucion1;
	private double mtsFaltante1;
	private double diferencia1;
	private double consumo2;
	private double mtsSolicitados2;
	private double mtsEnviados2;
	private double diferencia2;
	private double consumo3;
	private double mtsSolicitados3;
	private double mtsEnviados3;
	private double diferencia3;
	private String observaciones;
	private boolean revisado;
	private boolean isaac;
	private String mes;
	private String usuarioModifico;
	private Date fechaModifico;
	private boolean habilitacionEnviada;

	public SerProduccion() {
	}

	public SerProduccion(String empresa, long temporada, long numOrden, String maquileroCorte, String cliente, String departamento, String descripcion, int estatus, Date fechaProgramada, String modelo, String referencia, long cantidad, long corteSobreTela, long cantidadCorte, long cantidadEntrega, long faltanteMaquilero, long faltanteCorte, String taller, double precio, double costura, double total, double precioFaltante, double totalPorPagar, Date fechaSalida, Date fechaEntrega, String proceso1, String tallerProceso1, double precioProceso1, String proceso2, String tallerProceso2, double precioProceso2, double consumo1, double mtsSolicitados1, double mtsEnviados1, double mtsDevolucion1, double mtsFaltante1, double diferencia1, double consumo2, double mtsSolicitados2, double mtsEnviados2, double diferencia2, double consumo3, double mtsSolicitados3, double mtsEnviados3, double diferencia3, String observaciones, boolean revisado, boolean isaac, String mes, String usuarioModifico,
			Date fechaModifico,
			boolean habilitacionEnviada) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.maquileroCorte = maquileroCorte;
		this.cliente = cliente;
		this.departamento = departamento;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.fechaProgramada = fechaProgramada;
		this.modelo = modelo;
		this.referencia = referencia;
		this.cantidad = cantidad;
		this.corteSobreTela = corteSobreTela;
		this.cantidadCorte = cantidadCorte;
		this.cantidadEntrega = cantidadEntrega;
		this.faltanteMaquilero = faltanteMaquilero;
		this.faltanteCorte = faltanteCorte;
		this.taller = taller;
		this.precio = precio;
		this.costura = costura;
		this.total = total;
		this.precioFaltante = precioFaltante;
		this.totalPorPagar = totalPorPagar;
		this.fechaSalida = fechaSalida;
		this.fechaEntrega = fechaEntrega;
		this.proceso1 = proceso1;
		this.tallerProceso1 = tallerProceso1;
		this.precioProceso1 = precioProceso1;
		this.proceso2 = proceso2;
		this.tallerProceso2 = tallerProceso2;
		this.precioProceso2 = precioProceso2;
		this.consumo1 = consumo1;
		this.mtsSolicitados1 = mtsSolicitados1;
		this.mtsEnviados1 = mtsEnviados1;
		this.mtsDevolucion1 = mtsDevolucion1;
		this.mtsFaltante1 = mtsFaltante1;
		this.diferencia1 = diferencia1;
		this.consumo2 = consumo2;
		this.mtsSolicitados2 = mtsSolicitados2;
		this.mtsEnviados2 = mtsEnviados2;
		this.diferencia2 = diferencia2;
		this.consumo3 = consumo3;
		this.mtsSolicitados3 = mtsSolicitados3;
		this.mtsEnviados3 = mtsEnviados3;
		this.diferencia3 = diferencia3;
		this.observaciones = observaciones;
		this.revisado = revisado;
		this.isaac = isaac;
		this.mes = mes;
		this.usuarioModifico = usuarioModifico;
		this.fechaModifico = fechaModifico;
		this.habilitacionEnviada = habilitacionEnviada;
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

	public long getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(long numOrden) {
		this.numOrden = numOrden;
	}

	public String getMaquileroCorte() {
		return maquileroCorte;
	}

	public void setMaquileroCorte(String maquileroCorte) {
		this.maquileroCorte = maquileroCorte;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Date getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public long getCorteSobreTela() {
		return corteSobreTela;
	}

	public void setCorteSobreTela(long corteSobreTela) {
		this.corteSobreTela = corteSobreTela;
	}

	public long getCantidadCorte() {
		return cantidadCorte;
	}

	public void setCantidadCorte(long cantidadCorte) {
		this.cantidadCorte = cantidadCorte;
	}

	public long getCantidadEntrega() {
		return cantidadEntrega;
	}

	public void setCantidadEntrega(long cantidadEntrega) {
		this.cantidadEntrega = cantidadEntrega;
	}

	public long getFaltanteMaquilero() {
		return faltanteMaquilero;
	}

	public void setFaltanteMaquilero(long faltanteMaquilero) {
		this.faltanteMaquilero = faltanteMaquilero;
	}

	public long getFaltanteCorte() {
		return faltanteCorte;
	}

	public void setFaltanteCorte(long faltanteCorte) {
		this.faltanteCorte = faltanteCorte;
	}

	public String getTaller() {
		return taller;
	}

	public void setTaller(String taller) {
		this.taller = taller;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getCostura() {
		return costura;
	}

	public void setCostura(double costura) {
		this.costura = costura;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPrecioFaltante() {
		return precioFaltante;
	}

	public void setPrecioFaltante(double precioFaltante) {
		this.precioFaltante = precioFaltante;
	}

	public double getTotalPorPagar() {
		return totalPorPagar;
	}

	public void setTotalPorPagar(double totalPorPagar) {
		this.totalPorPagar = totalPorPagar;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getProceso1() {
		return proceso1;
	}

	public void setProceso1(String proceso1) {
		this.proceso1 = proceso1;
	}

	public String getTallerProceso1() {
		return tallerProceso1;
	}

	public void setTallerProceso1(String tallerProceso1) {
		this.tallerProceso1 = tallerProceso1;
	}

	public double getPrecioProceso1() {
		return precioProceso1;
	}

	public void setPrecioProceso1(double precioProceso1) {
		this.precioProceso1 = precioProceso1;
	}

	public String getProceso2() {
		return proceso2;
	}

	public void setProceso2(String proceso2) {
		this.proceso2 = proceso2;
	}

	public String getTallerProceso2() {
		return tallerProceso2;
	}

	public void setTallerProceso2(String tallerProceso2) {
		this.tallerProceso2 = tallerProceso2;
	}

	public double getPrecioProceso2() {
		return precioProceso2;
	}

	public void setPrecioProceso2(double precioProceso2) {
		this.precioProceso2 = precioProceso2;
	}

	public double getConsumo1() {
		return consumo1;
	}

	public void setConsumo1(double consumo1) {
		this.consumo1 = consumo1;
	}

	public double getMtsSolicitados1() {
		return mtsSolicitados1;
	}

	public void setMtsSolicitados1(double mtsSolicitados1) {
		this.mtsSolicitados1 = mtsSolicitados1;
	}

	public double getMtsEnviados1() {
		return mtsEnviados1;
	}

	public void setMtsEnviados1(double mtsEnviados1) {
		this.mtsEnviados1 = mtsEnviados1;
	}

	public double getMtsDevolucion1() {
		return mtsDevolucion1;
	}

	public void setMtsDevolucion1(double mtsDevolucion1) {
		this.mtsDevolucion1 = mtsDevolucion1;
	}

	public double getMtsFaltante1() {
		return mtsFaltante1;
	}

	public void setMtsFaltante1(double mtsFaltante1) {
		this.mtsFaltante1 = mtsFaltante1;
	}

	public double getDiferencia1() {
		return diferencia1;
	}

	public void setDiferencia1(double diferencia1) {
		this.diferencia1 = diferencia1;
	}

	public double getConsumo2() {
		return consumo2;
	}

	public void setConsumo2(double consumo2) {
		this.consumo2 = consumo2;
	}

	public double getMtsSolicitados2() {
		return mtsSolicitados2;
	}

	public void setMtsSolicitados2(double mtsSolicitados2) {
		this.mtsSolicitados2 = mtsSolicitados2;
	}

	public double getMtsEnviados2() {
		return mtsEnviados2;
	}

	public void setMtsEnviados2(double mtsEnviados2) {
		this.mtsEnviados2 = mtsEnviados2;
	}

	public double getDiferencia2() {
		return diferencia2;
	}

	public void setDiferencia2(double diferencia2) {
		this.diferencia2 = diferencia2;
	}

	public double getConsumo3() {
		return consumo3;
	}

	public void setConsumo3(double consumo3) {
		this.consumo3 = consumo3;
	}

	public double getMtsSolicitados3() {
		return mtsSolicitados3;
	}

	public void setMtsSolicitados3(double mtsSolicitados3) {
		this.mtsSolicitados3 = mtsSolicitados3;
	}

	public double getMtsEnviados3() {
		return mtsEnviados3;
	}

	public void setMtsEnviados3(double mtsEnviados3) {
		this.mtsEnviados3 = mtsEnviados3;
	}

	public double getDiferencia3() {
		return diferencia3;
	}

	public void setDiferencia3(double diferencia3) {
		this.diferencia3 = diferencia3;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean getRevisado() {
		return revisado;
	}

	public void setRevisado(boolean revisado) {
		this.revisado = revisado;
	}

	public boolean getIsaac() {
		return isaac;
	}

	public void setIsaac(boolean isaac) {
		this.isaac = isaac;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(String usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Date getFechaModifico() {
		return fechaModifico;
	}

	public void setFechaModifico(Date fechaModifico) {
		this.fechaModifico = fechaModifico;
	}

	public boolean getHabilitacionEnviada() {
		return habilitacionEnviada;
	}

	public void setHabilitacionEnviada(boolean habilitacionEnviada) {
		this.habilitacionEnviada = habilitacionEnviada;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
