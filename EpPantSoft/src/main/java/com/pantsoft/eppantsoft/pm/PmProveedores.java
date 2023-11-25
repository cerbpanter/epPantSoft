package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.entidades.DbProveedorPago;
import com.pantsoft.eppantsoft.serializable.SerProveedorPago;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProveedores {

	// ProveedorPago ////////////////////////////////////////////////////////////////////
	public SerProveedorPago agregarProveedorPago(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		String pos = "inico";
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			pos = "Db";
			serProveedorPago.setRevisado(false);
			serProveedorPago.setAutorizado(false);
			serProveedorPago.setPagado(false);
			serProveedorPago.setTerminado(false);
			serProveedorPago.setFechaVencimiento(null);
			DbProveedorPago dbProveedorPago = new DbProveedorPago(serProveedorPago);

			pos = "existe";
			if (ClsEntidad.existeEntidad(datastore, "DbProveedorPago", dbProveedorPago.getKey().getName()))
				throw new ExcepcionControlada("La Factura de proveedor '" + serProveedorPago.getUuid() + "' ya existe.");

			pos = "guardar";
			dbProveedorPago.guardar(datastore);
			pos = "commit";
			tx.commit();

			pos = "serializar";
			return dbProveedorPago.toSerProveedorPago();
		} catch (Exception e) {
			throw new Exception("Pos: " + pos + " - " + e.getMessage());
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProveedorPago marcarRevisado(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
			DbProveedorPago dbProveedorPago = new DbProveedorPago(datastore.get(key));

			if (dbProveedorPago.getRevisado())
				throw new Exception("La proveedorPago ya está revisado");

			dbProveedorPago.setFechaVencimiento(serProveedorPago.getFechaVencimiento(), serProveedorPago.getZonaHoraria());
			dbProveedorPago.setRevisado(true);

			dbProveedorPago.guardar(datastore, tx);
			tx.commit();

			dbProveedorPago = new DbProveedorPago(datastore.get(key));
			return dbProveedorPago.toSerProveedorPago();
		} catch (EntityNotFoundException e) {
			throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProveedorPago marcarAutorizado(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
			DbProveedorPago dbProveedorPago = new DbProveedorPago(datastore.get(key));

			if (dbProveedorPago.getAutorizado())
				throw new Exception("La proveedorPago ya está autorizado");

			dbProveedorPago.setRevisado(true);

			dbProveedorPago.guardar(datastore, tx);
			tx.commit();

			dbProveedorPago = new DbProveedorPago(datastore.get(key));
			return dbProveedorPago.toSerProveedorPago();
		} catch (EntityNotFoundException e) {
			throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProveedorPago marcarPagado(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
			DbProveedorPago dbProveedorPago = new DbProveedorPago(datastore.get(key));

			if (dbProveedorPago.getPagado())
				throw new Exception("La proveedorPago ya está pagado");

			dbProveedorPago.setPagado(true);

			dbProveedorPago.guardar(datastore, tx);
			tx.commit();

			dbProveedorPago = new DbProveedorPago(datastore.get(key));
			return dbProveedorPago.toSerProveedorPago();
		} catch (EntityNotFoundException e) {
			throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProveedorPago marcarTerminado(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
			DbProveedorPago dbProveedorPago = new DbProveedorPago(datastore.get(key));

			if (dbProveedorPago.getTerminado())
				throw new Exception("La proveedorPago ya está terminado");

			dbProveedorPago.setTerminado(true);

			dbProveedorPago.guardar(datastore, tx);
			tx.commit();

			dbProveedorPago = new DbProveedorPago(datastore.get(key));
			return dbProveedorPago.toSerProveedorPago();
		} catch (EntityNotFoundException e) {
			throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void eliminarProveedorPago(String empresa, String uuid) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key key = KeyFactory.createKey("DbProveedorPago", empresa + "-" + uuid);
			DbProveedorPago dbProveedorPago = new DbProveedorPago(datastore.get(key));

			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("folioProveedorPago", FilterOperator.EQUAL, folioProveedorPago));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProveedorPagoProceso", lstFiltros))
			// throw new Exception("La proveedorPago " + folioProveedorPago + " tiene procesos, imposible eliminar.");

			dbProveedorPago.eliminar(datastore, tx);

			tx.commit();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La proveedorPago '" + uuid + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

}
