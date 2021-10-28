package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerListaPrecios;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbListaPrecios extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo idListaPrecios = new ClsCampo("idListaPrecios", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo descripcion = new ClsCampo("descripcion", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 50, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaGenerada = new ClsCampo("fechaGenerada", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modoBloqueo = new ClsCampo("modoBloqueo", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_TRUE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo descuento = new ClsCampo("descuento", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo autoGenerar = new ClsCampo("autoGenerar", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo pvp = new ClsCampo("pvp", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioPropietario = new ClsCampo("usuarioPropietario", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo filtros = new ClsCampo("filtros", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo valido = new ClsCampo("valido", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_TRUE, 0, NO_SUSTITUIR_NULL);

	public DbListaPrecios(SerListaPrecios serListaPrecios) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbListaPrecios", serListaPrecios.getEmpresa() + "-" + serListaPrecios.getTemporada() + "-" + serListaPrecios.getIdListaPrecios());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serListaPrecios.getEmpresa());
		setLong(temporada, serListaPrecios.getTemporada());
		setLong(idListaPrecios, serListaPrecios.getIdListaPrecios());
		setDescripcion(serListaPrecios.getDescripcion());
		setFechaGenerada(serListaPrecios.getFechaGenerada());
		setModoBloqueo(serListaPrecios.getModoBloqueo());
		setDescuento(serListaPrecios.getDescuento());
		setAutoGenerar(serListaPrecios.getAutoGenerar());
		setPvp(serListaPrecios.getPvp());
		setUsuarioPropietario(serListaPrecios.getUsuarioPropietario());
		setFiltros(serListaPrecios.getFiltros());
		setValido(serListaPrecios.getValido());
	}

	public DbListaPrecios(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, idListaPrecios, descripcion, fechaGenerada, modoBloqueo, descuento, autoGenerar, pvp, usuarioPropietario, filtros, valido);
	}

	public SerListaPrecios toSerListaPrecios() throws ExcepcionControlada {
		return new SerListaPrecios(getEmpresa(), getTemporada(), getIdListaPrecios(), getDescripcion(), getFechaGenerada(), getModoBloqueo(), getDescuento(), getAutoGenerar(), getPvp(), getUsuarioPropietario(), getFiltros(), getValido());
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

	public String getDescripcion() throws ExcepcionControlada {
		return getString(descripcion);
	}

	public void setDescripcion(String descripcion) throws ExcepcionControlada {
		setString(this.descripcion, descripcion);
	}

	public Date getFechaGenerada() throws ExcepcionControlada {
		return getDate(fechaGenerada);
	}

	public void setFechaGenerada(Date fechaGenerada) throws ExcepcionControlada {
		setDate(this.fechaGenerada, fechaGenerada);
	}

	public Boolean getModoBloqueo() throws ExcepcionControlada {
		return getBoolean(modoBloqueo);
	}

	public void setModoBloqueo(Boolean modoBloqueo) throws ExcepcionControlada {
		setBoolean(this.modoBloqueo, modoBloqueo);
	}

	public Double getDescuento() throws ExcepcionControlada {
		return getDouble(descuento);
	}

	public void setDescuento(Double descuento) throws ExcepcionControlada {
		setDouble(this.descuento, descuento);
	}

	public Boolean getAutoGenerar() throws ExcepcionControlada {
		return getBoolean(autoGenerar);
	}

	public void setAutoGenerar(Boolean autoGenerar) throws ExcepcionControlada {
		setBoolean(this.autoGenerar, autoGenerar);
	}

	public Double getPvp() throws ExcepcionControlada {
		return getDouble(pvp);
	}

	public void setPvp(Double pvp) throws ExcepcionControlada {
		setDouble(this.pvp, pvp);
	}

	public String getUsuarioPropietario() throws ExcepcionControlada {
		return getString(usuarioPropietario);
	}

	public void setUsuarioPropietario(String usuarioPropietario) throws ExcepcionControlada {
		setString(this.usuarioPropietario, usuarioPropietario);
	}

	public String getFiltros() throws ExcepcionControlada {
		return getText(filtros);
	}

	public void setFiltros(String filtros) throws ExcepcionControlada {
		setText(this.filtros, filtros);
	}

	public Boolean getValido() throws ExcepcionControlada {
		return getBoolean(valido);
	}

	public void setValido(Boolean valido) throws ExcepcionControlada {
		setBoolean(this.valido, valido);
	}
}