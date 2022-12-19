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
import com.pantsoft.eppantsoft.entidades.DbAlmEntrada;
import com.pantsoft.eppantsoft.entidades.DbAlmEntradaDet;
import com.pantsoft.eppantsoft.entidades.DbAlmSalida;
import com.pantsoft.eppantsoft.entidades.DbAlmSalidaDet;
import com.pantsoft.eppantsoft.entidades.DbAlmacen;
import com.pantsoft.eppantsoft.entidades.DbInvModeloDet;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerAlmEntrada;
import com.pantsoft.eppantsoft.serializable.SerAlmEntradaDet;
import com.pantsoft.eppantsoft.serializable.SerAlmSalida;
import com.pantsoft.eppantsoft.serializable.SerAlmSalidaDet;
import com.pantsoft.eppantsoft.serializable.SerAlmacen;
import com.pantsoft.eppantsoft.serializable.SerInvModeloDet;
import com.pantsoft.eppantsoft.util.ClsBlobReader;
import com.pantsoft.eppantsoft.util.ClsBlobWriter;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmAlmacen {

	// AlmEntrada
	public SerAlmEntrada almEntrada_agregar(SerAlmEntrada serAlmEntrada, DatastoreService datastore, Transaction tx) throws Exception {
		boolean hacerCommit = false;
		try {
			if (datastore == null) {
				if (tx != null)
					throw new Exception("La tx debe ser null cuando datastore es null");
				datastore = DatastoreServiceFactory.getDatastoreService();
				tx = ClsEntidad.iniciarTransaccion(datastore);
				hacerCommit = true;
			}

			long folio = ClsUtil.dameSiguienteId(serAlmEntrada.getEmpresa(), 0L, "AlmEntrada", datastore, tx);
			serAlmEntrada.setFolioAlmEntrada(folio);
			if (serAlmEntrada.getTipo() != 4) {
				serAlmEntrada.setFechaAlmEntrada(new Date());
			} else {
				if (serAlmEntrada.getFechaAlmEntrada() == null)
					throw new Exception("La entradas tipo factura deben traer fecha");
			}

			DbAlmEntrada dbAlmEntrada = new DbAlmEntrada(serAlmEntrada);

			if (ClsEntidad.existeEntidad(datastore, tx, "DbAlmEntrada", dbAlmEntrada.getKey().getName()))
				throw new ExcepcionControlada("El folio AlmEntrada '" + serAlmEntrada.getFolioAlmEntrada() + "' ya existe.");

			if (!serAlmEntrada.getTieneError() && ClsUtil.esNulo(dbAlmEntrada.getDetalle()))
				throw new Exception("El detalle no puede estar vacío");
			if (serAlmEntrada.getTieneError() && !ClsUtil.esNulo(dbAlmEntrada.getDetalle()))
				throw new Exception("El detalle debe estar vacío si tieneError");
			if (serAlmEntrada.getTipo() == 4) {
				if (serAlmEntrada.getSerieFactura() == null || serAlmEntrada.getFolioFactura() == 0)
					throw new Exception("Las entradas tipo factura deben tener serie y folio");
			} else {
				if (serAlmEntrada.getSerieFactura() != null || serAlmEntrada.getFolioFactura() != 0)
					throw new Exception("Solo las entradas tipo factura deben tener serie y folio");
			}

			if (serAlmEntrada.getTieneError()) {
				dbAlmEntrada.setDbDetalle(null);
				dbAlmEntrada.setModelos(null);
			} else {

				ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmEntrada.getDetalle(), true);
				if (blobR.getLengthFilas() == 0)
					throw new Exception("La entrada de almacén debe tener al menos un código de barras");

				ArrayList<DbAlmEntradaDet> lstDbDetalle = new ArrayList<DbAlmEntradaDet>();
				ArrayList<String> lstModelos = new ArrayList<String>();
				long cantidadTotal = 0;
				while (blobR.siguienteFila()) {
					SerAlmEntradaDet serDet = new SerAlmEntradaDet(dbAlmEntrada.getEmpresa(), folio, dbAlmEntrada.getAlmacen(), blobR.getValorStr("modelo"), blobR.getValorStr("color"), blobR.getValorStr("talla"), blobR.getValorStr("codigoDeBarras"), dbAlmEntrada.getFechaAlmEntrada(), dbAlmEntrada.getDia(), dbAlmEntrada.getAnio(), dbAlmEntrada.getMes(), blobR.getValorLong("cantidad"));
					DbAlmEntradaDet dbDet = new DbAlmEntradaDet(serDet);
					dbDet.guardar(datastore, tx);
					lstDbDetalle.add(dbDet);

					cantidadTotal += dbDet.getCantidad();

					// Calculo los modelos para guardarlos en un array indexado
					if (!lstModelos.contains(dbDet.getModelo()))
						lstModelos.add(dbDet.getModelo());

					// Aumento el inventario
					Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
					Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

					DbInvModeloDet dbInv;
					try {
						dbInv = new DbInvModeloDet(datastore.get(tx, key));
						dbInv.setCantidad(dbInv.getCantidad() + dbDet.getCantidad());
					} catch (EntityNotFoundException e) {
						SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad());
						dbInv = new DbInvModeloDet(serInv);
					}
					if (dbInv.getCantidad() == 0)
						dbInv.eliminar(datastore, tx);
					else
						dbInv.guardar(datastore, tx);
				}
				dbAlmEntrada.setDbDetalle(lstDbDetalle);
				dbAlmEntrada.setCantidadTotal(cantidadTotal);
				dbAlmEntrada.setModelos(lstModelos);
			}

			dbAlmEntrada.guardar(datastore, tx);
			if (hacerCommit) {
				tx.commit();
			}

			return dbAlmEntrada.toSerAlmEntrada();
		} finally {
			if (tx != null && tx.isActive() && hacerCommit == true)
				tx.rollback();
		}
	}

	public SerAlmEntrada almEntrada_actualizarConError(SerAlmEntrada serAlmEntrada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbAlmEntrada dbAlmEntrada;
			try {
				dbAlmEntrada = new DbAlmEntrada(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmEntrada", serAlmEntrada.getEmpresa() + "-" + serAlmEntrada.getFolioAlmEntrada()));
			} catch (EntityNotFoundException e1) {
				throw new ExcepcionControlada("El folio AlmSalida '" + serAlmEntrada.getFolioAlmEntrada() + "' no existe.");
			}

			if (!dbAlmEntrada.getTieneError())
				throw new Exception("La Entrada no tiene error, no se puede reprocesar");
			if (dbAlmEntrada.getTipo() != 2)
				throw new Exception("La entrada no es de tipo factura, no se puede reprocesar");
			if (dbAlmEntrada.getDetalle() != null)
				throw new Exception("La Entrada tiene detalle != null, imposible reprocesar");
			if (!serAlmEntrada.getSerieFactura().equals(dbAlmEntrada.getSerieFactura()))
				throw new Exception("No coincide la serie, imposible reprocesar");
			if (serAlmEntrada.getFolioFactura() != dbAlmEntrada.getFolioFactura())
				throw new Exception("No coincide el folio, imposible reprocesar");
			if (serAlmEntrada.getTieneError())
				throw new Exception("El serializable tieneError=true, imposible reprocesar");
			if (ClsUtil.esNulo(serAlmEntrada.getAlmacen()))
				throw new Exception("El serializable no tiene almacén, imposible reprocesar");

			dbAlmEntrada.setAlmacen(serAlmEntrada.getAlmacen());
			dbAlmEntrada.setDetalle(serAlmEntrada.getDetalle());
			dbAlmEntrada.setObservaciones(serAlmEntrada.getObservaciones());
			dbAlmEntrada.setTieneError(false);

			ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmEntrada.getDetalle(), true);
			if (blobR.getLengthFilas() == 0)
				throw new Exception("La entrada de almacén debe tener al menos un código de barras");

			ArrayList<DbAlmEntradaDet> lstDbDetalle = new ArrayList<DbAlmEntradaDet>();
			ArrayList<String> lstModelos = new ArrayList<String>();
			long cantidadTotal = 0;
			while (blobR.siguienteFila()) {
				SerAlmEntradaDet serDet = new SerAlmEntradaDet(dbAlmEntrada.getEmpresa(), dbAlmEntrada.getFolioAlmEntrada(), dbAlmEntrada.getAlmacen(), blobR.getValorStr("modelo"), blobR.getValorStr("color"), blobR.getValorStr("talla"), blobR.getValorStr("codigoDeBarras"), dbAlmEntrada.getFechaAlmEntrada(), dbAlmEntrada.getDia(), dbAlmEntrada.getAnio(), dbAlmEntrada.getMes(), blobR.getValorLong("cantidad"));
				DbAlmEntradaDet dbDet = new DbAlmEntradaDet(serDet);
				dbDet.guardar(datastore, tx);
				lstDbDetalle.add(dbDet);

				// Calculo los modelos para guardarlos en un array indexado
				if (!lstModelos.contains(dbDet.getModelo()))
					lstModelos.add(dbDet.getModelo());

				cantidadTotal += dbDet.getCantidad();

				// Aumento el inventario
				Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
				Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

				DbInvModeloDet dbInv;
				try {
					dbInv = new DbInvModeloDet(datastore.get(tx, key));
					dbInv.setCantidad(dbInv.getCantidad() + dbDet.getCantidad());
				} catch (EntityNotFoundException e) {
					SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad());
					dbInv = new DbInvModeloDet(serInv);
				}
				if (dbInv.getCantidad() == 0)
					dbInv.eliminar(datastore, tx);
				else
					dbInv.guardar(datastore, tx);
			}
			dbAlmEntrada.setDbDetalle(lstDbDetalle);
			dbAlmEntrada.setCantidadTotal(cantidadTotal);
			dbAlmEntrada.setModelos(lstModelos);

			dbAlmEntrada.guardar(datastore, tx);

			tx.commit();

			return dbAlmEntrada.toSerAlmEntrada();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerAlmEntrada almEntrada_eliminar(String empresa, long folioAlmEntrada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbAlmEntrada dbAlmEntrada;
			try {
				dbAlmEntrada = new DbAlmEntrada(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmEntrada", empresa + "-" + folioAlmEntrada));
			} catch (EntityNotFoundException e1) {
				throw new ExcepcionControlada("El folio AlmEntrada '" + folioAlmEntrada + "' no existe.");
			}

			List<DbAlmEntradaDet> lstDetalle = dbAlmEntrada.getDbDetalle(datastore, tx);
			for (DbAlmEntradaDet dbDet : lstDetalle) {
				// Decremento el inventario
				Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
				Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

				DbInvModeloDet dbInv;
				try {
					dbInv = new DbInvModeloDet(datastore.get(tx, key));
					dbInv.setCantidad(dbInv.getCantidad() - dbDet.getCantidad());
				} catch (EntityNotFoundException e) {
					SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad() * -1);
					dbInv = new DbInvModeloDet(serInv);
				}
				if (dbInv.getCantidad() == 0)
					dbInv.eliminar(datastore, tx);
				else
					dbInv.guardar(datastore, tx);
			}

			dbAlmEntrada.eliminar(datastore, tx);

			tx.commit();

			return dbAlmEntrada.toSerAlmEntrada();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public Respuesta almEntrada_cambiarCodigoDeBarras(String empresa, String codigoDeBarras, String codigoDeBarrasNuevo, String cursor) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Respuesta resp = new Respuesta();
		Transaction tx = null;

		if (cursor != null && cursor.equals("inicio"))
			cursor = null;

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("codigoDeBarras", FilterOperator.EQUAL, codigoDeBarras));
		List<Entity> lstDetalle = ClsEntidad.ejecutarConsultaSoloKeys(datastore, "DbAlmEntradaDet", lstFiltros, 200, cursor);
		if (lstDetalle != null && lstDetalle.size() > 0) {
			if (lstDetalle.size() == 200)
				resp.setCadena(ClsEntidad.getStrCursor());
			resp.setLargo(lstDetalle.size());
			for (Entity entidad : lstDetalle) {
				try {
					tx = datastore.beginTransaction();

					DbAlmEntradaDet dbDet = new DbAlmEntradaDet(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmEntradaDet", entidad.getKey().getName(), entidad.getKey().getParent()));
					dbDet.setCodigoDeBarras(codigoDeBarrasNuevo);
					dbDet.guardar(datastore, tx);
					// Corrigo el detalle en el encabezado
					DbAlmEntrada dbAlmEntrada;
					try {
						dbAlmEntrada = new DbAlmEntrada(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmEntrada", dbDet.getEmpresa() + "-" + dbDet.getFolioAlmEntrada()));
					} catch (EntityNotFoundException e) {
						throw new Exception("La entrada de almacén '" + dbDet.getFolioAlmEntrada() + "' no existe");
					}
					ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmEntrada.getDetalle(), true);
					ClsBlobWriter blobW = new ClsBlobWriter("¬");
					blobW.insertarAlInicioBlobStr("0|&NullSiNube;|modelo|String|color|String|talla|String|codigoDeBarras|String|cantidad|Long");
					while (blobR.siguienteFila()) {
						blobW.nuevaFila();
						blobW.agregarStr(blobR.getValorStr("modelo"));
						blobW.agregarStr(blobR.getValorStr("color"));
						blobW.agregarStr(blobR.getValorStr("talla"));
						if (blobR.getValorStr("modelo").equals(dbDet.getModelo()) && blobR.getValorStr("color").equals(dbDet.getColor()) && blobR.getValorStr("talla").equals(dbDet.getTalla())) {
							blobW.agregarStr(dbDet.getCodigoDeBarras());
						} else {
							blobW.agregarStr(blobR.getValorStr("codigoDeBarras"));
						}
						blobW.agregarLong(blobR.getValorLong("cantidad"));
					}
					dbAlmEntrada.setDetalle(blobW.getString());

					dbAlmEntrada.guardar(datastore, tx);
					tx.commit();
				} finally {
					if (tx != null && tx.isActive())
						tx.rollback();
					tx = null;
				}
			}
		}
		return resp;
	}

	// AlmSalida
	public SerAlmSalida almSalida_agregar(SerAlmSalida serAlmSalida) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			long folio = ClsUtil.dameSiguienteId(serAlmSalida.getEmpresa(), 0L, "AlmSalida", datastore, tx);
			serAlmSalida.setFolioAlmSalida(folio);
			if (serAlmSalida.getTipo() != 2) {
				serAlmSalida.setFechaAlmSalida(new Date());
			} else {
				if (serAlmSalida.getFechaAlmSalida() == null)
					throw new Exception("La salidas tipo factura deben traer fecha");
			}

			DbAlmSalida dbAlmSalida = new DbAlmSalida(serAlmSalida);

			if (ClsEntidad.existeEntidad(datastore, tx, "DbAlmSalida", dbAlmSalida.getKey().getName()))
				throw new ExcepcionControlada("El folio AlmSalida '" + serAlmSalida.getFolioAlmSalida() + "' ya existe.");

			if (!serAlmSalida.getTieneError() && ClsUtil.esNulo(dbAlmSalida.getDetalle()))
				throw new Exception("El detalle no puede estar vacío");
			if (serAlmSalida.getTieneError() && !ClsUtil.esNulo(dbAlmSalida.getDetalle()))
				throw new Exception("El detalle debe estar vacío si tieneError");
			if (serAlmSalida.getTipo() == 2) {
				if (serAlmSalida.getSerieFactura() == null || serAlmSalida.getFolioFactura() == 0)
					throw new Exception("Las salida tipo factura deben tener serie y folio");
			} else {
				if (serAlmSalida.getSerieFactura() != null || serAlmSalida.getFolioFactura() != 0)
					throw new Exception("Solo las salida tipo factura deben tener serie y folio");
			}

			if (serAlmSalida.getTieneError()) {
				dbAlmSalida.setDbDetalle(null);
				dbAlmSalida.setModelos(null);
			} else {
				ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmSalida.getDetalle(), true);
				if (blobR.getLengthFilas() == 0)
					throw new Exception("La salida de almacén debe tener al menos un código de barras");

				ArrayList<DbAlmSalidaDet> lstDbDetalle = new ArrayList<DbAlmSalidaDet>();
				ArrayList<String> lstModelos = new ArrayList<String>();
				long cantidadTotal = 0;
				while (blobR.siguienteFila()) {
					SerAlmSalidaDet serDet = new SerAlmSalidaDet(dbAlmSalida.getEmpresa(), folio, dbAlmSalida.getAlmacen(), blobR.getValorStr("modelo"), blobR.getValorStr("color"), blobR.getValorStr("talla"), blobR.getValorStr("codigoDeBarras"), dbAlmSalida.getFechaAlmSalida(), dbAlmSalida.getDia(), dbAlmSalida.getMes(), dbAlmSalida.getAnio(), blobR.getValorLong("cantidad"));
					DbAlmSalidaDet dbDet = new DbAlmSalidaDet(serDet);
					dbDet.guardar(datastore, tx);
					lstDbDetalle.add(dbDet);

					// Calculo los modelos para guardarlos en un array indexado
					if (!lstModelos.contains(dbDet.getModelo()))
						lstModelos.add(dbDet.getModelo());

					cantidadTotal += dbDet.getCantidad();

					// Decremento el inventario
					Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
					Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

					try {
						DbInvModeloDet dbInv = new DbInvModeloDet(datastore.get(tx, key));
						if (dbInv.getCantidad() < dbDet.getCantidad() && serAlmSalida.getValidarInventario()) {
							throw new Exception("No hay suficiente inventario para " + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla() + " (Inv: " + dbInv.getCantidad() + ", Cant: " + dbDet.getCantidad() + ")");
						}
						if (dbInv.getCantidad() == dbDet.getCantidad()) {
							// Si se va a cero se elimina el regitro de inventario
							dbInv.eliminar(datastore, tx);
						} else {
							dbInv.setCantidad(dbInv.getCantidad() - dbDet.getCantidad());
							dbInv.guardar(datastore, tx);
						}
					} catch (EntityNotFoundException e) {
						if (serAlmSalida.getValidarInventario())
							throw new Exception("No hay suficiente inventario para " + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla() + " (Inv: 0, Cant: " + dbDet.getCantidad() + ")");
						SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad() * -1);
						DbInvModeloDet dbInv = new DbInvModeloDet(serInv);
						dbInv.guardar(datastore, tx);
					}
				}
				dbAlmSalida.setDbDetalle(lstDbDetalle);
				dbAlmSalida.setCantidadTotal(cantidadTotal);
				dbAlmSalida.setModelos(lstModelos);
			}

			if (dbAlmSalida.getTipo() == 3) {
				// Es traspaso, se hace la entrada de almacen
				if (ClsUtil.esNulo(dbAlmSalida.getAlmacenTraspaso()))
					throw new Exception("El almacén de traspaso es requerido para salidas de traspaso");
				if (dbAlmSalida.getAlmacenTraspaso().equals(dbAlmSalida.getAlmacen()))
					throw new Exception("No se pueden hacer traspaso al mismo almacén");
				if (serAlmSalida.getTieneError())
					throw new Exception("No se pueden hacer traspaso con tienError");
				SerAlmEntrada serAlmEntrada = dbAlmSalida.toSerAlmEntradaTraspaso(datastore, tx);
				serAlmEntrada = almEntrada_agregar(serAlmEntrada, datastore, tx);
				dbAlmSalida.setFolioAlmEntradaTraspaso(serAlmEntrada.getFolioAlmEntrada());
			} else {
				dbAlmSalida.setFolioAlmEntradaTraspaso(0L);
				dbAlmSalida.setAlmacenTraspaso(null);
			}

			dbAlmSalida.guardar(datastore, tx);

			tx.commit();

			return dbAlmSalida.toSerAlmSalida();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerAlmSalida almSalida_actualizarConError(SerAlmSalida serAlmSalida) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbAlmSalida dbAlmSalida;
			try {
				dbAlmSalida = new DbAlmSalida(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmSalida", serAlmSalida.getEmpresa() + "-" + serAlmSalida.getFolioAlmSalida()));
			} catch (EntityNotFoundException e1) {
				throw new ExcepcionControlada("El folio AlmSalida '" + serAlmSalida.getFolioAlmSalida() + "' no existe.");
			}

			if (!dbAlmSalida.getTieneError())
				throw new Exception("La salida no tiene error, no se puede reprocesar");
			if (dbAlmSalida.getTipo() != 2)
				throw new Exception("La salida no es de tipo factura, no se puede reprocesar");
			if (dbAlmSalida.getDetalle() != null)
				throw new Exception("La salida tiene detalle != null, imposible reprocesar");
			if (!serAlmSalida.getSerieFactura().equals(dbAlmSalida.getSerieFactura()))
				throw new Exception("No coincide la serie, imposible reprocesar");
			if (serAlmSalida.getFolioFactura() != dbAlmSalida.getFolioFactura())
				throw new Exception("No coincide el folio, imposible reprocesar");
			if (serAlmSalida.getTieneError())
				throw new Exception("El serializable tieneError=true, imposible reprocesar");
			if (ClsUtil.esNulo(serAlmSalida.getAlmacen()))
				throw new Exception("El serializable no tiene almacén, imposible reprocesar");

			dbAlmSalida.setAlmacen(serAlmSalida.getAlmacen());
			dbAlmSalida.setDetalle(serAlmSalida.getDetalle());
			dbAlmSalida.setObservaciones(serAlmSalida.getObservaciones());
			dbAlmSalida.setTieneError(false);

			ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmSalida.getDetalle(), true);
			if (blobR.getLengthFilas() == 0)
				throw new Exception("La salida de almacén debe tener al menos un código de barras");

			ArrayList<DbAlmSalidaDet> lstDbDetalle = new ArrayList<DbAlmSalidaDet>();
			ArrayList<String> lstModelos = new ArrayList<String>();
			long cantidadTotal = 0;
			while (blobR.siguienteFila()) {
				SerAlmSalidaDet serDet = new SerAlmSalidaDet(dbAlmSalida.getEmpresa(), dbAlmSalida.getFolioAlmSalida(), dbAlmSalida.getAlmacen(), blobR.getValorStr("modelo"), blobR.getValorStr("color"), blobR.getValorStr("talla"), blobR.getValorStr("codigoDeBarras"), dbAlmSalida.getFechaAlmSalida(), dbAlmSalida.getDia(), dbAlmSalida.getMes(), dbAlmSalida.getAnio(), blobR.getValorLong("cantidad"));
				DbAlmSalidaDet dbDet = new DbAlmSalidaDet(serDet);
				dbDet.guardar(datastore, tx);
				lstDbDetalle.add(dbDet);

				// Calculo los modelos para guardarlos en un array indexado
				if (!lstModelos.contains(dbDet.getModelo()))
					lstModelos.add(dbDet.getModelo());

				cantidadTotal += dbDet.getCantidad();

				// Decremento el inventario
				Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
				Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

				try {
					DbInvModeloDet dbInv = new DbInvModeloDet(datastore.get(tx, key));
					if (dbInv.getCantidad() < dbDet.getCantidad() && serAlmSalida.getValidarInventario()) {
						throw new Exception("No hay suficiente inventario para " + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla() + " (Inv: " + dbInv.getCantidad() + ", Cant: " + dbDet.getCantidad() + ")");
					}
					if (dbInv.getCantidad() == dbDet.getCantidad()) {
						// Si se va a cero se elimina el regitro de inventario
						dbInv.eliminar(datastore, tx);
					} else {
						dbInv.setCantidad(dbInv.getCantidad() - dbDet.getCantidad());
						dbInv.guardar(datastore, tx);
					}
				} catch (EntityNotFoundException e) {
					if (serAlmSalida.getValidarInventario())
						throw new Exception("No hay suficiente inventario para " + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla() + " (Inv: 0, Cant: " + dbDet.getCantidad() + ")");
					SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad() * -1);
					DbInvModeloDet dbInv = new DbInvModeloDet(serInv);
					dbInv.guardar(datastore, tx);
				}
			}
			dbAlmSalida.setDbDetalle(lstDbDetalle);
			dbAlmSalida.setCantidadTotal(cantidadTotal);
			dbAlmSalida.setModelos(lstModelos);

			dbAlmSalida.guardar(datastore, tx);

			tx.commit();

			return dbAlmSalida.toSerAlmSalida();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerAlmSalida almSalida_eliminar(String empresa, long folioAlmSalida) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbAlmSalida dbAlmSalida;
			try {
				dbAlmSalida = new DbAlmSalida(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmSalida", empresa + "-" + folioAlmSalida));
			} catch (EntityNotFoundException e1) {
				throw new ExcepcionControlada("El folio AlmSalida '" + folioAlmSalida + "' no existe.");
			}

			List<DbAlmSalidaDet> lstDetalle = dbAlmSalida.getDbDetalle(datastore, tx);
			for (DbAlmSalidaDet dbDet : lstDetalle) {
				// Aumento el inventario
				Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
				Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

				DbInvModeloDet dbInv;
				try {
					dbInv = new DbInvModeloDet(datastore.get(tx, key));
					dbInv.setCantidad(dbInv.getCantidad() + dbDet.getCantidad());
				} catch (EntityNotFoundException e) {
					SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad());
					dbInv = new DbInvModeloDet(serInv);
				}
				dbInv.guardar(datastore, tx);
			}

			dbAlmSalida.eliminar(datastore, tx);

			tx.commit();

			return dbAlmSalida.toSerAlmSalida();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public Respuesta almSalida_cambiarCodigoDeBarras(String empresa, String codigoDeBarras, String codigoDeBarrasNuevo, String cursor) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Respuesta resp = new Respuesta();
		Transaction tx = null;

		if (cursor != null && cursor.equals("inicio"))
			cursor = null;

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("codigoDeBarras", FilterOperator.EQUAL, codigoDeBarras));
		List<Entity> lstDetalle = ClsEntidad.ejecutarConsulta(datastore, "DbAlmSalidaDet", lstFiltros, 200, cursor);
		if (lstDetalle != null && lstDetalle.size() > 0) {
			if (lstDetalle.size() == 200)
				resp.setCadena(ClsEntidad.getStrCursor());
			resp.setLargo(lstDetalle.size());
			for (Entity entidad : lstDetalle) {
				try {
					tx = datastore.beginTransaction();

					DbAlmSalidaDet dbDet = new DbAlmSalidaDet(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmSalidaDet", entidad.getKey().getName(), entidad.getKey().getParent()));
					dbDet.setCodigoDeBarras(codigoDeBarrasNuevo);
					dbDet.guardar(datastore, tx);
					// Corrigo el detalle en el encabezado
					DbAlmSalida dbAlmSalida;
					try {
						dbAlmSalida = new DbAlmSalida(ClsEntidad.obtenerEntidad(datastore, tx, "DbAlmSalida", dbDet.getEmpresa() + "-" + dbDet.getFolioAlmSalida()));
					} catch (EntityNotFoundException e) {
						throw new Exception("La salida de almacén '" + dbDet.getFolioAlmSalida() + "' no existe");
					}
					ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmSalida.getDetalle(), true);
					ClsBlobWriter blobW = new ClsBlobWriter("¬");
					blobW.insertarAlInicioBlobStr("0|&NullSiNube;|modelo|String|color|String|talla|String|codigoDeBarras|String|cantidad|Long");
					while (blobR.siguienteFila()) {
						blobW.nuevaFila();
						blobW.agregarStr(blobR.getValorStr("modelo"));
						blobW.agregarStr(blobR.getValorStr("color"));
						blobW.agregarStr(blobR.getValorStr("talla"));
						if (blobR.getValorStr("modelo").equals(dbDet.getModelo()) && blobR.getValorStr("color").equals(dbDet.getColor()) && blobR.getValorStr("talla").equals(dbDet.getTalla())) {
							blobW.agregarStr(dbDet.getCodigoDeBarras());
						} else {
							blobW.agregarStr(blobR.getValorStr("codigoDeBarras"));
						}
						blobW.agregarLong(blobR.getValorLong("cantidad"));
					}
					dbAlmSalida.setDetalle(blobW.getString());

					dbAlmSalida.guardar(datastore, tx);
					tx.commit();
				} finally {
					if (tx != null && tx.isActive())
						tx.rollback();
					tx = null;
				}
			}
		}
		return resp;
	}

	// Inventario
	public void inventario_actualizar(SerAlmEntradaDet serDet) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key keyp = KeyFactory.createKey("DbEmpresa", serDet.getEmpresa());
		Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", serDet.getEmpresa() + "-" + serDet.getAlmacen() + "-" + serDet.getModelo() + "-" + serDet.getColor() + "-" + serDet.getTalla());

		DbInvModeloDet dbInv;
		try {
			dbInv = new DbInvModeloDet(datastore.get(key));
			if (serDet.getCantidad() == 0) {
				dbInv.eliminar(datastore);
			} else {
				dbInv.setCantidad(dbInv.getCantidad() + serDet.getCantidad());
				dbInv.guardar(datastore);
			}
		} catch (EntityNotFoundException e) {
			if (serDet.getCantidad() != 0L) {
				SerInvModeloDet serInv = new SerInvModeloDet(serDet.getEmpresa(), serDet.getAlmacen(), serDet.getModelo(), serDet.getColor(), serDet.getTalla(), serDet.getCodigoDeBarras(), serDet.getCantidad());
				dbInv = new DbInvModeloDet(serInv);
				dbInv.guardar(datastore);
			}
		}
	}

	// CatAlmacen
	public void catAlmacen_agregar(SerAlmacen serAlmacen) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbAlmacen dbAlmacen = new DbAlmacen(serAlmacen);

		if (ClsEntidad.existeEntidad(datastore, "DbAlmacen", dbAlmacen.getKey().getName()))
			throw new ExcepcionControlada("El Almacen '" + serAlmacen.getAlmacen() + "' ya existe.");

		dbAlmacen.guardar(datastore);
	}

	public void catAlmacen_eliminar(String empresa, String almacen) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbAlmacen", empresa + "-" + almacen);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Almacen", FilterOperator.EQUAL, Almacen));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Almacen " + Almacen + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El almacen '" + almacen + "' no existe.");
		}
	}

	public SerAlmacen[] catAlmacen_dameAlmacenes(String empresa) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		List<Entity> lstAlmacenes = ClsEntidad.ejecutarConsulta(datastore, "DbAlmacen", lstFiltros);
		if (lstAlmacenes == null || lstAlmacenes.size() == 0)
			return new SerAlmacen[0];
		SerAlmacen[] arr = new SerAlmacen[lstAlmacenes.size()];
		for (int i = 0; i < lstAlmacenes.size(); i++) {
			arr[i] = new DbAlmacen(lstAlmacenes.get(i)).toSerAlmacen();
		}
		return arr;
	}

}
