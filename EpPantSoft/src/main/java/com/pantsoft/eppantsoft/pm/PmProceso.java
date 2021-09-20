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
import com.pantsoft.eppantsoft.entidades.DbProceso;
import com.pantsoft.eppantsoft.serializable.SerProceso;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProceso {

	public void agregar(SerProceso SerProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbProceso dbProceso = new DbProceso(SerProceso);

		if (ClsEntidad.existeEntidad(datastore, "DbProceso", dbProceso.getKey().getName()))
			throw new ExcepcionControlada("El Proceso '" + SerProceso.getProceso() + "' ya existe.");

		dbProceso.guardar(datastore);
	}

	public void eliminar(String empresa, long temporada, String Proceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbProceso", empresa + "-" + temporada + "-" + Proceso);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Proceso", FilterOperator.EQUAL, Proceso));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Proceso " + Proceso + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La Proceso '" + Proceso + "' no existe.");
		}
	}

	public SerProceso[] dameProcesos(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstProcesos = ClsEntidad.ejecutarConsulta(datastore, "DbProceso", lstFiltros);
		if (lstProcesos == null || lstProcesos.size() == 0)
			return new SerProceso[0];
		SerProceso[] arr = new SerProceso[lstProcesos.size()];
		for (int i = 0; i < lstProcesos.size(); i++) {
			arr[i] = new DbProceso(lstProcesos.get(i)).toSerProceso();
		}
		return arr;
	}

}
