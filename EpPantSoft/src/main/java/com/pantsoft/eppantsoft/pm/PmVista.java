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
import com.pantsoft.eppantsoft.entidades.DbVista;
import com.pantsoft.eppantsoft.serializable.SerVista;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmVista {

	public void agregar(SerVista serVista) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbVista dbVista = new DbVista(serVista);

		if (ClsEntidad.existeEntidad(datastore, "DbVista", dbVista.getKey().getName()))
			throw new ExcepcionControlada("La vista '" + serVista.getVista() + "' ya existe.");

		dbVista.guardar(datastore);
	}

	public void actualizar(SerVista serVista) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbVista", serVista.getEmpresa() + "-" + serVista.getVista());
			DbVista dbVista = new DbVista(datastore.get(key));

			dbVista.setPermisos(serVista.getPermisos());
			dbVista.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El vista '" + serVista.getVista() + "' no existe.");
		}
	}

	public SerVista[] dameVistas(String empresa) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		List<Entity> lstVistas = ClsEntidad.ejecutarConsulta(datastore, "DbVista", lstFiltros);
		if (lstVistas == null || lstVistas.size() == 0)
			return new SerVista[0];
		SerVista[] arr = new SerVista[lstVistas.size()];
		for (int i = 0; i < lstVistas.size(); i++) {
			arr[i] = new DbVista(lstVistas.get(i)).toSerVista();
		}
		return arr;
	}

	// public String dameVistasPost(String empresa) throws Exception {
	// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	//
	// List<Filter> lstFiltros = new ArrayList<Filter>();
	// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
	// List<Entity> lstVistas = ClsEntidad.ejecutarConsulta(datastore, "DbVista", lstFiltros);
	// if (lstVistas == null || lstVistas.size() == 0)
	// return "{\"items\": []}";
	// String json = "{\"items\": [";
	// boolean agregarComa = false;
	// for (Entity entidad : lstVistas) {
	// DbVista dbVista = new DbVista(entidad);
	// GsonBuilder builder = new GsonBuilder();
	// builder.setPrettyPrinting();
	//
	// Gson gson = builder.create();
	//
	// String jsonElement = gson.toJson(dbVista.toSerVista());
	// if (agregarComa)
	// json += ",";
	// else
	// agregarComa = true;
	// json += jsonElement;
	// }
	// json += "]}";
	// return json;
	// }

	public void eliminar(String empresa, String vista) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbVista", empresa + "-" + vista);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbAlmEntradaDetLote", lstFiltros))
			// throw new Exception("El almacén " + almacen + " tiene entradas, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La vista '" + vista + "' no existe.");
		}
	}

	// public SerVista dameVista(String empresa, String vista, String password) throws Exception {
	// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	// DbVista dbVista;
	// try {
	// Key key = KeyFactory.createKey("DbVista", empresa + "-" + vista);
	// dbVista = new DbVista(datastore.get(key));
	// } catch (EntityNotFoundException e) {
	// throw new Exception("El vista '" + vista + "' no existe.");
	// }
	//
	// return dbVista.toSerVista();
	// }

	// public String dameVistaPost(String empresa, String vista, String password) throws Exception {
	// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	// DbVista dbVista;
	// try {
	// Key key = KeyFactory.createKey("DbVista", empresa + "-" + vista);
	// dbVista = new DbVista(datastore.get(key));
	// } catch (EntityNotFoundException e) {
	// throw new Exception("El vista '" + vista + "' no existe.");
	// }
	//
	// String json = "{\"items\": [";
	// GsonBuilder builder = new GsonBuilder();
	// builder.setPrettyPrinting();
	//
	// Gson gson = builder.create();
	// SerVista serVista = dbVista.toSerVista();
	// String jsonElement = gson.toJson(serVista);
	// json += jsonElement;
	//
	// json += "]}";
	// return json;
	// }

}
