package com.pantsoft.eppantsoft.pm;

import java.util.ArrayList;
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
import com.pantsoft.eppantsoft.entidades.DbPedido;
import com.pantsoft.eppantsoft.entidades.DbPedidoDet;
import com.pantsoft.eppantsoft.entidades.DbProcesoProduccion;
import com.pantsoft.eppantsoft.serializable.SerProcesoProduccion;
import com.pantsoft.eppantsoft.serializable.SerProcesoProduccionLst;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProcesoProduccion {

	// ProcesoProduccion ////////////////////////////////////////////////////////////////////
	public SerProcesoProduccion agregar(SerProcesoProduccion serProcesoProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			long folio = ClsUtil.dameSiguienteId(serProcesoProduccion.getEmpresa(), 0L, "ProcesoProduccion", datastore, tx);
			serProcesoProduccion.setFolioProcesoProduccion(folio);

			DbProcesoProduccion dbProcesoProduccion = new DbProcesoProduccion(serProcesoProduccion);

			if (ClsEntidad.existeEntidad(datastore, "DbProcesoProduccion", dbProcesoProduccion.getKey().getName()))
				throw new ExcepcionControlada("El procesoProduccion '" + serProcesoProduccion.getFolioProcesoProduccion() + "' ya existe.");

			dbProcesoProduccion.guardar(datastore);
			tx.commit();

			return dbProcesoProduccion.toSerProcesoProduccion();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProcesoProduccion actualizar(SerProcesoProduccion serProcesoProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProcesoProduccion", serProcesoProduccion.getEmpresa() + "-" + serProcesoProduccion.getFolioProcesoProduccion());
			DbProcesoProduccion dbProcesoProduccion = new DbProcesoProduccion(datastore.get(key));

			dbProcesoProduccion.setEstatus(serProcesoProduccion.getEstatus());
			dbProcesoProduccion.setMaquilero(serProcesoProduccion.getMaquilero());
			dbProcesoProduccion.setCantidadEntrada(serProcesoProduccion.getCantidadEntrada());
			dbProcesoProduccion.setCantidadSalida(serProcesoProduccion.getCantidadSalida());
			dbProcesoProduccion.setObservaciones(serProcesoProduccion.getObservaciones());
			dbProcesoProduccion.setDetalleEntrada(serProcesoProduccion.getDetalleEntrada());
			dbProcesoProduccion.setDetalleSalida(serProcesoProduccion.getDetalleSalida());

			// Validaciones de estatus
			if (dbProcesoProduccion.getEstatus() == 1) { // En proceso
				if (ClsUtil.esNulo(dbProcesoProduccion.getMaquilero()))
					throw new Exception("Un proceso en producción debe tener maquilero");
			}
			if (dbProcesoProduccion.getEstatus() == 2) { // Terminado
				if (ClsUtil.esNulo(dbProcesoProduccion.getMaquilero()))
					throw new Exception("Un proceso terminado debe tener maquilero");
				if (dbProcesoProduccion.getCantidadSalida() == 0)
					throw new Exception("Un proceso terminado debe tener cantidad salida");
				if (ClsUtil.esNulo(dbProcesoProduccion.getDetalleSalida()))
					throw new Exception("Un proceso terminado debe tener detalle salida");
			}

			dbProcesoProduccion.guardar(datastore, tx);
			tx.commit();

			dbProcesoProduccion = new DbProcesoProduccion(datastore.get(key));
			return dbProcesoProduccion.toSerProcesoProduccion();
		} catch (EntityNotFoundException e) {
			throw new Exception("El procesoProduccion '" + serProcesoProduccion.getFolioProcesoProduccion() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProcesoProduccionLst actualizarLst(SerProcesoProduccionLst lst) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		DbConsecutivo dbConsecutivo = null;
		List<SerProcesoProduccion> lstResp = new ArrayList<SerProcesoProduccion>();
		String pos = "ini";
		try {
			if (lst == null || lst.getProcesos() == null || lst.getProcesos().length == 0)
				throw new Exception("Debe mandar al menos un procesoProduccion");

			// Revisar que el orden sea consecutivo sin saltar números
			for (long i = 1; i <= lst.getProcesos().length; i++) {
				boolean noExiste = true;
				for (SerProcesoProduccion serProc : lst.getProcesos()) {
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

			SerProcesoProduccion serPrimerProceso = lst.getProcesos()[0];

			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, serPrimerProceso.getEmpresa()));
			lstFiltros.add(new FilterPredicate("folioPedido", FilterOperator.EQUAL, serPrimerProceso.getFolioPedido()));
			lstFiltros.add(new FilterPredicate("renglonPedido", FilterOperator.EQUAL, serPrimerProceso.getRenglonPedido()));
			List<Entity> lstProcesos = ClsEntidad.ejecutarConsulta(datastore, "DbProcesoProduccion", lstFiltros);

			tx.rollback();

			// Campos que deben venir forzosamente
			// private String empresa;
			// private long folioProcesoProduccion;
			// private long orden;
			// private long folioPedido;
			// private long renglon;
			// private long folioProcesoOrigen;
			// private long folioProcesoDestino;
			// private String proceso;

			tx = ClsEntidad.iniciarTransaccion(datastore);

			pos = "ped";
			// Obtengo el Pedido
			Key keyP = KeyFactory.createKey("DbPedido", serPrimerProceso.getEmpresa() + "-" + serPrimerProceso.getFolioPedido());
			DbPedido dbPedido = null;
			try {
				dbPedido = new DbPedido(datastore.get(tx, keyP));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el pedido " + serPrimerProceso.getFolioPedido());
			}

			pos = "peddet";
			// Obtengo el PedidoDet
			Key key = KeyFactory.createKey(keyP, "DbPedidoDet", serPrimerProceso.getEmpresa() + "-" + serPrimerProceso.getFolioPedido() + "-" + serPrimerProceso.getRenglonPedido());
			DbPedidoDet dbPedidoDet;
			try {
				dbPedidoDet = new DbPedidoDet(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el renglón del pedido pedido " + serPrimerProceso.getFolioPedido() + "-" + serPrimerProceso.getRenglonPedido());
			}

			for (SerProcesoProduccion serProcesoProduccion : lst.getProcesos()) {
				DbProcesoProduccion dbProcesoProduccion = null;

				if (!serPrimerProceso.getEmpresa().equals(serProcesoProduccion.getEmpresa()))
					throw new Exception("La empresa no puede ser diferente");
				if (serPrimerProceso.getFolioPedido() != serProcesoProduccion.getFolioPedido())
					throw new Exception("El folioPedido no puede ser diferente");
				if (serPrimerProceso.getRenglonPedido() != serProcesoProduccion.getRenglonPedido())
					throw new Exception("El renglonPedido no puede ser diferente");
				if (serProcesoProduccion.getFolioProcesoProduccion() > 0) {
					pos = "existe:" + serProcesoProduccion.getProceso();
					// Ya existe se actualiza

					pos = "existe obtener:" + serProcesoProduccion.getProceso();
					// Obtengo el ProcesoProduccion
					key = KeyFactory.createKey("DbProcesoProduccion", serProcesoProduccion.getEmpresa() + "-" + serProcesoProduccion.getFolioProcesoProduccion());
					try {
						dbProcesoProduccion = new DbProcesoProduccion(datastore.get(tx, key));
					} catch (EntityNotFoundException e) {
						throw new Exception("No existe el ProcesoProduccion " + serProcesoProduccion.getFolioProcesoProduccion());
					}

					if (dbPedido.getTemporada() != dbProcesoProduccion.getTemporada())
						throw new Exception("La temporada del proceso '" + dbProcesoProduccion.getProceso() + "' no coincide con la del pedido. (PED:" + dbPedido.getFolioPedido() + ",PROC:" + dbProcesoProduccion.getFolioProcesoProduccion() + ")");
					if (!dbPedidoDet.getModelo().equals(dbProcesoProduccion.getModelo()))
						throw new Exception("El modelo del proceso '" + dbProcesoProduccion.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + dbProcesoProduccion.getFolioProcesoProduccion() + ")");
					if (!dbPedidoDet.getReferencia().equals(dbProcesoProduccion.getReferencia()))
						throw new Exception("La referencia del proceso '" + dbProcesoProduccion.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + dbProcesoProduccion.getFolioProcesoProduccion() + ")");
					if (!dbPedidoDet.getTallas().equals(dbProcesoProduccion.getTallas()))
						throw new Exception("Las tallas del proceso '" + dbProcesoProduccion.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + dbProcesoProduccion.getFolioProcesoProduccion() + ")");

					if (dbProcesoProduccion.getEstatus() == 0) {
						pos = "existe estatus 0:" + serProcesoProduccion.getProceso();
						if (serProcesoProduccion.getOrden() != dbProcesoProduccion.getOrden().longValue())
							dbProcesoProduccion.setOrden(serProcesoProduccion.getOrden());
						// if (serProcesoProduccion.getFolioProcesoOrigen() != dbProcesoProduccion.getFolioProcesoOrigen())
						// dbProcesoProduccion.setFolioProcesoOrigen(serProcesoProduccion.getFolioProcesoOrigen());
						// if (serProcesoProduccion.getFolioProcesoDestino() != dbProcesoProduccion.getFolioProcesoDestino())
						// dbProcesoProduccion.setFolioProcesoDestino(serProcesoProduccion.getFolioProcesoDestino());
						dbProcesoProduccion.guardar(datastore, tx);
					} else {
						pos = "existe no guardar:" + serProcesoProduccion.getProceso();
						if (serProcesoProduccion.getOrden() != dbProcesoProduccion.getOrden().longValue())
							throw new Exception("No se puede modificar el orden del procesoProduccion " + dbProcesoProduccion.getFolioProcesoProduccion() + " pues ya no se encuentra pendiente");
						// if (serProcesoProduccion.getFolioProcesoOrigen() != dbProcesoProduccion.getFolioProcesoOrigen())
						// throw new Exception("No se puede modificar el orden del procesoProduccion " + dbProcesoProduccion.getFolioProcesoProduccion() + " pues ya no se encuentra pendiente");
						// if (serProcesoProduccion.getFolioProcesoDestino() != dbProcesoProduccion.getFolioProcesoDestino())
						// throw new Exception("No se puede modificar el orden del procesoProduccion " + dbProcesoProduccion.getFolioProcesoProduccion() + " pues ya no se encuentra pendiente");
					}
				} else {
					pos = "no existe:" + serProcesoProduccion.getProceso();
					// No existe se agrega
					if (dbConsecutivo == null) {
						try {
							key = KeyFactory.createKey("DbConsecutivo", serProcesoProduccion.getEmpresa() + "-0-ProcesoProduccion");
							dbConsecutivo = new DbConsecutivo(datastore.get(tx, key));
						} catch (EntityNotFoundException e) {
							dbConsecutivo = new DbConsecutivo(serProcesoProduccion.getEmpresa(), 0L, "ProcesoProduccion");
						}
					}
					long id = dbConsecutivo.getId() + 1;
					dbConsecutivo.setId(id);

					pos = "ser:" + serProcesoProduccion.getProceso();
					serProcesoProduccion.setFolioProcesoProduccion(id);
					serProcesoProduccion.setTemporada(dbPedido.getTemporada());
					serProcesoProduccion.setEstatus(0);
					serProcesoProduccion.setModelo(dbPedidoDet.getModelo());
					serProcesoProduccion.setReferencia(dbPedidoDet.getReferencia());
					serProcesoProduccion.setTallas(dbPedidoDet.getTallas());
					serProcesoProduccion.setMaquilero(null);
					serProcesoProduccion.setCantidadEntrada(0);
					serProcesoProduccion.setCantidadSalida(0);
					serProcesoProduccion.setDetalleEntrada(null);
					serProcesoProduccion.setDetalleSalida(null);

					pos = "no existe db:" + dbPedidoDet.getReferencia() + "-" + serProcesoProduccion.getReferencia() + "-" + serProcesoProduccion.getProceso();
					dbProcesoProduccion = new DbProcesoProduccion(serProcesoProduccion);
					pos = "no existe guardar:" + dbPedidoDet.getReferencia() + "-" + serProcesoProduccion.getReferencia() + "-" + dbProcesoProduccion.getReferencia() + "-" + serProcesoProduccion.getProceso();
					dbProcesoProduccion.guardar(datastore, tx);
					pos = "no existe siguiente:" + serProcesoProduccion.getProceso();
				}
				lstResp.add(dbProcesoProduccion.toSerProcesoProduccion());
			}

			// Falta eliminar los que ya no existe pero estan en BD
			if (lstProcesos != null && lstProcesos.size() > 0) {
				for (Entity entidad : lstProcesos) {
					DbProcesoProduccion dbProc = new DbProcesoProduccion(entidad);
					boolean noExiste = true;
					for (SerProcesoProduccion serProc : lstResp) {
						if (serProc.getFolioProcesoProduccion() == dbProc.getFolioProcesoProduccion().longValue()) {
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

			lst.setProcesos(lstResp.toArray(new SerProcesoProduccion[0]));
			return lst;
		} catch (Exception e) {
			throw new Exception("Pos:" + pos + "--" + e.getMessage());
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void eliminar(String empresa, long folioProcesoProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProcesoProduccion", empresa + "-" + folioProcesoProduccion);
			DbProcesoProduccion dbProcesoProduccion = new DbProcesoProduccion(datastore.get(key));
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			// lstFiltros.add(new FilterPredicate("procesoProduccion", FilterOperator.EQUAL, procesoProduccion));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("El procesoProduccion " + procesoProduccion + " tiene producciones, imposible eliminar.");

			dbProcesoProduccion.eliminar(datastore, tx);

			tx.commit();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El procesoProduccion '" + folioProcesoProduccion + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

}
