package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.serializable.SerAlmEntrada;
import com.pantsoft.eppantsoft.serializable.SerAlmEntradaDet;
import com.pantsoft.eppantsoft.serializable.SerAlmSalida;
import com.pantsoft.eppantsoft.serializable.SerAlmSalidaDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbAlmSalida extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmSalida = new ClsCampo("folioAlmSalida", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL, "1-Ajuste, 2-Factura. 3-Traspaso");
	private final ClsCampo zonaHoraria = new ClsCampo("zonaHoraria", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaAlmSalida = new ClsCampo("fechaAlmSalida", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anio = new ClsCampo("anio", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioCreo = new ClsCampo("usuarioCreo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioModifico = new ClsCampo("usuarioModifico", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo serieFactura = new ClsCampo("serieFactura", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioFactura = new ClsCampo("folioFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, SUSTITUIR_NULL);
	private final ClsCampo folioCliente = new ClsCampo("folioCliente", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cliente = new ClsCampo("cliente", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalle = new ClsCampo("detalle", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_GRANDE, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelos = new ClsCampo("modelos", Tipo.ArrayString, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmEntradaTraspaso = new ClsCampo("folioAlmEntradaTraspaso", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, SUSTITUIR_NULL);
	private final ClsCampo almacenTraspaso = new ClsCampo("almacenTraspaso", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tieneError = new ClsCampo("tieneError", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);

	// Dependencias
	private List<DbAlmSalidaDet> dbDetalle = null;

	public DbAlmSalida(SerAlmSalida serAlmSalida) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbAlmSalida", serAlmSalida.getEmpresa() + "-" + serAlmSalida.getFolioAlmSalida());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmSalida.getEmpresa());
		setLong(folioAlmSalida, serAlmSalida.getFolioAlmSalida());
		setAlmacen(serAlmSalida.getAlmacen());
		setTipo(serAlmSalida.getTipo());
		setFechaAlmSalida(serAlmSalida.getFechaAlmSalida(), serAlmSalida.getZonaHoraria());
		setUsuarioCreo(serAlmSalida.getUsuarioCreo());
		setUsuarioModifico(serAlmSalida.getUsuarioModifico());
		setObservaciones(serAlmSalida.getObservaciones());
		setSerieFactura(serAlmSalida.getSerieFactura());
		setFolioFactura(serAlmSalida.getFolioFactura());
		setFolioCliente(serAlmSalida.getFolioCliente());
		setCliente(serAlmSalida.getCliente());
		setDetalle(serAlmSalida.getDetalle());
		setFolioAlmEntradaTraspaso(serAlmSalida.getFolioAlmEntradaTraspaso());
		setAlmacenTraspaso(serAlmSalida.getAlmacenTraspaso());
		setTieneError(serAlmSalida.getTieneError());
	}

	public DbAlmSalida(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmSalida, almacen, tipo, zonaHoraria, fechaAlmSalida, dia, mes, anio, usuarioCreo, usuarioModifico, observaciones, serieFactura, folioFactura, folioCliente, cliente, detalle, modelos, folioAlmEntradaTraspaso, almacenTraspaso, tieneError);
	}

	public SerAlmSalida toSerAlmSalida() throws ExcepcionControlada {
		return new SerAlmSalida(getEmpresa(), getFolioAlmSalida(), getAlmacen(), getTipo(), getZonaHoraria(), getFechaAlmSalida(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getSerieFactura(), getFolioFactura(), getFolioCliente(), getCliente(), getDetalle(), getFolioAlmEntradaTraspaso(), getAlmacenTraspaso(), getTieneError());
	}

	public SerAlmSalida toSerAlmSalidaCompleto(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerAlmSalida serAlmSalida = new SerAlmSalida(getEmpresa(), getFolioAlmSalida(), getAlmacen(), getTipo(), getZonaHoraria(), getFechaAlmSalida(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getSerieFactura(), getFolioFactura(), getFolioCliente(), getCliente(), getDetalle(), getFolioAlmEntradaTraspaso(), getAlmacenTraspaso(), getTieneError());

		// Agrego el detalle
		getDbDetalle(datastore, tx);
		List<SerAlmSalidaDet> lstDetalle = new ArrayList<SerAlmSalidaDet>();
		for (DbAlmSalidaDet dbDet : dbDetalle)
			lstDetalle.add(dbDet.toSerAlmSalidaDet());
		serAlmSalida.setDbDetalle(lstDetalle.toArray(new SerAlmSalidaDet[0]));

		return serAlmSalida;
	}

	public SerAlmEntrada toSerAlmEntradaTraspaso(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerAlmEntrada serAlmEntrada = new SerAlmEntrada(getEmpresa(), 0L, getAlmacenTraspaso(), 3L, getZonaHoraria(), getFechaAlmSalida(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), 0L, 0L, null, getDetalle(), getFolioAlmSalida(), getAlmacen(), null, 0L, 0L, null);

		// Agrego el detalle
		getDbDetalle(datastore, tx);
		List<SerAlmEntradaDet> lstDetalle = new ArrayList<SerAlmEntradaDet>();
		for (DbAlmSalidaDet dbDet : dbDetalle)
			lstDetalle.add(dbDet.toSerAlmEntradaDetTraspaso(getAlmacenTraspaso()));
		serAlmEntrada.setDbDetalle(lstDetalle.toArray(new SerAlmEntradaDet[0]));

		return serAlmEntrada;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioAlmSalida() throws ExcepcionControlada {
		return getLong(folioAlmSalida);
	}

	public String getAlmacen() throws ExcepcionControlada {
		return getString(almacen);
	}

	public void setAlmacen(String almacen) throws ExcepcionControlada {
		setString(this.almacen, almacen);
	}

	public Long getTipo() throws ExcepcionControlada {
		return getLong(tipo);
	}

	public void setTipo(Long tipo) throws ExcepcionControlada {
		setLong(this.tipo, tipo);
	}

	public String getZonaHoraria() throws ExcepcionControlada {
		return getString(zonaHoraria);
	}

	public Date getFechaAlmSalida() throws ExcepcionControlada {
		return getDate(fechaAlmSalida);
	}

	public void setFechaAlmSalida(Date fechaAlmSalida, String zonaHoraria) throws ExcepcionControlada {
		setDate(this.fechaAlmSalida, fechaAlmSalida);

		if (fechaAlmSalida == null) {
			throw new ExcepcionControlada("El campo Fecha no puede quedar vacío.");
		}
		setDate(this.fechaAlmSalida, fechaAlmSalida);
		setString(this.zonaHoraria, zonaHoraria);
		TimeZone tzGMT = TimeZone.getTimeZone(zonaHoraria);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaAlmSalida);
		cal.setTimeZone(tzGMT);

		setLong(this.anio, (long) cal.get(Calendar.YEAR));
		setLong(this.mes, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.MONTH) + 1));
		setLong(this.dia, (long) ((cal.get(Calendar.YEAR) * 10000) + ((cal.get(Calendar.MONTH) + 1) * 100) + cal.get(Calendar.DAY_OF_MONTH)));

	}

	public Long getDia() throws ExcepcionControlada {
		return getLong(dia);
	}

	public void setDia(Long dia) throws ExcepcionControlada {
		setLong(this.dia, dia);
	}

	public Long getMes() throws ExcepcionControlada {
		return getLong(mes);
	}

	public void setMes(Long mes) throws ExcepcionControlada {
		setLong(this.mes, mes);
	}

	public Long getAnio() throws ExcepcionControlada {
		return getLong(anio);
	}

	public String getUsuarioCreo() throws ExcepcionControlada {
		return getString(usuarioCreo);
	}

	public void setUsuarioCreo(String usuarioCreo) throws ExcepcionControlada {
		setString(this.usuarioCreo, usuarioCreo);
	}

	public String getUsuarioModifico() throws ExcepcionControlada {
		return getString(usuarioModifico);
	}

	public void setUsuarioModifico(String usuarioModifico) throws ExcepcionControlada {
		setString(this.usuarioModifico, usuarioModifico);
	}

	public String getObservaciones() throws ExcepcionControlada {
		return getString(observaciones);
	}

	public void setObservaciones(String observaciones) throws ExcepcionControlada {
		setString(this.observaciones, observaciones);
	}

	public String getSerieFactura() throws ExcepcionControlada {
		return getString(serieFactura);
	}

	public void setSerieFactura(String serieFactura) throws ExcepcionControlada {
		setString(this.serieFactura, serieFactura);
	}

	public Long getFolioFactura() throws ExcepcionControlada {
		return getLong(folioFactura);
	}

	public void setFolioFactura(Long folioFactura) throws ExcepcionControlada {
		setLong(this.folioFactura, folioFactura);
	}

	public Long getFolioCliente() throws ExcepcionControlada {
		return getLong(folioCliente);
	}

	public void setFolioCliente(Long folioCliente) throws ExcepcionControlada {
		setLong(this.folioCliente, folioCliente);
	}

	public String getCliente() throws ExcepcionControlada {
		return getString(cliente);
	}

	public void setCliente(String cliente) throws ExcepcionControlada {
		setString(this.cliente, cliente);
	}

	public String getDetalle() throws ExcepcionControlada {
		return getText(detalle);
	}

	public void setDetalle(String detalle) throws ExcepcionControlada {
		setText(this.detalle, detalle);
	}

	public ArrayList<String> getModelos() throws ExcepcionControlada {
		return getArrayString(modelos);
	}

	public void setModelos(ArrayList<String> modelos) throws ExcepcionControlada {
		setArrayString(this.modelos, modelos);
	}

	public List<DbAlmSalidaDet> getDbDetalle(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (dbDetalle == null) {
			List<Entity> lstDetalle = ejecutarConsulta(datastore, tx, "DbAlmSalidaDet", getKey());
			dbDetalle = new ArrayList<DbAlmSalidaDet>();
			for (Entity det : lstDetalle)
				dbDetalle.add(new DbAlmSalidaDet(det));
		}
		return dbDetalle;
	}

	public void setDbDetalle(List<DbAlmSalidaDet> dbDetalle) throws ExcepcionControlada {
		this.dbDetalle = dbDetalle;
	}

	public Long getFolioAlmEntradaTraspaso() throws ExcepcionControlada {
		return getLong(folioAlmEntradaTraspaso);
	}

	public void setFolioAlmEntradaTraspaso(Long folioAlmEntradaTraspaso) throws ExcepcionControlada {
		setLong(this.folioAlmEntradaTraspaso, folioAlmEntradaTraspaso);
	}

	public String getAlmacenTraspaso() throws ExcepcionControlada {
		return getString(almacenTraspaso);
	}

	public void setAlmacenTraspaso(String almacenTraspaso) throws ExcepcionControlada {
		setString(this.almacenTraspaso, almacenTraspaso);
	}

	public boolean getTieneError() throws ExcepcionControlada {
		return getBoolean(tieneError);
	}

	public void setTieneError(boolean tieneError) throws ExcepcionControlada {
		setBoolean(this.tieneError, tieneError);
	}

}