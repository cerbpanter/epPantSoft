package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.entidades.DbDepartamento;
import com.pantsoft.eppantsoft.serializable.SerDepartamento;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmDepartamento {

	public void agregar(SerDepartamento serDepartamento) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbDepartamento dbDepartamento = new DbDepartamento(serDepartamento);

		if (ClsEntidad.existeEntidad(datastore, "DbDepartamento", dbDepartamento.getKey().getName()))
			throw new ExcepcionControlada("El Departamento '" + serDepartamento.getDepartamento() + "' ya existe.");

		dbDepartamento.guardar(datastore);
	}

	public void eliminar(String empresa, String departamento) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbDepartamento", empresa + "-" + departamento);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Departamento", FilterOperator.EQUAL, Departamento));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Departamento " + Departamento + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El departamento '" + departamento + "' no existe.");
		}
	}

}
