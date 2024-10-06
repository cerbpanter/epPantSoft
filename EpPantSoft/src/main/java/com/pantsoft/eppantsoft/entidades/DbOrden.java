package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.serializable.SerOrden;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbOrden extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo folioOrden = new ClsCampo("folioOrden", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo folioPedido = new ClsCampo("folioPedido", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo renglonPedido = new ClsCampo("renglonPedido", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo revisado = new ClsCampo("revisado", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	// Diseño
	private final ClsCampo usuarioDiseno = new ClsCampo("usuarioDiseno", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaDiseno = new ClsCampo("fechaDiseno", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo disenoTerminado = new ClsCampo("disenoTerminado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);
	private final ClsCampo carpetaTrazo = new ClsCampo("carpetaTrazo", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo piezasMolde = new ClsCampo("piezasMolde", Tipo.Long, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo bies = new ClsCampo("bies", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo prioridadDiseno = new ClsCampo("prioridadDiseno", Tipo.Long, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	// Trazo
	private final ClsCampo usuarioTrazo = new ClsCampo("usuarioTrazo", Tipo.String, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo fechaTrazo = new ClsCampo("fechaTrazo", Tipo.Date, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo trazoTerminado = new ClsCampo("trazoTerminado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);
	private final ClsCampo trazos = new ClsCampo("trazos", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo prioridadTrazo = new ClsCampo("prioridadTrazo", Tipo.Long, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo habilitacion = new ClsCampo("habilitacion", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, SUSTITUIR_NULL);
	// Telas
	private final ClsCampo entregasTela = new ClsCampo("entregasTela", Tipo.Text, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, SUSTITUIR_NULL);
	private final ClsCampo estatusTela = new ClsCampo("estatusTela", Tipo.Rating, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, SUSTITUIR_NULL, "0:Sin entregas,1:Entregas parciales,2:Terminado");

	// Dependencias
	private List<DbOrdenProceso> procesos = null;

	public DbOrden(SerOrden serOrden) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serOrden.getEmpresa());
		setLong(folioOrden, serOrden.getFolioOrden());
		setTemporada(serOrden.getTemporada());
		setFolioPedido(serOrden.getFolioPedido());
		setRenglonPedido(serOrden.getRenglonPedido());
		setModelo(serOrden.getModelo());
		setReferencia(serOrden.getReferencia());
		setRevisado(false);
		setUsuarioDiseno(serOrden.getUsuarioDiseno());
		setUsuarioTrazo(serOrden.getUsuarioTrazo());
	}

	public DbOrden(Entity entidad) throws Exception {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, folioOrden, temporada, folioPedido, renglonPedido, modelo, referencia, revisado, usuarioDiseno, fechaDiseno, disenoTerminado, carpetaTrazo, piezasMolde, bies, prioridadDiseno, usuarioTrazo, fechaTrazo, trazoTerminado, trazos, prioridadTrazo, habilitacion, entregasTela, estatusTela);
	}

	public SerOrden toSerOrden(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		SerOrden serOrden = new SerOrden(getEmpresa(), getFolioOrden(), getTemporada(), getFolioPedido(), getRenglonPedido(), getModelo(), getReferencia(), getRevisado(), getUsuarioDiseno(), getFechaDiseno(), getDisenoTerminado(), getUsuarioTrazo(), getFechaTrazo(), getTrazoTerminado());

		// Agrego los procesos
		// getProcesos(datastore, tx);
		// if (procesos != null & procesos.size() > 0) {
		// List<SerOrdenProceso> lstProcesos = new ArrayList<SerOrdenProceso>();
		// for (DbOrdenProceso dbD : procesos)
		// lstProcesos.add(dbD.toSerOrdenProceso());
		// serOrden.setProcesos(lstProcesos.toArray(new SerOrdenProceso[0]));
		// }
		return serOrden;
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getFolioOrden() throws ExcepcionControlada {
		return getLong(folioOrden);
	}

	public Long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public void setTemporada(Long temporada) throws ExcepcionControlada {
		setLong(this.temporada, temporada);
	}

	public Long getFolioPedido() throws ExcepcionControlada {
		return getLong(folioPedido);
	}

	public void setFolioPedido(Long folioPedido) throws ExcepcionControlada {
		setLong(this.folioPedido, folioPedido);
	}

	public Long getRenglonPedido() throws ExcepcionControlada {
		return getLong(renglonPedido);
	}

	public void setRenglonPedido(Long renglonPedido) throws ExcepcionControlada {
		setLong(this.renglonPedido, renglonPedido);
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

	public Boolean getRevisado() throws ExcepcionControlada {
		return getBoolean(revisado);
	}

	public void setRevisado(boolean revisado) throws ExcepcionControlada {
		setBoolean(this.revisado, revisado);
	}

	public String getUsuarioDiseno() throws ExcepcionControlada {
		return getString(usuarioDiseno);
	}

	public void setUsuarioDiseno(String usuarioDiseno) throws ExcepcionControlada {
		setString(this.usuarioDiseno, usuarioDiseno);
	}

	public Date getFechaDiseno() throws ExcepcionControlada {
		return getDate(fechaDiseno);
	}

	public void setFechaDiseno(Date fechaDiseno) throws ExcepcionControlada {
		setDate(this.fechaDiseno, fechaDiseno);
	}

	public boolean getDisenoTerminado() throws ExcepcionControlada {
		return getBoolean(disenoTerminado);
	}

	public void setDisenoTerminado(boolean disenoTerminado) throws ExcepcionControlada {
		setBoolean(this.disenoTerminado, disenoTerminado);
	}

	public String getCarpetaTrazo() throws ExcepcionControlada {
		return getString(carpetaTrazo);
	}

	public void setCarpetaTrazo(String carpetaTrazo) throws ExcepcionControlada {
		setString(this.carpetaTrazo, carpetaTrazo);
	}

	public Long getPiezasMolde() throws ExcepcionControlada {
		return getLong(piezasMolde);
	}

	public void setPiezasMolde(Long piezasMolde) throws ExcepcionControlada {
		setLong(this.piezasMolde, piezasMolde);
	}

	public String getBies() throws ExcepcionControlada {
		return getText(bies);
	}

	public void setBies(String bies) throws ExcepcionControlada {
		setText(this.bies, bies);
	}

	public Long getPrioridadDiseno() throws ExcepcionControlada {
		return getLong(prioridadDiseno);
	}

	public void setPrioridadDiseno(Long rioridadDiseno) throws ExcepcionControlada {
		setLong(this.prioridadDiseno, rioridadDiseno);
	}

	public String getUsuarioTrazo() throws ExcepcionControlada {
		return getString(usuarioTrazo);
	}

	public void setUsuarioTrazo(String usuarioTrazo) throws ExcepcionControlada {
		setString(this.usuarioTrazo, usuarioTrazo);
	}

	public Date getFechaTrazo() throws ExcepcionControlada {
		return getDate(fechaTrazo);
	}

	public void setFechaTrazo(Date fechaTrazo) throws ExcepcionControlada {
		setDate(this.fechaTrazo, fechaTrazo);
	}

	public boolean getTrazoTerminado() throws ExcepcionControlada {
		return getBoolean(trazoTerminado);
	}

	public void setTrazoTerminado(boolean trazoTerminado) throws ExcepcionControlada {
		setBoolean(this.trazoTerminado, trazoTerminado);
	}

	public String getTrazos() throws ExcepcionControlada {
		return getText(trazos);
	}

	public void setTrazos(String trazos) throws ExcepcionControlada {
		setText(this.trazos, trazos);
	}

	public Long getPrioridadTrazo() throws ExcepcionControlada {
		return getLong(prioridadTrazo);
	}

	public void setPrioridadTrazo(Long rioridadTrazo) throws ExcepcionControlada {
		setLong(this.prioridadTrazo, rioridadTrazo);
	}

	public boolean getHabilitacion() throws ExcepcionControlada {
		return getBoolean(habilitacion);
	}

	public void setHabilitacion(boolean habilitacion) throws ExcepcionControlada {
		setBoolean(this.habilitacion, habilitacion);
	}

	public String getEntregasTela() throws ExcepcionControlada {
		return getText(entregasTela);
	}

	public void setEntregasTela(String entregasTela) throws ExcepcionControlada {
		setText(this.entregasTela, entregasTela);
	}

	public int getEstatusTela() throws ExcepcionControlada {
		return getRating(estatusTela);
	}

	public void setEstatusTela(int estatusTela) throws ExcepcionControlada {
		setRating(this.estatusTela, estatusTela);
	}

	public List<DbOrdenProceso> getProcesos(DatastoreService datastore, Transaction tx) throws Exception {
		if (procesos == null && datastore != null) {
			List<Entity> lstProcesos = ejecutarConsulta(datastore, tx, "DbOrdenProceso", getKey());
			procesos = new ArrayList<DbOrdenProceso>();
			for (Entity proceso : lstProcesos)
				procesos.add(new DbOrdenProceso(proceso));
		}
		return procesos;
	}

}