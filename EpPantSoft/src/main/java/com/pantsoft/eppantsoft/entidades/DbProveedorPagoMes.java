package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.serializable.SerProveedorPagoMes;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbProveedorPagoMes extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo mes = new ClsCampo("mes", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo autorizado1 = new ClsCampo("autorizado1", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo autorizado2 = new ClsCampo("autorizado2", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo autorizado3 = new ClsCampo("autorizado3", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo autorizado4 = new ClsCampo("autorizado4", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo autorizado5 = new ClsCampo("autorizado5", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo terminado = new ClsCampo("terminado", Tipo.Boolean, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_FALSE, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo titulo1 = new ClsCampo("titulo1", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo titulo2 = new ClsCampo("titulo2", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo titulo3 = new ClsCampo("titulo3", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo titulo4 = new ClsCampo("titulo4", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo titulo5 = new ClsCampo("titulo5", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

	// Dependencias
	private List<DbProveedorPago> pagos = null;

	public DbProveedorPagoMes(SerProveedorPagoMes serProveedorPagoMes) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbProveedorPagoMes", serProveedorPagoMes.getEmpresa() + "-" + serProveedorPagoMes.getMes());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serProveedorPagoMes.getEmpresa());
		setLong(mes, serProveedorPagoMes.getMes());
		setAutorizado1(serProveedorPagoMes.getAutorizado1());
		setAutorizado2(serProveedorPagoMes.getAutorizado2());
		setAutorizado3(serProveedorPagoMes.getAutorizado3());
		setAutorizado4(serProveedorPagoMes.getAutorizado4());
		setAutorizado5(serProveedorPagoMes.getAutorizado5());
		setTerminado(serProveedorPagoMes.getTerminado());
		setTitulo1(serProveedorPagoMes.getTitulo1());
		setTitulo2(serProveedorPagoMes.getTitulo2());
		setTitulo3(serProveedorPagoMes.getTitulo3());
		setTitulo4(serProveedorPagoMes.getTitulo4());
		setTitulo5(serProveedorPagoMes.getTitulo5());
	}

	public DbProveedorPagoMes(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, mes, autorizado1, autorizado2, autorizado3, autorizado4, autorizado5, terminado, titulo1, titulo2, titulo3, titulo4, titulo5);
	}

	public SerProveedorPagoMes toSerProveedorPagoMes() throws ExcepcionControlada {
		return new SerProveedorPagoMes(getEmpresa(), getMes(), getAutorizado1(), getAutorizado2(), getAutorizado3(), getAutorizado4(), getAutorizado5(), getTerminado(), getTitulo1(), getTitulo2(), getTitulo3(), getTitulo4(), getTitulo5());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public Long getMes() throws ExcepcionControlada {
		return getLong(mes);
	}

	public Boolean getAutorizado1() throws ExcepcionControlada {
		return getBoolean(autorizado1);
	}

	public void setAutorizado1(Boolean autorizado1) throws ExcepcionControlada {
		setBoolean(this.autorizado1, autorizado1);
	}

	public Boolean getAutorizado2() throws ExcepcionControlada {
		return getBoolean(autorizado2);
	}

	public void setAutorizado2(Boolean autorizado2) throws ExcepcionControlada {
		setBoolean(this.autorizado2, autorizado2);
	}

	public Boolean getAutorizado3() throws ExcepcionControlada {
		return getBoolean(autorizado3);
	}

	public void setAutorizado3(Boolean autorizado3) throws ExcepcionControlada {
		setBoolean(this.autorizado3, autorizado3);
	}

	public Boolean getAutorizado4() throws ExcepcionControlada {
		return getBoolean(autorizado4);
	}

	public void setAutorizado4(Boolean autorizado4) throws ExcepcionControlada {
		setBoolean(this.autorizado4, autorizado4);
	}

	public Boolean getAutorizado5() throws ExcepcionControlada {
		return getBoolean(autorizado5);
	}

	public void setAutorizado5(Boolean autorizado5) throws ExcepcionControlada {
		setBoolean(this.autorizado5, autorizado5);
	}

	public Boolean getTerminado() throws ExcepcionControlada {
		return getBoolean(terminado);
	}

	public void setTerminado(Boolean terminado) throws ExcepcionControlada {
		setBoolean(this.terminado, terminado);
	}

	public List<DbProveedorPago> getPagos(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		if (pagos == null) {
			List<Entity> lstPagos = ejecutarConsulta(datastore, tx, "DbProveedorPago", getKey());
			pagos = new ArrayList<DbProveedorPago>();
			for (Entity pago : lstPagos)
				pagos.add(new DbProveedorPago(pago));
		}
		return pagos;
	}

	public String getTitulo1() throws ExcepcionControlada {
		return getString(titulo1);
	}

	public void setTitulo1(String titulo1) throws ExcepcionControlada {
		setString(this.titulo1, titulo1);
	}

	public String getTitulo2() throws ExcepcionControlada {
		return getString(titulo2);
	}

	public void setTitulo2(String titulo2) throws ExcepcionControlada {
		setString(this.titulo2, titulo2);
	}

	public String getTitulo3() throws ExcepcionControlada {
		return getString(titulo3);
	}

	public void setTitulo3(String titulo3) throws ExcepcionControlada {
		setString(this.titulo3, titulo3);
	}

	public String getTitulo4() throws ExcepcionControlada {
		return getString(titulo4);
	}

	public void setTitulo4(String titulo4) throws ExcepcionControlada {
		setString(this.titulo4, titulo4);
	}

	public String getTitulo5() throws ExcepcionControlada {
		return getString(titulo5);
	}

	public void setTitulo5(String titulo5) throws ExcepcionControlada {
		setString(this.titulo5, titulo5);
	}

}