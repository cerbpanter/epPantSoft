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
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.entidades.DbListaPrecios;
import com.pantsoft.eppantsoft.entidades.DbListaPreciosDet;
import com.pantsoft.eppantsoft.serializable.SerListaPrecios;
import com.pantsoft.eppantsoft.serializable.SerListaPreciosDet;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmListaPrecios {

	// ListaPrecios ////////////////////////////////////////////////////////////////////
	public SerListaPrecios listaPrecios_agregar(SerListaPrecios serListaPrecios) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbListaPrecios dbListaPrecios = new DbListaPrecios(serListaPrecios);

			if (ClsEntidad.existeEntidad(datastore, tx, "DbListaPrecios", dbListaPrecios.getKey().getName()))
				throw new ExcepcionControlada("El listaPrecios '" + serListaPrecios.getIdListaPrecios() + "' ya existe.");

			dbListaPrecios.guardar(datastore, tx);

			tx.commit();

			return dbListaPrecios.toSerListaPrecios();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerListaPrecios listaPrecios_actualizar(SerListaPrecios serListaPrecios) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbListaPrecios", serListaPrecios.getEmpresa() + "-" + serListaPrecios.getTemporada() + "-" + serListaPrecios.getIdListaPrecios());
			DbListaPrecios dbListaPrecios = new DbListaPrecios(datastore.get(key));

			dbListaPrecios.setDescripcion(serListaPrecios.getDescripcion());
			dbListaPrecios.setFechaGenerada(serListaPrecios.getFechaGenerada());
			dbListaPrecios.setModoBloqueo(serListaPrecios.getModoBloqueo());
			dbListaPrecios.setDescuento(serListaPrecios.getDescuento());
			dbListaPrecios.setAutoGenerar(serListaPrecios.getAutoGenerar());
			dbListaPrecios.setPvp(serListaPrecios.getPvp());
			dbListaPrecios.setUsuarioPropietario(serListaPrecios.getUsuarioPropietario());
			dbListaPrecios.setFiltros(serListaPrecios.getFiltros());
			dbListaPrecios.setValido(serListaPrecios.getValido());
			dbListaPrecios.guardar(datastore);

			return dbListaPrecios.toSerListaPrecios();
		} catch (EntityNotFoundException e) {
			throw new Exception("La listaPrecios '" + serListaPrecios.getIdListaPrecios() + "' no existe.");
		}
	}

	public SerListaPrecios listaPrecios_dameListaPrecios(String empresa, long temporada, long idListaPrecios, String usuario) throws Exception {
		if (ClsUtil.esNulo(usuario))
			throw new Exception("Falta el usuario");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbListaPrecios", empresa + "-" + temporada + "-" + idListaPrecios);
			DbListaPrecios dbListaPrecios = new DbListaPrecios(datastore.get(key));
			if (!ClsUtil.esNulo(dbListaPrecios.getUsuarioPropietario()) && !dbListaPrecios.getUsuarioPropietario().equals(usuario))
				throw new Exception("No tiene acceso a la lista de precio");
			return dbListaPrecios.toSerListaPrecios();
		} catch (EntityNotFoundException e) {
			throw new Exception("La listaPrecios '" + idListaPrecios + "' no existe.");
		}
	}

	public SerListaPrecios[] listaPrecios_dameListasPrecios(String empresa, long temporada) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
		List<Entity> lstListaPrecios = ClsEntidad.ejecutarConsulta(datastore, "DbListaPrecios", lstFiltros);
		if (lstListaPrecios == null || lstListaPrecios.size() == 0)
			return new SerListaPrecios[0];
		List<SerListaPrecios> lst = new ArrayList<SerListaPrecios>();
		DbListaPrecios dbListaPrecios;
		for (int i = 0; i < lstListaPrecios.size(); i++) {
			dbListaPrecios = new DbListaPrecios(lstListaPrecios.get(i));
			lst.add(dbListaPrecios.toSerListaPrecios());
		}
		return lst.toArray(new SerListaPrecios[0]);
	}

	public void listaPrecios_eliminar(String empresa, long temporada, long idListaPrecios) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbListaPrecios", empresa + "-" + temporada + "-" + idListaPrecios);
			DbListaPrecios dbListaPrecios = new DbListaPrecios(datastore.get(key));
			// Validar que no participe
			List<Filter> lstFiltros = new ArrayList<Filter>();
			lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			lstFiltros.add(new FilterPredicate("temporada", FilterOperator.EQUAL, temporada));
			lstFiltros.add(new FilterPredicate("idListaPrecios", FilterOperator.EQUAL, idListaPrecios));
			if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbListaPreciosDet", lstFiltros))
				throw new Exception("La listaPrecios " + idListaPrecios + " tiene detalles, imposible eliminar.");

			dbListaPrecios.eliminar(datastore);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El listaPrecios '" + idListaPrecios + "' no existe.");
		}
	}

	// ListaPreciosDet ////////////////////////////////////////////////////////////////////
	public SerListaPreciosDet listaPreciosDet_agregar(SerListaPreciosDet serListaPreciosDet) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			DbListaPreciosDet dbListaPreciosDet = new DbListaPreciosDet(serListaPreciosDet);

			if (ClsEntidad.existeEntidad(datastore, tx, "DbListaPreciosDet", dbListaPreciosDet.getKey().getName()))
				throw new ExcepcionControlada("La listaPreciosDet '" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia() + "' ya existe.");

			dbListaPreciosDet.guardar(datastore, tx);

			tx.commit();

			return dbListaPreciosDet.toSerListaPreciosDet();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public void listaPreciosDet_agregarArr(SerListaPreciosDet[] serListaPreciosDetArr) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);

			for (SerListaPreciosDet serListaPreciosDet : serListaPreciosDetArr) {
				DbListaPreciosDet dbListaPreciosDet = new DbListaPreciosDet(serListaPreciosDet);

				if (ClsEntidad.existeEntidad(datastore, tx, "DbListaPreciosDet", dbListaPreciosDet.getKey().getName()))
					throw new ExcepcionControlada("La listaPreciosDet '" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia() + "' ya existe.");

				dbListaPreciosDet.guardar(datastore, tx);
			}

			tx.commit();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerListaPreciosDet listaPreciosDet_actualizar(SerListaPreciosDet serListaPreciosDet) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key keyp = KeyFactory.createKey("DbListaPrecios", serListaPreciosDet.getEmpresa() + "-" + serListaPreciosDet.getTemporada() + "-" + serListaPreciosDet.getIdListaPrecios());
			Key key = KeyFactory.createKey(keyp, "DbListaPreciosDet", serListaPreciosDet.getEmpresa() + "-" + serListaPreciosDet.getTemporada() + "-" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia());
			DbListaPreciosDet dbListaPreciosDet = new DbListaPreciosDet(datastore.get(key));

			dbListaPreciosDet.setTalla(serListaPreciosDet.getTalla());
			dbListaPreciosDet.setDescripcion(serListaPreciosDet.getDescripcion());
			dbListaPreciosDet.setTela(serListaPreciosDet.getTela());
			dbListaPreciosDet.setDepartamento(serListaPreciosDet.getDepartamento());
			dbListaPreciosDet.setPrecosto(serListaPreciosDet.getPrecosto());
			dbListaPreciosDet.setMargen(serListaPreciosDet.getMargen());
			dbListaPreciosDet.setPrecioVenta(serListaPreciosDet.getPrecioVenta());
			dbListaPreciosDet.setPrecioVentaPublico(serListaPreciosDet.getPrecioVentaPublico());
			dbListaPreciosDet.setSeleccion(serListaPreciosDet.getSeleccion());
			dbListaPreciosDet.setValido(serListaPreciosDet.getValido());
			dbListaPreciosDet.guardar(datastore);

			return dbListaPreciosDet.toSerListaPreciosDet();
		} catch (EntityNotFoundException e) {
			throw new Exception("La listaPreciosDet '" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia() + "' no existe.");
		}
	}

	public void listaPreciosDet_actualizarArr(SerListaPreciosDet[] serListaPreciosDetArr) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = null;
		try {
			tx = ClsEntidad.iniciarTransaccion(datastore);
			for (SerListaPreciosDet serListaPreciosDet : serListaPreciosDetArr) {
				try {
					Key keyp = KeyFactory.createKey("DbListaPrecios", serListaPreciosDet.getEmpresa() + "-" + serListaPreciosDet.getTemporada() + "-" + serListaPreciosDet.getIdListaPrecios());
					Key key = KeyFactory.createKey(keyp, "DbListaPreciosDet", serListaPreciosDet.getEmpresa() + "-" + serListaPreciosDet.getTemporada() + "-" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia());
					DbListaPreciosDet dbListaPreciosDet = new DbListaPreciosDet(datastore.get(tx, key));

					dbListaPreciosDet.setTalla(serListaPreciosDet.getTalla());
					dbListaPreciosDet.setDescripcion(serListaPreciosDet.getDescripcion());
					dbListaPreciosDet.setTela(serListaPreciosDet.getTela());
					dbListaPreciosDet.setDepartamento(serListaPreciosDet.getDepartamento());
					dbListaPreciosDet.setPrecosto(serListaPreciosDet.getPrecosto());
					dbListaPreciosDet.setMargen(serListaPreciosDet.getMargen());
					dbListaPreciosDet.setPrecioVenta(serListaPreciosDet.getPrecioVenta());
					dbListaPreciosDet.setPrecioVentaPublico(serListaPreciosDet.getPrecioVentaPublico());
					dbListaPreciosDet.setSeleccion(serListaPreciosDet.getSeleccion());
					dbListaPreciosDet.setValido(serListaPreciosDet.getValido());
					dbListaPreciosDet.guardar(datastore, tx);
				} catch (EntityNotFoundException e) {
					throw new Exception("La listaPreciosDet '" + serListaPreciosDet.getIdListaPrecios() + "-" + serListaPreciosDet.getModelo() + "-" + serListaPreciosDet.getReferencia() + "' no existe.");
				}
			}
			tx.commit();
		} finally {
			if (tx != null && tx.isActive())
				tx.rollback();
		}
	}

	public SerListaPreciosDet[] listaPreciosDet_dameDetalles(String empresa, long temporada, long idListaPrecios) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key keyAncestor = KeyFactory.createKey("DbListaPrecios", empresa + "-" + temporada + "-" + idListaPrecios);
		List<Entity> lstListaPreciosDet = ClsEntidad.ejecutarConsulta(datastore, null, "DbListaPreciosDet", keyAncestor);
		if (lstListaPreciosDet == null || lstListaPreciosDet.size() == 0)
			return new SerListaPreciosDet[0];
		SerListaPreciosDet[] arr = new SerListaPreciosDet[lstListaPreciosDet.size()];
		for (int i = 0; i < lstListaPreciosDet.size(); i++) {
			arr[i] = new DbListaPreciosDet(lstListaPreciosDet.get(i)).toSerListaPreciosDet();
		}
		return arr;
	}

	public void listaPreciosDet_eliminar(String empresa, long temporada, String idListaPrecios, String modelo, String referencia) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key keyp = KeyFactory.createKey("DbListaPrecios", empresa + "-" + temporada + "-" + idListaPrecios);
			Key key = KeyFactory.createKey(keyp, "DbListaPreciosDet", empresa + "-" + temporada + "-" + idListaPrecios + "-" + modelo + "-" + referencia);
			DbListaPreciosDet dbListaPreciosDet = new DbListaPreciosDet(datastore.get(key));

			dbListaPreciosDet.eliminar(datastore);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El listaPreciosDet '" + idListaPrecios + "-" + modelo + "-" + referencia + "' no existe.");
		}
	}

}
