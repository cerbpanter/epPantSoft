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
import com.pantsoft.eppantsoft.entidades.DbTallas;
import com.pantsoft.eppantsoft.serializable.SerTallas;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmTallas {

	public void agregar(SerTallas serTallas) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbTallas dbTallas = new DbTallas(serTallas);

		if (ClsEntidad.existeEntidad(datastore, "DbTallas", dbTallas.getKey().getName()))
			throw new ExcepcionControlada("El Tallas '" + serTallas.getTalla() + "' ya existe.");

		dbTallas.guardar(datastore);
	}

	public SerTallas actualizar(SerTallas serTallas) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTallas", serTallas.getEmpresa() + "-" + serTallas.getTemporada() + "-" + serTallas.getTalla());
			DbTallas dbTallas = new DbTallas(datastore.get(key));

			dbTallas.setTallas(serTallas.getTallas());
			dbTallas.guardar(datastore);

			return dbTallas.toSerTallas();
		} catch (EntityNotFoundException e) {
			throw new Exception("Las tallas '" + serTallas.getTalla() + "' no existen.");
		}
	}

	public void eliminar(String empresa, long temporada, String talla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTallas", empresa + "-" + temporada + "-" + talla);
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

	public SerTallas dameTalla(String empresa, long temporada, String talla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		try {
			Key key = KeyFactory.createKey("DbTallas", empresa + "-" + temporada + "-" + talla);
			DbTallas dbTallas = new DbTallas(datastore.get(key));

			return dbTallas.toSerTallas();
		} catch (EntityNotFoundException e) {
			throw new Exception("La talla del modelo no existe, favor de agregarla en el catálogo de tallas");
		}
	}

	public SerTallas[] dameTallas(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstTallas = ClsEntidad.ejecutarConsulta(datastore, "DbTallas", lstFiltros);
		if (lstTallas == null || lstTallas.size() == 0)
			return new SerTallas[0];
		SerTallas[] arr = new SerTallas[lstTallas.size()];
		for (int i = 0; i < lstTallas.size(); i++) {
			arr[i] = new DbTallas(lstTallas.get(i)).toSerTallas();
		}
		return arr;
	}

}
