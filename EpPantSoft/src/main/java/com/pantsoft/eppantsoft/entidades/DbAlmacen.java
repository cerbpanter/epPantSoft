package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerAlmacen;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbAlmacen extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);

	public DbAlmacen(SerAlmacen serAlmacen) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbAlmacen", serAlmacen.getEmpresa() + "-" + serAlmacen.getAlmacen());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmacen.getEmpresa());
		setString(almacen, serAlmacen.getAlmacen());
	}

	public DbAlmacen(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, almacen);
	}

	public SerAlmacen toSerAlmacen() throws ExcepcionControlada {
		return new SerAlmacen(getEmpresa(), getAlmacen());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getAlmacen() throws ExcepcionControlada {
		return getString(almacen);
	}
}