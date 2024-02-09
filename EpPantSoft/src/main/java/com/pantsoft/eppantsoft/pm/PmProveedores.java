package com.pantsoft.eppantsoft.pm;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.entidades.DbProveedorPago;
import com.pantsoft.eppantsoft.entidades.DbProveedorPagoMes;
import com.pantsoft.eppantsoft.serializable.SerProveedorPago;
import com.pantsoft.eppantsoft.serializable.SerProveedorPagoMes;
import com.pantsoft.eppantsoft.util.ClsBlobReader;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProveedores {
	// ProveedorPagoMes ////////////////////////////////////////////////////////////////////

	public void autorizarSemana(String empresa, long mesVencimiento, long semana) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		DbProveedorPagoMes dbProveedorPagoMes;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);
			try {
				Key key = KeyFactory.createKey("DbProveedorPagoMes", empresa + "-" + mesVencimiento);
				dbProveedorPagoMes = new DbProveedorPagoMes(datastore.get(key));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el DbProveedorPagoMes: " + mesVencimiento);
			}
			if (semana == 1) {
				if (dbProveedorPagoMes.getAutorizado1()) {
					throw new Exception("La semana 1 ya está autorizada");
				}
				dbProveedorPagoMes.setAutorizado1(true);
			} else if (semana == 2) {
				if (dbProveedorPagoMes.getAutorizado2()) {
					throw new Exception("La semana 2 ya está autorizada");
				}
				dbProveedorPagoMes.setAutorizado2(true);
			} else if (semana == 3) {
				if (dbProveedorPagoMes.getAutorizado3()) {
					throw new Exception("La semana 3 ya está autorizada");
				}
				dbProveedorPagoMes.setAutorizado3(true);
			} else if (semana == 4) {
				if (dbProveedorPagoMes.getAutorizado4()) {
					throw new Exception("La semana 4 ya está autorizada");
				}
				dbProveedorPagoMes.setAutorizado4(true);
			} else if (semana == 5) {
				if (dbProveedorPagoMes.getAutorizado5()) {
					throw new Exception("La semana 5 ya está autorizada");
				}
				dbProveedorPagoMes.setAutorizado5(true);
			} else {
				throw new Exception("La semana no es válida: " + semana);
			}
			dbProveedorPagoMes.guardar(datastore, tx);

			List<DbProveedorPago> lstProveedorPago = dbProveedorPagoMes.getPagos(datastore, tx);
			if (lstProveedorPago != null && lstProveedorPago.size() > 0) {
				for (DbProveedorPago dbProveedorPago : lstProveedorPago) {
					// Se autorizan solo los no terminados y no autorizados
					if (!dbProveedorPago.getTerminado() && dbProveedorPago.getAutorizado() == false && dbProveedorPago.getSemana() == semana) {
						dbProveedorPago.setAutorizado(true);
						dbProveedorPago.guardar(datastore, tx);
					}
				}
			}
			tx.commit();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void terminarMes(String empresa, long mesVencimiento) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		DbProveedorPagoMes dbProveedorPagoMes;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);
			try {
				Key key = KeyFactory.createKey("DbProveedorPagoMes", empresa + "-" + mesVencimiento);
				dbProveedorPagoMes = new DbProveedorPagoMes(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("No existe el DbProveedorPagoMes: " + mesVencimiento);
			}
			if (dbProveedorPagoMes.getTerminado()) {
				throw new Exception("El mes ya está terminado");
			}
			dbProveedorPagoMes.setTerminado(true);
			dbProveedorPagoMes.guardar(datastore, tx);

			List<DbProveedorPago> lstProveedorPago = dbProveedorPagoMes.getPagos(datastore, tx);
			if (lstProveedorPago != null && lstProveedorPago.size() > 0) {
				for (DbProveedorPago dbProveedorPago : lstProveedorPago) {
					// Se autorizan solo los no terminados
					if (!dbProveedorPago.getTerminado()) {
						dbProveedorPago.setTerminado(true);
						dbProveedorPago.guardar(datastore, tx);
					}
				}
			}
			tx.commit();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void actualizarTitulo(String empresa, long mesVencimiento, long semana, String titulo) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbProveedorPagoMes dbProveedorPagoMes;

		try {
			Key key = KeyFactory.createKey("DbProveedorPagoMes", empresa + "-" + mesVencimiento);
			dbProveedorPagoMes = new DbProveedorPagoMes(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("No existe el DbProveedorPagoMes: " + mesVencimiento);
		}
		if (dbProveedorPagoMes.getTerminado()) {
			throw new Exception("El mes ya está terminado");
		}
		if (semana == 1L) {
			dbProveedorPagoMes.setTitulo1(titulo);
		} else if (semana == 2L) {
			dbProveedorPagoMes.setTitulo2(titulo);
		} else if (semana == 3L) {
			dbProveedorPagoMes.setTitulo3(titulo);
		} else if (semana == 4L) {
			dbProveedorPagoMes.setTitulo4(titulo);
		} else if (semana == 5L) {
			dbProveedorPagoMes.setTitulo5(titulo);
		}
		dbProveedorPagoMes.guardar(datastore);
	}

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
			// serProveedorPago.setPagado(false);
			serProveedorPago.setTerminado(false);
			serProveedorPago.setFechaVencimiento(null);
			serProveedorPago.setSemana(0);
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
		DbProveedorPago dbProveedorPago;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			if (serProveedorPago.getMes() != 0) {
				throw new Exception("El ProveedorPago ya tiene mes");
			}

			try {
				Key keyp = KeyFactory.createKey("DbProveedorPagoMes", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getMes());
				Key key = KeyFactory.createKey(keyp, "DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
				dbProveedorPago = new DbProveedorPago(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
			}

			if (dbProveedorPago.getTerminado())
				throw new Exception("El proveedorPago ya está terminado: " + dbProveedorPago.getKey().getName());
			if (dbProveedorPago.getRevisado())
				throw new Exception("El proveedorPago ya está revisado");

			// Creo la copia incluyendo la fechaCancelación para que se vincule al mes correcto
			SerProveedorPago serProveedorPagoNuevo = dbProveedorPago.toSerProveedorPago();
			serProveedorPagoNuevo.setFechaVencimiento(serProveedorPago.getFechaVencimiento());
			serProveedorPagoNuevo.setZonaHoraria(serProveedorPago.getZonaHoraria());
			serProveedorPagoNuevo.setRevisado(true);

			DbProveedorPago dbProveedorPagoNuevo = new DbProveedorPago(serProveedorPagoNuevo);

			dbProveedorPagoNuevo.guardar(datastore, tx);
			dbProveedorPago.eliminar(datastore, tx);

			// Reviso que exista el encabezado si no lo creo
			if (!ClsEntidad.existeEntidad(datastore, "DbProveedorPagoMes", dbProveedorPagoNuevo.getEmpresa() + "-" + dbProveedorPagoNuevo.getMes())) {
				DbProveedorPagoMes dbProveedorPagoMes = new DbProveedorPagoMes(new SerProveedorPagoMes(dbProveedorPagoNuevo.getEmpresa(), dbProveedorPagoNuevo.getMes(), false, false, false, false, false, false, null, null, null, null, null));
				dbProveedorPagoMes.guardar(datastore, tx);
			}

			tx.commit();

			return dbProveedorPagoNuevo.toSerProveedorPago();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProveedorPago actualizarFechaVencimiento(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		DbProveedorPago dbProveedorPago;
		long mesOriginal;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			if (serProveedorPago.getMes() == 0) {
				throw new Exception("El ProveedorPago no se ha revisado");
			}
			mesOriginal = serProveedorPago.getMes();

			try {
				Key keyp = KeyFactory.createKey("DbProveedorPagoMes", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getMes());
				Key key = KeyFactory.createKey(keyp, "DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
				dbProveedorPago = new DbProveedorPago(datastore.get(tx, key));
			} catch (EntityNotFoundException e) {
				throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
			}

			if (dbProveedorPago.getTerminado())
				throw new Exception("El proveedorPago ya está terminado: " + dbProveedorPago.getKey().getName());
			if (!dbProveedorPago.getRevisado())
				throw new Exception("El proveedorPago no se ha revisado");

			dbProveedorPago.setFechaVencimiento(serProveedorPago.getFechaVencimiento(), serProveedorPago.getZonaHoraria());

			if (mesOriginal == dbProveedorPago.getMes()) {
				// No cambió el mes, solo se actualiza la entidad
				dbProveedorPago.guardar(datastore, tx);
			} else {
				// Creo la copia incluyendo la fechaCancelación para que se vincule al mes correcto
				SerProveedorPago serProveedorPagoNuevo = dbProveedorPago.toSerProveedorPago();
				serProveedorPagoNuevo.setFechaVencimiento(serProveedorPago.getFechaVencimiento());
				serProveedorPagoNuevo.setZonaHoraria(serProveedorPago.getZonaHoraria());

				DbProveedorPago dbProveedorPagoNuevo = new DbProveedorPago(serProveedorPagoNuevo);

				dbProveedorPagoNuevo.guardar(datastore, tx);
				dbProveedorPago.eliminar(datastore, tx);

				// Reviso que exista el encabezado si no lo creo
				if (!ClsEntidad.existeEntidad(datastore, "DbProveedorPagoMes", dbProveedorPagoNuevo.getEmpresa() + "-" + dbProveedorPagoNuevo.getMes())) {
					DbProveedorPagoMes dbProveedorPagoMes = new DbProveedorPagoMes(new SerProveedorPagoMes(dbProveedorPagoNuevo.getEmpresa(), dbProveedorPagoNuevo.getMes(), false, false, false, false, false, false, null, null, null, null, null));
					dbProveedorPagoMes.guardar(datastore, tx);
				}
				dbProveedorPago = dbProveedorPagoNuevo;
			}

			tx.commit();

			return dbProveedorPago.toSerProveedorPago();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerProveedorPago actualizarPagado(SerProveedorPago serProveedorPago) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key keyp = KeyFactory.createKey("DbProveedorPagoMes", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getMes());
			Key key = KeyFactory.createKey(keyp, "DbProveedorPago", serProveedorPago.getEmpresa() + "-" + serProveedorPago.getUuid());
			DbProveedorPago dbProveedorPago = new DbProveedorPago(datastore.get(key));

			if (dbProveedorPago.getPagado() != serProveedorPago.getPagado()) {
				dbProveedorPago.setPagado(serProveedorPago.getPagado());

				dbProveedorPago.guardar(datastore, tx);
				tx.commit();
			}

			dbProveedorPago = new DbProveedorPago(datastore.get(key));
			return dbProveedorPago.toSerProveedorPago();
		} catch (EntityNotFoundException e) {
			throw new Exception("La proveedorPago '" + serProveedorPago.getUuid() + "' no existe.");
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void marcarTerminado(String blobStr) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbProveedorPago dbProveedorPago;

		ClsBlobReader blobR = new ClsBlobReader("¬", blobStr, true);
		while (blobR.siguienteFila()) {
			try {
				Key keyp = KeyFactory.createKey("DbProveedorPagoMes", blobR.getValorStr("empresa") + "-" + blobR.getValorLong("mes"));
				Key key = KeyFactory.createKey(keyp, "DbProveedorPago", blobR.getValorStr("empresa") + "-" + blobR.getValorStr("uuid"));
				dbProveedorPago = new DbProveedorPago(datastore.get(key));
			} catch (EntityNotFoundException e) {
				throw new Exception("Error al obtener los datos de la factura  [marcarTerminado {" + blobR.getValorStr("empresa") + "-" + blobR.getValorStr("uuid") + "-" + blobR.getValorLong("mes") + "}]");
			}

			if (dbProveedorPago.getTerminado() == false) {
				dbProveedorPago.setTerminado(true);
				dbProveedorPago.guardar(datastore);
			}
		}
	}

	public void asignarSemana(String blobStr, long semana) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbProveedorPago dbProveedorPago;

		ClsBlobReader blobR = new ClsBlobReader("¬", blobStr, true);
		while (blobR.siguienteFila()) {
			try {
				Key keyp = KeyFactory.createKey("DbProveedorPagoMes", blobR.getValorStr("empresa") + "-" + blobR.getValorLong("mes"));
				Key key = KeyFactory.createKey(keyp, "DbProveedorPago", blobR.getValorStr("empresa") + "-" + blobR.getValorStr("uuid"));
				dbProveedorPago = new DbProveedorPago(datastore.get(key));
			} catch (EntityNotFoundException e) {
				throw new Exception("Error al obtener los datos de la factura  [marcarTerminado {" + blobR.getValorStr("empresa") + "-" + blobR.getValorStr("uuid") + "-" + blobR.getValorLong("mes") + "}]");
			}

			if (dbProveedorPago.getTerminado())
				throw new Exception("El proveedorPago ya está terminado: " + blobR.getValorStr("empresa") + "-" + blobR.getValorStr("uuid") + "-" + blobR.getValorLong("mes"));

			if (dbProveedorPago.getSemana() != semana) {
				dbProveedorPago.setSemana(semana);
				dbProveedorPago.guardar(datastore);
			}
		}
	}

	public void eliminarProveedorPago(String empresa, long mes, String uuid) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			Key keyp = KeyFactory.createKey("DbProveedorPagoMes", empresa + "-" + mes);
			Key key = KeyFactory.createKey(keyp, "DbProveedorPago", empresa + "-" + uuid);
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
