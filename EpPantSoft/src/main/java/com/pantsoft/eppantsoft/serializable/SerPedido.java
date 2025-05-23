package com.pantsoft.eppantsoft.serializable;

import java.util.Date;

public class SerPedido {
	private String empresa;
	private long folioPedido;
	private long temporada;
	private String zonaHoraria;
	private Date fechaPedido;
	private Long folioCliente;
	private String cliente;
	private String subCliente;
	private Date fechaCancelacion;
	private String departamento;
	private boolean confirmado;
	private boolean resurtido;

	private SerPedidoDet[] detalles;

	public SerPedido(String empresa, long folioPedido, long temporada, String zonaHoraria, Date fechaPedido, Long folioCliente, String cliente, String subCliente, Date fechaCancelacion, String departamento, boolean confirmado, boolean resurtido) {
		setEmpresa(empresa);
		setFolioPedido(folioPedido);
		setTemporada(temporada);
		setZonaHoraria(zonaHoraria);
		setFechaPedido(fechaPedido);
		setFolioCliente(folioCliente);
		setCliente(cliente);
		setSubCliente(subCliente);
		setFechaCancelacion(fechaCancelacion);
		setDepartamento(departamento);
		setConfirmado(confirmado);
		setResurtido(resurtido);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getFolioPedido() {
		return folioPedido;
	}

	public void setFolioPedido(long folioPedido) {
		this.folioPedido = folioPedido;
	}

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public String getZonaHoraria() {
		return zonaHoraria;
	}

	public void setZonaHoraria(String zonaHoraria) {
		this.zonaHoraria = zonaHoraria;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Long getFolioCliente() {
		return folioCliente;
	}

	public void setFolioCliente(Long folioCliente) {
		this.folioCliente = folioCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getSubCliente() {
		return subCliente;
	}

	public void setSubCliente(String subCliente) {
		this.subCliente = subCliente;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public SerPedidoDet[] getDetalles() {
		return detalles;
	}

	public void setDetalles(SerPedidoDet[] detalles) {
		this.detalles = detalles;
	}

	public boolean getResurtido() {
		return resurtido;
	}

	public void setResurtido(boolean resurtido) {
		this.resurtido = resurtido;
	}

}
