package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerModeloProceso;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbModeloProceso extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo proceso = new ClsCampo("proceso", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo precosto = new ClsCampo("precosto", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo costo = new ClsCampo("costo", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);

	public DbModeloProceso(SerModeloProceso serModeloProceso) throws ExcepcionControlada {
		Key keyP = KeyFactory.createKey("DbModelo", serModeloProceso.getEmpresa() + "-" + serModeloProceso.getTemporada() + "-" + serModeloProceso.getModelo() + "-" + serModeloProceso.getReferencia());
		Key key = KeyFactory.createKey(keyP, "DbModeloProceso", serModeloProceso.getEmpresa() + "-" + serModeloProceso.getTemporada() + "-" + serModeloProceso.getModelo() + "-" + serModeloProceso.getReferencia() + "-" + serModeloProceso.getProceso());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serModeloProceso.getEmpresa());
		setLong(temporada, serModeloProceso.getTemporada());
		setString(modelo, serModeloProceso.getModelo());
		setString(referencia, serModeloProceso.getReferencia());
		setString(proceso, serModeloProceso.getProceso());
		setPrecosto(serModeloProceso.getPrecosto());
		setCosto(serModeloProceso.getCosto());
	}

	public DbModeloProceso(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, modelo, referencia, proceso, precosto, costo);
	}

	public SerModeloProceso toSerModeloProceso() throws ExcepcionControlada {
		return new SerModeloProceso(getEmpresa(), getTemporada(), getModelo(), getReferencia(), getProceso(), getPrecosto(), getCosto());
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

	public String getProceso() throws ExcepcionControlada {
		return getString(proceso);
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
}