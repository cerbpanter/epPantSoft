package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerAlmSalidaDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbAlmSalidaDet extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioAlmSalida = new ClsCampo("folioAlmSalida", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo dia = new ClsCampo("dia", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidad = new ClsCampo("cantidad", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalle = new ClsCampo("detalle", Tipo.Text, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbAlmSalidaDet(SerAlmSalidaDet serAlmSalidaDet) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbAlmSalidaDet", serAlmSalidaDet.getEmpresa() + "-" + serAlmSalidaDet.getFolioAlmSalida());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serAlmSalidaDet.getEmpresa());
		setLong(folioAlmSalida, serAlmSalidaDet.getFolioAlmSalida());
		setAlmacen(serAlmSalidaDet.getAlmacen());
		setTemporada(serAlmSalidaDet.getTemporada());
		setModelo(serAlmSalidaDet.getModelo());
		setDia(serAlmSalidaDet.getDia());
		setMes(serAlmSalidaDet.getMes());
		setCantidad(serAlmSalidaDet.getCantidad());
		setDetalle(serAlmSalidaDet.getDetalle());
	}

	public DbAlmSalidaDet(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioAlmSalida, almacen, temporada, modelo, dia, mes, cantidad, detalle);
	}

	public SerAlmSalidaDet toSerAlmSalidaDet() throws ExcepcionControlada {
		return new SerAlmSalidaDet(getEmpresa(), getFolioAlmSalida(), getAlmacen(), getTemporada(), getModelo(), getDia(), getMes(), getCantidad(), getDetalle());
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

	public void setAlmacen(String almacen) throws ExcepcionControlada {
		setString(this.almacen, almacen);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public void setTemporada(Long temporada) throws ExcepcionControlada {
		setLong(this.temporada, temporada);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public void setModelo(String modelo) throws ExcepcionControlada {
		setString(this.modelo, modelo);
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

	public long getCantidad() throws ExcepcionControlada {
		return getLong(cantidad);
	}

	public void setCantidad(long cantidad) throws ExcepcionControlada {
		setLong(this.cantidad, cantidad);
	}

	public String getDetalle() throws ExcepcionControlada {
		return getText(detalle);
	}

	public void setDetalle(String detalle) throws ExcepcionControlada {
		setText(this.detalle, detalle);
	}
}