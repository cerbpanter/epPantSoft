package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerTemporada;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbTemporada extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo descripcion = new ClsCampo("descripcion", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 1, 100, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo temporadaSql = new ClsCampo("temporadaSql", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 2, SUSTITUIR_NULL);

	public DbTemporada(SerTemporada serTemporada) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbTemporada", serTemporada.getEmpresa() + "-" + serTemporada.getTemporada());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serTemporada.getEmpresa());
		setLong(temporada, serTemporada.getTemporada());
		setDescripcion(serTemporada.getDescripcion());
		setTemporadaSql(serTemporada.getTemporadaSql());
	}

	public DbTemporada(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, descripcion, temporadaSql);
	}

	public SerTemporada toSerTemporada() throws ExcepcionControlada {
		return new SerTemporada(getEmpresa(), getTemporada(), getDescripcion(), getTemporadaSql());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getDescripcion() throws ExcepcionControlada {
		return getString(descripcion);
	}

	public void setDescripcion(String descripcion) throws ExcepcionControlada {
		setString(this.descripcion, descripcion);
	}

	public long getTemporadaSql() throws ExcepcionControlada {
		return getLong(temporadaSql);
	}

	public void setTemporadaSql(long temporadaSql) throws ExcepcionControlada {
		setLong(this.temporadaSql, temporadaSql);
	}
}