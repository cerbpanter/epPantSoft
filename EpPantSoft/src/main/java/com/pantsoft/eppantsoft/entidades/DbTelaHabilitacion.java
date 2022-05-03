package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerTelaHabilitacion;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbTelaHabilitacion extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo materia = new ClsCampo("materia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 15, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 1, TAM_NORMAL, "H", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precios = new ClsCampo("precios", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo ancho = new ClsCampo("ancho", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);

	public DbTelaHabilitacion(SerTelaHabilitacion ser) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbTelaHabilitacion", ser.getEmpresa() + "-" + ser.getMateria());
		entidad = new Entity(key);
		asignarValoresDefault();

		setString(this.empresa, ser.getEmpresa());
		setString(this.materia, ser.getMateria());
		setTipo(ser.getTipo());
		setPrecios(ser.getPrecios());
		setAncho(ser.getAncho());
	}

	public DbTelaHabilitacion(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	@Override
	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, materia, tipo, precios, ancho);
	}

	public SerTelaHabilitacion toSerTelaHabilitacion() throws ExcepcionControlada {
		return new SerTelaHabilitacion(getEmpresa(), getMateria(), getTipo(), getPrecios(), getAncho());
	}

	@Override
	public boolean getLiberado() {
		return true;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getMateria() throws ExcepcionControlada {
		return getString(materia);
	}

	public String getTipo() throws ExcepcionControlada {
		return getString(tipo);
	}

	public void setTipo(String tipo) throws ExcepcionControlada {
		setString(this.tipo, tipo);
	}

	public String getPrecios() throws ExcepcionControlada {
		return getText(precios);
	}

	public void setPrecios(String precios) throws ExcepcionControlada {
		setText(this.precios, precios);
	}

	public double getAncho() throws ExcepcionControlada {
		return getDouble(ancho);
	}

	public void setAncho(double ancho) throws ExcepcionControlada {
		setDouble(this.ancho, ancho);
	}
}