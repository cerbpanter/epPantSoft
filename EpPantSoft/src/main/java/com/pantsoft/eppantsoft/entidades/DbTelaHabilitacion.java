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
	private final ClsCampo composicion1 = new ClsCampo("composicion1", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo composicion2 = new ClsCampo("composicion2", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo composicion3 = new ClsCampo("composicion3", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo composicion4 = new ClsCampo("composicion4", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

	public DbTelaHabilitacion(SerTelaHabilitacion ser) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbTelaHabilitacion", ser.getEmpresa() + "-" + ser.getMateria());
		entidad = new Entity(key);
		asignarValoresDefault();

		setString(this.empresa, ser.getEmpresa());
		setString(this.materia, ser.getMateria());
		setTipo(ser.getTipo());
		setPrecios(ser.getPrecios());
		setAncho(ser.getAncho());
		setComposicion1(ser.getComposicion1());
		setComposicion2(ser.getComposicion2());
		setComposicion3(ser.getComposicion3());
		setComposicion4(ser.getComposicion4());
	}

	public DbTelaHabilitacion(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	@Override
	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, materia, tipo, precios, ancho, composicion1, composicion2, composicion3, composicion4);
	}

	public SerTelaHabilitacion toSerTelaHabilitacion() throws ExcepcionControlada {
		return new SerTelaHabilitacion(getEmpresa(), getMateria(), getTipo(), getPrecios(), getAncho(), getComposicion1(), getComposicion2(), getComposicion3(), getComposicion4());
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

	public String getComposicion1() throws ExcepcionControlada {
		return getString(composicion1);
	}

	public void setComposicion1(String composicion) throws ExcepcionControlada {
		setString(this.composicion1, composicion);
	}

	public String getComposicion2() throws ExcepcionControlada {
		return getString(composicion2);
	}

	public void setComposicion2(String composicion) throws ExcepcionControlada {
		setString(this.composicion2, composicion);
	}

	public String getComposicion3() throws ExcepcionControlada {
		return getString(composicion3);
	}

	public void setComposicion3(String composicion) throws ExcepcionControlada {
		setString(this.composicion3, composicion);
	}

	public String getComposicion4() throws ExcepcionControlada {
		return getString(composicion4);
	}

	public void setComposicion4(String composicion) throws ExcepcionControlada {
		setString(this.composicion4, composicion);
	}

}