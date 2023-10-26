package com.pantsoft.eppantsoft.pm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
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
import com.pantsoft.eppantsoft.entidades.DbUsuario;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerUsuario;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmUsuario {

	public void agregar(SerUsuario serUsuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		DbUsuario dbUsuario = new DbUsuario(serUsuario);

		if (ClsEntidad.existeEntidad(datastore, "DbUsuario", dbUsuario.getKey().getName()))
			throw new ExcepcionControlada("El usuario '" + serUsuario.getUsuario() + "' ya existe.");

		dbUsuario.guardar(datastore);
	}

	public void actualizar(SerUsuario serUsuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbUsuario", serUsuario.getEmpresa() + "-" + serUsuario.getUsuario());
			DbUsuario dbUsuario = new DbUsuario(datastore.get(key));

			dbUsuario.setPassword(serUsuario.getPassword());
			dbUsuario.setPermisos(serUsuario.getPermisos());
			dbUsuario.setDiseno(serUsuario.getDiseno());
			dbUsuario.setTrazo(serUsuario.getTrazo());
			dbUsuario.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + serUsuario.getUsuario() + "' no existe.");
		}
	}

	public void actualizarTalleres(SerUsuario serUsuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbUsuario", serUsuario.getEmpresa() + "-" + serUsuario.getUsuario());
			DbUsuario dbUsuario = new DbUsuario(datastore.get(key));

			dbUsuario.setTalleres(serUsuario.getTalleres());
			dbUsuario.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + serUsuario.getUsuario() + "' no existe.");
		}
	}

	public void actualizarClientes(SerUsuario serUsuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbUsuario", serUsuario.getEmpresa() + "-" + serUsuario.getUsuario());
			DbUsuario dbUsuario = new DbUsuario(datastore.get(key));

			dbUsuario.setClientes(serUsuario.getClientes());
			dbUsuario.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + serUsuario.getUsuario() + "' no existe.");
		}
	}

	public void actualizarAlmacenesTipos(SerUsuario serUsuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbUsuario", serUsuario.getEmpresa() + "-" + serUsuario.getUsuario());
			DbUsuario dbUsuario = new DbUsuario(datastore.get(key));

			dbUsuario.setAlmacenes(serUsuario.getAlmacenes());
			dbUsuario.setTipoEntrada(serUsuario.getTiposEntrada());
			dbUsuario.setTipoSalida(serUsuario.getTiposSalida());
			dbUsuario.guardar(datastore);
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + serUsuario.getUsuario() + "' no existe.");
		}
	}

	public SerUsuario[] dameUsuarios(String empresa) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		List<Entity> lstUsuarios = ClsEntidad.ejecutarConsulta(datastore, "DbUsuario", lstFiltros);
		if (lstUsuarios == null || lstUsuarios.size() == 0)
			return new SerUsuario[0];
		SerUsuario[] arr = new SerUsuario[lstUsuarios.size()];
		for (int i = 0; i < lstUsuarios.size(); i++) {
			arr[i] = new DbUsuario(lstUsuarios.get(i)).toSerUsuario();
		}
		return arr;
	}

	public void eliminar(String empresa, String usuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			datastore.get(key);
			// Validar que no participe
			// List<Filter> lstFiltros = new ArrayList<Filter>();
			// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
			// if (ClsEntidad.ejecutarConsultaHayEntidades(datastore, "DbAlmEntradaDetLote", lstFiltros))
			// throw new Exception("El almacén " + almacen + " tiene entradas, imposible eliminar.");

			datastore.delete(key);
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("El usuario '" + usuario + "' no existe.");
		}
	}

	public SerUsuario dameUsuario(String empresa, String usuario, String password) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbUsuario dbUsuario;
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			dbUsuario = new DbUsuario(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + usuario + "' no existe.");
		}

		if (!dbUsuario.getPassword().equals(password))
			throw new Exception("Contraseña incorrecta");

		return dbUsuario.toSerUsuario();
	}

	public SerUsuario iniciarSesion(String empresa, String usuario, String password) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbUsuario dbUsuario;
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			dbUsuario = new DbUsuario(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + usuario + "' no existe.");
		}

		if (!dbUsuario.getPassword().equals(password))
			throw new Exception("Contraseña incorrecta");

		dbUsuario.setSesion(dameSesion());
		dbUsuario.setVigencia(getVigencia());
		dbUsuario.guardar(datastore);

		return dbUsuario.toSerUsuario();
	}

	public SerUsuario validarSesion(String empresa, String usuario, String sesion) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbUsuario dbUsuario;
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			dbUsuario = new DbUsuario(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + usuario + "' no existe.");
		}

		if (!dbUsuario.getSesion().equals(sesion) || dbUsuario.getVigencia() != getVigencia())
			throw new Exception("Sesión inválida");

		return dbUsuario.toSerUsuario();
	}

	private long getVigencia() {
		Date hoy = new Date();
		@SuppressWarnings("deprecation")
		long vigencia = ((hoy.getYear() + 1900) * 10000) + ((hoy.getMonth() + 1) * 100) + hoy.getDate();
		return vigencia;
	}

	private SecureRandom random = new SecureRandom();

	private String dameSesion() {
		return new BigInteger(40, random).toString(32).toUpperCase();
	}

	public SerUsuario dameTalleresUsuario(String empresa, String usuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbUsuario dbUsuario;
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			dbUsuario = new DbUsuario(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + usuario + "' no existe.");
		}

		SerUsuario serUsuario = new SerUsuario();
		serUsuario.setUsuario(dbUsuario.getUsuario());
		serUsuario.setTalleres(dbUsuario.getTalleres());

		return serUsuario;
	}

	public SerUsuario dameClientesUsuario(String empresa, String usuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbUsuario dbUsuario;
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			dbUsuario = new DbUsuario(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + usuario + "' no existe.");
		}

		SerUsuario serUsuario = new SerUsuario();
		serUsuario.setUsuario(dbUsuario.getUsuario());
		serUsuario.setClientes(dbUsuario.getClientes());

		return serUsuario;
	}

	public SerUsuario dameTalleresClientesUsuario(String empresa, String usuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		DbUsuario dbUsuario;
		try {
			Key key = KeyFactory.createKey("DbUsuario", empresa + "-" + usuario);
			dbUsuario = new DbUsuario(datastore.get(key));
		} catch (EntityNotFoundException e) {
			throw new Exception("El usuario '" + usuario + "' no existe.");
		}

		SerUsuario serUsuario = new SerUsuario();
		serUsuario.setUsuario(dbUsuario.getUsuario());
		serUsuario.setTalleres(dbUsuario.getTalleres());
		serUsuario.setClientes(dbUsuario.getClientes());

		return serUsuario;
	}

	public Respuesta dameEmpresas(String usuario) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("usuario", FilterOperator.EQUAL, usuario));
		List<Entity> lstUsuarios = ClsEntidad.ejecutarConsulta(datastore, "DbUsuario", lstFiltros);
		if (lstUsuarios == null || lstUsuarios.size() == 0)
			return new Respuesta();
		String arr = null;
		for (Entity entidad : lstUsuarios) {
			if (arr == null) {
				arr = (String) entidad.getProperty("empresa");
			} else {
				arr += "," + (String) entidad.getProperty("empresa");
			}
		}
		return new Respuesta(arr);
	}

}
