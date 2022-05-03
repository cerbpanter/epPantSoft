package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.entidades.DbTallas;
import com.pantsoft.eppantsoft.serializable.SerTallas;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmTallas {

	public void agregar(SerTallas serTallas) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Solo acepta letras mayusculas, número y /
		if (!serTallas.getTalla().matches("^[A-Z0-9/]+$") || serTallas.getTalla().contains("  ") || serTallas.getTalla().startsWith(" ") || serTallas.getTalla().endsWith(" "))
			throw new Exception("La talla solo acepta mayusculas, numeros y diagonal");

		DbTallas dbTallas = new DbTallas(serTallas);

		if (ClsEntidad.existeEntidad(datastore, "DbTallas", dbTallas.getKey().getName()))
			throw new ExcepcionControlada("El Tallas '" + serTallas.getTalla() + "' ya existe.");

		dbTallas.guardar(datastore);
	}

	public SerTallas actualizar(SerTallas serTallas) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTallas", serTallas.getEmpresa() + "-" + serTallas.getTalla());
			DbTallas dbTallas = new DbTallas(datastore.get(key));

			dbTallas.setTallas(serTallas.getTallas());
			dbTallas.guardar(datastore);

			return dbTallas.toSerTallas();
		} catch (EntityNotFoundException e) {
			throw new Exception("Las tallas '" + serTallas.getTalla() + "' no existen.");
		}
	}

	public void eliminar(String empresa, String talla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTallas", empresa + "-" + talla);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Tallas", FilterOperator.EQUAL, Tallas));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Tallas " + Tallas + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("Las tallas '" + talla + "' no existen.");
		}
	}

}
