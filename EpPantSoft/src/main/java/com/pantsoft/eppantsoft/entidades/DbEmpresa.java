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

public class DbEmpresa extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);

	public DbEmpresa(String empresa) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbEmpresa", empresa);
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(this.empresa, empresa);
	}

	public DbEmpresa(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return true;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa);
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

}