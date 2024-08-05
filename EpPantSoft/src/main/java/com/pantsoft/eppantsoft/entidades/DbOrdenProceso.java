package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerOrdenProceso;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbOrdenProceso extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioOrdenProceso = new ClsCampo("folioOrdenProceso", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo folioOrden = new ClsCampo("folioOrden", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo orden = new ClsCampo("orden", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo estatus = new ClsCampo("estatus", Tipo.Rating, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL, "0-Pendiente, 1-En Proceso, 2-Terminado");
	private final ClsCampo folioPedido = new ClsCampo("folioPedido", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo renglonPedido = new ClsCampo("renglonPedido", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tallas = new ClsCampo("tallas", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo proceso = new ClsCampo("proceso", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo maquilero = new ClsCampo("maquilero", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidadEntrada = new ClsCampo("cantidadEntrada", Tipo.Long, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidadSalida = new ClsCampo("cantidadSalida", Tipo.Long, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalleEntrada = new ClsCampo("detalleEntrada", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalleSalida = new ClsCampo("detalleSalida", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo bitacora = new ClsCampo("bitacora", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo porRevisar = new ClsCampo("porRevisar", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo obsRevision = new ClsCampo("obsRevision", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

	public DbOrdenProceso(SerOrdenProceso serOrdenProceso) throws ExcepcionControlada {
		Key keyp = KeyFactory.createKey("DbOrden", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrden());
		Key key = KeyFactory.createKey(keyp, "DbOrdenProceso", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrdenProceso());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serOrdenProceso.getEmpresa());
		setLong(folioOrdenProceso, serOrdenProceso.getFolioOrdenProceso());
		setFolioOrden(serOrdenProceso.getFolioOrden());
		setOrden(serOrdenProceso.getOrden());
		setTemporada(serOrdenProceso.getTemporada());
		setEstatus(serOrdenProceso.getEstatus());
		setFolioPedido(serOrdenProceso.getFolioPedido());
		setRenglonPedido(serOrdenProceso.getRenglonPedido());
		setModelo(serOrdenProceso.getModelo());
		setReferencia(serOrdenProceso.getReferencia());
		setTallas(serOrdenProceso.getTallas());
		setProceso(serOrdenProceso.getProceso());
		setMaquilero(serOrdenProceso.getMaquilero());
		setCantidadEntrada(serOrdenProceso.getCantidadEntrada());
		setCantidadSalida(serOrdenProceso.getCantidadSalida());
		setObservaciones(serOrdenProceso.getObservaciones());
		setDetalleEntrada(serOrdenProceso.getDetalleEntrada());
		setDetalleSalida(serOrdenProceso.getDetalleSalida());
		setPorRevisar(serOrdenProceso.getPorRevisar());
		setObsRevision(serOrdenProceso.getObsRevision());
	}

	public DbOrdenProceso(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioOrdenProceso, folioOrden, orden, temporada, estatus, folioPedido, renglonPedido, modelo, referencia, tallas, proceso, maquilero, cantidadEntrada, cantidadSalida, observaciones, detalleEntrada, detalleSalida, bitacora, porRevisar, obsRevision);
	}

	public SerOrdenProceso toSerOrdenProceso() throws ExcepcionControlada {
		SerOrdenProceso ser = new SerOrdenProceso(getEmpresa(), getFolioOrden(), getFolioOrdenProceso(), getOrden(), getTemporada(), getEstatus(), getFolioPedido(), getRenglonPedido(), getModelo(), getReferencia(), getTallas(), getProceso(), getMaquilero(), getCantidadEntrada(), getCantidadSalida(), getObservaciones(), getDetalleEntrada(), getDetalleSalida(), getPorRevisar(), getObsRevision());
		ser.setBitacora(getBitacora());
		return ser;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioOrdenProceso() throws ExcepcionControlada {
		return getLong(folioOrdenProceso);
	}

	public Long getFolioOrden() throws ExcepcionControlada {
		return getLong(folioOrden);
	}

	public void setFolioOrden(Long folioOrden) throws ExcepcionControlada {
		setLong(this.folioOrden, folioOrden);
	}

	public Long getOrden() throws ExcepcionControlada {
		return getLong(orden);
	}

	public void setOrden(Long orden) throws ExcepcionControlada {
		setLong(this.orden, orden);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public void setTemporada(Long temporada) throws ExcepcionControlada {
		setLong(this.temporada, temporada);
	}

	public int getEstatus() throws ExcepcionControlada {
		return getRating(estatus);
	}

	public void setEstatus(int estatus) throws ExcepcionControlada {
		setRating(this.estatus, estatus);
	}

	public Long getFolioPedido() throws ExcepcionControlada {
		return getLong(folioPedido);
	}

	public void setFolioPedido(Long folioPedido) throws ExcepcionControlada {
		setLong(this.folioPedido, folioPedido);
	}

	public Long getRenglonPedido() throws ExcepcionControlada {
		return getLong(renglonPedido);
	}

	public void setRenglonPedido(Long renglonPedido) throws ExcepcionControlada {
		setLong(this.renglonPedido, renglonPedido);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public void setModelo(String modelo) throws ExcepcionControlada {
		setString(this.modelo, modelo);
	}

	public String getReferencia() throws ExcepcionControlada {
		return getString(referencia);
	}

	public void setReferencia(String referencia) throws ExcepcionControlada {
		setString(this.referencia, referencia);
	}

	public String getTallas() throws ExcepcionControlada {
		return getString(tallas);
	}

	public void setTallas(String tallas) throws ExcepcionControlada {
		setString(this.tallas, tallas);
	}

	public String getProceso() throws ExcepcionControlada {
		return getString(proceso);
	}

	public void setProceso(String proceso) throws ExcepcionControlada {
		setString(this.proceso, proceso);
	}

	public String getMaquilero() throws ExcepcionControlada {
		return getString(maquilero);
	}

	public void setMaquilero(String maquilero) throws ExcepcionControlada {
		setString(this.maquilero, maquilero);
	}

	public Long getCantidadEntrada() throws ExcepcionControlada {
		return getLong(cantidadEntrada);
	}

	public void setCantidadEntrada(Long cantidadEntrada) throws ExcepcionControlada {
		setLong(this.cantidadEntrada, cantidadEntrada);
	}

	public Long getCantidadSalida() throws ExcepcionControlada {
		return getLong(cantidadSalida);
	}

	public void setCantidadSalida(Long cantidadSalida) throws ExcepcionControlada {
		setLong(this.cantidadSalida, cantidadSalida);
	}

	public String getObservaciones() throws ExcepcionControlada {
		return getString(observaciones);
	}

	public void setObservaciones(String observaciones) throws ExcepcionControlada {
		setString(this.observaciones, observaciones);
	}

	public String getDetalleEntrada() throws ExcepcionControlada {
		return getText(detalleEntrada);
	}

	public void setDetalleEntrada(String detalleEntrada) throws ExcepcionControlada {
		setText(this.detalleEntrada, detalleEntrada);
	}

	public String getDetalleSalida() throws ExcepcionControlada {
		return getText(detalleSalida);
	}

	public void setDetalleSalida(String detalleSalida) throws ExcepcionControlada {
		setText(this.detalleSalida, detalleSalida);
	}

	public String getBitacora() throws ExcepcionControlada {
		return getText(bitacora);
	}

	public void setAgregarBitacora(String usuario, Date fecha, String descripcion) throws ExcepcionControlada {
		String bitacora = ClsUtil.agregarBitacora(getBitacora(), usuario, fecha, descripcion);
		setText(this.bitacora, bitacora);
	}

	public boolean getPorRevisar() throws ExcepcionControlada {
		return getBoolean(porRevisar);
	}

	public void setPorRevisar(boolean porRevisar) throws ExcepcionControlada {
		setBoolean(this.porRevisar, porRevisar);
	}

	public String getObsRevision() throws ExcepcionControlada {
		return getString(obsRevision);
	}

	public void setObsRevision(String obsRevision) throws ExcepcionControlada {
		setString(this.obsRevision, obsRevision);
	}

}