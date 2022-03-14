package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbEntidadCampo extends ClsEntidad {
	private final ClsCampo entidad = new ClsCampo("entidad", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo campo = new ClsCampo("campo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo tipo = new ClsCampo("tipo", Tipo.String, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo indexado = new ClsCampo("indexado", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo permiteNull = new ClsCampo("permiteNull", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo largoMinimo = new ClsCampo("largoMinimo", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo largoMaximo = new ClsCampo("largoMaximo", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo entidadGrande = new ClsCampo("entidadGrande", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo valorDefault = new ClsCampo("valorDefault", Tipo.String, NO_INDEXADO, PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo posLlave = new ClsCampo("posLlave", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo sustituirNull = new ClsCampo("sustituirNull", Tipo.Boolean, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbEntidadCampo(String entidad, ClsCampo clsCampo) throws ExcepcionControlada {
		Key key = KeyFactory.createKey("DbEntidadCampo", entidad + "-" + clsCampo.getNombre());
		setEntidad(new Entity(key));
		asignarValoresDefault();
		setString(this.entidad, entidad);
		setString(campo, clsCampo.getNombre());
		setTipo(clsCampo.getTipo().name());
		setIndexado(clsCampo.getIndexado());
		setPermiteNull(clsCampo.getPermiteNull());
		setLargoMinimo(clsCampo.getLargoMinimo());
		setLargoMaximo(clsCampo.getLargoMaximo());
		setEntidadGrande(clsCampo.getEntidadGrande());
		setValorDefault(clsCampo.getValorDefault());
		setPosLlave(clsCampo.getPosLlave());
		setSustituirNull(clsCampo.getSustituirNull());
	}

	public DbEntidadCampo(Entity entidad) {
		setEntidad(entidad);
	}

	public boolean getLiberado() {
		return false;
	}

	public List<ClsCampo> getCampos() {
		return Arrays.asList(entidad, campo, tipo, indexado, permiteNull, largoMinimo, largoMaximo, entidadGrande, valorDefault, posLlave, sustituirNull);
	}

	public ClsCampo toClsCampo() throws ExcepcionControlada {
		return new ClsCampo(getCampo(), Tipo.valueOf(getTipo()), getIndexado(), getPermiteNull(), (int) getLargoMinimo(), (int) getLargoMaximo(), getEntidadGrande(), getValorDefault(), (int) getPosLlave(), getSustituirNull());
	}

	public String getEntidadNombre() throws ExcepcionControlada {
		return getString(entidad);
	}

	public String getCampo() throws ExcepcionControlada {
		return getString(campo);
	}

	public String getTipo() throws ExcepcionControlada {
		return getString(tipo);
	}

	public void setTipo(String tipo) throws ExcepcionControlada {
		setString(this.tipo, tipo);
	}

	public boolean getIndexado() throws ExcepcionControlada {
		return getBoolean(indexado);
	}

	public void setIndexado(boolean indexado) throws ExcepcionControlada {
		setBoolean(this.indexado, indexado);
	}

	public boolean getPermiteNull() throws ExcepcionControlada {
		return getBoolean(permiteNull);
	}

	public void setPermiteNull(boolean permiteNull) throws ExcepcionControlada {
		setBoolean(this.permiteNull, permiteNull);
	}

	public long getLargoMinimo() throws ExcepcionControlada {
		return getLong(largoMinimo);
	}

	public void setLargoMinimo(long largoMinimo) throws ExcepcionControlada {
		setLong(this.largoMinimo, largoMinimo);
	}

	public long getLargoMaximo() throws ExcepcionControlada {
		return getLong(largoMaximo);
	}

	public void setLargoMaximo(long largoMaximo) throws ExcepcionControlada {
		setLong(this.largoMaximo, largoMaximo);
	}

	public boolean getEntidadGrande() throws ExcepcionControlada {
		return getBoolean(entidadGrande);
	}

	public void setEntidadGrande(boolean entidadGrande) throws ExcepcionControlada {
		setBoolean(this.entidadGrande, entidadGrande);
	}

	public String getValorDefault() throws ExcepcionControlada {
		return getString(valorDefault);
	}

	public void setValorDefault(String valorDefault) throws ExcepcionControlada {
		setString(this.valorDefault, valorDefault);
	}

	public long getPosLlave() throws ExcepcionControlada {
		return getLong(posLlave);
	}

	public void setPosLlave(long posLlave) throws ExcepcionControlada {
		setLong(this.posLlave, posLlave);
	}

	public boolean getSustituirNull() throws ExcepcionControlada {
		return getBoolean(sustituirNull);
	}

	public void setSustituirNull(boolean sustituirNull) throws ExcepcionControlada {
		setBoolean(this.sustituirNull, sustituirNull);
	}
}