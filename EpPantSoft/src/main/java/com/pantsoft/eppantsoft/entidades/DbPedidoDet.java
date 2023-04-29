package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerPedidoDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbPedidoDet extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioPedido = new ClsCampo("folioPedido", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo renglon = new ClsCampo("renglon", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precio = new ClsCampo("precio", Tipo.Double, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidad = new ClsCampo("cantidad", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tallas = new ClsCampo("tallas", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo detalle = new ClsCampo("detalle", Tipo.Text, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbPedidoDet(SerPedidoDet serPedidoDet) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbPedidoDet", serPedidoDet.getEmpresa() + "-" + serPedidoDet.getFolioPedido() + "-" + serPedidoDet.getRenglon());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serPedidoDet.getEmpresa());
		setLong(folioPedido, serPedidoDet.getFolioPedido());
		setLong(renglon, serPedidoDet.getRenglon());
		setTemporada(serPedidoDet.getTemporada());
		setModelo(serPedidoDet.getModelo());
		setReferencia(serPedidoDet.getReferencia());
		setPrecio(serPedidoDet.getPrecio());
		setCantidad(serPedidoDet.getCantidad());
		setTallas(serPedidoDet.getTallas());
		setObservaciones(serPedidoDet.getObservaciones());
		setDetalle(serPedidoDet.getDetalle());
	}

	public DbPedidoDet(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioPedido, renglon, temporada, modelo, referencia, precio, cantidad, tallas, observaciones, detalle);
	}

	public SerPedidoDet toSerPedidoDet() throws ExcepcionControlada {
		return new SerPedidoDet(getEmpresa(), getFolioPedido(), getRenglon(), getTemporada(), getModelo(), getReferencia(), getPrecio(), getCantidad(), getTallas(), getObservaciones(), getDetalle());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioPedido() throws ExcepcionControlada {
		return getLong(folioPedido);
	}

	public Long getRenglon() throws ExcepcionControlada {
		return getLong(renglon);
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

	public String getReferencia() throws ExcepcionControlada {
		return getString(referencia);
	}

	public void setReferencia(String referencia) throws ExcepcionControlada {
		setString(this.referencia, referencia);
	}

	public Double getPrecio() throws ExcepcionControlada {
		return getDouble(precio);
	}

	public void setPrecio(Double precio) throws ExcepcionControlada {
		setDouble(this.precio, precio);
	}

	public Long getCantidad() throws ExcepcionControlada {
		return getLong(cantidad);
	}

	public void setCantidad(Long cantidad) throws ExcepcionControlada {
		setLong(this.cantidad, cantidad);
	}

	public String getTallas() throws ExcepcionControlada {
		return getString(tallas);
	}

	public void setTallas(String tallas) throws ExcepcionControlada {
		setString(this.tallas, tallas);
	}

	public String getObservaciones() throws ExcepcionControlada {
		return getString(observaciones);
	}

	public void setObservaciones(String observaciones) throws ExcepcionControlada {
		setString(this.observaciones, observaciones);
	}

	public String getDetalle() throws ExcepcionControlada {
		return getText(detalle);
	}

	public void setDetalle(String detalle) throws ExcepcionControlada {
		setText(this.detalle, detalle);
	}
}