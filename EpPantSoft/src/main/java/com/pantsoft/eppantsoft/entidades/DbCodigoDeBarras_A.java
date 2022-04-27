package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerCodigoDeBarras;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbCodigoDeBarras_A extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo codigoDeBarras = new ClsCampo("codigoDeBarras", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo color = new ClsCampo("color", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbCodigoDeBarras_A(SerCodigoDeBarras serCodigoDeBarras) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbCodigoDeBarras_A", serCodigoDeBarras.getEmpresa() + "-" + serCodigoDeBarras.getCodigoDeBarras());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serCodigoDeBarras.getEmpresa());
		setString(codigoDeBarras, serCodigoDeBarras.getCodigoDeBarras());
		setString(modelo, serCodigoDeBarras.getModelo());
		setString(color, serCodigoDeBarras.getColor());
		setString(talla, serCodigoDeBarras.getTalla());
	}

	public DbCodigoDeBarras_A(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, codigoDeBarras, modelo, color, talla);
	}

	public SerCodigoDeBarras toSerCodigoDeBarras() throws ExcepcionControlada {
		return new SerCodigoDeBarras(getEmpresa(), getModelo(), getColor(), getTalla(), getCodigoDeBarras());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getCodigoDeBarras() throws ExcepcionControlada {
		return getString(codigoDeBarras);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public String getColor() throws ExcepcionControlada {
		return getString(color);
	}

	public String getTalla() throws ExcepcionControlada {
		return getString(talla);
	}
}