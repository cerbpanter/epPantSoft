package com.pantsoft.eppantsoft.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;
import com.pantsoft.eppantsoft.util.base64.ClsBase64;

public class ClsUtil {

	public static boolean esNulo(Object objeto) {
		if (objeto == null || CadenaX(objeto).trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String CadenaX(Object val) {
		try {
			if (val == null)
				return "";
			return String.valueOf(val);
		} catch (Exception e) {
			return "";
		}
	}

	public static String Desencriptar(String textoEncriptado) throws ExcepcionControlada {

		String secretKey = "pabloandresjoseluiscarlos"; // llave para desenciptar datos
		String base64EncryptedString = "";
		String linea = "0";

		try {
			byte[] message = ClsBase64.decodifica(textoEncriptado);
			linea = "1";
			MessageDigest md = MessageDigest.getInstance("MD5");
			linea = "2";
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			linea = "3";
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			linea = "4";
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			linea = "5";
			Cipher decipher = Cipher.getInstance("DESede");
			linea = "6";
			decipher.init(Cipher.DECRYPT_MODE, key);
			linea = "7";
			byte[] plainText = decipher.doFinal(message);
			linea = "8";
			base64EncryptedString = new String(plainText, "UTF-8");
			linea = "9";
		} catch (Exception ex) {
			throw new ExcepcionControlada(ex.getMessage() + "Linea [" + linea + "]");
		}
		return base64EncryptedString;
	}

	// public static TokenLogin validaToken(String token) throws ExcepcionControlada {
	// String tk = Desencriptar(token).trim();
	// String[] arrTk = tk.split("\\|");
	// if (arrTk.length != 4) {
	// throw new ExcepcionControlada("EP: Las credenciales no son válidas. Cadena [" + tk + "]");
	// }
	//
	// // Valida el RFC
	// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	// Query q = new Query("DbEmpresa");
	// q.setFilter(new FilterPredicate("empresa", FilterOperator.EQUAL, arrTk[0]));
	// QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());
	//
	// if (lstEntidades == null || lstEntidades.size() == 0) {
	// throw new ExcepcionControlada("La empresa '" + arrTk[0] + "' no existe. (validaToken GetPost)");
	// }
	//
	// // Valida si el usuario está registrado en sinube, sea vigente
	// q = new Query("DbUsuario");
	// q.setFilter(new FilterPredicate("email", FilterOperator.EQUAL, arrTk[2]));
	// lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());
	//
	// if (lstEntidades == null || lstEntidades.size() == 0) {
	// throw new ExcepcionControlada("No existe el usuario en SiNube: "
	// + arrTk[2]);
	// }
	//
	// if ((Long) lstEntidades.get(0).getProperty("vigencia") < (new Date().getTime()))
	// throw new ExcepcionControlada("El usuario '" + arrTk[0]
	// + "' ya no es vigente en SiNube");
	//
	// // Valida que el usuario tenga acceso a la sucursal
	// q = new Query("DbUsuarioSucursal");
	//
	// List<Filter> lstFiltros = new ArrayList<Filter>();
	// lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, arrTk[0]));
	// lstFiltros.add(new FilterPredicate("sucursal", FilterOperator.EQUAL, arrTk[1]));
	// lstFiltros.add(new FilterPredicate("usuario", FilterOperator.EQUAL, arrTk[2]));
	//
	// q.setFilter(CompositeFilterOperator.and(lstFiltros));
	//
	// lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());
	//
	// if (lstEntidades == null || lstEntidades.size() == 0) {
	// throw new ExcepcionControlada("El usuario '" + arrTk[2]
	// + "' no tiene acceso a la sucursal," + arrTk[0] + "-"
	// + arrTk[1] + "-" + arrTk[2]);
	// }
	//
	// return new TokenLogin(arrTk[0], arrTk[1], arrTk[2], arrTk[3].equals("1") ? true : false);
	// }

	public static void validaUsuarioEmpresa(String empresa, String sucursal, String usuario, String pwd) throws ExcepcionControlada {
		// Valida los valores
		if (ClsUtil.esNulo(empresa))
			throw new ExcepcionControlada("El campo empresa es requerido");
		if (ClsUtil.esNulo(usuario))
			throw new ExcepcionControlada("El campo usuario es requerido");
		if (ClsUtil.esNulo(pwd))
			throw new ExcepcionControlada("El campo pwd es requerido");

		// Valida el RFC
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("DbEmpresa");
		q.setFilter(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());

		if (lstEntidades == null || lstEntidades.size() == 0)
			throw new ExcepcionControlada("La empresa '" + empresa + "' no existe. (validaUsuarioEmpresa GetPost)");

		Entity dbEmpresa = lstEntidades.get(0);
		if (ClsUtil.esNulo(dbEmpresa.getProperty("pwd")))
			throw new ExcepcionControlada("Para los procesos de comunicación de facturas es requerido que defina su contraseña de comunicación en Sinube.");
		if (!((String) dbEmpresa.getProperty("pwd")).equalsIgnoreCase(pwd))
			throw new ExcepcionControlada("La contraseña de comunicación no coincide con la de la empresa solicitada.");

		// Valida si el usuario está registrado en sinube, sea vigente
		q = new Query("DbUsuario");
		q.setFilter(new FilterPredicate("email", FilterOperator.EQUAL, usuario));
		lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());

		if (lstEntidades == null || lstEntidades.size() == 0)
			throw new ExcepcionControlada("No existe el usuario en SiNube: " + usuario);

		if ((Long) lstEntidades.get(0).getProperty("vigencia") < (new Date().getTime()))
			throw new ExcepcionControlada("El usuario '" + usuario + "' ya no es vigente en SiNube");

		// Valida que el usuario tenga acceso a la sucursal
		q = new Query("DbUsuarioSucursal");

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("sucursal", FilterOperator.EQUAL, sucursal));
		lstFiltros.add(new FilterPredicate("usuario", FilterOperator.EQUAL, usuario));
		q.setFilter(CompositeFilterOperator.and(lstFiltros));

		lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());

		if (lstEntidades == null || lstEntidades.size() == 0)
			throw new ExcepcionControlada("El usuario '" + usuario + "' no tiene acceso a la sucursal," + empresa + "-" + sucursal + "-" + usuario);
	}

	public static boolean validaUsuarioEmpresaAdmin(String empresa, String sucursal, String usuario, String pwd, boolean noAdminSiNube) throws ExcepcionControlada {
		// Valida los valores
		if (ClsUtil.esNulo(empresa))
			throw new ExcepcionControlada("El campo empresa es requerido");
		if (ClsUtil.esNulo(sucursal))
			throw new ExcepcionControlada("El campo sucursal es requerido");
		if (ClsUtil.esNulo(usuario))
			throw new ExcepcionControlada("El campo usuario es requerido");
		if (ClsUtil.esNulo(pwd))
			throw new ExcepcionControlada("El campo pwd es requerido");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Valida si el usuario está registrado en sinube, sea vigente
		try {
			Entity dbUsuario = ClsEntidad.obtenerEntidad(datastore, "DbUsuario", usuario);
			if (!noAdminSiNube && dbUsuario.hasProperty("passwordAdmin")) {
				if (!pwd.equals((String) dbUsuario.getProperty("passwordAdmin"))) {
					throw new ExcepcionControlada("Está usando un usuario administrador que no pudo validarse");
				}
				return true;
				// } else {
				// dbUsuario.setUnindexedProperty("passwordAdmin", "password");
				// datastore.put(dbUsuario);
			}
			if ((Long) dbUsuario.getProperty("vigencia") < (new Date().getTime()))
				throw new ExcepcionControlada("El usuario '" + usuario + "' ya no es vigente en SiNube");
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("No existe el usuario en SiNube: " + usuario);
		}

		// Valida el RFC
		Query q = new Query("DbEmpresa");
		q.setFilter(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		QueryResultList<Entity> lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());

		if (lstEntidades == null || lstEntidades.size() == 0)
			throw new ExcepcionControlada("La empresa '" + empresa + "' no existe. (validausuarioEmpresaAdmin GetPost)");

		Entity dbEmpresa = lstEntidades.get(0);
		if (ClsUtil.esNulo(dbEmpresa.getProperty("pwd")))
			throw new ExcepcionControlada("Para los procesos de comunicación de facturas es requerido que defina su contraseña de comunicación en Sinube.");
		if (!((String) dbEmpresa.getProperty("pwd")).equalsIgnoreCase(pwd))
			throw new ExcepcionControlada("La contraseña de comunicación no coincide con la de la empresa solicitada.");

		// Valida que el usuario tenga acceso a la sucursal
		q = new Query("DbUsuarioSucursal");

		List<Filter> lstFiltros = new ArrayList<Filter>();
		lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
		lstFiltros.add(new FilterPredicate("sucursal", FilterOperator.EQUAL, sucursal));
		lstFiltros.add(new FilterPredicate("usuario", FilterOperator.EQUAL, usuario));
		q.setFilter(CompositeFilterOperator.and(lstFiltros));

		lstEntidades = datastore.prepare(q).asQueryResultList(FetchOptions.Builder.withDefaults());

		if (lstEntidades == null || lstEntidades.size() == 0)
			throw new ExcepcionControlada("El usuario '" + usuario + "' no tiene acceso a la sucursal," + empresa + "-" + sucursal + "-" + usuario);
		return false;
	}

	public static String dameToken(String empresa, String sucursal, String usuario, boolean esAdmin) throws Exception {
		String tk = empresa + "|" + sucursal + "|" + usuario.toLowerCase() + "|" + (esAdmin ? "1" : "0");
		return ClsUtil.Encriptar(tk);
	}

	public static String Encriptar(String texto) {
		String secretKey = "pabloandresjoseluiscarlos"; // llave para encriptar datos
		String base64EncryptedString = "";

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);

			base64EncryptedString = ClsBase64.encode(buf);

		} catch (Exception ex) {
		}
		return base64EncryptedString;
	}

	/**
	 * Redondea un número a los decimales especificados.
	 * 
	 * @param valor
	 *          El número que se quiere redondear
	 * @param decimales
	 *          El número de decimales requerido
	 */
	public static double Redondear(double valor, int decimales) {
		// double p = Math.pow(10, decimales);
		// valor = valor * p;
		// valor = Math.round(valor);
		// return valor / p;

		double p = (double) Math.pow(10, decimales);
		valor = (valor * p) + 0.000000001;
		valor = Math.round(valor);
		return (double) valor / p;

	}

	public static boolean esIgualConNulo(String v1, String v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Boolean v1, Boolean v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Long v1, Long v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Double v1, Double v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Integer v1, Integer v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

}
