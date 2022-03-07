package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerTalla;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbTalla extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 10, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo orden = new ClsCampo("orden", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);

	public DbTalla(SerTalla serTalla) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbTalla", serTalla.getEmpresa() + "-" + serTalla.getTemporada() + "-" + serTalla.getTalla());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serTalla.getEmpresa());
		setLong(temporada, serTalla.getTemporada());
		setString(talla, serTalla.getTalla());
		setOrden(serTalla.getOrden());
	}

	public DbTalla(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return true;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, talla);
	}

	public SerTalla toSerTalla() throws ExcepcionControlada {
		return new SerTalla(getEmpresa(), getTemporada(), getTalla(), getOrden());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getTalla() throws ExcepcionControlada {
		return getString(talla);
	}

	public void setOrden(long orden) throws ExcepcionControlada {
		setLong(this.orden, orden);
	}

	public long getOrden() throws ExcepcionControlada {
		return getLong(orden);
	}

}