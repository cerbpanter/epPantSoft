package com.pantsoft.eppantsoft.pm;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.util.ClsBlobReader;
import com.pantsoft.eppantsoft.util.ClsEntidad;

public class PmAdministracion {

	public Respuesta eliminarEntidades(String nombreEntidad, String blobStrFiltros, String cursor) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		if (cursor.equals("Inicio")) {
			cursor = null;
		}
		List<Filter> lstFiltros = new ArrayList<Filter>();
		ClsBlobReader blobR = new ClsBlobReader("¬", blobStrFiltros, true);
		FilterOperator operador;
		while (blobR.siguienteFila()) {
			if (blobR.getValorStr("operador").equals("EQUAL")) {
				operador = FilterOperator.EQUAL;
			} else {
				throw new Exception("El operador '" + blobR.getValorStr("operador") + "' no se soporta");
			}
			if (blobR.getValorStr("tipo").equals("String")) {
				lstFiltros.add(new FilterPredicate(blobR.getValorStr("campo"), operador, blobR.getValorStr("valor")));
			} else if (blobR.getValorStr("tipo").equals("Long")) {
				lstFiltros.add(new FilterPredicate(blobR.getValorStr("campo"), operador, Long.parseLong(blobR.getValorStr("valor"))));
			} else {
				throw new Exception("El tipo '" + blobR.getValorStr("tipo") + "' no se soporta");
			}
		}
		long eliminados = 0;
		List<Entity> lstEntidades = ClsEntidad.ejecutarConsulta(datastore, nombreEntidad, lstFiltros, 200, cursor);
		if (lstEntidades != null && lstEntidades.size() > 0) {
			for (Entity entidad : lstEntidades) {
				datastore.delete(entidad.getKey());
				eliminados++;
			}
		}
		Respuesta resp = new Respuesta();
		resp.setCadena(ClsEntidad.getStrCursor());
		resp.setLargo(eliminados);
		return resp;
	}

}
