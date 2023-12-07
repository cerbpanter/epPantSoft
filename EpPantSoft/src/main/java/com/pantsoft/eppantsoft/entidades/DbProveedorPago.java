package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerProveedorPago;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbProveedorPago extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo serieFactura = new ClsCampo("serieFactura", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo folioFactura = new ClsCampo("folioFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo uuid = new ClsCampo("uuid", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo importeTotal = new ClsCampo("importeTotal", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo revisado = new ClsCampo("revisado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo autorizado = new ClsCampo("autorizado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo pagado = new ClsCampo("pagado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo terminado = new ClsCampo("terminado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioProveedor = new ClsCampo("folioProveedor", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo proveedor = new ClsCampo("proveedor", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaFactura = new ClsCampo("fechaFactura", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo diaFactura = new ClsCampo("diaFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo semanaFactura = new ClsCampo("semanaFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mesFactura = new ClsCampo("mesFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anioFactura = new ClsCampo("anioFactura", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaVencimiento = new ClsCampo("fechaVencimiento", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo diaVencimiento = new ClsCampo("diaVencimiento", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo semanaVencimiento = new ClsCampo("semanaVencimiento", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mesVencimiento = new ClsCampo("mesVencimiento", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anioVencimiento = new ClsCampo("anioVencimiento", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo semana = new ClsCampo("semana", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "1", 0, NO_SUSTITUIR_NULL);

	public DbProveedorPago(SerProveedorPago serProveedorPago) throws ExcepcionControlada {
		if (serProveedorPago.getSerieFactura() == null)
			throw new ExcepcionControlada("El campo 'serieFactura' no puede ser Null");
		long mes = 0;
		// Si no esta revisdo no tiene fechavencimiento y se asigna el mes cero, de lo contrario se asigna al mes de fechaVencimiento
		if (fechaVencimiento != null) {
			TimeZone tzGMT = TimeZone.getTimeZone(serProveedorPago.getZonaHoraria());
			Calendar cal = Calendar.getInstance();
			cal.setTime(serProveedorPago.getFechaVencimiento());
			cal.setTimeZone(tzGMT);

			mes = (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.MONTH) + 1);
		}
		Key keyp = KeyFactory.createKey("DbProveedorPagoMes", serProveedorPago.getEmpresa() + "-" + mes);
		Key key = KeyFactory.createKey(keyp, "DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serProveedorPago.getEmpresa());
		setString(serieFactura, serProveedorPago.getSerieFactura());
		setFolioFactura(serProveedorPago.getFolioFactura());
		setUuid(serProveedorPago.getUuid());
		setImporteTotal(serProveedorPago.getImporteTotal());
		setRevisado(serProveedorPago.getRevisado());
		setAutorizado(serProveedorPago.getAutorizado());
		setPagado(serProveedorPago.getPagado());
		setTerminado(serProveedorPago.getTerminado());
		setFolioProveedor(serProveedorPago.getFolioProveedor());
		setProveedor(serProveedorPago.getProveedor());
		setFechaFactura(serProveedorPago.getFechaFactura(), serProveedorPago.getZonaHoraria());
		setFechaVencimiento(serProveedorPago.getFechaVencimiento(), serProveedorPago.getZonaHoraria());
		setSemana(serProveedorPago.getSemana());
	}

	public DbProveedorPago(Entity entidad) {
		this.entidad = entidad;
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, serieFactura, folioFactura, uuid, importeTotal, revisado, autorizado, pagado, terminado, folioProveedor, proveedor, fechaFactura, diaFactura, semanaFactura, mesFactura, anioFactura, fechaVencimiento, diaVencimiento, semanaVencimiento, mesVencimiento, anioVencimiento, semana);
	}

	public SerProveedorPago toSerProveedorPago() throws ExcepcionControlada {
		return new SerProveedorPago(getEmpresa(), getSerieFactura(), getFolioFactura(), getUuid(), getImporteTotal(), getRevisado(), getAutorizado(), getPagado(), getTerminado(), getFolioProveedor(), getProveedor(), getFechaFactura(), getFechaVencimiento(), getSemana());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getSerieFactura() throws ExcepcionControlada {
		return getString(serieFactura);
	}

	public Long getFolioFactura() throws ExcepcionControlada {
		return getLong(folioFactura);
	}

	public void setFolioFactura(Long folioFactura) throws ExcepcionControlada {
		setLong(this.folioFactura, folioFactura);
	}

	public String getUuid() throws ExcepcionControlada {
		return getString(uuid);
	}

	public void setUuid(String uuid) throws ExcepcionControlada {
		setString(this.uuid, uuid);
	}

	public Double getImporteTotal() throws ExcepcionControlada {
		return getDouble(importeTotal);
	}

	public void setImporteTotal(Double importeTotal) throws ExcepcionControlada {
		setDouble(this.importeTotal, importeTotal);
	}

	public Boolean getRevisado() throws ExcepcionControlada {
		return getBoolean(revisado);
	}

	public void setRevisado(Boolean revisado) throws ExcepcionControlada {
		setBoolean(this.revisado, revisado);
	}

	public Boolean getAutorizado() throws ExcepcionControlada {
		return getBoolean(autorizado);
	}

	public void setAutorizado(Boolean autorizado) throws ExcepcionControlada {
		setBoolean(this.autorizado, autorizado);
	}

	public Boolean getPagado() throws ExcepcionControlada {
		return getBoolean(pagado);
	}

	public void setPagado(Boolean pagado) throws ExcepcionControlada {
		setBoolean(this.pagado, pagado);
	}

	public Boolean getTerminado() throws ExcepcionControlada {
		return getBoolean(terminado);
	}

	public void setTerminado(Boolean terminado) throws ExcepcionControlada {
		setBoolean(this.terminado, terminado);
	}

	public Long getFolioProveedor() throws ExcepcionControlada {
		return getLong(folioProveedor);
	}

	public void setFolioProveedor(Long folioProveedor) throws ExcepcionControlada {
		setLong(this.folioProveedor, folioProveedor);
	}

	public String getProveedor() throws ExcepcionControlada {
		return getString(proveedor);
	}

	public void setProveedor(String proveedor) throws ExcepcionControlada {
		setString(this.proveedor, proveedor);
	}

	public Date getFechaFactura() throws ExcepcionControlada {
		return getDate(fechaFactura);
	}

	public void setFechaFactura(Date fechaFactura, String zonaHoraria) throws ExcepcionControlada {
		setDate(this.fechaFactura, fechaFactura);
		if (fechaFactura == null) {
			setLong(this.anioFactura, 0L);
			setLong(this.mesFactura, 0L);
			setLong(this.semanaFactura, 0L);
			setLong(this.diaFactura, 0L);
		} else {
			TimeZone tzGMT = TimeZone.getTimeZone(zonaHoraria);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaFactura);
			cal.setTimeZone(tzGMT);

			setLong(this.anioFactura, (long) cal.get(Calendar.YEAR));
			setLong(this.mesFactura, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.MONTH) + 1));
			setLong(this.semanaFactura, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.WEEK_OF_YEAR)));
			setLong(this.diaFactura, (long) ((cal.get(Calendar.YEAR) * 10000) + ((cal.get(Calendar.MONTH) + 1) * 100) + cal.get(Calendar.DAY_OF_MONTH)));
		}
	}

	public Long getDiaFactura() throws ExcepcionControlada {
		return getLong(diaFactura);
	}

	public Long getSemanaFactura() throws ExcepcionControlada {
		return getLong(semanaFactura);
	}

	public Long getMesFactura() throws ExcepcionControlada {
		return getLong(mesFactura);
	}

	public Long getAnioFactura() throws ExcepcionControlada {
		return getLong(anioFactura);
	}

	public Date getFechaVencimiento() throws ExcepcionControlada {
		return getDate(fechaVencimiento);
	}

	public void setFechaVencimiento(Date fechaVencimiento, String zonaHoraria) throws ExcepcionControlada {
		setDate(this.fechaVencimiento, fechaVencimiento);
		if (fechaVencimiento == null) {
			setLong(this.anioVencimiento, 0L);
			setLong(this.mesVencimiento, 0L);
			setLong(this.semanaVencimiento, 0L);
			setLong(this.diaVencimiento, 0L);
		} else {
			TimeZone tzGMT = TimeZone.getTimeZone(zonaHoraria);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaVencimiento);
			cal.setTimeZone(tzGMT);

			setLong(this.anioVencimiento, (long) cal.get(Calendar.YEAR));
			setLong(this.mesVencimiento, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.MONTH) + 1));
			setLong(this.semanaVencimiento, (long) ((cal.get(Calendar.YEAR) * 100) + cal.get(Calendar.WEEK_OF_YEAR)));
			setLong(this.diaVencimiento, (long) ((cal.get(Calendar.YEAR) * 10000) + ((cal.get(Calendar.MONTH) + 1) * 100) + cal.get(Calendar.DAY_OF_MONTH)));
		}
	}

	public Long getDiaVencimiento() throws ExcepcionControlada {
		return getLong(diaVencimiento);
	}

	public Long getSemanaVencimiento() throws ExcepcionControlada {
		return getLong(semanaVencimiento);
	}

	public Long getMesVencimiento() throws ExcepcionControlada {
		return getLong(mesVencimiento);
	}

	public Long getAnioVencimiento() throws ExcepcionControlada {
		return getLong(anioVencimiento);
	}

	public Long getSemana() throws ExcepcionControlada {
		return getLong(semana);
	}

	public void setSemana(Long semana) throws ExcepcionControlada {
		setLong(this.semana, semana);
	}

}