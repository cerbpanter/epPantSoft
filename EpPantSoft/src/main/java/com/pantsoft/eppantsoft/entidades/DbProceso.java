package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerProceso;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbProceso extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo proceso = new ClsCampo("proceso", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 50, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);

	public DbProceso(SerProceso serProceso) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbProceso", serProceso.getEmpresa() + "-" + serProceso.getTemporada() + "-" + serProceso.getProceso());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serProceso.getEmpresa());
		setLong(temporada, serProceso.getTemporada());
		setString(proceso, serProceso.getProceso());
	}

	public DbProceso(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, proceso);
	}

	public SerProceso toSerProceso() throws ExcepcionControlada {
		return new SerProceso(getEmpresa(), getTemporada(), getProceso());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getProceso() throws ExcepcionControlada {
		return getString(proceso);
	}
}