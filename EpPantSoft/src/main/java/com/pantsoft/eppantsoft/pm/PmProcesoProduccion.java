package com.pantsoft.eppantsoft.pm;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
		try {
			if (lst == null || lst.getProcesos() == null || lst.getProcesos().length == 0)
				throw new Exception("Debe mandar al menos un procesoProduccion");

			// Campos que deben venir forzosamente
			// private String empresa;
			// private long folioProcesoProduccion;
			// private long folioPedido;
			// private long renglon;
			// private long folioProcesoOrigen;
			// private long folioProcesoDestino;
			// private String proceso;

			tx = ClsEntidad.iniciarTransaccion(datastore);

			SerProcesoProduccion serPrimerProceso = lst.getProcesos()[0];

			// Obtengo el Pedido
			Key key = KeyFactory.createKey("DbPedido", serPrimerProceso.getEmpresa() + "-" + serPrimerProceso.getFolioPedido());
			DbPedido dbPedido = null;
			try {
				dbPedido = new DbPedido(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el pedido " + serPrimerProceso.getFolioPedido());
			}

			// Obtengo el PedidoDet
			key = KeyFactory.createKey(key, "DbPedidoDet", serPrimerProceso.getEmpresa() + "-" + serPrimerProceso.getFolioPedido() + "-" + serPrimerProceso.getRenglon());
			DbPedidoDet dbPedidoDet;
			try {
				dbPedidoDet = new DbPedidoDet(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el renglón del pedido pedido " + serPrimerProceso.getFolioPedido() + "-" + serPrimerProceso.getRenglon());
			}

			for (SerProcesoProduccion serProcesoProduccion : lst.getProcesos()) {
				DbProcesoProduccion dbProcesoProduccion = null;

				if (!serPrimerProceso.getEmpresa().equals(serProcesoProduccion.getEmpresa()))
					throw new Exception("La empresa no puede ser diferente");
				if (serPrimerProceso.getFolioPedido() != serProcesoProduccion.getFolioPedido())
					throw new Exception("El folioPedido no puede ser diferente");
				if (serPrimerProceso.getRenglon() != serProcesoProduccion.getRenglon())
					throw new Exception("El renglón no puede ser diferente");
				if (serProcesoProduccion.getFolioProcesoProduccion() > 0) {
					// Ya existe se actualiza
					if (dbPedido.getTemporada() != serProcesoProduccion.getTemporada())
						throw new Exception("La temporada del proceso '" + serProcesoProduccion.getProceso() + "' no coincide con la del pedido. (PED:" + dbPedido.getFolioPedido() + ",PROC:" + serProcesoProduccion.getFolioProcesoProduccion() + ")");
					if (!dbPedidoDet.getModelo().equals(serProcesoProduccion.getModelo()))
						throw new Exception("El modelo del proceso '" + serProcesoProduccion.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + serProcesoProduccion.getFolioProcesoProduccion() + ")");
					if (!dbPedidoDet.getReferencia().equals(serProcesoProduccion.getReferencia()))
						throw new Exception("La referencia del proceso '" + serProcesoProduccion.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + serProcesoProduccion.getFolioProcesoProduccion() + ")");
					if (!dbPedidoDet.getTallas().equals(serProcesoProduccion.getTallas()))
						throw new Exception("Las tallas del proceso '" + serProcesoProduccion.getProceso() + "' no coincide con el del pedido. (PED:" + dbPedidoDet.getFolioPedido() + "-" + dbPedidoDet.getRenglon() + ",PROC:" + serProcesoProduccion.getFolioProcesoProduccion() + ")");

					// Obtengo el ProcesoProduccion
					key = KeyFactory.createKey("DbProcesoProduccion", serProcesoProduccion.getEmpresa() + "-" + serProcesoProduccion.getFolioProcesoProduccion());
					try {
						dbProcesoProduccion = new DbProcesoProduccion(datastore.get(tx, key));
					} catch (EntityNotFoundException e) {
						throw new Exception("No existe el ProcesoProduccion " + serProcesoProduccion.getFolioProcesoProduccion());
					}

					if (dbProcesoProduccion.getEstatus() == 0) {
						if (serProcesoProduccion.getFolioProcesoOrigen() != dbProcesoProduccion.getFolioProcesoOrigen())
							dbProcesoProduccion.setFolioProcesoOrigen(serProcesoProduccion.getFolioProcesoOrigen());
						if (serProcesoProduccion.getFolioProcesoDestino() != dbProcesoProduccion.getFolioProcesoDestino())
							dbProcesoProduccion.setFolioProcesoDestino(serProcesoProduccion.getFolioProcesoDestino());
						dbProcesoProduccion.guardar(datastore, tx);
					} else {
						if (serProcesoProduccion.getFolioProcesoOrigen() != dbProcesoProduccion.getFolioProcesoOrigen())
							throw new Exception("No se puede modificar el orden del procesoProduccion " + dbProcesoProduccion.getFolioProcesoProduccion() + " pues ya no se encuentra pendiente");
						if (serProcesoProduccion.getFolioProcesoDestino() != dbProcesoProduccion.getFolioProcesoDestino())
							throw new Exception("No se puede modificar el orden del procesoProduccion " + dbProcesoProduccion.getFolioProcesoProduccion() + " pues ya no se encuentra pendiente");
					}
				} else {
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

					dbProcesoProduccion = new DbProcesoProduccion(serProcesoProduccion);
					dbProcesoProduccion.guardar(datastore, tx);
				}
				lstResp.add(dbProcesoProduccion.toSerProcesoProduccion());
			}

			if (dbConsecutivo != null)
				dbConsecutivo.guardar(datastore, tx);

			tx.commit();

			lst.setProcesos(lstResp.toArray(new SerProcesoProduccion[0]));
			return lst;
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
