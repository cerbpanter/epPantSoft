package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerListaPreciosDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbListaPreciosDet extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo idListaPrecios = new ClsCampo("idListaPrecios", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo talla = new ClsCampo("talla", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo descripcion = new ClsCampo("descripcion", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tela = new ClsCampo("tela", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo departamento = new ClsCampo("departamento", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precosto = new ClsCampo("precosto", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo margen = new ClsCampo("margen", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precioVenta = new ClsCampo("precioVenta", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precioVentaPublico = new ClsCampo("precioVentaPublico", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo seleccion = new ClsCampo("seleccion", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo valido = new ClsCampo("valido", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_TRUE, 0, NO_SUSTITUIR_NULL);

	public DbListaPreciosDet(SerListaPreciosDet serListaPreciosDet) throws ExcepcionControlada {
		Key keyp = KeyFactory.createKey("DbListaPrecios", serListaPreciosDet.getEmpresa() + "-" + serListaPreciosDet.getTemporada() + "-" + serListaPreciosDet.getIdListaPrecios());
		Key key = KeyFactory.createKey(keyp, "DbListaPreciosDet", serListaPreciosDet.getEmpresa() + "-" + serListaPreciosDet.getTemporada() + "-" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serListaPreciosDet.getEmpresa());
		setLong(temporada, serListaPreciosDet.getTemporada());
		setLong(idListaPrecios, serListaPreciosDet.getIdListaPrecios());
		setString(modelo, serListaPreciosDet.getModelo());
		setString(referencia, serListaPreciosDet.getReferencia());
		setTalla(serListaPreciosDet.getTalla());
		setDescripcion(serListaPreciosDet.getDescripcion());
		setTela(serListaPreciosDet.getTela());
		setDepartamento(serListaPreciosDet.getDepartamento());
		setPrecosto(serListaPreciosDet.getPrecosto());
		setMargen(serListaPreciosDet.getMargen());
		setPrecioVenta(serListaPreciosDet.getPrecioVenta());
		setPrecioVentaPublico(serListaPreciosDet.getPrecioVentaPublico());
		setSeleccion(serListaPreciosDet.getSeleccion());
		setValido(serListaPreciosDet.getValido());
	}

	public DbListaPreciosDet(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, idListaPrecios, modelo, referencia, talla, descripcion, tela, departamento, precosto, margen, precioVenta, precioVentaPublico, seleccion, valido);
	}

	public SerListaPreciosDet toSerListaPreciosDet() throws ExcepcionControlada {
		return new SerListaPreciosDet(getEmpresa(), getTemporada(), getIdListaPrecios(), getModelo(), getReferencia(), getTalla(), getDescripcion(), getTela(), getDepartamento(), getPrecosto(), getMargen(), getPrecioVenta(), getPrecioVentaPublico(), getSeleccion(), getValido());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public Long getIdListaPrecios() throws ExcepcionControlada {
		return getLong(idListaPrecios);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public String getReferencia() throws ExcepcionControlada {
		return getString(referencia);
	}

	public String getTalla() throws ExcepcionControlada {
		return getString(talla);
	}

	public void setTalla(String talla) throws ExcepcionControlada {
		setString(this.talla, talla);
	}

	public String getDescripcion() throws ExcepcionControlada {
		return getString(descripcion);
	}

	public void setDescripcion(String descripcion) throws ExcepcionControlada {
		setString(this.descripcion, descripcion);
	}

	public String getTela() throws ExcepcionControlada {
		return getString(tela);
	}

	public void setTela(String tela) throws ExcepcionControlada {
		setString(this.tela, tela);
	}

	public String getDepartamento() throws ExcepcionControlada {
		return getString(departamento);
	}

	public void setDepartamento(String departamento) throws ExcepcionControlada {
		setString(this.departamento, departamento);
	}

	public Double getPrecosto() throws ExcepcionControlada {
		return getDouble(precosto);
	}

	public void setPrecosto(Double precosto) throws ExcepcionControlada {
		setDouble(this.precosto, precosto);
	}

	public Double getMargen() throws ExcepcionControlada {
		return getDouble(margen);
	}

	public void setMargen(Double margen) throws ExcepcionControlada {
		setDouble(this.margen, margen);
	}

	public Double getPrecioVenta() throws ExcepcionControlada {
		return getDouble(precioVenta);
	}

	public void setPrecioVenta(Double precioVenta) throws ExcepcionControlada {
		setDouble(this.precioVenta, precioVenta);
	}

	public Double getPrecioVentaPublico() throws ExcepcionControlada {
		return getDouble(precioVentaPublico);
	}

	public void setPrecioVentaPublico(Double precioVentaPublico) throws ExcepcionControlada {
		setDouble(this.precioVentaPublico, precioVentaPublico);
	}

	public Boolean getSeleccion() throws ExcepcionControlada {
		return getBoolean(seleccion);
	}

	public void setSeleccion(Boolean seleccion) throws ExcepcionControlada {
		setBoolean(this.seleccion, seleccion);
	}

	public Boolean getValido() throws ExcepcionControlada {
		return getBoolean(valido);
	}

	public void setValido(Boolean valido) throws ExcepcionControlada {
		setBoolean(this.valido, valido);
	}
}