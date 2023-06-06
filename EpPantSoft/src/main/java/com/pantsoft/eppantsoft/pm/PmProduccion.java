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
import com.pantsoft.eppantsoft.entidades.DbModeloHabilitacion;
import com.pantsoft.eppantsoft.entidades.DbModeloProducido;
import com.pantsoft.eppantsoft.entidades.DbProduccion;
import com.pantsoft.eppantsoft.entidades.DbTelaHabilitacion;
import com.pantsoft.eppantsoft.serializable.SerProduccion;
import com.pantsoft.eppantsoft.util.ClsBlobReader;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProduccion {

	public SerProduccion agregar(SerProduccion serProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			serProduccion.setEstatus(0);
			serProduccion.setCantidadEntrega(0); // La cantidad entrega ya es por entradas de almacén
			DbProduccion dbProduccion = new DbProduccion(serProduccion);

			if (ClsEntidad.existeEntidad(datastore, "DbProduccion", dbProduccion.getKey().getName()))
				throw new ExcepcionControlada("La orden '" + serProduccion.getNumOrden() + "' ya existe.");

			if (!ClsUtil.esNulo(dbProduccion.getReferencia())) {
				try {
					Key key = KeyFactory.createKey("DbModeloProducido", dbProduccion.getEmpresa() + "-" + dbProduccion.getTemporada() + "-" + dbProduccion.getModelo());
					DbModeloProducido dbModPro = new DbModeloProducido(datastore.get(key));
					if (!dbModPro.getReferencia().equals(dbProduccion.getReferencia()))
						throw new Exception("La referencia no coincide con la que se marcó en el modelo para producción: " + dbModPro.getReferencia());
				} catch (EntityNotFoundException e) {
					DbModeloProducido dbModPro = new DbModeloProducido(dbProduccion.getEmpresa(), dbProduccion.getTemporada(), dbProduccion.getModelo(), dbProduccion.getReferencia());
					dbModPro.guardar(datastore, tx);
				}
			}

			dbProduccion.guardar(datastore, tx);
			tx.commit();

			return dbProduccion.toSerProduccion();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProduccion actualizar(SerProduccion serProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);
			Key key = KeyFactory.createKey("DbProduccion", serProduccion.getEmpresa() + "-" + serProduccion.getTemporada() + "-" + serProduccion.getNumOrden());

			// Cambios de estatus automáticos
			DbProduccion dbProduccion = new DbProduccion(datastore.get(key));
			if (!ClsUtil.esIgualConNulo(dbProduccion.getTaller(), serProduccion.getTaller()) || dbProduccion.getMtsEnviados1() != serProduccion.getMtsEnviados1() || dbProduccion.getHabilitacionEnviada() != serProduccion.getHabilitacionEnviada() || dbProduccion.getCantidadCorte() != serProduccion.getCantidadCorte() || dbProduccion.getCantidadEntrega() != serProduccion.getCantidadEntrega()) {
				int estatus = 0;
				if (!ClsUtil.esNulo(serProduccion.getTaller())) {
					estatus = 1;
					if (serProduccion.getMtsEnviados1() > 0) { // M enviados 1
						estatus = 2;
						if (serProduccion.getHabilitacionEnviada() == true) { // Habilitación enviada
							estatus = 3;
							if (serProduccion.getCantidadCorte() > 0) { // CCorte
								estatus = 4;
								if (serProduccion.getCantidadEntrega() > 0) { // CEntrega
									if (serProduccion.getCantidadEntrega() < (serProduccion.getCantidadCorte() * .9))
										estatus = 5;
									else
										estatus = 6;
								}
							}
						}
					}
				}
				serProduccion.setEstatus(estatus);
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
			dbProduccion.setReferencia(serProduccion.getReferencia());
			if (!ClsUtil.esNulo(dbProduccion.getReferencia())) {
				try {
					key = KeyFactory.createKey("DbModeloProducido", dbProduccion.getEmpresa() + "-" + dbProduccion.getTemporada() + "-" + dbProduccion.getModelo());
					DbModeloProducido dbModPro = new DbModeloProducido(datastore.get(key));
					if (!dbModPro.getReferencia().equals(dbProduccion.getReferencia()))
						throw new Exception("La referencia no coincide con la que se marcó en el modelo para producción: " + dbModPro.getReferencia());
				} catch (EntityNotFoundException e) {
					DbModeloProducido dbModPro = new DbModeloProducido(dbProduccion.getEmpresa(), dbProduccion.getTemporada(), dbProduccion.getModelo(), dbProduccion.getReferencia());
					dbModPro.guardar(datastore, tx);
				}
			}
			dbProduccion.setCantidad(serProduccion.getCantidad());
			dbProduccion.setCorteSobreTela(serProduccion.getCorteSobreTela());
			dbProduccion.setCantidadCorte(serProduccion.getCantidadCorte());
			// Cantidad Entrega ya es por entrada de almacén
			// dbProduccion.setCantidadEntrega(serProduccion.getCantidadEntrega());
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
			if (dbProduccion.getConsumo1() != serProduccion.getConsumo1()) {
				dbProduccion.setConsumo1(serProduccion.getConsumo1());
				// Busco la tela y si solo hay una, actualizo el trazo con el consumo 1
				List<Filter> lstFiltros = new ArrayList<Filter>();
				lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, dbProduccion.getEmpresa()));
				lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, dbProduccion.getTemporada()));
				lstFiltros.add(new FilterPredicate("modelo", FilterOperator.EQUAL, dbProduccion.getModelo()));
				lstFiltros.add(new FilterPredicate("referencia", FilterOperator.EQUAL, dbProduccion.getReferencia()));
				List<Entity> lstMaterias = ClsEntidad.ejecutarConsulta(datastore, "DbModeloHabilitacion", lstFiltros);

				DbModeloHabilitacion dbTela = null;
				if (lstMaterias != null && lstMaterias.size() > 0) {
					for (int i = 0; i < lstMaterias.size(); i++) {
						DbModeloHabilitacion dbModeloHabilitacion = new DbModeloHabilitacion(lstMaterias.get(i));
						try {
							DbTelaHabilitacion dbTelaHabilitacion = new DbTelaHabilitacion(ClsEntidad.obtenerEntidad(datastore, "DbTelaHabilitacion", dbModeloHabilitacion.getEmpresa() + "-" + dbModeloHabilitacion.getMateria()));
							if (dbTelaHabilitacion.getTipo().equals("T")) {
								if (dbTela == null) {
									dbTela = new DbModeloHabilitacion(datastore.get(tx, dbModeloHabilitacion.getKey()));
								} else {
									if (dbTela.getConsumo() < dbModeloHabilitacion.getConsumo()) {
										dbTela = new DbModeloHabilitacion(datastore.get(tx, dbModeloHabilitacion.getKey()));
									}
								}
							}
						} catch (EntityNotFoundException e) {
						}
					}
				}
				if (dbTela != null) {
					dbTela.setTrazo(dbProduccion.getConsumo1());
					if (dbProduccion.getCantidadCorte() <= 0) {
						dbTela.setConsumoReal(0D);
					} else {
						dbTela.setConsumoReal(ClsUtil.Redondear((dbProduccion.getMtsEnviados1() - dbProduccion.getMtsDevolucion1()) / dbProduccion.getCantidadCorte(), 2));
					}
					dbTela.guardar(datastore, tx);
				} else {
					throw new Exception("El modelo " + dbProduccion.getTemporada() + "-" + dbProduccion.getModelo() + "-" + dbProduccion.getReferencia() + " no tiene tela");
				}
			}

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
			dbProduccion.guardar(datastore, tx);

			tx.commit();

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

	public void actualizarEntrega(String empresa, long temporada, long numOrden, long cantidadEntrega) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbProduccion", empresa + "-" + temporada + "-" + numOrden);
			DbProduccion dbProduccion = new DbProduccion(datastore.get(key));

			if (dbProduccion.getCantidadEntrega() != cantidadEntrega) {
				dbProduccion.setCantidadEntrega(cantidadEntrega);
				int estatus = 0;
				if (!ClsUtil.esNulo(dbProduccion.getTaller())) {
					estatus = 1;
					if (dbProduccion.getMtsEnviados1() > 0) { // M enviados 1
						estatus = 2;
						if (dbProduccion.getHabilitacionEnviada() == true) { // Habilitación enviada
							estatus = 3;
							if (dbProduccion.getCantidadCorte() > 0) { // CCorte
								estatus = 4;
								if (dbProduccion.getCantidadEntrega() > 0) { // CEntrega
									if (dbProduccion.getCantidadEntrega() < (dbProduccion.getCantidadCorte() * .9))
										estatus = 5;
									else
										estatus = 6;
								}
							}
						}
					}
				}
				dbProduccion.setEstatus(estatus);
				dbProduccion.guardar(datastore);
			}
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + numOrden + "' no existe.");
		}
	}

	public void actualizarCostura(String empresa, long temporada, String blobStr) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ClsBlobReader blobR = new ClsBlobReader("¬", blobStr, true);
		while (blobR.siguienteFila()) {
			try {
				Key key = KeyFactory.createKey("DbProduccion", empresa + "-" + temporada + "-" + blobR.getValorLong("numOrden"));
				DbProduccion dbProduccion = new DbProduccion(datastore.get(key));

				if (dbProduccion.getCostura() != blobR.getValorDbl("costura")) {
					dbProduccion.setCostura(blobR.getValorDbl("costura"));
					dbProduccion.guardar(datastore);
				}
			} catch (EntityNotFoundException e) {
				throw new Exception("La orden '" + blobR.getValorLong("orden") + "' no existe.");
			}
		}
	}

	public SerProduccion actualizarDepartamento(SerProduccion serProduccion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbProduccion", serProduccion.getEmpresa() + "-" + serProduccion.getTemporada() + "-" + serProduccion.getNumOrden());
			DbProduccion dbProduccion = new DbProduccion(datastore.get(key));

			dbProduccion.setDepartamento(serProduccion.getDepartamento());
			dbProduccion.guardar(datastore);

			return dbProduccion.toSerProduccion();
		} catch (EntityNotFoundException e) {
			throw new Exception("La orden '" + serProduccion.getNumOrden() + "' no existe.");
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

				if (!ClsUtil.esNulo(dbProduccion.getReferencia())) {
					try {
						key = KeyFactory.createKey("DbModeloProducido", dbProduccion.getEmpresa() + "-" + dbProduccion.getTemporada() + "-" + dbProduccion.getModelo());
						DbModeloProducido dbModPro = new DbModeloProducido(datastore.get(key));
						if (!dbModPro.getReferencia().equals(dbProduccion.getReferencia()))
							throw new Exception("La referencia no coincide con la que se marcó en el modelo para producción en la nueva temporada: " + dbModPro.getReferencia());
					} catch (EntityNotFoundException e) {
						DbModeloProducido dbModPro = new DbModeloProducido(dbProduccion.getEmpresa(), dbProduccion.getTemporada(), dbProduccion.getModelo(), dbProduccion.getReferencia());
						dbModPro.guardar(datastore, tx);
					}
				}

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
