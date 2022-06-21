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
import com.pantsoft.eppantsoft.serializable.SerModelo;
import com.pantsoft.eppantsoft.serializable.SerModeloHabilitacion;
import com.pantsoft.eppantsoft.serializable.SerModeloProceso;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbModelo extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 10, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 4, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo copiaDe = new ClsCampo("copiaDe", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 10, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo departamento = new ClsCampo("departamento", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 25, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo descripcionSeccion = new ClsCampo("descripcionSeccion", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 25, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo descripcion2 = new ClsCampo("descripcion2", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 50, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tela = new ClsCampo("tela", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 25, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 10, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo costura = new ClsCampo("costura", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo otros = new ClsCampo("otros", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precosto = new ClsCampo("precosto", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo costo = new ClsCampo("costo", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fecha = new ClsCampo("fecha", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_HOY, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo ok = new ClsCampo("ok", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cortado = new ClsCampo("cortado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo esPantSoft = new ClsCampo("esPantSoft", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);

	// Dependencias
	private List<DbModeloHabilitacion> habilitaciones = null;
	private List<DbModeloProceso> procesos = null;

	public DbModelo(SerModelo serModelo) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbModelo", serModelo.getEmpresa() + "-" + serModelo.getTemporada() + "-" + serModelo.getModelo() + "-" + serModelo.getReferencia());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serModelo.getEmpresa());
		setLong(temporada, serModelo.getTemporada());
		setString(modelo, serModelo.getModelo());
		setString(referencia, serModelo.getReferencia());
		setCopiaDe(serModelo.getCopiaDe());
		setDepartamento(serModelo.getDepartamento());
		setDescripcionSeccion(serModelo.getDescripcionSeccion());
		setDescripcion2(serModelo.getDescripcion2());
		setTela(serModelo.getTela());
		setTalla(serModelo.getTalla());
		setObservaciones(serModelo.getObservaciones());
		setCostura(serModelo.getCostura());
		setOtros(serModelo.getOtros());
		setPrecosto(serModelo.getPrecosto());
		setCosto(serModelo.getCosto());
		setFecha(serModelo.getFecha());
		setOk(serModelo.getOk());
		setCortado(serModelo.getCortado());
		setEsPantSoft(true);
	}

	public DbModelo(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, modelo, referencia, copiaDe, departamento, descripcionSeccion, descripcion2, tela, talla, observaciones, costura, otros, precosto, costo, fecha, ok, cortado, esPantSoft);
	}

	public SerModelo toSerModelo() throws ExcepcionControlada {
		return new SerModelo(getEmpresa(), getTemporada(), getModelo(), getReferencia(), getCopiaDe(), getDepartamento(), getDescripcionSeccion(), getDescripcion2(), getTela(), getTalla(), getObservaciones(), getCostura(), getOtros(), getPrecosto(), getCosto(), getFecha(), getOk(), getCortado(), getEsPantSoft());
	}

	public SerModelo toSerModeloCompleto(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerModelo serModelo = new SerModelo(getEmpresa(), getTemporada(), getModelo(), getReferencia(), getCopiaDe(), getDepartamento(), getDescripcionSeccion(), getDescripcion2(), getTela(), getTalla(), getObservaciones(), getCostura(), getOtros(), getPrecosto(), getCosto(), getFecha(), getOk(), getCortado(), getEsPantSoft());

		// Agrego las habilitaciones
		getHabilitaciones(datastore, tx);
		List<SerModeloHabilitacion> lstHabilitaciones = new ArrayList<SerModeloHabilitacion>();
		for (DbModeloHabilitacion dbD : habilitaciones)
			lstHabilitaciones.add(dbD.toSerModeloHabilitacion());
		serModelo.setHabilitaciones(lstHabilitaciones.toArray(new SerModeloHabilitacion[0]));

		// Agrego las procesos
		getProcesos(datastore, tx);
		List<SerModeloProceso> lstProcesos = new ArrayList<SerModeloProceso>();
		for (DbModeloProceso dbD : procesos)
			lstProcesos.add(dbD.toSerModeloProceso());
		serModelo.setProcesos(lstProcesos.toArray(new SerModeloProceso[0]));

		return serModelo;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public String getReferencia() throws ExcepcionControlada {
		return getString(referencia);
	}

	public String getCopiaDe() throws ExcepcionControlada {
		return getString(copiaDe);
	}

	public void setCopiaDe(String copiaDe) throws ExcepcionControlada {
		setString(this.copiaDe, copiaDe);
	}

	public String getDepartamento() throws ExcepcionControlada {
		return getString(departamento);
	}

	public void setDepartamento(String departamento) throws ExcepcionControlada {
		setString(this.departamento, departamento);
	}

	public String getDescripcionSeccion() throws ExcepcionControlada {
		return getString(descripcionSeccion);
	}

	public void setDescripcionSeccion(String descripcionSeccion) throws ExcepcionControlada {
		setString(this.descripcionSeccion, descripcionSeccion);
	}

	public String getDescripcion2() throws ExcepcionControlada {
		return getString(descripcion2);
	}

	public void setDescripcion2(String descripcion2) throws ExcepcionControlada {
		setString(this.descripcion2, descripcion2);
	}

	public String getTela() throws ExcepcionControlada {
		return getString(tela);
	}

	public void setTela(String tela) throws ExcepcionControlada {
		setString(this.tela, tela);
	}

	public String getTalla() throws ExcepcionControlada {
		return getString(talla);
	}

	public void setTalla(String talla) throws ExcepcionControlada {
		setString(this.talla, talla);
	}

	public String getObservaciones() throws ExcepcionControlada {
		return getString(observaciones);
	}

	public void setObservaciones(String observaciones) throws ExcepcionControlada {
		setString(this.observaciones, observaciones);
	}

	public Double getCostura() throws ExcepcionControlada {
		return getDouble(costura);
	}

	public void setCostura(Double costura) throws ExcepcionControlada {
		setDouble(this.costura, costura);
	}

	public Double getOtros() throws ExcepcionControlada {
		return getDouble(otros);
	}

	public void setOtros(Double otros) throws ExcepcionControlada {
		setDouble(this.otros, otros);
	}

	public Double getPrecosto() throws ExcepcionControlada {
		return getDouble(precosto);
	}

	public void setPrecosto(Double precosto) throws ExcepcionControlada {
		setDouble(this.precosto, precosto);
	}

	public Double getCosto() throws ExcepcionControlada {
		return getDouble(costo);
	}

	public void setCosto(Double costo) throws ExcepcionControlada {
		setDouble(this.costo, costo);
	}

	public Date getFecha() throws ExcepcionControlada {
		return getDate(fecha);
	}

	public void setFecha(Date fecha) throws ExcepcionControlada {
		setDate(this.fecha, fecha);
	}

	public Boolean getOk() throws ExcepcionControlada {
		return getBoolean(ok);
	}

	public void setOk(Boolean ok) throws ExcepcionControlada {
		setBoolean(this.ok, ok);
	}

	public Boolean getCortado() throws ExcepcionControlada {
		return getBoolean(cortado);
	}

	public void setCortado(Boolean cortado) throws ExcepcionControlada {
		setBoolean(this.cortado, cortado);
	}

	public Boolean getEsPantSoft() throws ExcepcionControlada {
		return getBoolean(esPantSoft);
	}

	public void setEsPantSoft(Boolean esPantSoft) throws ExcepcionControlada {
		setBoolean(this.esPantSoft, esPantSoft);
	}

	public List<DbModeloHabilitacion> getHabilitaciones(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (habilitaciones == null) {
			List<Entity> lstHabilitaciones = ejecutarConsulta(datastore, tx, "DbModeloHabilitacion", getKey());
			habilitaciones = new ArrayList<DbModeloHabilitacion>();
			for (Entity habilitacion : lstHabilitaciones)
				habilitaciones.add(new DbModeloHabilitacion(habilitacion));
		}
		return habilitaciones;
	}

	public List<DbModeloProceso> getProcesos(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (procesos == null) {
			List<Entity> lstProcesos = ejecutarConsulta(datastore, tx, "DbModeloProceso", getKey());
			procesos = new ArrayList<DbModeloProceso>();
			for (Entity proceso : lstProcesos)
				procesos.add(new DbModeloProceso(proceso));
		}
		return procesos;
	}

}