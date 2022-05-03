package com.pantsoft.eppantsoft.pm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.entidades.DbTelaHabilitacion;
import com.pantsoft.eppantsoft.serializable.SerTelaHabilitacion;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmTelaHabilitacion {

	public void agregar(SerTelaHabilitacion serTelaHabilitacion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbTelaHabilitacion dbTelaHabilitacion = new DbTelaHabilitacion(serTelaHabilitacion);

		if (ClsEntidad.existeEntidad(datastore, "DbTelaHabilitacion", dbTelaHabilitacion.getKey().getName()))
			throw new ExcepcionControlada("La materia '" + serTelaHabilitacion.getMateria() + "' ya existe.");

		dbTelaHabilitacion.guardar(datastore);
	}

	public void actualizar(SerTelaHabilitacion serTelaHabilitacion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTelaHabilitacion", serTelaHabilitacion.getEmpresa() + "-" + serTelaHabilitacion.getMateria());
			DbTelaHabilitacion dbTelaHabilitacion = new DbTelaHabilitacion(datastore.get(key));

			if (!ClsUtil.esIgualConNulo(dbTelaHabilitacion.getTipo(), serTelaHabilitacion.getTipo()))
				dbTelaHabilitacion.setTipo(serTelaHabilitacion.getTipo());
			if (dbTelaHabilitacion.getPrecios() != serTelaHabilitacion.getPrecios())
				dbTelaHabilitacion.setPrecios(serTelaHabilitacion.getPrecios());
			if (dbTelaHabilitacion.getAncho() != serTelaHabilitacion.getAncho())
				dbTelaHabilitacion.setAncho(serTelaHabilitacion.getAncho());
			dbTelaHabilitacion.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("La materia '" + serTelaHabilitacion.getMateria() + "' no existe.");
		}
	}

	public void eliminar(String empresa, String materia) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTelaHabilitacion", empresa + "-" + materia);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
			// throw new Exception("La temporada " + temporada + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La materia '" + materia + "' no existe.");
		}
	}

}
