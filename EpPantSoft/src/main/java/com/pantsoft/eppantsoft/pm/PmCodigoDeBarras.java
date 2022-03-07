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
import com.pantsoft.eppantsoft.entidades.DbCodigoDeBarras;
import com.pantsoft.eppantsoft.entidades.DbCodigoDeBarras_A;
import com.pantsoft.eppantsoft.serializable.SerCodigoDeBarras;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmCodigoDeBarras {

	public void agregar(SerCodigoDeBarras serCodigoDeBarras) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbCodigoDeBarras dbCodigoDeBarras = new DbCodigoDeBarras(serCodigoDeBarras);

			if (ClsEntidad.existeEntidad(datastore, "DbCodigoDeBarras", dbCodigoDeBarras.getKey().getName()))
				throw new ExcepcionControlada("El CodigoDeBarras '" + dbCodigoDeBarras.getKey().getName() + "' ya existe.");

			DbCodigoDeBarras_A dbCodigoDeBarras_A = new DbCodigoDeBarras_A(serCodigoDeBarras);

			if (ClsEntidad.existeEntidad(datastore, "DbCodigoDeBarras_A", dbCodigoDeBarras_A.getKey().getName()))
				throw new ExcepcionControlada("El CodigoDeBarras_A '" + dbCodigoDeBarras_A.getKey().getName() + "' ya existe.");

			dbCodigoDeBarras.guardar(datastore);
			dbCodigoDeBarras_A.guardar(datastore);

			tx.commit();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerCodigoDeBarras actualizar(SerCodigoDeBarras serCodigoDeBarras) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbCodigoDeBarras dbCodigoDeBarras;
		DbCodigoDeBarras_A dbCodigoDeBarras_A;
		DbCodigoDeBarras_A dbCodigoDeBarras_A_Nuevo;
		Transaction tx = null;

		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);
			try {
				Key key = KeyFactory.createKey("DbCodigoDeBarras", serCodigoDeBarras.getEmpresa() + "-" + serCodigoDeBarras.getTemporada() + "-" + serCodigoDeBarras.getModelo() + "-" + serCodigoDeBarras.getColor() + "-" + serCodigoDeBarras.getTalla());
				dbCodigoDeBarras = new DbCodigoDeBarras(datastore.get(key));
			} catch (EntityNotFoundException e) {
				throw new ExcepcionControlada("El CodigoDeBarras '" + serCodigoDeBarras.getEmpresa() + "-" + serCodigoDeBarras.getTemporada() + "-" + serCodigoDeBarras.getModelo() + "-" + serCodigoDeBarras.getColor() + "-" + serCodigoDeBarras.getTalla() + "' no existe.");
			}

			try {
				Key key = KeyFactory.createKey("DbCodigoDeBarras_A", dbCodigoDeBarras.getEmpresa() + "-" + dbCodigoDeBarras.getTemporada() + "-" + dbCodigoDeBarras.getCodigoDeBarras());
				dbCodigoDeBarras_A = new DbCodigoDeBarras_A(datastore.get(key));
			} catch (EntityNotFoundException e) {
				throw new ExcepcionControlada("El CodigoDeBarras_A '" + dbCodigoDeBarras.getEmpresa() + "-" + dbCodigoDeBarras.getTemporada() + "-" + dbCodigoDeBarras.getCodigoDeBarras() + "' no existe.");
			}

			if (!dbCodigoDeBarras.getCodigoDeBarras().equals(serCodigoDeBarras.getCodigoDeBarras())) {
				// Reviso si ya existe el nuevo codigo de Barras
				try {
					Key key = KeyFactory.createKey("DbCodigoDeBarras_A", dbCodigoDeBarras.getEmpresa() + "-" + dbCodigoDeBarras.getTemporada() + "-" + serCodigoDeBarras.getCodigoDeBarras());
					DbCodigoDeBarras_A dbCodigoDeBarras_A2 = new DbCodigoDeBarras_A(datastore.get(key));
					throw new Exception("El código de barras ya existe para '" + dbCodigoDeBarras_A2.getModelo() + "-" + dbCodigoDeBarras_A2.getColor() + "-" + dbCodigoDeBarras_A2.getTalla() + "'");
				} catch (EntityNotFoundException e) {
					// Si no existe no hay problema
				}

				dbCodigoDeBarras_A_Nuevo = new DbCodigoDeBarras_A(serCodigoDeBarras);

				dbCodigoDeBarras.setCodigoDeBarras(serCodigoDeBarras.getCodigoDeBarras());

				dbCodigoDeBarras.guardar(datastore, tx);
				dbCodigoDeBarras_A.eliminar(datastore, tx);
				dbCodigoDeBarras_A_Nuevo.guardar(datastore, tx);

				tx.commit();
			}

			return dbCodigoDeBarras.toSerCodigoDeBarras();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void eliminar(String empresa, long temporada, String modelo, String color, String talla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbCodigoDeBarras", empresa + "-" + temporada + "-" + modelo + "-" + color + "-" + talla);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("CodigoDeBarras", FilterOperator.EQUAL, CodigoDeBarras));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La CodigoDeBarras " + CodigoDeBarras + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El codigoDeBarras '" + empresa + "-" + temporada + "-" + modelo + "-" + color + "-" + talla + "' no existe.");
		}
	}

	public SerCodigoDeBarras[] dameCodigosDeBarras(String empresa, long temporada, String modelo) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		lstFiltros.add(new FilterPredicate("modelo", FilterOperator.EQUAL, modelo));
		List<Entity> lstCodigoDeBarrass = ClsEntidad.ejecutarConsulta(datastore, "DbCodigoDeBarras", lstFiltros);
		if (lstCodigoDeBarrass == null || lstCodigoDeBarrass.size() == 0)
			return new SerCodigoDeBarras[0];
		SerCodigoDeBarras[] arr = new SerCodigoDeBarras[lstCodigoDeBarrass.size()];
		for (int i = 0; i < lstCodigoDeBarrass.size(); i++) {
			arr[i] = new DbCodigoDeBarras(lstCodigoDeBarrass.get(i)).toSerCodigoDeBarras();
		}
		return arr;
	}

}
