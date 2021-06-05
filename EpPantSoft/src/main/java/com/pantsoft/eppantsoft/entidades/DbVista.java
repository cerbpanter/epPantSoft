package com.pantsoft.eppantsoft.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerVista;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbVista extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo vista = new ClsCampo("vista", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo permisos = new ClsCampo("permisos", Tipo.ArrayLong, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

	public DbVista(SerVista serVista) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbVista", serVista.getEmpresa() + "-" + serVista.getVista());
		entidad = new Entity(key);
		asignarValoresDefault();
		setString(empresa, serVista.getEmpresa());
		setString(vista, serVista.getVista());
		setPermisos(serVista.getPermisos());
	}

	public DbVista(Entity entidad) throws Exception {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, vista, permisos);
	}

	public SerVista toSerVista() throws ExcepcionControlada {
		return new SerVista(getEmpresa(), getVista(), getPermisos());
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public String getVista() throws ExcepcionControlada {
		return getString(vista);
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
}