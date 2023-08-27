package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.serializable.SerPedido;
import com.pantsoft.eppantsoft.serializable.SerPedidoDet;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbPedido extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioPedido = new ClsCampo("folioPedido", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo zonaHoraria = new ClsCampo("zonaHoraria", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaPedido = new ClsCampo("fechaPedido", Tipo.Date, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaCancelacion = new ClsCampo("fechaCancelacion", Tipo.Date, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo diaCancelacion = new ClsCampo("diaCancelacion", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo semanaCancelacion = new ClsCampo("semanaCancelacion", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mesCancelacion = new ClsCampo("mesCancelacion", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anioCancelacion = new ClsCampo("anioCancelacion", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioCliente = new ClsCampo("folioCliente", Tipo.Long, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cliente = new ClsCampo("cliente", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo departamento = new ClsCampo("departamento", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo confirmado = new ClsCampo("confirmado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelos = new ClsCampo("modelos", Tipo.ArrayString, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo marca = new ClsCampo("marca", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

	// Dependencias
	private List<DbPedidoDet> detalles = null;

	public DbPedido(SerPedido serPedido) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbPedido", serPedido.getEmpresa() + "-" + serPedido.getFolioPedido());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serPedido.getEmpresa());
		setLong(folioPedido, serPedido.getFolioPedido());
		setTemporada(serPedido.getTemporada());
		setFechaPedido(serPedido.getFechaPedido());
		setFolioCliente(serPedido.getFolioCliente());
		setCliente(serPedido.getCliente());
		setFechaCancelacion(serPedido.getFechaCancelacion(), serPedido.getZonaHoraria());
		setDepartamento(serPedido.getDepartamento());
		setConfirmado(serPedido.getConfirmado());
	}

	public DbPedido(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioPedido, temporada, zonaHoraria, fechaCancelacion, fechaPedido, diaCancelacion, semanaCancelacion, mesCancelacion, anioCancelacion, folioCliente, cliente, departamento, confirmado, modelos, marca);
	}

	public SerPedido toSerPedido(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerPedido serPedido = new SerPedido(getEmpresa(), getFolioPedido(), getTemporada(), getZonaHoraria(), getFechaPedido(), getFolioCliente(), getCliente(), getFechaCancelacion(), getDepartamento(), getConfirmado(), getMarca());

		// Agrego los Detalles
		getDetalles(datastore, tx);
		List<SerPedidoDet> lstDetalles = new ArrayList<SerPedidoDet>();
		for (DbPedidoDet dbD : detalles)
			lstDetalles.add(dbD.toSerPedidoDet());
		serPedido.setDetalles(lstDetalles.toArray(new SerPedidoDet[0]));

		return serPedido;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public long getFolioPedido() throws ExcepcionControlada {
		return getLong(folioPedido);
	}

	public long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public void setTemporada(long temporada) throws ExcepcionControlada {
		setLong(this.temporada, temporada);
	}

	public String getZonaHoraria() throws ExcepcionControlada {
		return getString(zonaHoraria);
	}

	public Date getFechaPedido() throws ExcepcionControlada {
		return getDate(fechaPedido);
	}

	public void setFechaPedido(Date fechaPedido) throws ExcepcionControlada {
		if (fechaPedido == null) {
			throw new ExcepcionControlada("El campo Fecha no puede quedar vacío.");
		}
		setDate(this.fechaPedido, fechaPedido);
	}

	public Date getFechaCancelacion() throws ExcepcionControlada {
		return getDate(fechaCancelacion);
	}

	public void setFechaCancelacion(Date fechaCancelacion, String zonaHoraria) throws ExcepcionControlada {
		if (fechaCancelacion == null) {
			throw new ExcepcionControlada("El campo Fecha Cancelación no puede quedar vacío.");
		}

		setDate(this.fechaCancelacion, fechaCancelacion);

		setDate(this.fechaCancelacion, fechaCancelacion);
		setString(this.zonaHoraria, zonaHoraria);
		TimeZone tzGMT = TimeZone.getTimeZone(zonaHoraria);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaCancelacion);
		cal.setTimeZone(tzGMT);

		setLong(this.anioCancelacion, (long) cal.get(Calendar.YEAR));
		setLong(this.mesCancelacion, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.MONTH) + 1));
		setLong(this.semanaCancelacion, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.WEEK_OF_YEAR)));
		setLong(this.diaCancelacion, (long) ((cal.get(Calendar.YEAR) * 10000) + ((cal.get(Calendar.MONTH) + 1) * 100) + cal.get(Calendar.DAY_OF_MONTH)));
	}

	public long getDiaCancelacion() throws ExcepcionControlada {
		return getLong(diaCancelacion);
	}

	public long getSemanaCancelacion() throws ExcepcionControlada {
		return getLong(semanaCancelacion);
	}

	public long getMesCancelacion() throws ExcepcionControlada {
		return getLong(mesCancelacion);
	}

	public Long getAnioCancelacion() throws ExcepcionControlada {
		return getLong(anioCancelacion);
	}

	public Long getFolioCliente() throws ExcepcionControlada {
		return getLong(folioCliente);
	}

	public void setFolioCliente(Long folioCliente) throws ExcepcionControlada {
		setLong(this.folioCliente, folioCliente);
	}

	public String getCliente() throws ExcepcionControlada {
		return getString(cliente);
	}

	public void setCliente(String cliente) throws ExcepcionControlada {
		setString(this.cliente, cliente);
	}

	public String getDepartamento() throws ExcepcionControlada {
		return getString(departamento);
	}

	public void setDepartamento(String departamento) throws ExcepcionControlada {
		setString(this.departamento, departamento);
	}

	public boolean getConfirmado() throws ExcepcionControlada {
		return getBoolean(confirmado);
	}

	public void setConfirmado(boolean confirmado) throws ExcepcionControlada {
		setBoolean(this.confirmado, confirmado);
	}

	public String getMarca() throws ExcepcionControlada {
		return getString(marca);
	}

	public void setMarca(String marca) throws ExcepcionControlada {
		setString(this.marca, marca);
	}

	public List<DbPedidoDet> getDetalles(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (detalles == null) {
			List<Entity> lstDetalles = ejecutarConsulta(datastore, tx, "DbPedidoDet", getKey());
			detalles = new ArrayList<DbPedidoDet>();
			for (Entity detalle : lstDetalles)
				detalles.add(new DbPedidoDet(detalle));
		}
		return detalles;
	}

	public ArrayList<String> getModelos() throws ExcepcionControlada {
		return getArrayString(modelos);
	}

	public void setModelos(ArrayList<String> modelos) throws ExcepcionControlada {
		setArrayString(this.modelos, modelos);
	}

}