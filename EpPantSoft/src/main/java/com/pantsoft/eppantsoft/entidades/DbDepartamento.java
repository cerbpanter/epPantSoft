package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerDepartamento;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbDepartamento extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo departamento = new ClsCampo("departamento", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);

	public DbDepartamento(SerDepartamento serDepartamento) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbDepartamento", serDepartamento.getEmpresa() + "-" + serDepartamento.getDepartamento());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serDepartamento.getEmpresa());
		setString(departamento, serDepartamento.getDepartamento());
	}

	public DbDepartamento(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, departamento);
	}

	public SerDepartamento toSerDepartamento() throws ExcepcionControlada {
		return new SerDepartamento(getEmpresa(), getDepartamento());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getDepartamento() throws ExcepcionControlada {
		return getString(departamento);
	}
}