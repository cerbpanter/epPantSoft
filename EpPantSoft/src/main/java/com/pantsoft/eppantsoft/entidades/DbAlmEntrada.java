package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaAlmEntrada = new ClsCampo("fechaAlmEntrada", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioCreo = new ClsCampo("usuarioCreo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioModifico = new ClsCampo("usuarioModifico", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioOrdenProduccion = new ClsCampo("folioOrdenProduccion", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioMaquilero = new ClsCampo("folioMaquilero", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo maquilero = new ClsCampo("maquilero", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

	// Dependencias
	private List<DbAlmEntradaDet> detalle = null;

	public DbAlmEntrada(SerAlmEntrada serAlmEntrada) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbAlmEntrada", serAlmEntrada.getEmpresa() + "-" + serAlmEntrada.getFolioAlmEntrada());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmEntrada.getEmpresa());
		setLong(folioAlmEntrada, serAlmEntrada.getFolioAlmEntrada());
		setAlmacen(serAlmEntrada.getAlmacen());
		setTipo(serAlmEntrada.getTipo());
		setFechaAlmEntrada(serAlmEntrada.getFechaAlmEntrada());
		setDia(serAlmEntrada.getDia());
		setMes(serAlmEntrada.getMes());
		setUsuarioCreo(serAlmEntrada.getUsuarioCreo());
		setUsuarioModifico(serAlmEntrada.getUsuarioModifico());
		setObservaciones(serAlmEntrada.getObservaciones());
		setFolioOrdenProduccion(serAlmEntrada.getFolioOrdenProduccion());
		setFolioMaquilero(serAlmEntrada.getFolioMaquilero());
		setMaquilero(serAlmEntrada.getMaquilero());
	}

	public DbAlmEntrada(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return true;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmEntrada, almacen, tipo, fechaAlmEntrada, dia, mes, usuarioCreo, usuarioModifico, observaciones, folioOrdenProduccion, folioMaquilero, maquilero);
	}

	public SerAlmEntrada toSerAlmEntrada(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerAlmEntrada serAlmEntrada = new SerAlmEntrada(getEmpresa(), getFolioAlmEntrada(), getAlmacen(), getTipo(), getFechaAlmEntrada(), getDia(), getMes(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getFolioOrdenProduccion(), getFolioMaquilero(), getMaquilero());

		// Agrego el detalle
		getDetalle(datastore, tx);
		List<SerAlmEntradaDet> lstDetalle = new ArrayList<SerAlmEntradaDet>();
		for (DbAlmEntradaDet dbDet : detalle)
			lstDetalle.add(dbDet.toSerAlmEntradaDet());
		serAlmEntrada.setDetalle(lstDetalle.toArray(new SerAlmEntradaDet[0]));

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

	public Date getFechaAlmEntrada() throws ExcepcionControlada {
		return getDate(fechaAlmEntrada);
	}

	public void setFechaAlmEntrada(Date fechaAlmEntrada) throws ExcepcionControlada {
		setDate(this.fechaAlmEntrada, fechaAlmEntrada);
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

	public List<DbAlmEntradaDet> getDetalle(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (detalle == null) {
			List<Entity> lstDetalle = ejecutarConsulta(datastore, tx, "DbAlmEntradaDet", getKey());
			detalle = new ArrayList<DbAlmEntradaDet>();
			for (Entity det : lstDetalle)
				detalle.add(new DbAlmEntradaDet(det));
		}
		return detalle;
	}
}