package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerEntidad;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbEntidad extends ClsEntidad {
	private final ClsCampo entidad = new ClsCampo("entidad", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo endpoint = new ClsCampo("endpoint", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo respaldar = new ClsCampo("respaldar", Tipo.Boolean, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo soloAdmin = new ClsCampo("soloAdmin", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);
	private final ClsCampo validarSucursal = new ClsCampo("validarSucursal", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_TRUE, 0, SUSTITUIR_NULL);

	public DbEntidad(String entidad, boolean endpoint, boolean respaldar, boolean soloAdmin, boolean validarSucursal) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbEntidad", entidad);
		setEntidad(new Entity(key));
		asignarValoresDefault();
		setString(this.entidad, entidad);
		setEndpoint(endpoint);
		setRespaldar(respaldar);
		setSoloAdmin(soloAdmin);
		setValidarSucursal(validarSucursal);
	}

	public DbEntidad(SerEntidad serEntidad) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbEntidad", serEntidad.getEntidad());
		setEntidad(new Entity(key));
		asignarValoresDefault();
		setString(this.entidad, serEntidad.getEntidad());
		setEndpoint(serEntidad.getEndpoint());
		setRespaldar(serEntidad.getRespaldar());
		setSoloAdmin(serEntidad.getSoloAdmin());
		setValidarSucursal(serEntidad.getValidarSucursal());
	}

	public DbEntidad(Entity entidad) throws ExcepcionControlada {
		setEntidad(entidad);
		asignarValoresDefault();
	}

	public SerEntidad toSerEntidad() throws Exception {
		return new SerEntidad(getEntidadNombre(), getRespaldar(), getEndpoint(), getSoloAdmin(), getValidarSucursal());
	}

	public boolean getLiberado() {
		return true;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(entidad, endpoint, respaldar, soloAdmin, validarSucursal);
	}

	public String getEntidadNombre() throws ExcepcionControlada {
		return getString(entidad);
	}

	public boolean getEndpoint() throws ExcepcionControlada {
		return getBoolean(endpoint);
	}

	public void setEndpoint(boolean endpoint) throws ExcepcionControlada {
		setBoolean(this.endpoint, endpoint);
	}

	public boolean getRespaldar() throws ExcepcionControlada {
		return getBoolean(respaldar);
	}

	public void setRespaldar(boolean respaldar) throws ExcepcionControlada {
		setBoolean(this.respaldar, respaldar);
	}

	public boolean getSoloAdmin() throws ExcepcionControlada {
		return getBoolean(soloAdmin);
	}

	public void setSoloAdmin(boolean soloAdmin) throws ExcepcionControlada {
		setBoolean(this.soloAdmin, soloAdmin);
	}

	public boolean getValidarSucursal() throws ExcepcionControlada {
		return getBoolean(validarSucursal);
	}

	public void setValidarSucursal(boolean validarSucursal) throws ExcepcionControlada {
		setBoolean(this.validarSucursal, validarSucursal);
	}
}