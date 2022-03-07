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
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaAlmSalida = new ClsCampo("fechaAlmSalida", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioCreo = new ClsCampo("usuarioCreo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioModifico = new ClsCampo("usuarioModifico", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo facturas = new ClsCampo("facturas", Tipo.ArrayString, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioCliente = new ClsCampo("folioCliente", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cliente = new ClsCampo("cliente", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalle = new ClsCampo("detalle", Tipo.Text, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_GRANDE, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

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
		setFechaAlmSalida(serAlmSalida.getFechaAlmSalida());
		setDia(serAlmSalida.getDia());
		setMes(serAlmSalida.getMes());
		setUsuarioCreo(serAlmSalida.getUsuarioCreo());
		setUsuarioModifico(serAlmSalida.getUsuarioModifico());
		setObservaciones(serAlmSalida.getObservaciones());
		setFacturas(serAlmSalida.getFacturas());
		setFolioCliente(serAlmSalida.getFolioCliente());
		setCliente(serAlmSalida.getCliente());
		setDetalle(serAlmSalida.getDetalle());
	}

	public DbAlmSalida(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmSalida, almacen, tipo, fechaAlmSalida, dia, mes, usuarioCreo, usuarioModifico, observaciones, facturas, folioCliente, cliente, detalle);
	}

	public SerAlmSalida toSerAlmSalida() throws ExcepcionControlada {
		return new SerAlmSalida(getEmpresa(), getFolioAlmSalida(), getAlmacen(), getTipo(), getFechaAlmSalida(), getDia(), getMes(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getFacturas(), getFolioCliente(), getCliente(), getDetalle());
	}

	public SerAlmSalida toSerAlmSalidaCompleto(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerAlmSalida serAlmSalida = new SerAlmSalida(getEmpresa(), getFolioAlmSalida(), getAlmacen(), getTipo(), getFechaAlmSalida(), getDia(), getMes(), getUsuarioCreo(), getUsuarioModifico(), getObservaciones(), getFacturas(), getFolioCliente(), getCliente(), getDetalle());

		// Agrego el detalle
		getDbDetalle(datastore, tx);
		List<SerAlmSalidaDet> lstDetalle = new ArrayList<SerAlmSalidaDet>();
		for (DbAlmSalidaDet dbDet : dbDetalle)
			lstDetalle.add(dbDet.toSerAlmSalidaDet());
		serAlmSalida.setDbDetalle(lstDetalle.toArray(new SerAlmSalidaDet[0]));

		return serAlmSalida;
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

	public Date getFechaAlmSalida() throws ExcepcionControlada {
		return getDate(fechaAlmSalida);
	}

	public void setFechaAlmSalida(Date fechaAlmSalida) throws ExcepcionControlada {
		setDate(this.fechaAlmSalida, fechaAlmSalida);
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

	public ArrayList<String> getFacturas() throws ExcepcionControlada {
		return getArrayString(facturas);
	}

	public void setFacturas(ArrayList<String> facturas) throws ExcepcionControlada {
		setArrayString(this.facturas, facturas);
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

	public List<DbAlmSalidaDet> getDbDetalle(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (dbDetalle == null) {
			List<Entity> lstDetalle = ejecutarConsulta(datastore, tx, "DbAlmSalidaDet", getKey());
			dbDetalle = new ArrayList<DbAlmSalidaDet>();
			for (Entity det : lstDetalle)
				dbDetalle.add(new DbAlmSalidaDet(det));
		}
		return dbDetalle;
	}
}