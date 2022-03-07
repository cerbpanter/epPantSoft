package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerAlmEntradaDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbAlmEntradaDet extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmEntrada = new ClsCampo("folioAlmEntrada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo color = new ClsCampo("color", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 6, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 7, NO_SUSTITUIR_NULL);
	private final ClsCampo codigoDeBarras = new ClsCampo("codigoDeBarras", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaAlmEntrada = new ClsCampo("fechaAlmEntrada", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidad = new ClsCampo("cantidad", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbAlmEntradaDet(SerAlmEntradaDet serAlmEntradaDet) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbAlmEntradaDet", serAlmEntradaDet.getEmpresa() + "-" + serAlmEntradaDet.getFolioAlmEntrada() + "-" + serAlmEntradaDet.getAlmacen() + "-" + serAlmEntradaDet.getModelo() + "-" + serAlmEntradaDet.getTemporada() + "-" + serAlmEntradaDet.getColor() + "-" + serAlmEntradaDet.getTalla());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmEntradaDet.getEmpresa());
		setLong(folioAlmEntrada, serAlmEntradaDet.getFolioAlmEntrada());
		setString(almacen, serAlmEntradaDet.getAlmacen());
		setString(modelo, serAlmEntradaDet.getModelo());
		setLong(temporada, serAlmEntradaDet.getTemporada());
		setString(color, serAlmEntradaDet.getColor());
		setString(talla, serAlmEntradaDet.getTalla());
		setCodigoDeBarras(serAlmEntradaDet.getCodigoDeBarras());
		setFechaAlmEntrada(serAlmEntradaDet.getFechaAlmEntrada());
		setDia(serAlmEntradaDet.getDia());
		setMes(serAlmEntradaDet.getMes());
		setCantidad(serAlmEntradaDet.getCantidad());
	}

	public DbAlmEntradaDet(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmEntrada, almacen, modelo, temporada, color, talla, codigoDeBarras, fechaAlmEntrada, dia, mes, cantidad);
	}

	public SerAlmEntradaDet toSerAlmEntradaDet() throws ExcepcionControlada {
		return new SerAlmEntradaDet(getEmpresa(), getFolioAlmEntrada(), getAlmacen(), getModelo(), getTemporada(), getColor(), getTalla(), getCodigoDeBarras(), getFechaAlmEntrada(), getDia(), getMes(), getCantidad());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioAlmEntrada() throws ExcepcionControlada {
		return getLong(folioAlmEntrada);
	}

	public String getAlmacen() throws ExcepcionControlada {
		return getString(almacen);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
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

	public Date getFechaAlmEntrada() throws ExcepcionControlada {
		return getDate(fechaAlmEntrada);
	}

	public void setFechaAlmEntrada(Date fechaAlmEntrada) throws ExcepcionControlada {
		setDate(this.fechaAlmEntrada, fechaAlmEntrada);
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

	public Long getCantidad() throws ExcepcionControlada {
		return getLong(cantidad);
	}

	public void setCantidad(Long cantidad) throws ExcepcionControlada {
		setLong(this.cantidad, cantidad);
	}
}