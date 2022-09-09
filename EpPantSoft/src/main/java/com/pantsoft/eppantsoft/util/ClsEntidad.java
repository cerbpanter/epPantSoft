package com.pantsoft.eppantsoft.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;

public abstract class ClsEntidad {
	protected Entity entidad;
	static protected String strCursor;

	static protected boolean INDEXADO = true;
	static protected boolean NO_INDEXADO = false;
	static protected boolean PERMITIR_NULL = true;
	static protected boolean NO_PERMITIR_NULL = false;
	static protected boolean TAM_NORMAL = false;
	static protected boolean TAM_GRANDE = true;
	static protected boolean SUSTITUIR_NULL = true;
	static protected boolean NO_SUSTITUIR_NULL = false;
	static protected String VAL_NULL = "<SiNubeNull>";
	static protected String VAL_HOY = "<SiNubeHoy>";
	static protected String VAL_DEFAULT = "<SiNubeDefault>";
	static protected String VAL_TRUE = "true";
	static protected String VAL_FALSE = "false";
	static protected String VAL_MISSING = "<SiNubeMissing>";

	public static String getStrCursor() {
		return strCursor;
	}

	public ClsEntidad() {
	}

	public ClsEntidad(Entity entidad) {
		this.entidad = entidad;
	}

	public abstract List<ClsCampo> getCampos();

	public abstract boolean getLiberado();

	protected void asignarValoresDefault() throws ExcepcionControlada {
		List<ClsCampo> lstCampos = getCampos();
		if (lstCampos == null)
			throw new ExcepcionControlada("getCampos es Nulo");
		asignarValoresDefault(lstCampos);
	}

	protected void asignarValoresDefault(List<ClsCampo> lstCampos) throws ExcepcionControlada {
		String campoAnalizado = "";
		try {
			if (lstCampos == null)
				throw new ExcepcionControlada("lstCampos es Nulo");
			for (ClsCampo campo : lstCampos) {
				campoAnalizado = campo.getNombre();
				if (entidad.hasProperty(campo.getNombre()) && (entidad.getProperty(campo.getNombre()) != null || !campo.getSustituirNull()))
					continue;
				if (campo.getValorDefault().equals(VAL_MISSING))
					continue;
				if (campo.getValorDefault() == null)
					throw new ExcepcionControlada("El campo '" + campo.getNombre() + "' no puede tener valor default Null en lugar debe ser VAL_NULL");
				switch (campo.getTipo()) {
				case String:
					if (campo.getValorDefault().equals(VAL_NULL))
						setString(campo, null);
					else
						setString(campo, campo.getValorDefault());
					break;
				case Boolean:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setBoolean(campo, null);
					} else {
						boolean valor = Boolean.parseBoolean(campo.getValorDefault());
						setBoolean(campo, valor);
					}
					break;
				case Long:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setLong(campo, null);
					} else {
						try {
							long valor = Long.parseLong(campo.getValorDefault());
							setLong(campo, valor);
						} catch (NumberFormatException e) {
							throw new ExcepcionControlada("El valor default '" + campo.getValorDefault() + "' no se puede convertir en Long");
						}
					}
					break;
				case Double:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setDouble(campo, null);
					} else {
						try {
							double valor = Double.parseDouble(campo.getValorDefault());
							setDouble(campo, valor);
						} catch (NumberFormatException e) {
							throw new ExcepcionControlada("El valor default '" + campo.getValorDefault() + "' no se puede convertir en Double");
						}
					}
					break;
				case Date:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setDate(campo, null);
					} else if (campo.getValorDefault().equals(VAL_HOY)) {
						setDate(campo, new Date());
					} else {
						throw new ExcepcionControlada("El valor default '" + campo.getValorDefault() + "' no se puede convertir en Date");
					}
					break;
				case Rating:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setRating(campo, null);
					} else {
						try {
							Long valor = Long.parseLong(campo.getValorDefault());
							setRating(campo, valor.intValue());
						} catch (NumberFormatException e) {
							throw new ExcepcionControlada("El valor default '" + campo.getValorDefault() + "' no se puede convertir en Double");
						}
					}
					break;
				case Text:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setText(campo, null);
					} else {
						setText(campo, campo.getValorDefault());
					}
					break;
				case Blob:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setBlob(campo, null);
					} else {
						setBlob(campo, campo.getValorDefault().getBytes());
					}
					break;
				case ArrayString:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setArrayString(campo, null);
					} else {
						ArrayList<String> lst = new ArrayList<String>();
						lst.add(campo.getValorDefault());
						setArrayString(campo, lst);
					}
					break;
				case ArrayLong:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setArrayLong(campo, null);
					} else {
						ArrayList<Long> lst = new ArrayList<Long>();
						try {
							long valor = Long.parseLong(campo.getValorDefault());
							lst.add(valor);
						} catch (NumberFormatException e) {
							throw new ExcepcionControlada("El valor default '" + campo.getValorDefault() + "' no se puede convertir en ArrayLong");
						}
						setArrayLong(campo, lst);
					}
					break;
				case Email:
					if (campo.getValorDefault().equals(VAL_NULL)) {
						setEmail(campo, null);
					} else {
						setEmail(campo, campo.getValorDefault());
					}
					break;
				}
			}
		} catch (Exception e) {
			throw new ExcepcionControlada(e.getMessage() + " ENTIDAD [" + entidad == null ? "entidad = null" : entidad.getKey() + "] CAMPO [" + campoAnalizado + "] Err: " + e.getMessage());
		}

	}

	public Entity getEntidad() {
		return entidad;
	}

	public void setEntidad(Entity entidad) {
		this.entidad = entidad;
	}

	public Key getKey() {
		return entidad.getKey();
	}

	// Guardado
	public void guardar(DatastoreService datastore) throws ExcepcionControlada {
		List<String> lstCampos = new ArrayList<String>();
		for (ClsCampo campo : getCampos()) {
			if (!entidad.hasProperty(campo.getNombre()))
				throw new ExcepcionControlada("El campo '" + campo.getNombre() + "' es missing, imposible grabar en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (lstCampos.contains(campo.getNombre()))
				throw new ExcepcionControlada("El campo '" + campo.getNombre() + "' existe más de una vez, imposible grabar en " + entidad.getKind() + " > " + entidad.getKey().getName());
			lstCampos.add(campo.getNombre());
		}
		datastore.put(entidad);
	}

	public void guardar(DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		List<String> lstCampos = new ArrayList<String>();
		for (ClsCampo campo : getCampos()) {
			if (!entidad.hasProperty(campo.getNombre()))
				throw new ExcepcionControlada("El campo '" + campo.getNombre() + "' es missing, imposible grabar en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (lstCampos.contains(campo.getNombre()))
				throw new ExcepcionControlada("El campo '" + campo.getNombre() + "' existe más de una vez, imposible grabar en " + entidad.getKind() + " > " + entidad.getKey().getName());
			lstCampos.add(campo.getNombre());
		}
		datastore.put(tx, entidad);
	}

	public void eliminar(DatastoreService datastore) {
		datastore.delete(getKey());
	}

	public void eliminar(DatastoreService datastore, Transaction tx) {
		datastore.delete(tx, getKey());
	}

	// Get por Tipo/Campo
	public Boolean getBoolean(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return (Boolean) entidad.getProperty(campo.getNombre());
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			boolean valor = Boolean.parseBoolean(campo.getValorDefault());
			return valor;
		}
	}

	public Long getLong(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return (Long) entidad.getProperty(campo.getNombre());
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			try {
				long valor = Long.parseLong(campo.getValorDefault());
				return valor;
			} catch (NumberFormatException e) {
				throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Long en " + entidad.getKind() + " > " + entidad.getKey().getName());
			}
		}
	}

	public String getString(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return (String) entidad.getProperty(campo.getNombre());
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			return campo.getValorDefault();
		}
	}

	public Date getDate(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return (Date) entidad.getProperty(campo.getNombre());
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			if (campo.getValorDefault().equals(VAL_HOY))
				return new Date();
			throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Date en " + entidad.getKind() + " > " + entidad.getKey().getName());
		}
	}

	public Double getDouble(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return (Double) entidad.getProperty(campo.getNombre());
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			try {
				double valor = Double.parseDouble(campo.getValorDefault());
				return valor;
			} catch (NumberFormatException e) {
				throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Double en " + entidad.getKind() + " > " + entidad.getKey().getName());
			}
		}
	}

	public Integer getRating(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return ((Rating) entidad.getProperty(campo.getNombre())).getRating();
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			try {
				Long valor = Long.parseLong(campo.getValorDefault());
				if (valor < 0 || valor > 100)
					throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Rating en " + entidad.getKind() + " > " + entidad.getKey().getName());
				return valor.intValue();
			} catch (NumberFormatException e) {
				throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Rating en " + entidad.getKind() + " > " + entidad.getKey().getName());
			}
		}
	}

	public String getText(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return ((Text) entidad.getProperty(campo.getNombre())).getValue();
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			return campo.getValorDefault();
		}
	}

	public byte[] getBlob(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else {
				if (entidad.getProperty(campo.getNombre()) == null)
					return null;
				else
					return ((Blob) entidad.getProperty(campo.getNombre())).getBytes();
			}
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			try {
				Long valor = Long.parseLong(campo.getValorDefault());
				if (valor < 0 || valor > 255)
					throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Blob en " + entidad.getKind() + " > " + entidad.getKey().getName());
				byte[] arr = { valor.byteValue() };
				return arr;
			} catch (NumberFormatException e) {
				throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en Blob en " + entidad.getKind() + " > " + entidad.getKey().getName());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getArrayString(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return ((ArrayList<String>) entidad.getProperty(campo.getNombre()));
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			ArrayList<String> lst = new ArrayList<String>();
			lst.add(campo.getValorDefault());
			return lst;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Long> getArrayLong(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return ((ArrayList<Long>) entidad.getProperty(campo.getNombre()));
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			try {
				Long valor = Long.parseLong(campo.getValorDefault());
				ArrayList<Long> lst = new ArrayList<Long>();
				lst.add(valor);
				return lst;
			} catch (NumberFormatException e) {
				throw new ExcepcionControlada("El valor default del campo '" + campo.getNombre() + "' no se puede convertir en ArrayLong en " + entidad.getKind() + " > " + entidad.getKey().getName());
			}
		}
	}

	public String getEmail(ClsCampo campo) throws ExcepcionControlada {
		if (entidad.hasProperty(campo.getNombre())) {
			if (entidad.getProperty(campo.getNombre()) == null) {
				if (campo.getPermiteNull())
					return null;
				else
					throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es null en " + entidad.getKind() + " > " + entidad.getKey().getName());
			} else
				return ((Email) entidad.getProperty(campo.getNombre())).getEmail();
		} else {
			if (campo.getValorDefault().equals(VAL_MISSING))
				throw new ExcepcionControlada("El valor del campo '" + campo.getNombre() + "' es missing en " + entidad.getKind() + " > " + entidad.getKey().getName());
			if (campo.getValorDefault().equals(VAL_NULL))
				return null;
			return campo.getValorDefault();
		}
	}

	// Set por Tipo/Campo
	public void setBoolean(ClsCampo campo, Boolean valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Boolean)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Boolean");
		if (!campo.getPermiteNull() && valor == null)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setLong(ClsCampo campo, Long valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Long)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Long");
		if (!campo.getPermiteNull() && valor == null)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setString(ClsCampo campo, String valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.String)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo String");
		if (!campo.getPermiteNull() && (valor == null || valor.length() == 0))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío [" + getKey().getKind() + "]");
		if (campo.getLargoMinimo() > 0 && valor != null && valor.length() < campo.getLargoMinimo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' debe tener al menos " + campo.getLargoMinimo() + " caracteres");
		if (campo.getLargoMaximo() > 0 && valor != null && valor.length() > campo.getLargoMaximo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' acepta hasta " + campo.getLargoMaximo() + " caracteres");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setDate(ClsCampo campo, Date valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Date)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Date");
		if (!campo.getPermiteNull() && valor == null)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setDouble(ClsCampo campo, Double valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Double)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Double");
		if (!campo.getPermiteNull() && valor == null)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setRating(ClsCampo campo, Integer valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Rating)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Rating");
		if (valor == null && !campo.getPermiteNull())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (valor != null && (valor < 0 || valor > 100))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no acepta el valor '" + valor + "' Rating(0-100)");
		if (valor == null) {
			if (campo.getIndexado())
				entidad.setProperty(campo.getNombre(), null);
			else
				entidad.setUnindexedProperty(campo.getNombre(), null);
		} else {
			if (campo.getIndexado())
				entidad.setProperty(campo.getNombre(), new Rating(valor));
			else
				entidad.setUnindexedProperty(campo.getNombre(), new Rating(valor));
		}
	}

	public void setText(ClsCampo campo, String valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Text)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Text");
		if (!campo.getPermiteNull() && (valor == null || valor.length() == 0))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getLargoMinimo() > 0 && valor != null && valor.length() < campo.getLargoMinimo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' debe tener al menos " + campo.getLargoMinimo() + " caracteres");
		if (campo.getLargoMaximo() > 0 && valor != null && valor.length() > campo.getLargoMaximo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' acepta hasta " + campo.getLargoMaximo() + " caracteres");
		if (valor == null)
			entidad.setUnindexedProperty(campo.getNombre(), null);
		else
			entidad.setUnindexedProperty(campo.getNombre(), new Text(valor));
	}

	public void setBlob(ClsCampo campo, byte[] valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Blob)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Blob");
		if (!campo.getPermiteNull() && (valor == null || valor.length == 0))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getLargoMinimo() > 0 && valor != null && valor.length < campo.getLargoMinimo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' debe tener al menos " + campo.getLargoMinimo() + " bytes");
		if (campo.getLargoMaximo() > 0 && valor != null && valor.length > campo.getLargoMaximo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' acepta hasta " + campo.getLargoMaximo() + " bytes");
		if (valor == null)
			entidad.setUnindexedProperty(campo.getNombre(), null);
		else
			entidad.setUnindexedProperty(campo.getNombre(), new Blob(valor));
	}

	public void setArrayString(ClsCampo campo, ArrayList<String> valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.ArrayString)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo ArrayString");
		if (!campo.getPermiteNull() && (valor == null || valor.size() == 0))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setArrayLong(ClsCampo campo, ArrayList<Long> valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.ArrayLong)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo ArrayLong");
		if (!campo.getPermiteNull() && (valor == null || valor.size() == 0))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getIndexado())
			entidad.setProperty(campo.getNombre(), valor);
		else
			entidad.setUnindexedProperty(campo.getNombre(), valor);
	}

	public void setEmail(ClsCampo campo, String valor) throws ExcepcionControlada {
		if (campo.getTipo() != Tipo.Email)
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no es de tipo Email");
		if (!campo.getPermiteNull() && (valor == null || valor.length() == 0))
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' no puede quedar vacío");
		if (campo.getLargoMinimo() > 0 && valor != null && valor.length() < campo.getLargoMinimo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' debe tener al menos " + campo.getLargoMinimo() + " caracteres");
		if (campo.getLargoMaximo() > 0 && valor != null && valor.length() > campo.getLargoMaximo())
			throw new ExcepcionControlada("El campo'" + campo.getNombre() + "' acepta hasta " + campo.getLargoMaximo() + " caracteres");
		if (valor == null)
			entidad.setUnindexedProperty(campo.getNombre(), null);
		else
			entidad.setUnindexedProperty(campo.getNombre(), new Email(valor));
	}

	// M�todos Est�ticos
	public static Transaction iniciarTransaccion(DatastoreService datastore) {
		TransactionOptions txOpt = TransactionOptions.Builder.withXG(true);
		return datastore.beginTransaction(txOpt);
	}

	public static Entity obtenerEntidad(DatastoreService datastore, String entidad, String keyName) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(entidad, keyName);
		Entity ent = datastore.get(key);
		return ent;
	}

	public static Entity obtenerEntidad(DatastoreService datastore, String entidad, String keyName, Key parent) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(parent, entidad, keyName);
		Entity ent = datastore.get(key);
		return ent;

	}

	public static Entity obtenerEntidad(DatastoreService datastore, Transaction tx, String entidad, String keyName) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(entidad, keyName);
		Entity ent = datastore.get(tx, key);
		return ent;
	}

	public static Entity obtenerEntidad(DatastoreService datastore, Transaction tx, String entidad, String keyName, Key parent) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(parent, entidad, keyName);
		Entity ent = datastore.get(tx, key);
		return ent;
	}

	public static boolean existeEntidad(DatastoreService datastore, String entidad, String keyName) {
		Key key = KeyFactory.createKey(entidad, keyName);
		try {
			datastore.get(key);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

	public static boolean existeEntidad(DatastoreService datastore, Transaction tx, String entidad, String keyName) {
		Key key = KeyFactory.createKey(entidad, keyName);
		try {
			datastore.get(tx, key);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}

	public static List<Entity> ejecutarConsulta(DatastoreService datastore, Transaction tx, String entidad, Key keyAncestor) {
		Query q = new Query(entidad);
		q.setAncestor(keyAncestor);
		List<Entity> lstEntidades = datastore.prepare(tx, q).asList(FetchOptions.Builder.withDefaults());
		// return convertirList(lstEntidades);
		return lstEntidades;
	}

	public static List<Entity> ejecutarConsulta(DatastoreService datastore, String entidad, List<Filter> lstFiltros) {
		Query q = new Query(entidad);
		setFilter(q, lstFiltros);
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());
		return lstEntidades;
	}

	public static List<Entity> ejecutarConsulta(DatastoreService datastore, String entidad, List<Filter> lstFiltros, int limite) {
		Query q = new Query(entidad);
		setFilter(q, lstFiltros);
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withLimit(limite));
		return lstEntidades;
	}

	public static List<Entity> ejecutarConsulta(DatastoreService datastore, String entidad, List<Filter> lstFiltros, int limite, String cursor) {
		Query q = new Query(entidad);
		setFilter(q, lstFiltros);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limite);
		if (cursor != null && cursor.length() > 0)
			fetchOptions.startCursor(Cursor.fromWebSafeString(cursor));
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(fetchOptions);
		if (lstEntidades.size() == limite)
			strCursor = lstEntidades.getCursor().toWebSafeString();
		else
			strCursor = null;

		return lstEntidades;
	}

	public static List<Entity> ejecutarConsultaSoloKeys(DatastoreService datastore, String entidad, List<Filter> lstFiltros, int limite, String cursor) {
		Query q = new Query(entidad);
		setFilter(q, lstFiltros);
		q.setKeysOnly();
		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
		if (limite > 0)
			fetchOptions = FetchOptions.Builder.withLimit(limite);
		if (cursor != null && cursor.length() > 0)
			fetchOptions.startCursor(Cursor.fromWebSafeString(cursor));
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(fetchOptions);
		if (lstEntidades.size() == limite)
			strCursor = lstEntidades.getCursor().toWebSafeString();
		else
			strCursor = null;

		return lstEntidades;
	}

	public static boolean ejecutarConsultaHayEntidades(DatastoreService datastore, String entidad, List<Filter> lstFiltros) {
		Query q = new Query(entidad);
		setFilter(q, lstFiltros);
		q.setKeysOnly();
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withLimit(1));
		return lstEntidades.size() > 0;
	}

	public static List<Entity> ejecutarConsulta(DatastoreService datastore, Query q, int limite) {
		List<Entity> lstEntidades = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(limite));
		return lstEntidades;
	}

	private static void setFilter(Query q, List<Filter> lstFiltros) {
		if (lstFiltros != null && lstFiltros.size() > 0) {
			if (lstFiltros.size() == 1) {
				q.setFilter(lstFiltros.get(0));
			} else {
				q.setFilter(CompositeFilterOperator.and(lstFiltros));
			}
		}

	}
}