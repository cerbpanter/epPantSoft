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

public class DbCodigoDeBarras extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo color = new ClsCampo("color", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo codigoDeBarras = new ClsCampo("codigoDeBarras", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbCodigoDeBarras(SerCodigoDeBarras serCodigoDeBarras) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbCodigoDeBarras", serCodigoDeBarras.getEmpresa() + "-" + serCodigoDeBarras.getModelo() + "-" + serCodigoDeBarras.getColor() + "-" + serCodigoDeBarras.getTalla());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serCodigoDeBarras.getEmpresa());
		setString(modelo, serCodigoDeBarras.getModelo());
		setString(color, serCodigoDeBarras.getColor());
		setString(talla, serCodigoDeBarras.getTalla());
		setCodigoDeBarras(serCodigoDeBarras.getCodigoDeBarras());
	}

	public DbCodigoDeBarras(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, modelo, color, talla, codigoDeBarras);
	}

	public SerCodigoDeBarras toSerCodigoDeBarras() throws ExcepcionControlada {
		return new SerCodigoDeBarras(getEmpresa(), getModelo(), getColor(), getTalla(), getCodigoDeBarras());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
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

	public String getCodigoDeBarras() throws ExcepcionControlada {
		return getString(codigoDeBarras);
	}

	public void setCodigoDeBarras(String codigoDeBarras) throws ExcepcionControlada {
		setString(this.codigoDeBarras, codigoDeBarras);
	}
}