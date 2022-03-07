package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerInvModeloDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbInvModeloDet extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo almacen = new ClsCampo("almacen", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo color = new ClsCampo("color", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 6, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 7, NO_SUSTITUIR_NULL);
	private final ClsCampo codigoDeBarras = new ClsCampo("codigoDeBarras", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidad = new ClsCampo("cantidad", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbInvModeloDet(SerInvModeloDet serInvModeloDet) throws ExcepcionControlada {
		Key keyp = KeyFactory.createKey("DbEmpresa", serInvModeloDet.getEmpresa());
		Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", serInvModeloDet.getEmpresa() + "-" + serInvModeloDet.getAlmacen() + "-" + serInvModeloDet.getModelo() + "-" + serInvModeloDet.getTemporada() + "-" + serInvModeloDet.getColor() + "-" + serInvModeloDet.getTalla());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serInvModeloDet.getEmpresa());
		setString(almacen, serInvModeloDet.getAlmacen());
		setString(modelo, serInvModeloDet.getModelo());
		setLong(temporada, serInvModeloDet.getTemporada());
		setString(color, serInvModeloDet.getColor());
		setString(talla, serInvModeloDet.getTalla());
		setCodigoDeBarras(serInvModeloDet.getCodigoDeBarras());
		setCantidad(serInvModeloDet.getCantidad());
	}

	public DbInvModeloDet(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, almacen, modelo, temporada, color, talla, codigoDeBarras, cantidad);
	}

	public SerInvModeloDet toSerInvModeloDet() throws ExcepcionControlada {
		return new SerInvModeloDet(getEmpresa(), getAlmacen(), getModelo(), getTemporada(), getColor(), getTalla(), getCodigoDeBarras(), getCantidad());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
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

	public Long getCantidad() throws ExcepcionControlada {
		return getLong(cantidad);
	}

	public void setCantidad(Long cantidad) throws ExcepcionControlada {
		setLong(this.cantidad, cantidad);
	}
}