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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pantsoft.eppantsoft.entidades.DbTemporada;
import com.pantsoft.eppantsoft.serializable.SerTemporada;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmTemporada {

	public void agregar(SerTemporada serTemporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbTemporada dbTemporada = new DbTemporada(serTemporada);

		if (ClsEntidad.existeEntidad(datastore, "DbTemporada", dbTemporada.getKey().getName()))
			throw new ExcepcionControlada("La temporada '" + serTemporada.getTemporada() + "' ya existe.");

		dbTemporada.guardar(datastore);
	}

	public void actualizar(SerTemporada serTemporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTemporada", serTemporada.getEmpresa() + "-" + serTemporada.getTemporada());
			DbTemporada dbTemporada = new DbTemporada(datastore.get(key));

			if (!ClsUtil.esIgualConNulo(dbTemporada.getDescripcion(), serTemporada.getDescripcion()))
				dbTemporada.setDescripcion(serTemporada.getDescripcion());
			dbTemporada.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("La temporada '" + serTemporada.getTemporada() + "' no existe.");
		}
	}

	public void eliminar(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbTemporada", empresa + "-" + temporada);
			datastore.get(key);
			// Validar que no participe
			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbProduccion", lstFiltros))
				throw new Exception("La temporada " + temporada + " tiene registros de producción, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("La temporada '" + temporada + "' no existe.");
		}
	}

	public SerTemporada[] dameTemporadas(String empresa) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		List<Entity> lstTemporadas = ClsEntidad.ejecutarConsulta(datastore, "DbTemporada", lstFiltros);
		if (lstTemporadas == null || lstTemporadas.size() == 0)
			return new SerTemporada[0];
		SerTemporada[] arr = new SerTemporada[lstTemporadas.size()];
		for (int i = 0; i < lstTemporadas.size(); i++) {
			arr[i] = new DbTemporada(lstTemporadas.get(i)).toSerTemporada();
		}
		return arr;
	}

	public String dameTemporadasPost(String empresa) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		List<Entity> lstTemporadas = ClsEntidad.ejecutarConsulta(datastore, "DbTemporada", lstFiltros);
		if (lstTemporadas == null || lstTemporadas.size() == 0)
			return null;
		String json = "{\"items\": [";
		boolean agregarComa = false;
		for (Entity entidad : lstTemporadas) {
			DbTemporada dbTemporada = new DbTemporada(entidad);
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting();

			Gson gson = builder.create();
			String jsonElement = gson.toJson(dbTemporada.toSerTemporada());
			if (agregarComa)
				json += ",";
			else
				agregarComa = true;
			json += jsonElement;
		}
		json += "]}";
		return json;
	}

}
