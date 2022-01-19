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
import com.pantsoft.eppantsoft.entidades.DbAlmacen;
import com.pantsoft.eppantsoft.serializable.SerAlmacen;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmAlmacen {

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
