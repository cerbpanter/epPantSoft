package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerAlmEntradaDet;
import com.pantsoft.eppantsoft.serializable.SerAlmSalidaDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbAlmSalidaDet extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmSalida = new ClsCampo("folioAlmSalida", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo color = new ClsCampo("color", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 6, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 7, NO_SUSTITUIR_NULL);
	private final ClsCampo codigoDeBarras = new ClsCampo("codigoDeBarras", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaAlmSalida = new ClsCampo("fechaAlmSalida", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anio = new ClsCampo("anio", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidad = new ClsCampo("cantidad", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbAlmSalidaDet(SerAlmSalidaDet serAlmSalidaDet) throws ExcepcionControlada {
		Key keyp = KeyFactory.createKey("DbAlmSalida", serAlmSalidaDet.getEmpresa() + "-" + serAlmSalidaDet.getFolioAlmSalida());
		Key key = KeyFactory.createKey(keyp, "DbAlmSalidaDet", serAlmSalidaDet.getEmpresa() + "-" + serAlmSalidaDet.getFolioAlmSalida() + "-" + serAlmSalidaDet.getAlmacen() + "-" + serAlmSalidaDet.getModelo() + "-" + serAlmSalidaDet.getColor() + "-" + serAlmSalidaDet.getTalla());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmSalidaDet.getEmpresa());
		setLong(folioAlmSalida, serAlmSalidaDet.getFolioAlmSalida());
		setString(almacen, serAlmSalidaDet.getAlmacen());
		setString(modelo, serAlmSalidaDet.getModelo());
		setString(color, serAlmSalidaDet.getColor());
		setString(talla, serAlmSalidaDet.getTalla());
		setCodigoDeBarras(serAlmSalidaDet.getCodigoDeBarras());
		setFechaAlmSalida(serAlmSalidaDet.getFechaAlmSalida());
		setDia(serAlmSalidaDet.getDia());
		setMes(serAlmSalidaDet.getMes());
		setAnio(serAlmSalidaDet.getAnio());
		setCantidad(serAlmSalidaDet.getCantidad());
	}

	public DbAlmSalidaDet(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmSalida, almacen, modelo, color, talla, codigoDeBarras, fechaAlmSalida, dia, mes, anio, cantidad);
	}

	public SerAlmSalidaDet toSerAlmSalidaDet() throws ExcepcionControlada {
		return new SerAlmSalidaDet(getEmpresa(), getFolioAlmSalida(), getAlmacen(), getModelo(), getColor(), getTalla(), getCodigoDeBarras(), getFechaAlmSalida(), getDia(), getMes(), getAnio(), getCantidad());
	}

	public SerAlmEntradaDet toSerAlmEntradaDetTraspaso(String almacen) throws ExcepcionControlada {
		return new SerAlmEntradaDet(getEmpresa(), 0L, almacen, getModelo(), getColor(), getTalla(), getCodigoDeBarras(), getFechaAlmSalida(), getDia(), getMes(), getAnio(), getCantidad());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioAlmSalida() throws ExcepcionControlada {
		return getLong(folioAlmSalida);
	}

	public String getAlmacen() throws ExcepcionControlada {
		return getString(almacen);
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

	public Date getFechaAlmSalida() throws ExcepcionControlada {
		return getDate(fechaAlmSalida);
	}

	public void setFechaAlmSalida(Date fechaAlmSalida) throws ExcepcionControlada {
		setDate(this.fechaAlmSalida, fechaAlmSalida);
	}

	public Long getDia() throws ExcepcionControlada {
		return getLong(dia);
	}

	public void setDia(Long dia) throws ExcepcionControlada {
		setLong(this.dia, dia);
	}

	public Long getMes() throws ExcepcionControlada {
		return getLong(mes);
	}

	public void setMes(Long mes) throws ExcepcionControlada {
		setLong(this.mes, mes);
	}

	public Long getAnio() throws ExcepcionControlada {
		return getLong(anio);
	}

	public void setAnio(Long anio) throws ExcepcionControlada {
		setLong(this.anio, anio);
	}

	public Long getCantidad() throws ExcepcionControlada {
		return getLong(cantidad);
	}

	public void setCantidad(Long cantidad) throws ExcepcionControlada {
		setLong(this.cantidad, cantidad);
	}
}