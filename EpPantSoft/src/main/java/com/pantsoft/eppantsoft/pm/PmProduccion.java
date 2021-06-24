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
import com.pantsoft.eppantsoft.entidades.DbProduccion;
import com.pantsoft.eppantsoft.serializable.SerProduccion;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProduccion {

	public void agregar(SerProduccion serProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		serProduccion.setEstatus(0);
		DbProduccion dbProduccion = new DbProduccion(serProduccion);

		if (ClsEntidad.existeEntidad(datastore, "DbProduccion", dbProduccion.getKey().getName()))
			throw new ExcepcionControlada("La orden '" + serProduccion.getNumOrden() + "' ya existe.");

		dbProduccion.guardar(datastore);
	}

	public SerProduccion actualizar(SerProduccion serProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbProduccion", serProduccion.getEmpresa() + "-" + serProduccion.getTemporada() + "-" + serProduccion.getNumOrden());
			DbProduccion dbProduccion = new DbProduccion(datastore.get(key));

			// Cambios de estatus automáticos
			if (dbProduccion.getEstatus() == 0 && ClsUtil.esNulo(dbProduccion.getTaller()) && !ClsUtil.esNulo(serProduccion.getTaller())) {
				serProduccion.setEstatus(1);
			} else if (dbProduccion.getEstatus() == 1 && dbProduccion.getMtsEnviados1() == 0 && serProduccion.getMtsEnviados1() != 0) {
				serProduccion.setEstatus(2);
			} else if (dbProduccion.getEstatus() == 2 && dbProduccion.getHabilitacionEnviada() == false && serProduccion.getHabilitacionEnviada() == true) {
				serProduccion.setEstatus(3);
			} else if (dbProduccion.getEstatus() == 3 && dbProduccion.getCantidadCorte() == 0 && serProduccion.getCantidadCorte() != 0) {
				serProduccion.setEstatus(4);
			} else if (dbProduccion.getEstatus() == 4 && dbProduccion.getCantidadEntrega() == 0 && serProduccion.getCantidadEntrega() != 0) {
				if (serProduccion.getCantidadEntrega() < (serProduccion.getCantidadCorte() * .9))
					serProduccion.setEstatus(5);
				else
					serProduccion.setEstatus(6);
			}

			if (!ClsUtil.esIgualConNulo(dbProduccion.getMaquileroCorte(), serProduccion.getMaquileroCorte()))
				dbProduccion.setMaquileroCorte(serProduccion.getMaquileroCorte());
			if (!ClsUtil.esIgualConNulo(dbProduccion.getCliente(), serProduccion.getCliente()))
				dbProduccion.setCliente(serProduccion.getCliente());
			if (!ClsUtil.esIgualConNulo(dbProduccion.getDepartamento(), serProduccion.getDepartamento()))
				dbProduccion.setDepartamento(serProduccion.getDepartamento());
			if (!ClsUtil.esIgualConNulo(dbProduccion.getDescripcion(), serProduccion.getDescripcion()))
				dbProduccion.setDescripcion(serProduccion.getDescripcion());
			dbProduccion.setEstatus(serProduccion.getEstatus());
			dbProduccion.setFechaProgramada(serProduccion.getFechaProgramada());
			dbProduccion.setModelo(serProduccion.getModelo());
			dbProduccion.setCantidad(serProduccion.getCantidad());
			dbProduccion.setCorteSobreTela(serProduccion.getCorteSobreTela());
			dbProduccion.setCantidadCorte(serProduccion.getCantidadCorte());
			dbProduccion.setCantidadEntrega(serProduccion.getCantidadEntrega());
			dbProduccion.setFaltanteMaquilero(serProduccion.getFaltanteMaquilero());
			dbProduccion.setFaltanteCorte(serProduccion.getFaltanteCorte());
			dbProduccion.setTaller(serProduccion.getTaller());
			dbProduccion.setPrecio(serProduccion.getPrecio());
			dbProduccion.setTotal(serProduccion.getTotal());
			dbProduccion.setPrecioFaltante(serProduccion.getPrecioFaltante());
			dbProduccion.setTotalPorPagar(serProduccion.getTotalPorPagar());
			dbProduccion.setFechaSalida(serProduccion.getFechaSalida());
			dbProduccion.setFechaEntrega(serProduccion.getFechaEntrega());
			dbProduccion.setProceso1(serProduccion.getProceso1());
			dbProduccion.setTallerProceso1(serProduccion.getTallerProceso1());
			dbProduccion.setPrecioProceso1(serProduccion.getPrecioProceso1());
			dbProduccion.setProceso2(serProduccion.getProceso2());
			dbProduccion.setTallerProceso2(serProduccion.getTallerProceso2());
			dbProduccion.setPrecioProceso2(serProduccion.getPrecioProceso2());
			dbProduccion.setConsumo1(serProduccion.getConsumo1());
			dbProduccion.setMtsSolicitados1(serProduccion.getMtsSolicitados1());
			dbProduccion.setMtsEnviados1(serProduccion.getMtsEnviados1());
			dbProduccion.setMtsDevolucion1(serProduccion.getMtsDevolucion1());
			dbProduccion.setMtsFaltante1(serProduccion.getMtsFaltante1());
			dbProduccion.setDiferencia1(serProduccion.getDiferencia1());
			dbProduccion.setConsumo2(serProduccion.getConsumo2());
			dbProduccion.setMtsSolicitados2(serProduccion.getMtsSolicitados2());
			dbProduccion.setMtsEnviados2(serProduccion.getMtsEnviados2());
			dbProduccion.setDiferencia2(serProduccion.getDiferencia2());
			dbProduccion.setConsumo3(serProduccion.getConsumo3());
			dbProduccion.setMtsSolicitados3(serProduccion.getMtsSolicitados3());
			dbProduccion.setMtsEnviados3(serProduccion.getMtsEnviados3());
			dbProduccion.setDiferencia3(serProduccion.getDiferencia3());
			dbProduccion.setObservaciones(serProduccion.getObservaciones());
			dbProduccion.setRevisado(serProduccion.getRevisado());
			dbProduccion.setIsaac(serProduccion.getIsaac());
			dbProduccion.setMes(serProduccion.getMes());
			dbProduccion.setUsuarioModifico(serProduccion.getUsuarioModifico());
			dbProduccion.setFechaModifico(new Date());
			dbProduccion.setHabilitacionEnviada(serProduccion.getHabilitacionEnviada());
			dbProduccion.guardar(datastore);

			return dbProduccion.toSerProduccion();
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serProduccion.getNumOrden() + "' no existe.");
		}
	}

	public void actualizarEstatus(String empresa, long temporada, int estatus, List<Long> lstOrdenes) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		for (long orden : lstOrdenes) {
			try {
				Key key = KeyFactory.createKey("DbProduccion", empresa + "-" + temporada + "-" + orden);
				DbProduccion dbProduccion = new DbProduccion(datastore.get(key));

				if (dbProduccion.getEstatus() != estatus) {
					dbProduccion.setEstatus(estatus);
					dbProduccion.guardar(datastore);
				}
			} catch (EntityNotFoundException e) {
				throw new Exception("La orden '" + orden + "' no existe.");
			}
		}
	}

	public void cambiarTemporada(String empresa, long temporada, long nuevaTemporada, List<Long> lstOrdenes) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		for (long orden : lstOrdenes) {
			try {
				tx = ClsEntidad.iniciarTransaccion(datastore);

				// Busco y elimino la entidad original
				Key key = KeyFactory.createKey("DbProduccion", empresa + "-" + temporada + "-" + orden);
				DbProduccion dbProduccion = new DbProduccion(datastore.get(key));
				SerProduccion serProduccion = dbProduccion.toSerProduccion();
				dbProduccion.eliminar(datastore, tx);

				// Asigno la nueva temporada
				serProduccion.setTemporada(nuevaTemporada);

				// Creo y grabo el nuevo registro
				dbProduccion = new DbProduccion(serProduccion);
				if (ClsEntidad.existeEntidad(datastore, "DbProduccion", dbProduccion.getKey().getName()))
					throw new ExcepcionControlada("La orden '" + serProduccion.getNumOrden() + "' ya existe.");
				dbProduccion.guardar(datastore);

				tx.commit();
			} catch (EntityNotFoundException e) {
				throw new Exception("La orden '" + orden + "' no existe.");
			} finally {
				if (tx != null && tx.isActive())
					tx.rollback();
			}
		}
	}

	public SerProduccion[] dameProducciones(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstProduccion = ClsEntidad.ejecutarConsulta(datastore, "DbProduccion", lstFiltros);
		if (lstProduccion == null || lstProduccion.size() == 0)
			return new SerProduccion[0];
		SerProduccion[] arr = new SerProduccion[lstProduccion.size()];
		for (int i = 0; i < lstProduccion.size(); i++) {
			arr[i] = new DbProduccion(lstProduccion.get(i)).toSerProduccion();
		}
		return arr;
	}

	public void eliminar(String empresa, long temporada, long numOrden) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbProduccion", empresa + "-" + temporada + "-" + numOrden);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbAlmEntradaDetLote", lstFiltros))
			// throw new Exception("El almacén " + almacen + " tiene entradas, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La orden '" + numOrden + "' no existe.");
		}
	}

}
