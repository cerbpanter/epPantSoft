package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbModeloProducido extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);

	public DbModeloProducido(String empresa, long temporada, String modelo, String referencia) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbModeloProducido", empresa + "-" + temporada + "-" + modelo);
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(this.empresa, empresa);
		setLong(this.temporada, temporada);
		setString(this.modelo, modelo);
		setString(this.referencia, referencia);
	}

	public DbModeloProducido(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, modelo, referencia);
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

}