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
import com.pantsoft.eppantsoft.entidades.DbTalla;
import com.pantsoft.eppantsoft.serializable.SerTalla;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmTalla {

	public void agregar(SerTalla serTalla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbTalla dbTalla = new DbTalla(serTalla);

		if (ClsEntidad.existeEntidad(datastore, "DbTalla", dbTalla.getKey().getName()))
			throw new ExcepcionControlada("La Talla '" + serTalla.getTalla() + "' ya existe.");

		dbTalla.guardar(datastore);
	}

	public SerTalla actualizar(SerTalla serTalla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTalla", serTalla.getEmpresa() + "-" + serTalla.getTemporada() + "-" + serTalla.getTalla());
			DbTalla dbTalla = new DbTalla(datastore.get(key));

			if (dbTalla.getOrden() != serTalla.getOrden())
				dbTalla.setOrden(serTalla.getOrden());
			dbTalla.guardar(datastore);

			return dbTalla.toSerTalla();
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La Talla '" + serTalla.getTalla() + "' no existe.");
		}
	}

	public void eliminar(String empresa, long temporada, String talla) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTalla", empresa + "-" + temporada + "-" + talla);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Talla", FilterOperator.EQUAL, Talla));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Talla " + Talla + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La talla '" + talla + "' no existe.");
		}
	}

	public SerTalla[] dameTallas(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstTallas = ClsEntidad.ejecutarConsulta(datastore, "DbTalla", lstFiltros);
		if (lstTallas == null || lstTallas.size() == 0)
			return new SerTalla[0];
		SerTalla[] arr = new SerTalla[lstTallas.size()];
		for (int i = 0; i < lstTallas.size(); i++) {
			arr[i] = new DbTalla(lstTallas.get(i)).toSerTalla();
		}
		return arr;
	}

}
