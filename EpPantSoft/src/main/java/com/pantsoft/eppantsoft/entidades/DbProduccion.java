package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerProduccion;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbProduccion extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo numOrden = new ClsCampo("numOrden", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo maquileroCorte = new ClsCampo("maquileroCorte", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cliente = new ClsCampo("cliente", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo departamento = new ClsCampo("departamento", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo descripcion = new ClsCampo("descripcion", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo estatus = new ClsCampo("estatus", Tipo.Rating, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaProgramada = new ClsCampo("fechaProgramada", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidad = new ClsCampo("cantidad", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo corteSobreTela = new ClsCampo("corteSobreTela", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidadCorte = new ClsCampo("cantidadCorte", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo cantidadEntrega = new ClsCampo("cantidadEntrega", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo faltanteMaquilero = new ClsCampo("faltanteMaquilero", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo faltanteCorte = new ClsCampo("faltanteCorte", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo taller = new ClsCampo("taller", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precio = new ClsCampo("precio", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo costura = new ClsCampo("costura", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo total = new ClsCampo("total", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precioFaltante = new ClsCampo("precioFaltante", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo totalPorPagar = new ClsCampo("totalPorPagar", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaSalida = new ClsCampo("fechaSalida", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaEntrega = new ClsCampo("fechaEntrega", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo proceso1 = new ClsCampo("proceso1", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tallerProceso1 = new ClsCampo("tallerProceso1", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precioProceso1 = new ClsCampo("precioProceso1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo proceso2 = new ClsCampo("proceso2", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tallerProceso2 = new ClsCampo("tallerProceso2", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo precioProceso2 = new ClsCampo("precioProceso2", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo consumo1 = new ClsCampo("consumo1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsSolicitados1 = new ClsCampo("mtsSolicitados1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsEnviados1 = new ClsCampo("mtsEnviados1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsDevolucion1 = new ClsCampo("mtsDevolucion1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsFaltante1 = new ClsCampo("mtsFaltante1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo diferencia1 = new ClsCampo("diferencia1", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo consumo2 = new ClsCampo("consumo2", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsSolicitados2 = new ClsCampo("mtsSolicitados2", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsEnviados2 = new ClsCampo("mtsEnviados2", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo diferencia2 = new ClsCampo("diferencia2", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo consumo3 = new ClsCampo("consumo3", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsSolicitados3 = new ClsCampo("mtsSolicitados3", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo mtsEnviados3 = new ClsCampo("mtsEnviados3", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo diferencia3 = new ClsCampo("diferencia3", Tipo.Double, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);
	private final ClsCampo observaciones = new ClsCampo("observaciones", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo revisado = new ClsCampo("revisado", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);
	private final ClsCampo isaac = new ClsCampo("isaac", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 3, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo usuarioModifico = new ClsCampo("usuarioModifico", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "Sin usuario", 0, SUSTITUIR_NULL);
	private final ClsCampo fechaModifico = new ClsCampo("fechaModifico", Tipo.Date, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_HOY, 0, SUSTITUIR_NULL);
	private final ClsCampo habilitacionEnviada = new ClsCampo("habilitacionEnviada", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);

	public DbProduccion(SerProduccion serProduccion) throws Exception {
		Key key = KeyFactory.createKey("DbProduccion", serProduccion.getEmpresa() + "-" + serProduccion.getTemporada() + "-" + serProduccion.getNumOrden());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serProduccion.getEmpresa());
		setLong(temporada, serProduccion.getTemporada());
		if (serProduccion.getNumOrden() <= 0)
			throw new Exception("El número de orden debe ser mayor a cero");

		setLong(numOrden, serProduccion.getNumOrden());
		setMaquileroCorte(serProduccion.getMaquileroCorte());
		setCliente(serProduccion.getCliente());
		setDepartamento(serProduccion.getDepartamento());
		setDescripcion(serProduccion.getDescripcion());
		setEstatus(serProduccion.getEstatus());
		setFechaProgramada(serProduccion.getFechaProgramada());
		setModelo(serProduccion.getModelo());
		setReferencia(serProduccion.getReferencia());
		setCantidad(serProduccion.getCantidad());
		setCorteSobreTela(serProduccion.getCorteSobreTela());
		setCantidadCorte(serProduccion.getCantidadCorte());
		setCantidadEntrega(serProduccion.getCantidadEntrega());
		setFaltanteMaquilero(serProduccion.getFaltanteMaquilero());
		setFaltanteCorte(serProduccion.getFaltanteCorte());
		setTaller(serProduccion.getTaller());
		setPrecio(serProduccion.getPrecio());
		setTotal(serProduccion.getTotal());
		setPrecioFaltante(serProduccion.getPrecioFaltante());
		setTotalPorPagar(serProduccion.getTotalPorPagar());
		setFechaSalida(serProduccion.getFechaSalida());
		setFechaEntrega(serProduccion.getFechaEntrega());
		setProceso1(serProduccion.getProceso1());
		setTallerProceso1(serProduccion.getTallerProceso1());
		setPrecioProceso1(serProduccion.getPrecioProceso1());
		setProceso2(serProduccion.getProceso2());
		setTallerProceso2(serProduccion.getTallerProceso2());
		setPrecioProceso2(serProduccion.getPrecioProceso2());
		setConsumo1(serProduccion.getConsumo1());
		setMtsSolicitados1(serProduccion.getMtsSolicitados1());
		setMtsEnviados1(serProduccion.getMtsEnviados1());
		setMtsDevolucion1(serProduccion.getMtsDevolucion1());
		setMtsFaltante1(serProduccion.getMtsFaltante1());
		setDiferencia1(serProduccion.getDiferencia1());
		setConsumo2(serProduccion.getConsumo2());
		setMtsSolicitados2(serProduccion.getMtsSolicitados2());
		setMtsEnviados2(serProduccion.getMtsEnviados2());
		setDiferencia2(serProduccion.getDiferencia2());
		setConsumo3(serProduccion.getConsumo3());
		setMtsSolicitados3(serProduccion.getMtsSolicitados3());
		setMtsEnviados3(serProduccion.getMtsEnviados3());
		setDiferencia3(serProduccion.getDiferencia3());
		setObservaciones(serProduccion.getObservaciones());
		setRevisado(serProduccion.getRevisado());
		setIsaac(serProduccion.getIsaac());
		setMes(serProduccion.getMes());
		setUsuarioModifico(serProduccion.getUsuarioModifico());
		setFechaModifico(new Date());
		setHabilitacionEnviada(serProduccion.getHabilitacionEnviada());
	}

	public DbProduccion(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, numOrden, maquileroCorte, cliente, departamento, descripcion, estatus, fechaProgramada, modelo, referencia, cantidad, corteSobreTela, cantidadCorte, cantidadEntrega, faltanteMaquilero, faltanteCorte, taller, precio, costura, total, precioFaltante, totalPorPagar, fechaSalida, fechaEntrega, proceso1, tallerProceso1, precioProceso1, proceso2, tallerProceso2, precioProceso2, consumo1, mtsSolicitados1, mtsEnviados1, mtsDevolucion1, mtsFaltante1, diferencia1, consumo2, mtsSolicitados2, mtsEnviados2, diferencia2, consumo3, mtsSolicitados3, mtsEnviados3, diferencia3, observaciones, revisado, isaac, mes, usuarioModifico, fechaModifico, habilitacionEnviada);
	}

	public SerProduccion toSerProduccion() throws ExcepcionControlada {
		return new SerProduccion(getEmpresa(), getTemporada(), getNumOrden(), getMaquileroCorte(), getCliente(), getDepartamento(), getDescripcion(), getEstatus(), getFechaProgramada(), getModelo(), getReferencia(), getCantidad(), getCorteSobreTela(), getCantidadCorte(), getCantidadEntrega(), getFaltanteMaquilero(), getFaltanteCorte(), getTaller(), getPrecio(), getCostura(), getTotal(), getPrecioFaltante(), getTotalPorPagar(), getFechaSalida(), getFechaEntrega(), getProceso1(), getTallerProceso1(), getPrecioProceso1(), getProceso2(), getTallerProceso2(), getPrecioProceso2(), getConsumo1(), getMtsSolicitados1(), getMtsEnviados1(), getMtsDevolucion1(), getMtsFaltante1(), getDiferencia1(), getConsumo2(), getMtsSolicitados2(), getMtsEnviados2(), getDiferencia2(), getConsumo3(), getMtsSolicitados3(), getMtsEnviados3(), getDiferencia3(), getObservaciones(), getRevisado(), getIsaac(), getMes(), getUsuarioModifico(), getFechaModifico(), getHabilitacionEnviada());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public Long getNumOrden() throws ExcepcionControlada {
		return getLong(numOrden);
	}

	public String getMaquileroCorte() throws ExcepcionControlada {
		return getString(maquileroCorte);
	}

	public void setMaquileroCorte(String maquileroCorte) throws ExcepcionControlada {
		setString(this.maquileroCorte, maquileroCorte);
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

	public String getDescripcion() throws ExcepcionControlada {
		return getString(descripcion);
	}

	public void setDescripcion(String descripcion) throws ExcepcionControlada {
		setString(this.descripcion, descripcion);
	}

	public int getEstatus() throws ExcepcionControlada {
		return getRating(estatus);
	}

	public void setEstatus(int estatus) throws ExcepcionControlada {
		setRating(this.estatus, estatus);
	}

	public Date getFechaProgramada() throws ExcepcionControlada {
		return getDate(fechaProgramada);
	}

	public void setFechaProgramada(Date fechaProgramada) throws ExcepcionControlada {
		setDate(this.fechaProgramada, fechaProgramada);
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

	public Long getCantidad() throws ExcepcionControlada {
		return getLong(cantidad);
	}

	public void setCantidad(Long cantidad) throws ExcepcionControlada {
		setLong(this.cantidad, cantidad);
	}

	public Long getCorteSobreTela() throws ExcepcionControlada {
		return getLong(corteSobreTela);
	}

	public void setCorteSobreTela(Long corteSobreTela) throws ExcepcionControlada {
		setLong(this.corteSobreTela, corteSobreTela);
	}

	public Long getCantidadCorte() throws ExcepcionControlada {
		return getLong(cantidadCorte);
	}

	public void setCantidadCorte(Long cantidadCorte) throws ExcepcionControlada {
		setLong(this.cantidadCorte, cantidadCorte);
	}

	public Long getCantidadEntrega() throws ExcepcionControlada {
		return getLong(cantidadEntrega);
	}

	public void setCantidadEntrega(Long cantidadEntrega) throws ExcepcionControlada {
		setLong(this.cantidadEntrega, cantidadEntrega);
	}

	public Long getFaltanteMaquilero() throws ExcepcionControlada {
		return getLong(faltanteMaquilero);
	}

	public void setFaltanteMaquilero(Long faltanteMaquilero) throws ExcepcionControlada {
		setLong(this.faltanteMaquilero, faltanteMaquilero);
	}

	public Long getFaltanteCorte() throws ExcepcionControlada {
		return getLong(faltanteCorte);
	}

	public void setFaltanteCorte(Long faltanteCorte) throws ExcepcionControlada {
		setLong(this.faltanteCorte, faltanteCorte);
	}

	public String getTaller() throws ExcepcionControlada {
		return getString(taller);
	}

	public void setTaller(String taller) throws ExcepcionControlada {
		setString(this.taller, taller);
	}

	public Double getPrecio() throws ExcepcionControlada {
		return getDouble(precio);
	}

	public void setPrecio(Double precio) throws ExcepcionControlada {
		setDouble(this.precio, precio);
	}

	public Double getCostura() throws ExcepcionControlada {
		return getDouble(costura);
	}

	public void setCostura(Double costura) throws ExcepcionControlada {
		setDouble(this.costura, costura);
	}

	public Double getTotal() throws ExcepcionControlada {
		return getDouble(total);
	}

	public void setTotal(Double total) throws ExcepcionControlada {
		setDouble(this.total, total);
	}

	public Double getPrecioFaltante() throws ExcepcionControlada {
		return getDouble(precioFaltante);
	}

	public void setPrecioFaltante(Double precioFaltante) throws ExcepcionControlada {
		setDouble(this.precioFaltante, precioFaltante);
	}

	public Double getTotalPorPagar() throws ExcepcionControlada {
		return getDouble(totalPorPagar);
	}

	public void setTotalPorPagar(Double totalPorPagar) throws ExcepcionControlada {
		setDouble(this.totalPorPagar, totalPorPagar);
	}

	public Date getFechaSalida() throws ExcepcionControlada {
		return getDate(fechaSalida);
	}

	public void setFechaSalida(Date fechaSalida) throws ExcepcionControlada {
		setDate(this.fechaSalida, fechaSalida);
	}

	public Date getFechaEntrega() throws ExcepcionControlada {
		return getDate(fechaEntrega);
	}

	public void setFechaEntrega(Date fechaEntrega) throws ExcepcionControlada {
		setDate(this.fechaEntrega, fechaEntrega);
	}

	public String getProceso1() throws ExcepcionControlada {
		return getString(proceso1);
	}

	public void setProceso1(String proceso1) throws ExcepcionControlada {
		setString(this.proceso1, proceso1);
	}

	public String getTallerProceso1() throws ExcepcionControlada {
		return getString(tallerProceso1);
	}

	public void setTallerProceso1(String tallerProceso1) throws ExcepcionControlada {
		setString(this.tallerProceso1, tallerProceso1);
	}

	public Double getPrecioProceso1() throws ExcepcionControlada {
		return getDouble(precioProceso1);
	}

	public void setPrecioProceso1(Double precioProceso1) throws ExcepcionControlada {
		setDouble(this.precioProceso1, precioProceso1);
	}

	public String getProceso2() throws ExcepcionControlada {
		return getString(proceso2);
	}

	public void setProceso2(String proceso2) throws ExcepcionControlada {
		setString(this.proceso2, proceso2);
	}

	public String getTallerProceso2() throws ExcepcionControlada {
		return getString(tallerProceso2);
	}

	public void setTallerProceso2(String tallerProceso2) throws ExcepcionControlada {
		setString(this.tallerProceso2, tallerProceso2);
	}

	public Double getPrecioProceso2() throws ExcepcionControlada {
		return getDouble(precioProceso2);
	}

	public void setPrecioProceso2(Double precioProceso2) throws ExcepcionControlada {
		setDouble(this.precioProceso2, precioProceso2);
	}

	public Double getConsumo1() throws ExcepcionControlada {
		return getDouble(consumo1);
	}

	public void setConsumo1(Double consumo1) throws ExcepcionControlada {
		setDouble(this.consumo1, consumo1);
	}

	public Double getMtsSolicitados1() throws ExcepcionControlada {
		return getDouble(mtsSolicitados1);
	}

	public void setMtsSolicitados1(Double mtsSolicitados1) throws ExcepcionControlada {
		setDouble(this.mtsSolicitados1, mtsSolicitados1);
	}

	public Double getMtsEnviados1() throws ExcepcionControlada {
		return getDouble(mtsEnviados1);
	}

	public void setMtsEnviados1(Double mtsEnviados1) throws ExcepcionControlada {
		setDouble(this.mtsEnviados1, mtsEnviados1);
	}

	public Double getMtsDevolucion1() throws ExcepcionControlada {
		return getDouble(mtsDevolucion1);
	}

	public void setMtsDevolucion1(Double mtsDevolucion1) throws ExcepcionControlada {
		setDouble(this.mtsDevolucion1, mtsDevolucion1);
	}

	public Double getMtsFaltante1() throws ExcepcionControlada {
		return getDouble(mtsFaltante1);
	}

	public void setMtsFaltante1(Double mtsFaltante1) throws ExcepcionControlada {
		setDouble(this.mtsFaltante1, mtsFaltante1);
	}

	public Double getDiferencia1() throws ExcepcionControlada {
		return getDouble(diferencia1);
	}

	public void setDiferencia1(Double diferencia1) throws ExcepcionControlada {
		setDouble(this.diferencia1, diferencia1);
	}

	public Double getConsumo2() throws ExcepcionControlada {
		return getDouble(consumo2);
	}

	public void setConsumo2(Double consumo2) throws ExcepcionControlada {
		setDouble(this.consumo2, consumo2);
	}

	public Double getMtsSolicitados2() throws ExcepcionControlada {
		return getDouble(mtsSolicitados2);
	}

	public void setMtsSolicitados2(Double mtsSolicitados2) throws ExcepcionControlada {
		setDouble(this.mtsSolicitados2, mtsSolicitados2);
	}

	public Double getMtsEnviados2() throws ExcepcionControlada {
		return getDouble(mtsEnviados2);
	}

	public void setMtsEnviados2(Double mtsEnviados2) throws ExcepcionControlada {
		setDouble(this.mtsEnviados2, mtsEnviados2);
	}

	public Double getDiferencia2() throws ExcepcionControlada {
		return getDouble(diferencia2);
	}

	public void setDiferencia2(Double diferencia2) throws ExcepcionControlada {
		setDouble(this.diferencia2, diferencia2);
	}

	public Double getConsumo3() throws ExcepcionControlada {
		return getDouble(consumo3);
	}

	public void setConsumo3(Double consumo3) throws ExcepcionControlada {
		setDouble(this.consumo3, consumo3);
	}

	public Double getMtsSolicitados3() throws ExcepcionControlada {
		return getDouble(mtsSolicitados3);
	}

	public void setMtsSolicitados3(Double mtsSolicitados3) throws ExcepcionControlada {
		setDouble(this.mtsSolicitados3, mtsSolicitados3);
	}

	public Double getMtsEnviados3() throws ExcepcionControlada {
		return getDouble(mtsEnviados3);
	}

	public void setMtsEnviados3(Double mtsEnviados3) throws ExcepcionControlada {
		setDouble(this.mtsEnviados3, mtsEnviados3);
	}

	public Double getDiferencia3() throws ExcepcionControlada {
		return getDouble(diferencia3);
	}

	public void setDiferencia3(Double diferencia3) throws ExcepcionControlada {
		setDouble(this.diferencia3, diferencia3);
	}

	public String getObservaciones() throws ExcepcionControlada {
		return getText(observaciones);
	}

	public void setObservaciones(String observaciones) throws ExcepcionControlada {
		setText(this.observaciones, observaciones);
	}

	public boolean getRevisado() throws ExcepcionControlada {
		return getBoolean(revisado);
	}

	public void setRevisado(boolean revisado) throws ExcepcionControlada {
		setBoolean(this.revisado, revisado);
	}

	public boolean getIsaac() throws ExcepcionControlada {
		return getBoolean(isaac);
	}

	public void setIsaac(boolean isaac) throws ExcepcionControlada {
		setBoolean(this.isaac, isaac);
	}

	public String getMes() throws ExcepcionControlada {
		return getString(mes);
	}

	public void setMes(String mes) throws ExcepcionControlada {
		setString(this.mes, mes);
	}

	public String getUsuarioModifico() throws ExcepcionControlada {
		return getString(usuarioModifico);
	}

	public void setUsuarioModifico(String usuarioModifico) throws ExcepcionControlada {
		setString(this.usuarioModifico, usuarioModifico);
	}

	public Date getFechaModifico() throws ExcepcionControlada {
		return getDate(fechaModifico);
	}

	public void setFechaModifico(Date fechaModifico) throws ExcepcionControlada {
		setDate(this.fechaModifico, fechaModifico);
	}

	public boolean getHabilitacionEnviada() throws ExcepcionControlada {
		return getBoolean(habilitacionEnviada);
	}

	public void setHabilitacionEnviada(boolean habilitacionEnviada) throws ExcepcionControlada {
		setBoolean(this.habilitacionEnviada, habilitacionEnviada);
	}
}