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
	private final ClsCampo almacenes = new ClsCampo("almacenes", Tipo.ArrayString, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tiposEntrada = new ClsCampo("tiposEntrada", Tipo.ArrayLong, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo tiposSalida = new ClsCampo("tiposSalida", Tipo.ArrayLong, INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_NULL, 0, NO_SUSTITUIR_NULL);

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
		return Arrays.asList(empresa, usuario, password, permisos, talleres, sesion, vigencia, almacenes, tiposEntrada, tiposSalida);
	}

	public SerUsuario toSerUsuario() throws ExcepcionControlada {
		SerUsuario serUsuario = new SerUsuario(getEmpresa(), getUsuario(), getPassword(), getPermisos(), getTalleres(), getSesion(), getVigencia());
		serUsuario.setAlmacenes(getAlmacenes());
		serUsuario.setTiposEntrada(getTiposEntrada());
		serUsuario.setTiposSalida(getTiposSalida());
		return serUsuario;
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

	public String[] getAlmacenes() throws ExcepcionControlada {
		ArrayList<String> lstAlmacenes = getArrayString(this.almacenes);
		if (lstAlmacenes == null) {
			return null;
		} else {
			String[] arr = new String[lstAlmacenes.size()];
			for (int i = 0; i < lstAlmacenes.size(); i++)
				arr[i] = lstAlmacenes.get(i);
			return arr;
		}
	}

	public void setAlmacenes(String[] almacenes) throws ExcepcionControlada {
		if (almacenes == null || almacenes.length == 0) {
			setArrayString(this.almacenes, null);
		} else {
			ArrayList<String> lstAlmacenes = new ArrayList<String>();
			for (String almacen : almacenes) {
				lstAlmacenes.add(almacen);
			}
			setArrayString(this.almacenes, lstAlmacenes);
		}
	}

	public long[] getTiposEntrada() throws ExcepcionControlada {
		ArrayList<Long> lstTipos = getArrayLong(this.tiposEntrada);
		if (lstTipos == null) {
			return null;
		} else {
			long[] arr = new long[lstTipos.size()];
			for (int i = 0; i < lstTipos.size(); i++)
				arr[i] = lstTipos.get(i);
			return arr;
		}
	}

	public void setTipoEntrada(long[] tipos) throws ExcepcionControlada {
		if (tipos == null || tipos.length == 0) {
			setArrayLong(this.tiposEntrada, null);
		} else {
			ArrayList<Long> lstTipos = new ArrayList<Long>();
			for (long tipo : tipos) {
				lstTipos.add(tipo);
			}
			setArrayLong(this.tiposEntrada, lstTipos);
		}
	}

	public long[] getTiposSalida() throws ExcepcionControlada {
		ArrayList<Long> lstTipos = getArrayLong(this.tiposSalida);
		if (lstTipos == null) {
			return null;
		} else {
			long[] arr = new long[lstTipos.size()];
			for (int i = 0; i < lstTipos.size(); i++)
				arr[i] = lstTipos.get(i);
			return arr;
		}
	}

	public void setTipoSalida(long[] tipos) throws ExcepcionControlada {
		if (tipos == null || tipos.length == 0) {
			setArrayLong(this.tiposSalida, null);
		} else {
			ArrayList<Long> lstTipos = new ArrayList<Long>();
			for (long tipo : tipos) {
				lstTipos.add(tipo);
			}
			setArrayLong(this.tiposSalida, lstTipos);
		}
	}

}