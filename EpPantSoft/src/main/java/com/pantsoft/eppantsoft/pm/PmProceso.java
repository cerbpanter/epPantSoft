package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.entidades.DbProceso;
import com.pantsoft.eppantsoft.serializable.SerProceso;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmProceso {

	public void agregar(SerProceso serProceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbProceso dbProceso = new DbProceso(serProceso);

		if (ClsEntidad.existeEntidad(datastore, "DbProceso", dbProceso.getKey().getName()))
			throw new ExcepcionControlada("El Proceso '" + serProceso.getProceso() + "' ya existe.");

		dbProceso.guardar(datastore);
	}

	public void eliminar(String empresa, String proceso) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbProceso", empresa + "-" + proceso);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Proceso", FilterOperator.EQUAL, Proceso));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Proceso " + Proceso + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La Proceso '" + proceso + "' no existe.");
		}
	}

}
