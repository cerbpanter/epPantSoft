package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.entidades.DbParametro;
import com.pantsoft.eppantsoft.serializable.SerParametro;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmParametro {

	public void grabar(SerParametro serParametro) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbParametro dbParametro = null;
		try {
			// Actualizar
			Key key = KeyFactory.createKey("DbParametro", serParametro.getEmpresa() + "-" + serParametro.getParametro());
			dbParametro = new DbParametro(datastore.get(key));
			dbParametro.setValor(serParametro.getValor());
		} catch (EntityNotFoundException e) {
			// Agregar
			dbParametro = new DbParametro(serParametro);
		}

		dbParametro.guardar(datastore);
	}

	public void eliminar(String empresa, String parametro) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbParametro", empresa + "-" + parametro);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Parametro", FilterOperator.EQUAL, Parametro));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Parametro " + Parametro + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El parametro '" + parametro + "' no existe.");
		}
	}

}
