package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerUsuario;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbUsuario extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo usuario = new ClsCampo("usuario", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo password = new ClsCampo("password", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo permisos = new ClsCampo("permisos", Tipo.ArrayLong, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo talleres = new ClsCampo("talleres", Tipo.ArrayString, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo sesion = new ClsCampo("sesion", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo vigencia = new ClsCampo("vigencia", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, "0", 0, NO_SUSTITUIR_NULL);

	public DbUsuario(SerUsuario serUsuario) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbUsuario", serUsuario.getEmpresa() + "-" + serUsuario.getUsuario());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serUsuario.getEmpresa());
		setString(usuario, serUsuario.getUsuario());
		setPassword(serUsuario.getPassword());
		setPermisos(serUsuario.getPermisos());
		setSesion("");
		setVigencia(0);
	}

	public DbUsuario(Entity entidad) throws Exception {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, usuario, password, permisos, talleres, sesion, vigencia);
	}

	public SerUsuario toSerUsuario() throws ExcepcionControlada {
		return new SerUsuario(getEmpresa(), getUsuario(), getPassword(), getPermisos(), getTalleres(), getSesion(), getVigencia());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getUsuario() throws ExcepcionControlada {
		return getString(usuario);
	}

	public String getPassword() throws ExcepcionControlada {
		return getString(password);
	}

	public void setPassword(String password) throws ExcepcionControlada {
		setString(this.password, password);
	}

	public long[] getPermisos() throws ExcepcionControlada {
		ArrayList<Long> lstPermisos = getArrayLong(this.permisos);
		if (lstPermisos == null) {
			return null;
		} else {
			long[] arr = new long[lstPermisos.size()];
			for (int i = 0; i < lstPermisos.size(); i++)
				arr[i] = lstPermisos.get(i);
			return arr;
		}
	}

	public void setPermisos(long[] permisos) throws ExcepcionControlada {
		if (permisos == null || permisos.length == 0) {
			setArrayLong(this.permisos, null);
		} else {
			ArrayList<Long> lstPermisos = new ArrayList<Long>();
			for (long permiso : permisos) {
				lstPermisos.add(permiso);
			}
			setArrayLong(this.permisos, lstPermisos);
		}
	}

	public String[] getTalleres() throws ExcepcionControlada {
		ArrayList<String> lstTalleres = getArrayString(this.talleres);
		if (lstTalleres == null) {
			return null;
		} else {
			String[] arr = new String[lstTalleres.size()];
			for (int i = 0; i < lstTalleres.size(); i++)
				arr[i] = lstTalleres.get(i);
			return arr;
		}
	}

	public void setTalleres(String[] talleres) throws ExcepcionControlada {
		if (talleres == null || talleres.length == 0) {
			setArrayString(this.talleres, null);
		} else {
			ArrayList<String> lstTalleres = new ArrayList<String>();
			for (String taller : talleres) {
				lstTalleres.add(taller);
			}
			setArrayString(this.talleres, lstTalleres);
		}
	}

	public String getSesion() throws ExcepcionControlada {
		return getString(sesion);
	}

	public void setSesion(String sesion) throws ExcepcionControlada {
		setString(this.sesion, sesion);
	}

	public long getVigencia() throws ExcepcionControlada {
		return getLong(vigencia);
	}

	public void setVigencia(long vigencia) throws ExcepcionControlada {
		setLong(this.vigencia, vigencia);
	}
}