package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerTallas;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbTallas extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 10, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo tallas = new ClsCampo("tallas", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbTallas(SerTallas serTallas) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbTallas", serTallas.getEmpresa() + "-" + serTallas.getTalla());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serTallas.getEmpresa());
		setString(talla, serTallas.getTalla());
		setTallas(serTallas.getTallas());
	}

	public DbTallas(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, talla, tallas);
	}

	public SerTallas toSerTallas() throws ExcepcionControlada {
		return new SerTallas(getEmpresa(), getTalla(), getTallas());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getTalla() throws ExcepcionControlada {
		return getString(talla);
	}

	public String getTallas() throws ExcepcionControlada {
		return getString(tallas);
	}

	public void setTallas(String tallas) throws ExcepcionControlada {
		setString(this.tallas, tallas);
	}
}