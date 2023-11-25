package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerProveedorPago {
	private String empresa;
	private String serieFactura;
	private long folioFactura;
	private String uuid;
	private double importeTotal;
	private boolean revisado;
	private boolean autorizado;
	private boolean pagado;
	private boolean terminado;
	private long folioProveedor;
	private String proveedor;
	private Date fechaFactura;
	private Date fechaVencimiento;
	private String zonaHoraria;

	public SerProveedorPago() {
	}

	public SerProveedorPago(String empresa, String serieFactura, long folioFactura, String uuid, double importeTotal, boolean revisado, boolean autorizado, boolean pagado, boolean terminado, long folioProveedor, String proveedor, Date fechaFactura, Date fechaVencimiento) {
		setEmpresa(empresa);
		setSerieFactura(serieFactura);
		setFolioFactura(folioFactura);
		setUuid(uuid);
		setImporteTotal(importeTotal);
		setRevisado(revisado);
		setAutorizado(autorizado);
		setPagado(pagado);
		setTerminado(terminado);
		setFolioProveedor(folioProveedor);
		setProveedor(proveedor);
		setFechaFactura(fechaFactura);
		setFechaVencimiento(fechaVencimiento);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getSerieFactura() {
		return serieFactura;
	}

	public void setSerieFactura(String serieFactura) {
		this.serieFactura = serieFactura;
	}

	public long getFolioFactura() {
		return folioFactura;
	}

	public void setFolioFactura(long folioFactura) {
		this.folioFactura = folioFactura;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public boolean getRevisado() {
		return revisado;
	}

	public void setRevisado(boolean revisado) {
		this.revisado = revisado;
	}

	public boolean getAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public boolean getPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public boolean getTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public long getFolioProveedor() {
		return folioProveedor;
	}

	public void setFolioProveedor(long folioProveedor) {
		this.folioProveedor = folioProveedor;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getZonaHoraria() {
		return zonaHoraria;
	}

	public void setZonaHoraria(String zonaHoraria) {
		this.zonaHoraria = zonaHoraria;
	}

}
