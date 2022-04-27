package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.entidades.DbColor;
import com.pantsoft.eppantsoft.serializable.SerColor;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmColor {

	public void agregar(SerColor serColor) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbColor dbColor = new DbColor(serColor);

		if (ClsEntidad.existeEntidad(datastore, "DbColor", dbColor.getKey().getName()))
			throw new ExcepcionControlada("El Color '" + serColor.getColor() + "' ya existe.");

		dbColor.guardar(datastore);
	}

	public void eliminar(String empresa, String color) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbColor", empresa + "-" + color);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("Color", FilterOperator.EQUAL, Color));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La Color " + Color + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El color '" + color + "' no existe.");
		}
	}

}
