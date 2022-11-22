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
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbAlmEntrada extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmEntrada = new ClsCampo("folioAlmEntrada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL, "1:Ajuste, 2:Orden de producción, 3:Traspaso, 4:Factura");
	private final ClsCampo zonaHoraria = new ClsCampo("zonaHoraria", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaAlmEntrada = new ClsCampo("fechaAlmEntrada", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anio = new ClsCampo("anio", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioCreo = new ClsCampo("usuarioCreo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioModifico = new ClsCampo("usuarioModifico", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioOrdenProduccion = new ClsCampo("folioOrdenProduccion", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioMaquilero = new ClsCampo("folioMaquilero", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo maquilero = new ClsCampo("maquilero", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalle = new ClsCampo("detalle", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_GRANDE, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelos = new ClsCampo("modelos", Tipo.ArrayString, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmSalidaTraspaso = new ClsCampo("folioAlmSalidaTraspaso", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, SUSTITUIR_NULL);
	private final ClsCampo almacenTraspaso = new ClsCampo("almacenTraspaso", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo serieFactura = new ClsCampo("serieFactura", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioFactura = new ClsCampo("folioFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, SUSTITUIR_NULL);
	private final ClsCampo folioCliente = new ClsCampo("folioCliente", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cliente = new ClsCampo("cliente", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tieneError = new ClsCampo("tieneError", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);

	// Dependencias
	private List<DbAlmEntradaDet> dbDetalle = null;

	public DbAlmEntrada(SerAlmEntrada serAlmEntrada) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbAlmEntrada", serAlmEntrada.getEmpresa() + "-" + serAlmEntrada.getFolioAlmEntrada());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmEntrada.getEmpresa());
		setLong(folioAlmEntrada, serAlmEntrada.getFolioAlmEntrada());
		setAlmacen(serAlmEntrada.getAlmacen());
		setTipo(serAlmEntrada.getTipo());
		setFechaAlmEntrada(serAlmEntrada.getFechaAlmEntrada(), serAlmEntrada.getZonaHoraria());
		setUsuarioCreo(serAlmEntrada.getUsuarioCreo());
		setUsuarioModifico(serAlmEntrada.getUsuarioModifico());
		setObservaciones(serAlmEntrada.getObservaciones());
		setFolioOrdenProduccion(serAlmEntrada.getFolioOrdenProduccion());
		setFolioMaquilero(serAlmEntrada.getFolioMaquilero());
		setMaquilero(serAlmEntrada.getMaquilero());
		setDetalle(serAlmEntrada.getDetalle());
		setFolioAlmSalidaTraspaso(serAlmEntrada.getFolioAlmSalidaTraspaso());
		setAlmacenTraspaso(serAlmEntrada.getAlmacenTraspaso());
		setSerieFactura(serAlmEntrada.getSerieFactura());
		setFolioFactura(serAlmEntrada.getFolioFactura());
		setFolioCliente(serAlmEntrada.getFolioCliente());
		setCliente(serAlmEntrada.getCliente());
		setTieneError(serAlmEntrada.getTieneError());
	}

	public DbAlmEntrada(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return true;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmEntrada, almacen, tipo, zonaHoraria, fechaAlmEntrada, dia, mes, anio, usuarioCreo, usuarioModifico, observaciones, folioOrdenProduccion, folioMaquilero, maquilero, detalle, modelos, folioAlmSalidaTraspaso, almacenTraspaso, serieFactura, folioFactura, folioCliente, cliente, tieneError);
	}

	public SerAlmEntrada toSerAlmEntrada() throws ExcepcionControlada {
		return new SerAlmEntrada(getEmpresa(), getFolioAlmEntrada(), getAlmacen(), getTipo(), getZonaHoraria(), getFechaAlmEntrada(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getFolioOrdenProduccion(), getFolioMaquilero(), getMaquilero(), getDetalle(), getFolioAlmSalidaTraspaso(), getAlmacenTraspaso(), getSerieFactura(), getFolioFactura(), getFolioCliente(), getCliente(), getTieneError());
	}

	public SerAlmEntrada toSerAlmEntradaCompleto(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerAlmEntrada serAlmEntrada = new SerAlmEntrada(getEmpresa(), getFolioAlmEntrada(), getAlmacen(), getTipo(), getZonaHoraria(), getFechaAlmEntrada(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getFolioOrdenProduccion(), getFolioMaquilero(), getMaquilero(), getDetalle(), getFolioAlmSalidaTraspaso(), getAlmacenTraspaso(), getSerieFactura(), getFolioFactura(), getFolioCliente(), getCliente(), getTieneError());

		// Agrego el detalle
		getDbDetalle(datastore, tx);
		List<SerAlmEntradaDet> lstDetalle = new ArrayList<SerAlmEntradaDet>();
		for (DbAlmEntradaDet dbDet : dbDetalle)
			lstDetalle.add(dbDet.toSerAlmEntradaDet());
		serAlmEntrada.setDbDetalle(lstDetalle.toArray(new SerAlmEntradaDet[0]));

		return serAlmEntrada;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioAlmEntrada() throws ExcepcionControlada {
		return getLong(folioAlmEntrada);
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

	public Date getFechaAlmEntrada() throws ExcepcionControlada {
		return getDate(fechaAlmEntrada);
	}

	public void setFechaAlmEntrada(Date fechaAlmEntrada, String zonaHoraria) throws ExcepcionControlada {
		if (fechaAlmEntrada == null) {
			throw new ExcepcionControlada("El campo Fecha no puede quedar vacío.");
		}
		setDate(this.fechaAlmEntrada, fechaAlmEntrada);
		setString(this.zonaHoraria, zonaHoraria);
		TimeZone tzGMT = TimeZone.getTimeZone(zonaHoraria);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaAlmEntrada);
		cal.setTimeZone(tzGMT);

		setLong(this.anio, (long) cal.get(Calendar.YEAR));
		setLong(this.mes, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.MONTH) + 1));
		setLong(this.dia, (long) ((cal.get(Calendar.YEAR) * 10000) + ((cal.get(Calendar.MONTH) + 1) * 100) + cal.get(Calendar.DAY_OF_MONTH)));
	}

	public Long getDia() throws ExcepcionControlada {
		return getLong(dia);
	}

	public Long getMes() throws ExcepcionControlada {
		return getLong(mes);
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

	public Long getFolioOrdenProduccion() throws ExcepcionControlada {
		return getLong(folioOrdenProduccion);
	}

	public void setFolioOrdenProduccion(Long folioOrdenProduccion) throws ExcepcionControlada {
		setLong(this.folioOrdenProduccion, folioOrdenProduccion);
	}

	public Long getFolioMaquilero() throws ExcepcionControlada {
		return getLong(folioMaquilero);
	}

	public void setFolioMaquilero(Long folioMaquilero) throws ExcepcionControlada {
		setLong(this.folioMaquilero, folioMaquilero);
	}

	public String getMaquilero() throws ExcepcionControlada {
		return getString(maquilero);
	}

	public void setMaquilero(String maquilero) throws ExcepcionControlada {
		setString(this.maquilero, maquilero);
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

	public List<DbAlmEntradaDet> getDbDetalle(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (dbDetalle == null) {
			List<Entity> lstDetalle = ejecutarConsulta(datastore, tx, "DbAlmEntradaDet", getKey());
			dbDetalle = new ArrayList<DbAlmEntradaDet>();
			for (Entity det : lstDetalle)
				dbDetalle.add(new DbAlmEntradaDet(det));
		}
		return dbDetalle;
	}

	public void setDbDetalle(List<DbAlmEntradaDet> dbDetalle) throws ExcepcionControlada {
		this.dbDetalle = dbDetalle;
	}

	public Long getFolioAlmSalidaTraspaso() throws ExcepcionControlada {
		return getLong(folioAlmSalidaTraspaso);
	}

	public void setFolioAlmSalidaTraspaso(Long folioAlmSalidaTraspaso) throws ExcepcionControlada {
		setLong(this.folioAlmSalidaTraspaso, folioAlmSalidaTraspaso);
	}

	public String getAlmacenTraspaso() throws ExcepcionControlada {
		return getString(almacenTraspaso);
	}

	public void setAlmacenTraspaso(String almacenTraspaso) throws ExcepcionControlada {
		setString(this.almacenTraspaso, almacenTraspaso);
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

	public boolean getTieneError() throws ExcepcionControlada {
		return getBoolean(tieneError);
	}

	public void setTieneError(boolean tieneError) throws ExcepcionControlada {
		setBoolean(this.tieneError, tieneError);
	}

}