package com.pantsoft.eppantsoft.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.pantsoft.eppantsoft.entidades.DbConsecutivo;
import com.pantsoft.eppantsoft.entidades.DbOrden;
import com.pantsoft.eppantsoft.entidades.DbOrdenProceso;
import com.pantsoft.eppantsoft.entidades.DbPedido;
import com.pantsoft.eppantsoft.entidades.DbPedidoDet;
import com.pantsoft.eppantsoft.entidades.DbProduccion;
import com.pantsoft.eppantsoft.serializable.SerOrden;
import com.pantsoft.eppantsoft.serializable.SerOrdenProceso;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmOrden {

	// Orden ////////////////////////////////////////////////////////////////////
	public SerOrden agregarOrden(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		String pos = "inico";
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			pos = "folio";
			long folio = ClsUtil.dameSiguienteId(serOrden.getEmpresa(), 0L, "Orden", datastore, tx);
			serOrden.setFolioOrden(folio);

			pos = "Db";
			DbOrden dbOrden = new DbOrden(serOrden);

			pos = "existe";
			if (ClsEntidad.existeEntidad(datastore, "DbOrden", dbOrden.getKey().getName()))
				throw new ExcepcionControlada("La orden '" + serOrden.getFolioOrden() + "' ya existe.");

			pos = "guardar";
			dbOrden.guardar(datastore);
			pos = "commit";
			tx.commit();

			pos = "serializar";
			return dbOrden.toSerOrden(null, null);
		} catch (Exception e) {
			throw new Exception("Pos: " + pos + " - " + e.getMessage());
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden actualizarOrden(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			dbOrden.setModelo(serOrden.getModelo());
			dbOrden.setReferencia(serOrden.getReferencia());
			dbOrden.setUsuarioDiseno(serOrden.getUsuarioDiseno());
			dbOrden.setUsuarioTrazo(serOrden.getUsuarioTrazo());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("Orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden terminarDiseno(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (dbOrden.getDisenoTerminado())
				throw new Exception("La orden ya tiene terminado el diseño");

			dbOrden.setFechaDiseno(new Date());
			dbOrden.setDisenoTerminado(true);
			dbOrden.setCarpetaTrazo(serOrden.getCarpetaTrazo());
			dbOrden.setBies(serOrden.getBies());
			dbOrden.setPiezasMolde(serOrden.getPiezasMolde());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden abrirDiseno(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (!dbOrden.getDisenoTerminado())
				throw new Exception("La orden no tiene terminado el diseño");

			dbOrden.setFechaDiseno(null);
			dbOrden.setDisenoTerminado(false);

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden grabarTrazo(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			// if (dbOrden.getTrazoTerminado())
			// throw new Exception("La orden ya tiene terminado el trazo");

			dbOrden.setTrazos(serOrden.getTrazos());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden terminarTrazo(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (dbOrden.getTrazoTerminado())
				throw new Exception("La orden ya tiene terminado el trazo");

			dbOrden.setFechaTrazo(new Date());
			dbOrden.setTrazoTerminado(true);
			dbOrden.setTrazos(serOrden.getTrazos());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden terminarOrden(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (serOrden.getTerminada()) {
				if (dbOrden.getTerminada())
					throw new Exception("La orden ya está terminada");
			} else {
				if (!dbOrden.getTerminada())
					throw new Exception("La orden no está terminada");
			}

			dbOrden.setTerminada(serOrden.getTerminada());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden abrirTrazo(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (!dbOrden.getTrazoTerminado())
				throw new Exception("La orden no tiene terminado el trazo");

			dbOrden.setFechaTrazo(null);
			dbOrden.setTrazoTerminado(false);

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void eliminarOrden(String empresa, long folioOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", empresa + "-" + folioOrden);
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			// Validar que no participe
			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			lstFiltros.add(new FilterPredicate("folioOrden", FilterOperator.EQUAL, folioOrden));
			if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbOrdenProceso", lstFiltros))
				throw new Exception("La orden " + folioOrden + " tiene procesos, imposible eliminar.");

			dbOrden.eliminar(datastore, tx);

			tx.commit();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La orden '" + folioOrden + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void actualizarRevisado(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (dbOrden.getRevisado() != serOrden.getRevisado()) {
				dbOrden.setRevisado(serOrden.getRevisado());
				dbOrden.guardar(datastore, tx);
				tx.commit();
			}
		} catch (EntityNotFoundException e) {
			throw new Exception("Orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden actualizarPrioridadDiseno(String empresa, long folioOrden, long prioridad) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", empresa + "-" + folioOrden);
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			dbOrden.setPrioridadDiseno(prioridad);

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + folioOrden + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden actualizarPrioridadTrazo(String empresa, long folioOrden, long prioridad) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", empresa + "-" + folioOrden);
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			dbOrden.setPrioridadTrazo(prioridad);

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + folioOrden + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void actualizarHabilitacion(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			dbOrden.setHabilitacion(serOrden.getHabilitacion());
			dbOrden.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		}
	}

	public void actualizarTela(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			dbOrden.setEntregasTela(serOrden.getEntregasTela());
			dbOrden.setEstatusTela(serOrden.getEstatusTela());
			dbOrden.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		}
	}

	public SerOrden actualizarCodigosDeBarras(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			// Validar que no participe
			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, serOrden.getEmpresa()));
			lstFiltros.add(new FilterPredicate("folioOrdenProduccion", FilterOperator.EQUAL, serOrden.getFolioOrden()));
			if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbAlmEntradaDet", lstFiltros))
				throw new Exception("La orden " + serOrden.getFolioOrden() + " ya tiene entradas de almacén.");

			dbOrden.setCodigosDeBarras(serOrden.getCodigosDeBarras());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden actualizarModelo(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, serOrden.getEmpresa()));
			// lstFiltros.add(new FilterPredicate("folioOrdenProduccion", FilterOperator.EQUAL, serOrden.getFolioOrden()));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbAlmEntradaDet", lstFiltros))
			// throw new Exception("La orden " + serOrden.getFolioOrden() + " ya tiene entradas de almacén.");

			dbOrden.setModelo(serOrden.getModelo());
			dbOrden.setReferencia(serOrden.getReferencia());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrden actualizarFaltanteTela(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbOrden", serOrden.getEmpresa() + "-" + serOrden.getFolioOrden());
			DbOrden dbOrden = new DbOrden(datastore.get(key));

			if (dbOrden.getTerminada())
				throw new Exception("La orden ya está terminada");

			dbOrden.setFaltanteTela(serOrden.getFaltanteTela());
			dbOrden.setPorcentajeTela(serOrden.getPorcentajeTela());

			dbOrden.guardar(datastore, tx);
			tx.commit();

			dbOrden = new DbOrden(datastore.get(key));
			return dbOrden.toSerOrden(null, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serOrden.getFolioOrden() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	// OrdenProceso ////////////////////////////////////////////////////////////////////
	public SerOrdenProceso agregarOrdenProceso(SerOrdenProceso serOrdenProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			if (ClsUtil.esNulo(serOrdenProceso.getUsuario())) {
				throw new Exception("Falta el usuario");
			}

			tx = ClsEntidad.iniciarTransaccion(datastore);

			long folio = ClsUtil.dameSiguienteId(serOrdenProceso.getEmpresa(), 0L, "OrdenProceso", datastore, tx);
			serOrdenProceso.setFolioOrdenProceso(folio);

			DbOrdenProceso dbOrdenProceso = new DbOrdenProceso(serOrdenProceso);

			if (ClsEntidad.existeEntidad(datastore, "DbOrdenProceso", dbOrdenProceso.getKey().getName()))
				throw new ExcepcionControlada("El proceso '" + serOrdenProceso.getFolioOrdenProceso() + "' ya existe.");

			dbOrdenProceso.setAgregarBitacora(serOrdenProceso.getUsuario(), new Date(), "Agregar");

			dbOrdenProceso.guardar(datastore);
			tx.commit();

			return dbOrdenProceso.toSerOrdenProceso();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerOrdenProceso actualizarOrdenProceso(SerOrdenProceso serOrdenProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			if (ClsUtil.esNulo(serOrdenProceso.getUsuario())) {
				throw new Exception("Falta el usuario");
			}
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key keyp = KeyFactory.createKey("DbOrden", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrden());
			Key key = KeyFactory.createKey(keyp, "DbOrdenProceso", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrdenProceso());
			DbOrdenProceso dbOrdenProceso = new DbOrdenProceso(datastore.get(tx, key));

			String mensajeBitacora = "Actualizar";

			if (dbOrdenProceso.getEstatus() != serOrdenProceso.getEstatus()) {
				if (serOrdenProceso.getEstatus() == 1) {
					mensajeBitacora = "Iniciar proceso";
				} else if (serOrdenProceso.getEstatus() == 2) {
					mensajeBitacora = "Terminar proceso";
				}
			}

			if (dbOrdenProceso.getProceso().equals("CORTE")) {
				// Guardo el corte en Producción
				try {
					Key key2 = KeyFactory.createKey("DbProduccion", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getTemporada() + "-" + serOrdenProceso.getFolioOrden());
					DbProduccion dbProduccion = new DbProduccion(datastore.get(tx, key2));
					if (dbProduccion.getCantidadCorte().longValue() != serOrdenProceso.getCantidadSalida()) {
						dbProduccion.setCantidadCorte(serOrdenProceso.getCantidadSalida());
						dbProduccion.guardar(datastore, tx);
					}
				} catch (Exception e) {
					// No se hace nada si falla
				}
			}

			dbOrdenProceso.setEstatus(serOrdenProceso.getEstatus());
			dbOrdenProceso.setFolioMaquilero(serOrdenProceso.getFolioMaquilero());
			dbOrdenProceso.setMaquilero(serOrdenProceso.getMaquilero());
			dbOrdenProceso.setCantidadEntrada(serOrdenProceso.getCantidadEntrada());
			dbOrdenProceso.setCantidadSalida(serOrdenProceso.getCantidadSalida());
			dbOrdenProceso.setObservaciones(serOrdenProceso.getObservaciones());
			dbOrdenProceso.setDetalleEntrada(serOrdenProceso.getDetalleEntrada());
			dbOrdenProceso.setDetalleSalida(serOrdenProceso.getDetalleSalida());
			dbOrdenProceso.setAgregarBitacora(serOrdenProceso.getUsuario(), new Date(), mensajeBitacora);
			dbOrdenProceso.setPorRevisar(serOrdenProceso.getPorRevisar());
			dbOrdenProceso.setUsuarioModifico(serOrdenProceso.getUsuario());
			dbOrdenProceso.setFechaModifico(new Date(), serOrdenProceso.getZonaHoraria());

			// Validaciones de estatus
			// if (dbOrdenProceso.getEstatus() == 1) { // En proceso
			// if (ClsUtil.esNulo(dbOrdenProceso.getMaquilero()))
			// throw new Exception("Un proceso en producción debe tener maquilero");
			// }
			if (dbOrdenProceso.getEstatus() == 2) { // Terminado
				if (ClsUtil.esNulo(dbOrdenProceso.getMaquilero()))
					throw new Exception("Un proceso terminado debe tener maquilero");
				if (dbOrdenProceso.getCantidadSalida() == 0)
					throw new Exception("Un proceso terminado debe tener cantidad salida");
				if (ClsUtil.esNulo(dbOrdenProceso.getDetalleSalida()))
					throw new Exception("Un proceso terminado debe tener detalle salida");
			}

			dbOrdenProceso.guardar(datastore, tx);
			tx.commit();

			dbOrdenProceso = new DbOrdenProceso(datastore.get(key));
			return dbOrdenProceso.toSerOrdenProceso();
		} catch (EntityNotFoundException e) {
			throw new Exception("El proceso '" + serOrdenProceso.getFolioOrdenProceso() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void actualizarOrdenProcesoMaquilero(SerOrdenProceso serOrdenProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key keyp = KeyFactory.createKey("DbOrden", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrden());
			Key key = KeyFactory.createKey(keyp, "DbOrdenProceso", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrdenProceso());
			DbOrdenProceso dbOrdenProceso = new DbOrdenProceso(datastore.get(key));

			dbOrdenProceso.setFolioMaquilero(serOrdenProceso.getFolioMaquilero());
			dbOrdenProceso.setMaquilero(serOrdenProceso.getMaquilero());

			dbOrdenProceso.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El proceso '" + serOrdenProceso.getFolioOrdenProceso() + "' no existe.");
		}
	}

	public void actualizarOrdenProceso_PorRevisar(SerOrdenProceso serOrdenProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			if (ClsUtil.esNulo(serOrdenProceso.getUsuario())) {
				throw new Exception("Falta el usuario");
			}

			Key keyp = KeyFactory.createKey("DbOrden", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrden());
			Key key = KeyFactory.createKey(keyp, "DbOrdenProceso", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrdenProceso());
			DbOrdenProceso dbOrdenProceso = new DbOrdenProceso(datastore.get(key));

			dbOrdenProceso.setPorRevisar(serOrdenProceso.getPorRevisar());
			dbOrdenProceso.setAgregarBitacora(serOrdenProceso.getUsuario(), new Date(), "Actualizar porRevisar");
			dbOrdenProceso.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El proceso '" + serOrdenProceso.getFolioOrdenProceso() + "' no existe.");
		}
	}

	public void actualizarOrdenProceso_ObsRevision(SerOrdenProceso serOrdenProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			if (ClsUtil.esNulo(serOrdenProceso.getUsuario())) {
				throw new Exception("Falta el usuario");
			}

			Key keyp = KeyFactory.createKey("DbOrden", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrden());
			Key key = KeyFactory.createKey(keyp, "DbOrdenProceso", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrdenProceso());
			DbOrdenProceso dbOrdenProceso = new DbOrdenProceso(datastore.get(key));

			dbOrdenProceso.setObsRevision(serOrdenProceso.getObsRevision());
			dbOrdenProceso.setAgregarBitacora(serOrdenProceso.getUsuario(), new Date(), "Actualizar obsRevision");
			dbOrdenProceso.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El proceso '" + serOrdenProceso.getFolioOrdenProceso() + "' no existe.");
		}
	}

	public SerOrden actualizarLstOrdenProceso(SerOrden serOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		DbConsecutivo dbConsecutivo = null;
		List<SerOrdenProceso> lstResp = new ArrayList<SerOrdenProceso>();
		try {
			if (serOrden == null || serOrden.getProcesos() == null || serOrden.getProcesos().length == 0)
				throw new Exception("Debe mandar al menos un proceso");

			// Revisar que el orden sea consecutivo sin saltar números
			for (long i = 1; i <= serOrden.getProcesos().length; i++) {
				boolean noExiste = true;
				for (SerOrdenProceso serProc : serOrden.getProcesos()) {
					if (ClsUtil.esNulo(serProc.getUsuario())) {
						throw new Exception("Falta el usuario");
					}
					if (serProc.getOrden() == i) {
						noExiste = false;
						break;
					}
				}
				if (noExiste) {
					throw new Exception("Falta el proceso con Orden = " + i);
				}
			}

			tx = ClsEntidad.iniciarTransaccion(datastore);

			SerOrdenProceso serPrimerProceso = serOrden.getProcesos()[0];

			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, serPrimerProceso.getEmpresa()));
			lstFiltros.add(new FilterPredicate("folioOrden", FilterOperator.EQUAL, serPrimerProceso.getFolioOrden()));
			List<Entity> lstProcesos = ClsEntidad.ejecutarConsulta(datastore, "DbOrdenProceso", lstFiltros);

			tx.rollback();

			// Campos que deben venir forzosamente
			// private String empresa;
			// private long folioOrdenProceso;
			// private long orden;
			// private long folioPedido;
			// private long renglon;
			// private long folioProcesoOrigen;
			// private long folioProcesoDestino;
			// private String proceso;

			tx = ClsEntidad.iniciarTransaccion(datastore);

			// Obtengo el Pedido
			Key keyP = KeyFactory.createKey("DbPedido", serPrimerProceso.getEmpresa() + "-" + serPrimerProceso.getFolioPedido());
			DbPedido dbPedido = null;
			try {
				dbPedido = new DbPedido(datastore.get(tx, keyP));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el pedido " + serPrimerProceso.getFolioPedido());
			}

			// Obtengo el PedidoDet
			Key key = KeyFactory.createKey(keyP, "DbPedidoDet", serPrimerProceso.getEmpresa() + "-" + serPrimerProceso.getFolioPedido() + "-" + serPrimerProceso.getRenglonPedido());
			DbPedidoDet dbPedidoDet;
			try {
				dbPedidoDet = new DbPedidoDet(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el renglón del pedido pedido " + serPrimerProceso.getFolioPedido() + "-" + serPrimerProceso.getRenglonPedido());
			}

			for (SerOrdenProceso serOrdenProceso : serOrden.getProcesos()) {
				DbOrdenProceso dbOrdenProceso = null;

				if (!serPrimerProceso.getEmpresa().equals(serOrdenProceso.getEmpresa()))
					throw new Exception("La empresa no puede ser diferente");
				if (serPrimerProceso.getFolioOrden() != serOrdenProceso.getFolioOrden())
					throw new Exception("El folioOrden no puede ser diferente");
				if (serPrimerProceso.getFolioPedido() != serOrdenProceso.getFolioPedido())
					throw new Exception("El folioPedido no puede ser diferente");
				if (serPrimerProceso.getRenglonPedido() != serOrdenProceso.getRenglonPedido())
					throw new Exception("El renglonPedido no puede ser diferente");
				if (serOrdenProceso.getFolioOrdenProceso() > 0) {
					// Ya existe se actualiza
					// Obtengo el OrdenProceso
					Key keyp = KeyFactory.createKey("DbOrden", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrden());
					key = KeyFactory.createKey(keyp, "DbOrdenProceso", serOrdenProceso.getEmpresa() + "-" + serOrdenProceso.getFolioOrdenProceso());
					try {
						dbOrdenProceso = new DbOrdenProceso(datastore.get(tx, key));
					} catch (EntityNotFoundException e) {
						throw new Exception("No existe el OrdenProceso " + serOrdenProceso.getFolioOrdenProceso());
					}

					if (dbPedido.getTemporada() != dbOrdenProceso.getTemporada())
						throw new Exception("La temporada del proceso '" + dbOrdenProceso.getProceso() + "' no coincide con la del pedido. (PED:" + dbPedido.getFolioPedido() + ",PROC:" + dbOrdenProceso.getFolioOrdenProceso() + ")");
					if (!dbPedidoDet.getModelo().equals(dbOrdenProceso.getModelo()))
						throw new Exception("El modelo del proceso '" + dbOrdenProceso.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + dbOrdenProceso.getFolioOrdenProceso() + ")");
					if (!dbPedidoDet.getReferencia().equals(dbOrdenProceso.getReferencia()))
						throw new Exception("La referencia del proceso '" + dbOrdenProceso.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + dbOrdenProceso.getFolioOrdenProceso() + ")");
					if (!dbPedidoDet.getTallas().equals(dbOrdenProceso.getTallas()))
						throw new Exception("Las tallas del proceso '" + dbOrdenProceso.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + dbOrdenProceso.getFolioOrdenProceso() + ")");

					if (dbOrdenProceso.getEstatus() == 0) {
						if (serOrdenProceso.getOrden() != dbOrdenProceso.getOrden().longValue())
							dbOrdenProceso.setOrden(serOrdenProceso.getOrden());
						dbOrdenProceso.setAgregarBitacora(serOrdenProceso.getUsuario(), new Date(), "Actualizar orden");
						dbOrdenProceso.guardar(datastore, tx);
					} else {
						if (serOrdenProceso.getOrden() != dbOrdenProceso.getOrden().longValue())
							throw new Exception("No se puede modificar el orden del procesoProduccion " + dbOrdenProceso.getFolioOrdenProceso() + " pues ya no se encuentra pendiente");
					}
				} else {
					// No existe se agrega
					if (dbConsecutivo == null) {
						try {
							key = KeyFactory.createKey("DbConsecutivo", serOrdenProceso.getEmpresa() + "-0-OrdenProceso");
							dbConsecutivo = new DbConsecutivo(datastore.get(tx, key));
						} catch (EntityNotFoundException e) {
							dbConsecutivo = new DbConsecutivo(serOrdenProceso.getEmpresa(), 0L, "OrdenProceso");
						}
					}
					long id = dbConsecutivo.getId() + 1;
					dbConsecutivo.setId(id);

					serOrdenProceso.setFolioOrdenProceso(id);
					serOrdenProceso.setTemporada(dbPedido.getTemporada());
					serOrdenProceso.setEstatus(0);
					serOrdenProceso.setModelo(dbPedidoDet.getModelo());
					serOrdenProceso.setReferencia(dbPedidoDet.getReferencia());
					serOrdenProceso.setTallas(dbPedidoDet.getTallas());
					serOrdenProceso.setMaquilero(null);
					serOrdenProceso.setCantidadEntrada(0);
					serOrdenProceso.setCantidadSalida(0);
					serOrdenProceso.setDetalleEntrada(null);
					serOrdenProceso.setDetalleSalida(null);

					dbOrdenProceso = new DbOrdenProceso(serOrdenProceso);
					dbOrdenProceso.setAgregarBitacora(serOrdenProceso.getUsuario(), new Date(), "Agregar orden");
					dbOrdenProceso.guardar(datastore, tx);
				}
				lstResp.add(dbOrdenProceso.toSerOrdenProceso());
			}

			// Falta eliminar los que ya no existe pero estan en BD
			if (lstProcesos != null && lstProcesos.size() > 0) {
				for (Entity entidad : lstProcesos) {
					DbOrdenProceso dbProc = new DbOrdenProceso(entidad);
					boolean noExiste = true;
					for (SerOrdenProceso serProc : lstResp) {
						if (serProc.getFolioOrdenProceso() == dbProc.getFolioOrdenProceso().longValue()) {
							noExiste = false;
							break;
						}
					}
					if (noExiste) { // Existe en BD pero no exisre en el serializable, se elimina
						dbProc.eliminar(datastore, tx);
					}
				}
			}

			if (dbConsecutivo != null)
				dbConsecutivo.guardar(datastore, tx);

			tx.commit();

			serOrden.setProcesos(lstResp.toArray(new SerOrdenProceso[0]));
			return serOrden;
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void eliminarOrdenProceso(String empresa, long folioOrden, long folioOrdenProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key keyp = KeyFactory.createKey("DbOrden", empresa + "-" + folioOrden);
			Key key = KeyFactory.createKey(keyp, "DbOrdenProceso", empresa + "-" + folioOrdenProceso);
			DbOrden dbOrdenProceso = new DbOrden(datastore.get(key));
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			// lstFiltros.add(new FilterPredicate("procesoProduccion", FilterOperator.EQUAL, procesoProduccion));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("El procesoProduccion " + procesoProduccion + " tiene producciones, imposible eliminar.");

			dbOrdenProceso.eliminar(datastore, tx);

			tx.commit();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El proceso '" + folioOrdenProceso + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

}
