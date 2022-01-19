package com.pantsoft.eppantsoft.pm;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.entidades.DbModelo;
import com.pantsoft.eppantsoft.entidades.DbModeloHabilitacion;
import com.pantsoft.eppantsoft.entidades.DbModeloImagen;
import com.pantsoft.eppantsoft.entidades.DbModeloProceso;
import com.pantsoft.eppantsoft.entidades.DbTelaHabilitacion;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerModelo;
import com.pantsoft.eppantsoft.serializable.SerModeloHabilitacion;
import com.pantsoft.eppantsoft.serializable.SerModeloImagen;
import com.pantsoft.eppantsoft.serializable.SerModeloProceso;
import com.pantsoft.eppantsoft.util.ClsBlobWriter;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsEpUtil;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmModelo {

	// Modelo ////////////////////////////////////////////////////////////////////
	public SerModelo modelo_agregar(SerModelo serModelo) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbModelo dbModelo = new DbModelo(serModelo);
			dbModelo.setFecha(new Date());

			if (ClsEntidad.existeEntidad(datastore, "DbModelo", dbModelo.getKey().getName()))
				throw new ExcepcionControlada("El modelo '" + serModelo.getModelo() + "' ya existe.");

			dbModelo.guardar(datastore);

			// Guardo las habilitaciones
			if (serModelo.getHabilitaciones() != null && serModelo.getHabilitaciones().length > 0) {
				for (SerModeloHabilitacion serHab : serModelo.getHabilitaciones()) {
					serHab.setEmpresa(serModelo.getEmpresa());
					serHab.setTemporada(serModelo.getTemporada());
					serHab.setModelo(serModelo.getModelo());
					serHab.setReferencia(serModelo.getReferencia());
					DbModeloHabilitacion dbHab = new DbModeloHabilitacion(serHab);
					dbHab.guardar(datastore, tx);
				}
			}

			// Guardo las procesos
			if (serModelo.getProcesos() != null && serModelo.getProcesos().length > 0) {
				for (SerModeloProceso serPro : serModelo.getProcesos()) {
					serPro.setEmpresa(serModelo.getEmpresa());
					serPro.setTemporada(serModelo.getTemporada());
					serPro.setModelo(serModelo.getModelo());
					serPro.setReferencia(serModelo.getReferencia());
					DbModeloProceso dbHab = new DbModeloProceso(serPro);
					dbHab.guardar(datastore, tx);
				}
			}
			tx.commit();

			return dbModelo.toSerModeloCompleto(datastore, null);
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerModelo modelo_actualizar(SerModelo serModelo) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbModelo", serModelo.getEmpresa() + "-" + serModelo.getTemporada() + "-" + serModelo.getModelo() + "-" + serModelo.getReferencia());
			DbModelo dbModelo = new DbModelo(datastore.get(key));

			dbModelo.setDepartamento(serModelo.getDepartamento());
			dbModelo.setDescripcionSeccion(serModelo.getDescripcionSeccion());
			dbModelo.setDescripcion2(serModelo.getDescripcion2());
			dbModelo.setTela(serModelo.getTela());
			dbModelo.setTalla(serModelo.getTalla());
			dbModelo.setObservaciones(serModelo.getObservaciones());
			dbModelo.setCostura(serModelo.getCostura());
			dbModelo.setOtros(serModelo.getOtros());
			dbModelo.setPrecosto(serModelo.getPrecosto());
			dbModelo.setCosto(serModelo.getCosto());
			dbModelo.setOk(serModelo.getOk());
			dbModelo.setCortado(serModelo.getCortado());
			dbModelo.setEsPantSoft(true);
			dbModelo.guardar(datastore);

			// Guardo las habilitaciones
			Map<String, Boolean> mapHabilitaciones = new HashMap<String, Boolean>();
			if (serModelo.getHabilitaciones() != null && serModelo.getHabilitaciones().length > 0) {
				for (SerModeloHabilitacion serHab : serModelo.getHabilitaciones()) {
					serHab.setEmpresa(serModelo.getEmpresa());
					serHab.setTemporada(serModelo.getTemporada());
					serHab.setModelo(serModelo.getModelo());
					serHab.setReferencia(serModelo.getReferencia());
					mapHabilitaciones.put(serHab.getMateria(), true);
					// Busco el detalle
					boolean esNuevo = true;
					for (DbModeloHabilitacion dbHab : dbModelo.getHabilitaciones(datastore, tx)) {
						if (dbHab.getMateria() == serHab.getMateria()) {
							dbHab.setConsumo(serHab.getConsumo());
							dbHab.setConsumoReal(serHab.getConsumoReal());
							dbHab.setTrazo(serHab.getTrazo());
							dbHab.guardar(datastore, tx);
							esNuevo = false;
							break;
						}
					}
					if (esNuevo) {
						DbModeloHabilitacion dbHab = new DbModeloHabilitacion(serHab);
						dbHab.guardar(datastore, tx);
					}
				}
			}
			if (dbModelo.getHabilitaciones(datastore, tx) != null && dbModelo.getHabilitaciones(datastore, tx).size() > 0) {
				for (DbModeloHabilitacion dbHab : dbModelo.getHabilitaciones(datastore, tx)) {
					if (!mapHabilitaciones.containsKey(dbHab.getMateria()))
						dbHab.eliminar(datastore, tx);
				}
			}

			// Guardo las procesos
			Map<String, Boolean> mapProcesos = new HashMap<String, Boolean>();
			if (serModelo.getProcesos() != null && serModelo.getProcesos().length > 0) {
				for (SerModeloProceso serPro : serModelo.getProcesos()) {
					serPro.setEmpresa(serModelo.getEmpresa());
					serPro.setTemporada(serModelo.getTemporada());
					serPro.setModelo(serModelo.getModelo());
					serPro.setReferencia(serModelo.getReferencia());
					mapProcesos.put(serPro.getProceso(), true);
					// Busco el detalle
					boolean esNuevo = true;
					for (DbModeloProceso dbPro : dbModelo.getProcesos(datastore, tx)) {
						if (dbPro.getProceso() == serPro.getProceso()) {
							dbPro.setPrecosto(serPro.getPrecosto());
							dbPro.setCosto(serPro.getCosto());
							dbPro.guardar(datastore, tx);
							esNuevo = false;
							break;
						}
					}
					if (esNuevo) {
						DbModeloProceso dbHab = new DbModeloProceso(serPro);
						dbHab.guardar(datastore, tx);
					}
				}
			}
			if (dbModelo.getProcesos(datastore, tx) != null && dbModelo.getProcesos(datastore, tx).size() > 0) {
				for (DbModeloProceso dbHab : dbModelo.getProcesos(datastore, tx)) {
					if (!mapProcesos.containsKey(dbHab.getProceso()))
						dbHab.eliminar(datastore, tx);
				}
			}

			tx.commit();

			return dbModelo.toSerModeloCompleto(datastore, tx);
		} catch (EntityNotFoundException e) {
			throw new Exception("El modelo '" + serModelo.getModelo() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerModelo modelo_dameModelo(SerModelo serModelo) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbModelo", serModelo.getEmpresa() + "-" + serModelo.getTemporada() + "-" + serModelo.getModelo() + "-" + serModelo.getReferencia());
			DbModelo dbModelo = new DbModelo(datastore.get(key));
			return dbModelo.toSerModeloCompleto(datastore, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("El modelo '" + serModelo.getModelo() + "' no existe.");
		}
	}

	public SerModelo[] modelo_dameModelos(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstModelo = ClsEntidad.ejecutarConsulta(datastore, "DbModelo", lstFiltros);
		if (lstModelo == null || lstModelo.size() == 0)
			return new SerModelo[0];
		SerModelo[] arr = new SerModelo[lstModelo.size()];
		for (int i = 0; i < lstModelo.size(); i++) {
			arr[i] = new DbModelo(lstModelo.get(i)).toSerModelo();
		}
		return arr;
	}

	public Respuesta modelo_dameModelosCostura(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstModelo = ClsEntidad.ejecutarConsulta(datastore, "DbModelo", lstFiltros);
		ClsBlobWriter blobW = new ClsBlobWriter("¬");
		if (lstModelo == null || lstModelo.size() == 0) {
			blobW.insertarAlInicioBlobStr("0|&NullSiNube;|modelo|String|costura|Double");
		} else {
			for (int i = 0; i < lstModelo.size(); i++) {
				DbModelo dbModelo = new DbModelo(lstModelo.get(i));
				if (dbModelo.getReferencia().equals("1")) {
					blobW.nuevaFila();
					blobW.agregarStr(dbModelo.getModelo());
					blobW.agregarDbl(dbModelo.getCostura());
				}
			}
			blobW.insertarAlInicioBlobStr(lstModelo.size() + "|&NullSiNube;|modelo|String|costura|Double");
		}
		return new Respuesta(blobW.getString());
	}

	public void modelo_eliminar(String empresa, long temporada, String modelo, String referencia) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbModelo", empresa + "-" + temporada + "-" + modelo + "-" + referencia);
			DbModelo dbModelo = new DbModelo(datastore.get(key));
			// Validar que no participe
			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			lstFiltros.add(new FilterPredicate("modelo", FilterOperator.EQUAL, modelo));
			if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
				throw new Exception("El modelo " + modelo + " tiene producciones, imposible eliminar.");

			dbModelo.eliminar(datastore, tx);

			for (DbModeloHabilitacion dbHab : dbModelo.getHabilitaciones(datastore, tx)) {
				dbHab.eliminar(datastore, tx);
			}

			for (DbModeloProceso dbPro : dbModelo.getProcesos(datastore, tx)) {
				dbPro.eliminar(datastore, tx);
			}

			tx.commit();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El modelo '" + modelo + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public Object modelo_sincronizar(SerModelo serModelo, long bajar) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbModelo", serModelo.getEmpresa() + "-" + serModelo.getTemporada() + "-" + serModelo.getModelo() + "-" + serModelo.getReferencia());
			DbModelo dbModelo = new DbModelo(datastore.get(key));

			if (bajar != 0 || dbModelo.getEsPantSoft()) {
				return dbModelo.toSerModeloCompleto(datastore, tx);
			} else {
				boolean modeloActualizado = false;
				boolean hayCambios = false;
				String cambios = "";
				if (!ClsUtil.esIgualConNulo(dbModelo.getDepartamento(), serModelo.getDepartamento())) {
					dbModelo.setDepartamento(serModelo.getDepartamento());
					hayCambios = true;
					cambios += "a";
				}
				if (!ClsUtil.esIgualConNulo(dbModelo.getDescripcionSeccion(), serModelo.getDescripcionSeccion())) {
					dbModelo.setDescripcionSeccion(serModelo.getDescripcionSeccion());
					hayCambios = true;
					cambios += "b";
				}
				if (!ClsUtil.esIgualConNulo(dbModelo.getDescripcion2(), serModelo.getDescripcion2())) {
					dbModelo.setDescripcion2(serModelo.getDescripcion2());
					hayCambios = true;
					cambios += "c";
				}
				if (!ClsUtil.esIgualConNulo(dbModelo.getTela(), serModelo.getTela())) {
					dbModelo.setTela(serModelo.getTela());
					hayCambios = true;
					cambios += "d";
				}
				if (!ClsUtil.esIgualConNulo(dbModelo.getTalla(), serModelo.getTalla())) {
					dbModelo.setTalla(serModelo.getTalla());
					hayCambios = true;
					cambios += "e";
				}
				if (!ClsUtil.esIgualConNulo(dbModelo.getObservaciones(), serModelo.getObservaciones())) {
					dbModelo.setObservaciones(serModelo.getObservaciones());
					hayCambios = true;
					cambios += "f";
				}
				if (!ClsUtil.esIgualConNulo(dbModelo.getCostura(), serModelo.getCostura())) {
					dbModelo.setCostura(serModelo.getCostura());
					hayCambios = true;
					cambios += "g";
				}
				if (dbModelo.getOtros() != serModelo.getOtros()) {
					dbModelo.setOtros(serModelo.getOtros());
					hayCambios = true;
					cambios += "h";
				}
				if (dbModelo.getPrecosto() != serModelo.getPrecosto()) {
					dbModelo.setPrecosto(serModelo.getPrecosto());
					hayCambios = true;
					cambios += "i";
				}
				if (dbModelo.getCosto() != serModelo.getCosto()) {
					dbModelo.setCosto(serModelo.getCosto());
					hayCambios = true;
					cambios += "j";
				}
				if (dbModelo.getOk() != serModelo.getOk()) {
					dbModelo.setOk(serModelo.getOk());
					hayCambios = true;
					cambios += "k";
				}
				if (dbModelo.getCortado() != serModelo.getCortado()) {
					dbModelo.setCortado(serModelo.getCortado());
					hayCambios = true;
					cambios += "l";
				}
				if (hayCambios) {
					dbModelo.guardar(datastore);
					modeloActualizado = true;
					cambios += "m";
				}

				// Guardo las habilitaciones
				Map<String, Boolean> mapHabilitaciones = new HashMap<String, Boolean>();
				if (serModelo.getHabilitaciones() != null && serModelo.getHabilitaciones().length > 0) {
					for (SerModeloHabilitacion serHab : serModelo.getHabilitaciones()) {
						// serHab.setEmpresa(serModelo.getEmpresa());
						// serHab.setTemporada(serModelo.getTemporada());
						// serHab.setModelo(serModelo.getModelo());
						// serHab.setReferencia(serModelo.getReferencia());
						mapHabilitaciones.put(serHab.getMateria(), true);
						// Busco el detalle
						boolean esNuevo = true;
						for (DbModeloHabilitacion dbHab : dbModelo.getHabilitaciones(datastore, tx)) {
							if (dbHab.getMateria().equals(serHab.getMateria())) {
								if (dbHab.getConsumo() != serHab.getConsumo() || dbHab.getConsumoReal() != serHab.getConsumoReal() || dbHab.getTrazo() != serHab.getTrazo()) {
									dbHab.setConsumo(serHab.getConsumo());
									dbHab.setConsumoReal(serHab.getConsumoReal());
									dbHab.setTrazo(serHab.getTrazo());
									dbHab.guardar(datastore, tx);
									modeloActualizado = true;
									cambios += "n";
								}
								esNuevo = false;
								break;
							}
						}
						if (esNuevo) {
							DbModeloHabilitacion dbHab = new DbModeloHabilitacion(serHab);
							dbHab.guardar(datastore, tx);
							modeloActualizado = true;
							cambios += "o";
						}
					}
				}
				if (dbModelo.getHabilitaciones(datastore, tx) != null && dbModelo.getHabilitaciones(datastore, tx).size() > 0) {
					for (DbModeloHabilitacion dbHab : dbModelo.getHabilitaciones(datastore, tx)) {
						if (!mapHabilitaciones.containsKey(dbHab.getMateria())) {
							dbHab.eliminar(datastore, tx);
							modeloActualizado = true;
							cambios += "p";
						}
					}
				}

				// Guardo las procesos
				Map<String, Boolean> mapProcesos = new HashMap<String, Boolean>();
				if (serModelo.getProcesos() != null && serModelo.getProcesos().length > 0) {
					for (SerModeloProceso serPro : serModelo.getProcesos()) {
						// serPro.setEmpresa(serModelo.getEmpresa());
						// serPro.setTemporada(serModelo.getTemporada());
						// serPro.setModelo(serModelo.getModelo());
						// serPro.setReferencia(serModelo.getReferencia());
						mapProcesos.put(serPro.getProceso(), true);
						// Busco el detalle
						boolean esNuevo = true;
						for (DbModeloProceso dbPro : dbModelo.getProcesos(datastore, tx)) {
							if (dbPro.getProceso().equals(serPro.getProceso())) {
								if (dbPro.getPrecosto() != serPro.getPrecosto() || dbPro.getCosto() != serPro.getCosto()) {
									dbPro.setPrecosto(serPro.getPrecosto());
									dbPro.setCosto(serPro.getCosto());
									dbPro.guardar(datastore, tx);
									modeloActualizado = true;
									cambios += "q";
								}
								esNuevo = false;
								break;
							}
						}
						if (esNuevo) {
							DbModeloProceso dbHab = new DbModeloProceso(serPro);
							dbHab.guardar(datastore, tx);
							modeloActualizado = true;
							cambios += "r";
						}
					}
				}
				if (dbModelo.getProcesos(datastore, tx) != null && dbModelo.getProcesos(datastore, tx).size() > 0) {
					for (DbModeloProceso dbHab : dbModelo.getProcesos(datastore, tx)) {
						if (!mapProcesos.containsKey(dbHab.getProceso())) {
							dbHab.eliminar(datastore, tx);
							modeloActualizado = true;
							cambios += "s";
						}
					}
				}

				tx.commit();

				Respuesta resp = new Respuesta("Sin cambios");
				resp.booleano = false;
				if (modeloActualizado) {
					resp.cadena = "Actualizado(" + cambios + ")";
					resp.booleano = true;
				}
				return resp;
			}
		} catch (EntityNotFoundException e) {
			DbModelo dbModelo = new DbModelo(serModelo);
			dbModelo.setEsPantSoft(false);
			dbModelo.guardar(datastore, tx);

			// Guardo las habilitaciones
			if (serModelo.getHabilitaciones() != null && serModelo.getHabilitaciones().length > 0) {
				for (SerModeloHabilitacion serHab : serModelo.getHabilitaciones()) {
					serHab.setEmpresa(serModelo.getEmpresa());
					serHab.setTemporada(serModelo.getTemporada());
					serHab.setModelo(serModelo.getModelo());
					serHab.setReferencia(serModelo.getReferencia());
					DbModeloHabilitacion dbHab = new DbModeloHabilitacion(serHab);
					dbHab.guardar(datastore, tx);
				}
			}

			// Guardo las procesos
			if (serModelo.getProcesos() != null && serModelo.getProcesos().length > 0) {
				for (SerModeloProceso serPro : serModelo.getProcesos()) {
					serPro.setEmpresa(serModelo.getEmpresa());
					serPro.setTemporada(serModelo.getTemporada());
					serPro.setModelo(serModelo.getModelo());
					serPro.setReferencia(serModelo.getReferencia());
					DbModeloProceso dbHab = new DbModeloProceso(serPro);
					dbHab.guardar(datastore, tx);
				}
			}

			tx.commit();

			Respuesta resp = new Respuesta("Agregado");
			resp.booleano = true;
			return resp;

		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void modelo_marcarSincronizado(String empresa, long temporada, String modelo, String referencia) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbModelo", empresa + "-" + temporada + "-" + modelo + "-" + referencia);
			DbModelo dbModelo = new DbModelo(datastore.get(key));
			dbModelo.setEsPantSoft(false);
			dbModelo.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El modelo '" + modelo + "' no existe.");
		}
	}

	public Respuesta modelo_calcularPrecosto(String empresa, long temporada, int tamPag, String cursor) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		if (cursor.equals("Inicio"))
			cursor = null;

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstModelo = ClsEntidad.ejecutarConsulta(datastore, "DbModelo", lstFiltros, tamPag, cursor);
		cursor = ClsEntidad.getStrCursor();
		long corregidos = 0;
		for (Entity entidad : lstModelo) {
			DbModelo dbModelo = new DbModelo(entidad);
			double precosto = calcularPrecosto(dbModelo, datastore);
			if (precosto != dbModelo.getPrecosto().doubleValue()) {
				dbModelo.setPrecosto(precosto);
				dbModelo.guardar(datastore);
				corregidos++;
			}
		}
		Respuesta resp = new Respuesta();
		resp.cadena = cursor;
		resp.largo = corregidos;
		return resp;
	}

	private double calcularPrecosto(DbModelo dbModelo, DatastoreService datastore) throws Exception {
		double precosto = 0;
		List<DbModeloProceso> lstProcesos = dbModelo.getProcesos(datastore, null);
		if (lstProcesos != null && lstProcesos.size() > 0) {
			for (DbModeloProceso dbProceso : lstProcesos) {
				if (dbProceso.getPrecosto() != 0) {
					precosto += dbProceso.getPrecosto();
				} else {
					return 0;
				}
			}
		}
		boolean hayGancho = false;
		boolean hayTela = false;
		List<DbModeloHabilitacion> lstHabilitaciones = dbModelo.getHabilitaciones(datastore, null);
		for (DbModeloHabilitacion dbMateria : lstHabilitaciones) {
			DbTelaHabilitacion dbMateriaCat = new DbTelaHabilitacion(ClsEntidad.obtenerEntidad(datastore, "DbTelaHabilitacion", dbMateria.getEmpresa() + "-" + dbMateria.getTemporada() + "-" + dbMateria.getMateria()));
			if (dbMateriaCat.getPrecio() != 0) {
				if (dbMateria.getConsumo() != 0) {
					precosto += ClsUtil.Redondear(dbMateriaCat.getPrecio() * dbMateria.getConsumo(), 2);
				} else {
					return 0;
				}
			} else {
				return 0;
			}
			if (dbMateria.getMateria().toLowerCase().contains("gancho")) {
				hayGancho = true;
			}
			if (dbMateriaCat.getTipo().equals("T")) {
				hayTela = true;
			}
		}
		if (!hayGancho) {
			return 0;
		}
		if (!hayTela) {
			return 0;
		}
		if (dbModelo.getOtros() != null && dbModelo.getOtros() != 0) {
			precosto += dbModelo.getOtros();
		} else {
			return 0;
		}
		if (dbModelo.getCostura() != null && dbModelo.getCostura() != 0) {
			precosto += dbModelo.getCostura();
		} else {
			return 0;
		}

		return ClsUtil.Redondear(precosto, 2);
	}

	// Modelo Imagen //////////////////////////////////////////////////////////
	public void modeloImagen_agregar(SerModeloImagen serModeloImagen) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbModeloImagen dbModeloImagen = new DbModeloImagen(serModeloImagen);

		if (ClsEntidad.existeEntidad(datastore, "DbModeloImagen", dbModeloImagen.getKey().getName()))
			throw new ExcepcionControlada("La imagen '" + dbModeloImagen.getKey().getName() + "' ya existe.");

		dbModeloImagen.guardar(datastore);
	}

	public void modeloImagen_dameImagen(HttpServletRequest req, HttpServletResponse res, ClsEpUtil ep) throws Exception {
		String empresa = ep.dameParametroString("empresa");
		long temporada = ep.dameParametroLong("temporada");
		String modelo = ep.dameParametroString("modelo");
		String referencia = ep.dameParametroString("referencia");
		long renglon = ep.dameParametroLong("renglon");
		int mini = ep.dameParametroInt("mini");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key keyp = KeyFactory.createKey("DbModelo", empresa + "-" + temporada + "-" + modelo + "-" + referencia);
		Key key = KeyFactory.createKey(keyp, "DbModeloImagen", empresa + "-" + temporada + "-" + modelo + "-" + referencia + "-" + renglon);

		DbModeloImagen dbImagen;
		try {
			dbImagen = new DbModeloImagen(datastore.get(key));
		} catch (EntityNotFoundException e) {
			try {
				Key keyp2 = KeyFactory.createKey("DbModelo", "SinImagen-0-SinImagen-1");
				Key key2 = KeyFactory.createKey(keyp2, "DbModeloImagen", "SinImagen-0-SinImagen-1-1");
				dbImagen = new DbModeloImagen(datastore.get(key2));
			} catch (EntityNotFoundException e1) {
				throw new Exception("La imagen no existe");
			}
		}

		OutputStream out = res.getOutputStream();
		if (mini == 1) {
			out.write(dbImagen.getImagenMini());
		} else {
			out.write(dbImagen.getImagen());
		}
		out.close();
	}

	public void modeloImagen_eliminar(SerModeloImagen serModeloImagen) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key keyp = KeyFactory.createKey("DbModelo", serModeloImagen.getEmpresa() + "-" + serModeloImagen.getTemporada() + "-" + serModeloImagen.getModelo() + "-" + serModeloImagen.getReferencia());
		Key key = KeyFactory.createKey(keyp, "DbModeloImagen", serModeloImagen.getEmpresa() + "-" + serModeloImagen.getTemporada() + "-" + serModeloImagen.getModelo() + "-" + serModeloImagen.getReferencia() + "-" + serModeloImagen.getRenglon());

		DbModeloImagen dbImagen;
		try {
			dbImagen = new DbModeloImagen(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("La imagen no existe");
		}
		dbImagen.eliminar(datastore);
	}

	public SerModeloImagen[] modeloImagen_dameModelosImagenSinImagen(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));

		List<Entity> lstEntidades = ClsEntidad.ejecutarConsultaSoloKeys(datastore, "DbModeloImagen", lstFiltros, 0, null);
		// startCursor = ClsEntidad.getStrCursor();

		// Comienzo a eliminar las entidades
		List<SerModeloImagen> lstImagenes = new ArrayList<SerModeloImagen>();
		for (Entity entidad : lstEntidades) {
			String[] valores = entidad.getKey().getName().split("-");
			if (valores.length != 5)
				throw new Exception("Error en split: entidad.getKey().getName() : " + valores.length);
			lstImagenes.add(new SerModeloImagen(empresa, temporada, valores[2], valores[3], Long.parseLong(valores[4]), 0, 0, null, null));
		}

		return lstImagenes.toArray(new SerModeloImagen[0]);

		// List<Filter> lstFiltros = new ArrayList<Filter>();
		// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		// lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		// List<Entity> lstModeloImagen = ClsEntidad.ejecutarConsulta(datastore, "DbModeloImagen", lstFiltros);
		// if (lstModeloImagen == null || lstModeloImagen.size() == 0)
		// return new SerModeloImagen[0];
		// SerModeloImagen[] arr = new SerModeloImagen[lstModeloImagen.size()];
		// for (int i = 0; i < lstModeloImagen.size(); i++) {
		// arr[i] = new DbModeloImagen(lstModeloImagen.get(i)).toSerModeloImagenSinImagen();
		// }
		// return arr;
	}

}
