package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbConsecutivo extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 30, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo id = new ClsCampo("id", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);

	public DbConsecutivo(String empresa, long temporada, String tipo) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbConsecutivo", empresa + "-" + temporada + "-" + tipo);
		this.entidad = new Entity(key);
		asignarValoresDefault();
		setString(this.empresa, empresa);
		setLong(this.temporada, temporada);
		setString(this.tipo, tipo);
		setId(0);
	}

	public DbConsecutivo(Entity entidad) {
		this.entidad = entidad;
	}

	public java.util.List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, tipo, id);
	};

	public boolean getLiberado() {
		return true;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getTipo() throws ExcepcionControlada {
		return getString(tipo);
	}

	public long getId() throws ExcepcionControlada {
		return getLong(id);
	}

	public void setId(long id) throws ExcepcionControlada {
		setLong(this.id, id);
	}
}
