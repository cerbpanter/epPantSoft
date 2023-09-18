package com.pantsoft.eppantsoft.pm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.entidades.DbPedido;
import com.pantsoft.eppantsoft.entidades.DbPedidoDet;
import com.pantsoft.eppantsoft.serializable.SerPedido;
import com.pantsoft.eppantsoft.serializable.SerPedidoDet;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmPedido {

	// Pedido ////////////////////////////////////////////////////////////////////
	public SerPedido agregar(SerPedido serPedido) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			long folio = ClsUtil.dameSiguienteId(serPedido.getEmpresa(), 0L, "Pedido", datastore, tx);
			serPedido.setFolioPedido(folio);

			serPedido.setFechaPedido(new Date());
			DbPedido dbPedido = new DbPedido(serPedido);
			// dbPedido.setFecha(new Date());

			if (ClsEntidad.existeEntidad(datastore, "DbPedido", dbPedido.getKey().getName()))
				throw new ExcepcionControlada("El pedido '" + serPedido.getFolioPedido() + "' ya existe.");

			ArrayList<String> lstModelos = new ArrayList<String>();
			// Guardo los detalles
			if (serPedido.getDetalles() != null && serPedido.getDetalles().length > 0) {
				for (SerPedidoDet serDet : serPedido.getDetalles()) {
					serDet.setEmpresa(serPedido.getEmpresa());
					serDet.setFolioPedido(serPedido.getFolioPedido());
					serDet.setTemporada(serPedido.getTemporada());
					DbPedidoDet dbDet = new DbPedidoDet(serDet);
					dbDet.guardar(datastore, tx);
					lstModelos.add(serDet.getModelo());
				}
			}

			dbPedido.setModelos(lstModelos);
			dbPedido.guardar(datastore);
			tx.commit();

			return dbPedido.toSerPedido(datastore, null);
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerPedido actualizar(SerPedido serPedido) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbPedido", serPedido.getEmpresa() + "-" + serPedido.getFolioPedido());
			DbPedido dbPedido = new DbPedido(datastore.get(key));

			dbPedido.setFolioCliente(serPedido.getFolioCliente());
			dbPedido.setCliente(serPedido.getCliente());
			dbPedido.setSubCliente(serPedido.getSubCliente());
			dbPedido.setFechaCancelacion(serPedido.getFechaCancelacion(), serPedido.getZonaHoraria());
			dbPedido.setDepartamento(serPedido.getDepartamento());
			dbPedido.setConfirmado(serPedido.getConfirmado());
			dbPedido.setMarca(serPedido.getMarca());

			// Guardo los detalles
			ArrayList<String> lstModelos = new ArrayList<String>();
			Map<Long, Boolean> mapDetalles = new HashMap<Long, Boolean>();
			if (serPedido.getDetalles() != null && serPedido.getDetalles().length > 0) {
				for (SerPedidoDet serDet : serPedido.getDetalles()) {
					serDet.setEmpresa(serPedido.getEmpresa());
					serDet.setFolioPedido(serPedido.getFolioPedido());
					serDet.setTemporada(serPedido.getTemporada());
					mapDetalles.put(serDet.getRenglon(), true);
					lstModelos.add(serDet.getModelo());
					// Busco el detalle
					boolean esNuevo = true;
					for (DbPedidoDet dbDet : dbPedido.getDetalles(datastore, tx)) {
						if (dbDet.getRenglon() == serDet.getRenglon()) {
							dbDet.setModelo(serDet.getModelo());
							dbDet.setReferencia(serDet.getReferencia());
							dbDet.setPrecio(serDet.getPrecio());
							dbDet.setCantidad(serDet.getCantidad());
							dbDet.setTallas(serDet.getTallas());
							dbDet.setObservaciones(serDet.getObservaciones());
							dbDet.setDetalle(serDet.getDetalle());
							dbDet.setRevisado(serDet.getRevisado());
							dbDet.guardar(datastore, tx);
							esNuevo = false;
							break;
						}
					}
					if (esNuevo) {
						DbPedidoDet dbHab = new DbPedidoDet(serDet);
						dbHab.guardar(datastore, tx);
					}
				}
			}
			if (dbPedido.getDetalles(datastore, tx) != null && dbPedido.getDetalles(datastore, tx).size() > 0) {
				for (DbPedidoDet dbDet : dbPedido.getDetalles(datastore, tx)) {
					if (!mapDetalles.containsKey(dbDet.getRenglon()))
						dbDet.eliminar(datastore, tx);
				}
			}

			dbPedido.setModelos(lstModelos);
			dbPedido.guardar(datastore, tx);
			tx.commit();

			dbPedido = new DbPedido(datastore.get(key));
			return dbPedido.toSerPedido(datastore, null);
		} catch (EntityNotFoundException e) {
			throw new Exception("El pedido '" + serPedido.getFolioPedido() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void eliminar(String empresa, long folioPedido) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbPedido", empresa + "-" + folioPedido);
			DbPedido dbPedido = new DbPedido(datastore.get(key));
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			// lstFiltros.add(new FilterPredicate("pedido", FilterOperator.EQUAL, pedido));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("El pedido " + pedido + " tiene producciones, imposible eliminar.");

			dbPedido.eliminar(datastore, tx);

			for (DbPedidoDet dbDet : dbPedido.getDetalles(datastore, tx)) {
				dbDet.eliminar(datastore, tx);
			}

			tx.commit();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El pedido '" + folioPedido + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

}
