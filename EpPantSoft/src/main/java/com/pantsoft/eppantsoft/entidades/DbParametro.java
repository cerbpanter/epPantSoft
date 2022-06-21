package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerParametro;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbParametro extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo parametro = new ClsCampo("parametro", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo valor = new ClsCampo("valor", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbParametro(SerParametro serParametro) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbParametro", serParametro.getEmpresa() + "-" + serParametro.getParametro());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serParametro.getEmpresa());
		setString(parametro, serParametro.getParametro());
		setValor(serParametro.getValor());
	}

	public DbParametro(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, parametro, valor);
	}

	public SerParametro toSerParametro() throws ExcepcionControlada {
		return new SerParametro(getEmpresa(), getParametro(), getValor());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getParametro() throws ExcepcionControlada {
		return getString(parametro);
	}

	public String getValor() throws ExcepcionControlada {
		return getString(valor);
	}

	public void setValor(String valor) throws ExcepcionControlada {
		setString(this.valor, valor);
	}
}