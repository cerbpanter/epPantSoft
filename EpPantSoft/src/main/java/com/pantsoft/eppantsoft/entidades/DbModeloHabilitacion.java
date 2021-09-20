package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerModeloHabilitacion;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbModeloHabilitacion extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo materia = new ClsCampo("materia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo consumo = new ClsCampo("consumo", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo consumoReal = new ClsCampo("consumoReal", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo trazo = new ClsCampo("trazo", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);

	public DbModeloHabilitacion(SerModeloHabilitacion serModeloHabilitacion) throws ExcepcionControlada {
		Key keyP = KeyFactory.createKey("DbModelo", serModeloHabilitacion.getEmpresa() + "-" + serModeloHabilitacion.getTemporada() + "-" + serModeloHabilitacion.getModelo() + "-" + serModeloHabilitacion.getReferencia());
		Key key = KeyFactory.createKey(keyP, "DbModeloHabilitacion", serModeloHabilitacion.getEmpresa() + "-" + serModeloHabilitacion.getTemporada() + "-" + serModeloHabilitacion.getModelo() + "-" + serModeloHabilitacion.getReferencia() + "-" + serModeloHabilitacion.getMateria());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serModeloHabilitacion.getEmpresa());
		setLong(temporada, serModeloHabilitacion.getTemporada());
		setString(modelo, serModeloHabilitacion.getModelo());
		setString(referencia, serModeloHabilitacion.getReferencia());
		setString(materia, serModeloHabilitacion.getMateria());
		setConsumo(serModeloHabilitacion.getConsumo());
		setConsumoReal(serModeloHabilitacion.getConsumoReal());
		setTrazo(serModeloHabilitacion.getTrazo());
	}

	public DbModeloHabilitacion(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, modelo, referencia, materia, consumo, consumoReal, trazo);
	}

	public SerModeloHabilitacion toSerModeloHabilitacion() throws ExcepcionControlada {
		return new SerModeloHabilitacion(getEmpresa(), getTemporada(), getModelo(), getReferencia(), getMateria(), getConsumo(), getConsumoReal(), getTrazo());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public String getReferencia() throws ExcepcionControlada {
		return getString(referencia);
	}

	public String getMateria() throws ExcepcionControlada {
		return getString(materia);
	}

	public Double getConsumo() throws ExcepcionControlada {
		return getDouble(consumo);
	}

	public void setConsumo(Double consumo) throws ExcepcionControlada {
		setDouble(this.consumo, consumo);
	}

	public Double getConsumoReal() throws ExcepcionControlada {
		return getDouble(consumoReal);
	}

	public void setConsumoReal(Double consumoReal) throws ExcepcionControlada {
		setDouble(this.consumoReal, consumoReal);
	}

	public Double getTrazo() throws ExcepcionControlada {
		return getDouble(trazo);
	}

	public void setTrazo(Double trazo) throws ExcepcionControlada {
		setDouble(this.trazo, trazo);
	}
}