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
import com.pantsoft.eppantsoft.serializable.SerAlmEntrada;
import com.pantsoft.eppantsoft.serializable.SerAlmEntradaDet;
import com.pantsoft.eppantsoft.serializable.SerAlmSalida;
import com.pantsoft.eppantsoft.serializable.SerAlmSalidaDet;
import com.pantsoft.eppantsoft.serializable.SerAlmacen;
import com.pantsoft.eppantsoft.serializable.SerInvModeloDet;
import com.pantsoft.eppantsoft.util.ClsBlobReader;
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
			serAlmEntrada.setFechaAlmEntrada(new Date());

			DbAlmEntrada dbAlmEntrada = new DbAlmEntrada(serAlmEntrada);

			if (ClsEntidad.existeEntidad(datastore, tx, "DbAlmEntrada", dbAlmEntrada.getKey().getName()))
				throw new ExcepcionControlada("El folio AlmEntrada '" + serAlmEntrada.getFolioAlmEntrada() + "' ya existe.");

			if (ClsUtil.esNulo(dbAlmEntrada.getDetalle()))
				throw new Exception("El detalle no puede estar vacío");

			ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmEntrada.getDetalle(), true);
			if (blobR.getLengthFilas() == 0)
				throw new Exception("La entrada de almacén debe tener al menos un código de barras");

			ArrayList<String> lstModelos = new ArrayList<String>();
			ArrayList<String> lstModelosTemporada = new ArrayList<String>();
			while (blobR.siguienteFila()) {
				SerAlmEntradaDet serDet = new SerAlmEntradaDet(dbAlmEntrada.getEmpresa(), folio, dbAlmEntrada.getAlmacen(), blobR.getValorStr("modelo"), blobR.getValorLong("temporada"), blobR.getValorStr("color"), blobR.getValorStr("talla"), blobR.getValorStr("codigoDeBarras"), dbAlmEntrada.getFechaAlmEntrada(), dbAlmEntrada.getDia(), dbAlmEntrada.getAnio(), dbAlmEntrada.getMes(), blobR.getValorLong("cantidad"));
				DbAlmEntradaDet dbDet = new DbAlmEntradaDet(serDet);
				dbDet.guardar(datastore, tx);

				// Calculo los modelos para guardarlos en un array indexado
				if (!lstModelos.contains(dbDet.getModelo()))
					lstModelos.add(dbDet.getModelo());
				if (!lstModelosTemporada.contains(dbDet.getModelo() + "|" + dbDet.getTemporada()))
					lstModelosTemporada.add(dbDet.getModelo() + "|" + dbDet.getTemporada());

				// Aumento el inventario
				Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
				Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getTemporada() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

				DbInvModeloDet dbInv;
				try {
					dbInv = new DbInvModeloDet(datastore.get(tx, key));
					dbInv.setCantidad(dbInv.getCantidad() + dbDet.getCantidad());
				} catch (EntityNotFoundException e) {
					SerInvModeloDet serInv = new SerInvModeloDet(dbDet.getEmpresa(), dbDet.getAlmacen(), dbDet.getModelo(), dbDet.getTemporada(), dbDet.getColor(), dbDet.getTalla(), dbDet.getCodigoDeBarras(), dbDet.getCantidad());
					dbInv = new DbInvModeloDet(serInv);
				}
				dbInv.guardar(datastore, tx);
			}
			dbAlmEntrada.setModelos(lstModelos);
			dbAlmEntrada.setModelosTemporada(lstModelosTemporada);

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

	// AlmSalida
	public SerAlmSalida almSalida_agregar(SerAlmSalida serAlmSalida) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			long folio = ClsUtil.dameSiguienteId(serAlmSalida.getEmpresa(), 0L, "AlmSalida", datastore, tx);
			serAlmSalida.setFolioAlmSalida(folio);
			serAlmSalida.setFechaAlmSalida(new Date());

			DbAlmSalida dbAlmSalida = new DbAlmSalida(serAlmSalida);

			if (ClsEntidad.existeEntidad(datastore, tx, "DbAlmSalida", dbAlmSalida.getKey().getName()))
				throw new ExcepcionControlada("El folio AlmSalida '" + serAlmSalida.getFolioAlmSalida() + "' ya existe.");

			if (ClsUtil.esNulo(dbAlmSalida.getDetalle()))
				throw new Exception("El detalle no puede estar vacío");

			ClsBlobReader blobR = new ClsBlobReader("¬", dbAlmSalida.getDetalle(), true);
			if (blobR.getLengthFilas() == 0)
				throw new Exception("La salida de almacén debe tener al menos un código de barras");

			ArrayList<DbAlmSalidaDet> lstDbDetalle = new ArrayList<DbAlmSalidaDet>();
			ArrayList<String> lstModelos = new ArrayList<String>();
			ArrayList<String> lstModelosTemporada = new ArrayList<String>();
			while (blobR.siguienteFila()) {
				SerAlmSalidaDet serDet = new SerAlmSalidaDet(dbAlmSalida.getEmpresa(), folio, dbAlmSalida.getAlmacen(), blobR.getValorStr("modelo"), blobR.getValorLong("temporada"), blobR.getValorStr("color"), blobR.getValorStr("talla"), blobR.getValorStr("codigoDeBarras"), dbAlmSalida.getFechaAlmSalida(), dbAlmSalida.getDia(), dbAlmSalida.getMes(), dbAlmSalida.getAnio(), blobR.getValorLong("cantidad"));
				DbAlmSalidaDet dbDet = new DbAlmSalidaDet(serDet);
				dbDet.guardar(datastore, tx);
				lstDbDetalle.add(dbDet);

				// Calculo los modelos para guardarlos en un array indexado
				if (!lstModelos.contains(dbDet.getModelo()))
					lstModelos.add(dbDet.getModelo());
				if (!lstModelosTemporada.contains(dbDet.getModelo() + "|" + dbDet.getTemporada()))
					lstModelosTemporada.add(dbDet.getModelo() + "|" + dbDet.getTemporada());

				// Decremento el inventario
				Key keyp = KeyFactory.createKey("DbEmpresa", dbDet.getEmpresa());
				Key key = KeyFactory.createKey(keyp, "DbInvModeloDet", dbDet.getEmpresa() + "-" + dbDet.getAlmacen() + "-" + dbDet.getModelo() + "-" + dbDet.getTemporada() + "-" + dbDet.getColor() + "-" + dbDet.getTalla());

				try {
					DbInvModeloDet dbInv = new DbInvModeloDet(datastore.get(tx, key));
					if (dbInv.getCantidad() < dbDet.getCantidad()) {
						throw new Exception("No hay suficiente inventario para " + dbDet.getModelo() + "-" + dbDet.getTemporada() + "-" + dbDet.getColor() + "-" + dbDet.getTalla() + " (Inv: " + dbInv.getCantidad() + ", Cant: " + dbDet.getCantidad() + ")");
					} else if (dbInv.getCantidad() == dbDet.getCantidad()) {
						// Si se va a cero se elimina el regitro de inventario
						dbInv.eliminar(datastore, tx);
					} else {
						dbInv.setCantidad(dbInv.getCantidad() - dbDet.getCantidad());
						dbInv.guardar(datastore, tx);
					}
				} catch (EntityNotFoundException e) {
					throw new Exception("No hay suficiente inventario para " + dbDet.getModelo() + "-" + dbDet.getTemporada() + "-" + dbDet.getColor() + "-" + dbDet.getTalla() + " (Inv: 0, Cant: " + dbDet.getCantidad() + ")");
				}
			}
			dbAlmSalida.setDbDetalle(lstDbDetalle);
			dbAlmSalida.setModelos(lstModelos);
			dbAlmSalida.setModelosTemporada(lstModelosTemporada);

			if (dbAlmSalida.getTipo() == 3) {
				// Es traspaso, se hace la entrada de almacen
				if (ClsUtil.esNulo(dbAlmSalida.getAlmacenTraspaso()))
					throw new Exception("El almacén de traspaso es requerido para salidas de traspaso");
				if (dbAlmSalida.getAlmacenTraspaso().equals(dbAlmSalida.getAlmacen()))
					throw new Exception("No se pueden hacer traspaso al mismo almacén");
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
