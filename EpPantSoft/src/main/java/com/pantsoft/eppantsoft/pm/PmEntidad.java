package com.pantsoft.eppantsoft.pm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Rating;
import com.pantsoft.eppantsoft.entidades.DbAlmEntrada;
import com.pantsoft.eppantsoft.entidades.DbAlmEntradaDet;
import com.pantsoft.eppantsoft.entidades.DbAlmSalida;
import com.pantsoft.eppantsoft.entidades.DbAlmSalidaDet;
import com.pantsoft.eppantsoft.entidades.DbAlmacen;
import com.pantsoft.eppantsoft.entidades.DbCodigoDeBarras;
import com.pantsoft.eppantsoft.entidades.DbCodigoDeBarras_A;
import com.pantsoft.eppantsoft.entidades.DbColor;
import com.pantsoft.eppantsoft.entidades.DbConsecutivo;
import com.pantsoft.eppantsoft.entidades.DbDepartamento;
import com.pantsoft.eppantsoft.entidades.DbEmpresa;
import com.pantsoft.eppantsoft.entidades.DbEntidad;
import com.pantsoft.eppantsoft.entidades.DbEntidadCampo;
import com.pantsoft.eppantsoft.entidades.DbInvModeloDet;
import com.pantsoft.eppantsoft.entidades.DbListaPrecios;
import com.pantsoft.eppantsoft.entidades.DbListaPreciosDet;
import com.pantsoft.eppantsoft.entidades.DbModelo;
import com.pantsoft.eppantsoft.entidades.DbModeloHabilitacion;
import com.pantsoft.eppantsoft.entidades.DbModeloImagen;
import com.pantsoft.eppantsoft.entidades.DbModeloProceso;
import com.pantsoft.eppantsoft.entidades.DbModeloProducido;
import com.pantsoft.eppantsoft.entidades.DbParametro;
import com.pantsoft.eppantsoft.entidades.DbPedido;
import com.pantsoft.eppantsoft.entidades.DbPedidoDet;
import com.pantsoft.eppantsoft.entidades.DbProceso;
import com.pantsoft.eppantsoft.entidades.DbOrden;
import com.pantsoft.eppantsoft.entidades.DbProduccion;
import com.pantsoft.eppantsoft.entidades.DbTalla;
import com.pantsoft.eppantsoft.entidades.DbTallas;
import com.pantsoft.eppantsoft.entidades.DbTelaHabilitacion;
import com.pantsoft.eppantsoft.entidades.DbTemporada;
import com.pantsoft.eppantsoft.entidades.DbUsuario;
import com.pantsoft.eppantsoft.entidades.DbVista;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.util.ClsBlobWriter;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ClsUtil;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class PmEntidad {
	private boolean generarEncabezado;
	private long entidadesLeidas;
	private List<ClsSubConsulta> lstSubConsulta = null;

	class EntidadTemporal extends ClsEntidad {
		public List<ClsCampo> getCampos() {
			return null;
		}

		public boolean getLiberado() {
			return false;
		}

		public void asignarValoresDefault(Map<String, ClsCampo> mapCampos) throws ExcepcionControlada {
			List<ClsCampo> lstCampos;
			try {
				lstCampos = new ArrayList<ClsCampo>(mapCampos.values());
			} catch (Exception e) {
				throw new ExcepcionControlada("Error al crear list: " + e.getMessage());
			}
			try {
				asignarValoresDefault(lstCampos);
			} catch (Exception e) {
				throw new ExcepcionControlada("Error al asignar valores default: " + e.getMessage());
			}
		}
	}

	public String ejecutarConsultaSql(String empresa, String cns, boolean usuarioAdmin) throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		String valor;
		ClsEntidadCns entidadCns = null;
		List<ClsCampoCns> lstCamposCns = null;
		List<ClsCampoCns> lstFiltrosCns = null;
		int tamPag = 500;
		String cursorStr = null;
		List<Filter> lstFiltros = new ArrayList<Filter>();

		// --- SELECT ---
		// Cargo la estructura de la consulta
		cns = cns.trim();
		if (cns.length() < 7 || !cns.substring(0, 7).equalsIgnoreCase("select "))
			throw new Exception("La consulta debe iniciar con 'SELECT'");
		if (cns.endsWith("NOADMINSINUBE"))
			cns = cns.substring(0, cns.length() - 13);
		cns = cns.substring(7);
		// Cambio los espacios dentro de '' por &SiNubeEspacio&
		int pos = cns.indexOf("'");
		while (pos >= 0) {
			int posFin = cns.indexOf("'", pos + 1);
			if (posFin < 0)
				throw new Exception("Cuando se usa ' debe terminar con '");
			cns = cns.substring(0, pos) + cns.substring(pos, posFin).replace(" ", "&SiNubeEspacio&") + cns.substring(posFin);
			posFin = cns.indexOf("'", pos + 1);
			pos = cns.indexOf("'", posFin + 1);
		}
		// Cambio los espacios dentro de [] por &SiNubeEspacio&
		pos = cns.indexOf("[");
		while (pos >= 0) {
			int posFin = cns.indexOf("]", pos + 1);
			if (posFin < 0)
				throw new Exception("Cuando se usa [ debe terminar con ]");
			cns = cns.substring(0, pos) + cns.substring(pos, posFin).replace(" ", "&SiNubeEspacio&") + cns.substring(posFin);
			posFin = cns.indexOf("]", pos + 1);
			pos = cns.indexOf("[", posFin + 1);
		}
		// Reemplazo caracteres inservibles
		cns = cns.replace("\n", " ").replace("\r", " ");
		while (cns.contains("  "))
			cns = cns.replace("  ", " ");
		cns = cns.replace(" ,", ",").replace(", ", ",").replace("= ", "=").replace(" =", "=").replace(" <", "<");
		cns += " ";
		// Obtengo los Campos
		lstCamposCns = new ArrayList<ClsCampoCns>();
		pos = damePos(cns, true, true, false);
		while (pos > 0) {
			ClsCampoCns campoCns = null;
			// --- Reviso si es fórmula ---
			// RATING
			if (cns.startsWith("RATING(")) {
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion RATING debe terminar con ')'");
				String[] valores = cns.substring(7, pos).split(";");
				if (valores.length < 3)
					throw new Exception("La funcion RATING debe tener al menos un valor: " + cns.substring(7, pos + 1));
				campoCns = new ClsCampoCns(valores[0]);
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("RATING");
				for (int x = 1; x < valores.length; x++)
					campoCns.agregarValor(valores[x]);
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("ETI_CLI(")) {
				// ETI_CLI(F.cliente;departamento)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion ETI_CLI debe terminar con ')'");
				String[] valores = cns.substring(8, pos).split(";");
				if (valores.length != 2)
					throw new Exception("La funcion ETI_CLI debe tener 2 valores separados por ; : " + cns.substring(7, pos + 1));
				campoCns = new ClsCampoCns(valores[0]);
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("ETI_CLI");
				campoCns.agregarValor(valores[1]);
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("AÑO(")) {
				// AÑO(F.fechaFactura)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion AÑO debe terminar con ')'");
				campoCns = new ClsCampoCns(cns.substring(4, pos));
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("AÑO");
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("MES(")) {
				// MES(F.fechaFactura)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion MES debe terminar con ')'");
				campoCns = new ClsCampoCns(cns.substring(4, pos));
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("MES");
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("SEMANA(")) {
				// SEMANA(F.fechaFactura)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion SEMANA debe terminar con ')'");
				campoCns = new ClsCampoCns(cns.substring(7, pos));
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("SEMANA");
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("DIASEMANA(")) {
				// DIASEMANA(F.fechaFactura)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion DIASEMANA debe terminar con ')'");
				campoCns = new ClsCampoCns(cns.substring(10, pos));
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("DIASEMANA");
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("CAMPOADICIONAL(")) {
				// CAMPOADICIONAL(F.camposAdicionales;Nombre_Campo)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion CAMPOADICIONAL debe terminar con ')'");
				String[] valores = cns.substring(15, pos).split(";");
				if (valores.length != 2)
					throw new Exception("La funcion CAMPOADICIONAL debe tener 2 valores separados por ; : " + cns.substring(14, pos + 1));
				campoCns = new ClsCampoCns(valores[0]);
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("CAMPOADICIONAL");
				campoCns.agregarValor(valores[1]);
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("VALORLOTE(")) {
				// VALORLOTE(L.lotes;1)
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion VALORLOTE debe terminar con ')'");
				String[] valores = cns.substring(10, pos).split(";");
				if (valores.length != 2)
					throw new Exception("La funcion VALORLOTE debe tener 2 valores separados por ; : " + cns.substring(9, pos + 1));
				campoCns = new ClsCampoCns(valores[0]);
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("VALORLOTE");
				if (!valores[1].equals("1") && !valores[1].equals("2") && !valores[1].equals("3") && !valores[1].equals("4") && !valores[1].equals("5"))
					throw new Exception("La funcion VALORLOTE su segundo valor debe ser un número entre 1 y 5: " + valores[1]);
				campoCns.agregarValor(valores[1]);
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("LLAVELOTE(")) {
				pos = cns.indexOf(")");
				if (pos < 0)
					throw new Exception("La funcion LLAVELOTE debe terminar con ')'");
				String[] valores = cns.substring(10, pos).split(";");
				if (valores.length != 2)
					throw new Exception("La funcion LLAVELOTE debe tener 2 valores separados por ; : " + cns.substring(9, pos + 1));
				campoCns = new ClsCampoCns(valores[0]);
				cns = cns.substring(pos + 2);
				campoCns.setEsFormula(true);
				campoCns.setFormula("LLAVELOTE");
				if (!valores[1].equals("1") && !valores[1].equals("2") && !valores[1].equals("3") && !valores[1].equals("4") && !valores[1].equals("5"))
					throw new Exception("La funcion LLAVELOTE su segundo valor debe ser un número entre 1 y 5: " + valores[1]);
				campoCns.agregarValor(valores[1]);
				lstCamposCns.add(campoCns);
			} else if (cns.startsWith("'")) {
				// Constante de cadena
				pos = cns.indexOf("'", 1);
				if (pos < 0)
					throw new Exception("Error en la consulta, se esperaba apostrofo para cerrar el campo constante '" + cns + "')");
				if (cns.length() < (pos + 2) || !cns.substring(pos + 1, pos + 2).equals(" "))
					throw new Exception("El campo constante debe tener alias '" + cns + "')");
				campoCns = new ClsCampoCns(cns.substring(0, pos + 1));
				cns = cns.substring(pos + 2);
				lstCamposCns.add(campoCns);
			} else {
				// No es fórmula
				campoCns = new ClsCampoCns(cns.substring(0, pos));
				cns = cns.substring(pos + 1);
				lstCamposCns.add(campoCns);
			}
			// Busco el alias del campo
			if (cns.length() >= 3 && cns.substring(0, 3).equalsIgnoreCase("as ")) {
				cns = cns.substring(3);
				pos = damePos(cns, true, true, false);
				if (pos <= 0)
					throw new Exception("Error en la consulta, se esperaba espacio o coma al final del alias del campo (SELECT '" + cns + "')");
				campoCns.setAlias(cns.substring(0, pos));
				cns = cns.substring(pos + 1);
			} else if (campoCns.getEsConstante()) {
				throw new Exception("El campo constante '" + campoCns.getCampo() + "' debe tener alias");
			}

			if (cns.length() >= 4 && cns.substring(0, 4).equalsIgnoreCase("from"))
				pos = -1;
			else
				pos = damePos(cns, true, true, false);
		}

		// --- FROM ---
		// Obtengo el nombre de la entidad principal
		if (cns.length() < 5 || !cns.substring(0, 5).equalsIgnoreCase("from "))
			throw new Exception("Error en la consulta, se esperaba 'FROM'");
		cns = cns.substring(5);
		pos = damePos(cns, true, false, false);
		if (pos <= 0)
			throw new Exception("Error en la consulta, se esperaba espacio al final del nombre de la entidad (FROM)");
		entidadCns = new ClsEntidadCns("", cns.substring(0, pos));
		cns = cns.substring(pos + 1);

		// Busco el alias de la entidad principal
		if (cns.length() >= 3 && cns.substring(0, 3).equalsIgnoreCase("as ")) {
			cns = cns.substring(3);
			pos = damePos(cns, true, false, false);
			if (pos <= 0)
				throw new Exception("Error en la consulta, se esperaba espacio al final del alias de la entidad (FROM)");
			entidadCns.setAlias(cns.substring(0, pos));
			cns = cns.substring(pos + 1);
		}

		// --- JOIN ---
		ClsEntidadCns entidadPadre = entidadCns;
		while ((cns.length() >= 10 && cns.substring(0, 10).equalsIgnoreCase("left join ")) || (cns.length() >= 11 && cns.substring(0, 11).equalsIgnoreCase("inner join "))) {
			ClsEntidadCns subEntidadCns = new ClsEntidadCns("", "");
			entidadPadre.setSubEntidad(subEntidadCns);
			subEntidadCns.setEntidadPadre(entidadPadre);
			if (cns.substring(0, 10).equalsIgnoreCase("left join ")) {
				cns = cns.substring(10);
				subEntidadCns.setInnerJoin(false);
			} else if (cns.substring(0, 11).equalsIgnoreCase("inner join ")) {
				cns = cns.substring(11);
				subEntidadCns.setInnerJoin(true);
			} else {
				// No debe ocurrir, lo pongo solo por seguridad
				throw new Exception("Error al buscar tipo de JOIN");
			}
			// Obtengo el nombre de la sub entidad
			pos = damePos(cns, true, false, false);
			if (pos <= 0)
				throw new Exception("Error en la consulta, se esperaba espacio al final del nombre de la entidad (JOIN)");
			subEntidadCns.setEntidad(cns.substring(0, pos));
			cns = cns.substring(pos + 1);

			// Busco el alias de la entidad principal
			if (cns.length() >= 3 && cns.substring(0, 3).equalsIgnoreCase("as ")) {
				cns = cns.substring(3);
				pos = damePos(cns, true, false, false);
				if (pos <= 0)
					throw new Exception("Error en la consulta, se esperaba espacio al final del alias de la entidad (JOIN)");
				subEntidadCns.setAlias(cns.substring(0, pos));
				cns = cns.substring(pos + 1);
			}

			// Reviso que el alias no se repita
			String alias = subEntidadCns.getAlias();
			ClsEntidadCns entidad = subEntidadCns;
			while (entidad.getEntidadPadre() != null) {
				entidad = entidad.getEntidadPadre();
				if (entidad.getAlias().equals(alias))
					throw new Exception("El alias '" + alias + "' existe más de una vez");
			}

			// Obtengo la Entidad de Bd
			try {
				subEntidadCns.setDbEntidad(new DbEntidad(ClsEntidad.obtenerEntidad(datastore, "DbEntidad", subEntidadCns.getEntidad())));
			} catch (EntityNotFoundException e) {
				throw new ExcepcionControlada("No existe la entidad " + subEntidadCns.getEntidad() + " en BD");
			}

			// Obtengo los campos de BD
			Map<String, ClsCampo> mapCamposBd = new HashMap<String, ClsCampo>();
			List<Entity> lstEntidades = ClsEntidad.ejecutarConsulta(datastore, null, "DbEntidadCampo", subEntidadCns.getDbEntidad().getKey());
			if (lstEntidades.size() == 0)
				throw new ExcepcionControlada("La entidad " + subEntidadCns.getEntidad() + " no tiene campos en Bd");
			for (Entity entity : lstEntidades) {
				DbEntidadCampo dbCampo = new DbEntidadCampo(entity);
				mapCamposBd.put(dbCampo.getCampo(), dbCampo.toClsCampo());
			}
			subEntidadCns.setMapCamposBd(mapCamposBd);

			// --- ON ---
			// Obtengo los filtros de la sub entidad
			if (cns.length() < 3 || !cns.substring(0, 3).equalsIgnoreCase("on "))
				throw new Exception("Error en la consulta, se esperaba ON (JOIN)");
			cns = cns.substring(3);

			lstFiltrosCns = new ArrayList<ClsCampoCns>();
			subEntidadCns.setFiltrosOn(lstFiltrosCns);
			boolean sigue = true;
			while (sigue) {
				pos = damePos(cns, true, false, false);
				if (pos <= 0)
					throw new Exception("Error en la consulta, se esperaba espacio al final del filtro ON de un campo (JOIN '" + cns + "')");
				valor = cns.substring(0, pos);
				cns = cns.substring(pos + 1);
				pos = valor.indexOf("=");
				if (pos <= 0)
					throw new Exception("Error en la consulta, se esperaba = en el filtro ON de un campo (JOIN '" + valor + "')");
				ClsCampoCns filtro = new ClsCampoCns(valor.substring(0, pos));
				if (!filtro.getEntidadAlias().equals(subEntidadCns.getAlias()))
					throw new Exception("Error en la consulta, el filtro ON no pertenece a la entidad (JOIN '" + valor.substring(0, pos) + "')");
				filtro.setValor(valor.substring(pos + 1));
				lstFiltrosCns.add(filtro);
				if (cns.length() >= 4 && cns.substring(0, 4).equalsIgnoreCase("and ")) {
					cns = cns.substring(4);
				} else {
					sigue = false;
				}
			}

			entidadPadre = subEntidadCns;
		}

		// --- WHERE ---
		lstFiltrosCns = new ArrayList<ClsCampoCns>();
		entidadCns.setFiltrosOn(lstFiltrosCns);
		if (cns.trim().length() > 6 && cns.substring(0, 6).equalsIgnoreCase("where ")) {
			cns = cns.substring(6);
			// Obtengo los filtros WHERE
			boolean sigue = true;
			while (sigue) {
				pos = damePos(cns, true, false, true);
				if (pos <= 0)
					throw new Exception("Error en la consulta, se esperaba espacio al final del filtro de un campo (WHERE '" + cns + "')");
				valor = cns.substring(0, pos);
				cns = cns.substring(pos + 1);
				FilterOperator operador = null;
				int caracteresOperador = 0;
				pos = valor.indexOf(">>==");
				int posMenor = 1000000;
				if (pos > 0 && pos < posMenor) {
					posMenor = pos;
					operador = FilterOperator.GREATER_THAN_OR_EQUAL;
					caracteresOperador = 4;
				}
				pos = valor.indexOf("<<==");
				if (pos > 0 && pos < posMenor) {
					posMenor = pos;
					operador = FilterOperator.LESS_THAN_OR_EQUAL;
					caracteresOperador = 4;
				}
				pos = valor.indexOf(">");
				if (pos > 0 && pos < posMenor) {
					posMenor = pos;
					operador = FilterOperator.GREATER_THAN;
					caracteresOperador = 1;
				}
				pos = valor.indexOf("<");
				if (pos > 0 && pos < posMenor) {
					posMenor = pos;
					operador = FilterOperator.LESS_THAN;
					caracteresOperador = 1;
				}
				pos = valor.indexOf("=");
				if (pos > 0 && pos < posMenor) {
					posMenor = pos;
					operador = FilterOperator.EQUAL;
					caracteresOperador = 1;
				}
				if (operador == null && cns.startsWith("IN")) {
					pos = valor.length();
					posMenor = pos;
					int pos2 = damePos(cns, true, false, true);
					if (pos2 <= 0)
						throw new Exception("Error en la consulta, se esperaba espacio al final del filtro de un campo (WHERE '" + cns + "')");
					valor += "=" + cns.substring(0, pos2);
					cns = cns.substring(pos2 + 1);

					operador = FilterOperator.EQUAL;
					caracteresOperador = 1;
				}
				pos = posMenor;
				if (caracteresOperador == 0 || operador == null) {
					throw new Exception("Error en la consulta, se esperaba = en el filtro de un campo (WHERE '" + valor + "')");
				}
				ClsCampoCns filtro = new ClsCampoCns(valor.substring(0, pos));
				filtro.setValor(valor.substring(pos + caracteresOperador));
				filtro.setOperador(operador);
				lstFiltrosCns.add(filtro);
				if (cns.length() >= 3 && cns.substring(0, 3).equalsIgnoreCase("or ")) {
					throw new Exception("Error en la consulta, solo se permite AND (WHERE '" + cns + "')");
				} else if (cns.length() >= 4 && cns.substring(0, 4).equalsIgnoreCase("and ")) {
					cns = cns.substring(4);
				} else {
					sigue = false;
				}
				// Reviso que el filtro corresponda a alguna de las entidades
				ClsEntidadCns clsEntidadCnsTemp = entidadCns;
				boolean valida = false;
				while (clsEntidadCnsTemp != null) {
					if (clsEntidadCnsTemp.getAlias().equals(filtro.getEntidadAlias())) {
						valida = true;
						break;
					}
					clsEntidadCnsTemp = clsEntidadCnsTemp.getSubEntidad();
				}
				if (!valida)
					throw new Exception("El filtro WHERE " + filtro.getEntidadAlias() + "." + filtro.getCampo() + " no pertenece a ninguna entidad en la consulta");
			}
		}

		// Busco el tamaño de página
		if (cns.length() >= 7 && cns.substring(0, 7).equalsIgnoreCase("tampag ")) {
			cns = cns.substring(7);
			pos = damePos(cns, true, false, false);
			if (pos <= 0)
				throw new Exception("Error en la consulta, se esperaba espacio al final del tamaño de página (TAMPAG)");
			try {
				tamPag = Integer.parseInt(cns.substring(0, pos));
				if (tamPag < 1 || tamPag > 50000)
					throw new ExcepcionControlada("El tamaño de página debe ser un valor entre 1 y 50000");
			} catch (Exception e) {
				throw new Exception("Error en la consulta, el tamaño de página no es valido (" + cns.substring(0, pos) + ")");
			}
			cns = cns.substring(pos + 1);
		}

		// Busco el cursor
		if (cns.length() >= 7 && cns.substring(0, 7).equalsIgnoreCase("cursor ")) {
			cns = cns.substring(7);
			pos = damePos(cns, true, false, false);
			if (pos <= 0)
				throw new Exception("Error en la consulta, se esperaba espacio al final del cursor (CURSOR)");
			cursorStr = cns.substring(0, pos);
			cns = cns.substring(pos + 1);
		}

		if (cns.trim().length() > 0)
			throw new Exception("Error en la consulta, una sección no se pudo interpretar (" + cns + ")");

		// *********** TERMINO DE INTERPRETAR LA CONSULTA ***************************

		// Obtengo la Entidad principal de Bd
		try {
			entidadCns.setDbEntidad(new DbEntidad(ClsEntidad.obtenerEntidad(datastore, "DbEntidad", entidadCns.getEntidad())));
		} catch (EntityNotFoundException e) {
			throw new ExcepcionControlada("No existe la entidad " + entidadCns.getEntidad() + " en BD");
		}
		// Obtengo los campos de BD
		Map<String, ClsCampo> mapCamposBd = new HashMap<String, ClsCampo>();
		List<Entity> lstEntidades = ClsEntidad.ejecutarConsulta(datastore, null, "DbEntidadCampo", entidadCns.getDbEntidad().getKey());
		if (lstEntidades.size() == 0)
			throw new ExcepcionControlada("La entidad " + entidadCns.getEntidad() + " no tiene campos - 4");
		for (Entity entity : lstEntidades) {
			DbEntidadCampo dbCampo = new DbEntidadCampo(entity);
			mapCamposBd.put(dbCampo.getCampo(), dbCampo.toClsCampo());
		}
		entidadCns.setMapCamposBd(mapCamposBd);

		// Cambio los * por los campos en la entidad
		for (int x = 0; x < lstCamposCns.size(); x++) {
			ClsCampoCns campo = lstCamposCns.get(x);
			if (campo.getCampo().equals("*") || campo.getCampo().equals("**")) {
				boolean encontrado = false;
				ClsEntidadCns entidad = entidadCns;
				while (entidad != null) {
					if (entidad.getAlias().equals(campo.getEntidadAlias())) {
						encontrado = true;
						lstCamposCns.remove(x);
						if (campo.getCampo().equals("**")) {
							lstCamposCns.add(x, new ClsCampoCns(campo.getEntidadAlias(), "key"));
							x++;
							lstCamposCns.add(x, new ClsCampoCns(campo.getEntidadAlias(), "keyPath"));
						} else {
							x--;
						}
						ClsCampo[] arrCamposBD = entidad.getMapCamposBd().values().toArray(new ClsCampo[0]);
						Arrays.sort(arrCamposBD, new Comparator<ClsCampo>() {
							@Override
							public int compare(ClsCampo o1, ClsCampo o2) {
								return o1.getNombre().compareTo(o2.getNombre());
							}
						});
						for (ClsCampo campoMap : arrCamposBD) {
							ClsCampoCns campoCns = new ClsCampoCns(campo.getEntidadAlias(), campoMap.getNombre());
							x++;
							lstCamposCns.add(x, campoCns);
						}
						entidad = null;
					} else {
						entidad = entidad.getSubEntidad();
					}
				}
				if (!encontrado)
					throw new Exception("No se encontró la entidad correspondiente a " + campo.getEntidadAlias() + "." + campo.getCampo());
			}
		}

		// proceso los filtros de la entidad principal
		// boolean hayFiltro = false;
		boolean esFiltroPorKeyName = false;
		String keyName = null;
		for (ClsCampoCns filtro : lstFiltrosCns) {
			if (filtro.getEnMemoria() || !filtro.getEntidadAlias().equals(entidadCns.getAlias()))
				continue;
			if (filtro.getCampo().equals("keyName")) {
				esFiltroPorKeyName = true;
				if (filtro.getValor().startsWith("'") && filtro.getValor().endsWith("'")) {
					keyName = filtro.getValor().substring(1, filtro.getValor().length() - 1).replace("&SiNubeApostrofo;", "'");
				} else if (filtro.getValor().startsWith("IN(") && filtro.getValor().endsWith(")")) {
					keyName = filtro.getValor();
				} else {
					throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena o una estructura IN válida : " + filtro.getValor());
				}
				continue;
			}
			if (!mapCamposBd.containsKey(filtro.getCampo()))
				throw new ExcepcionControlada("No puede filtrar el campo " + filtro.getCampo() + " pues no existe en " + entidadCns.getEntidad() + " (FILTRO-P)");
			ClsCampo campo = mapCamposBd.get(filtro.getCampo());
			if (!campo.getIndexado() && entidadCns.getDbEntidad().getEndpoint())
				throw new ExcepcionControlada("El campo " + filtro.getCampo() + " no está indexado en " + entidadCns.getEntidad());
			if (filtro.getOperador() == null)
				throw new Exception("El filtro '" + filtro.getCampo() + " - " + filtro.getValor() + "' no tiene operador");
			if (filtro.getValor().equals("Null")) {
				lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), null));
			} else {
				switch (campo.getTipo()) {
				case String:
					if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), filtro.getValor().substring(1, filtro.getValor().length() - 1).replace("&SiNubeApostrofo;", "'")));
					// hayFiltro = true;
					break;
				case Boolean:
					boolean valBool;
					if (filtro.getValor().equalsIgnoreCase("true")) {
						valBool = true;
					} else if (filtro.getValor().equalsIgnoreCase("false")) {
						valBool = false;
					} else {
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean (true o false) : " + filtro.getValor());
					}
					// try {
					// valBool = Boolean.parseBoolean(filtro.getValor());
					// } catch (Exception e) {
					// throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean : " + filtro.getValor());
					// }
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), valBool));
					// hayFiltro = true;
					break;
				case Long:
					long valLong;
					try {
						valLong = Long.parseLong(filtro.getValor());
					} catch (Exception e) {
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 1");
					}
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), valLong));
					// hayFiltro = true;
					break;
				case Double:
					throw new ExcepcionControlada("No puede filtrar en campos Double: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
				case Date:
					try {
						valLong = Long.parseLong(filtro.getValor());
					} catch (Exception e) {
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 1");
					}
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Date(valLong)));
				case Rating:
					int valInt;
					try {
						valInt = Integer.parseInt(filtro.getValor());
					} catch (Exception e) {
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un int : " + filtro.getValor());
					}
					if (valInt < 0 || valInt > 99)
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un entero entre 0 y 99 : " + filtro.getValor());
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Rating(valInt)));
					// hayFiltro = true;
					break;
				case Text:
					throw new ExcepcionControlada("No puede filtrar en campos Text: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
				case Blob:
					throw new ExcepcionControlada("No puede filtrar en campos Blob: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
				case ArrayString:
					if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), filtro.getValor().substring(1, filtro.getValor().length() - 1).replace("&SiNubeApostrofo;", "'")));
					// hayFiltro = true;
					break;
				case ArrayLong:
					try {
						valLong = Long.parseLong(filtro.getValor());
					} catch (Exception e) {
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 2");
					}
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), valLong));
					// hayFiltro = true;
					break;
				case Email:
					if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
						throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
					lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Email(filtro.getValor().substring(1, filtro.getValor().length() - 1))));
					// hayFiltro = true;
					break;
				}
			}
		}

		// if (!usuarioAdmin & !hayFiltro && !entidadCns.getDbEntidad().getEntidadNombre().equals("DbEntidad"))
		// throw new ExcepcionControlada("Debe establecer al menos un filtro para la entidad principal");

		if (entidadCns.getDbEntidad().getSoloAdmin() && !usuarioAdmin)
			throw new Exception("La entidad '" + entidadCns.getEntidad() + "' esta restringida");

		// Obtengo las entidades
		if (esFiltroPorKeyName) {
			// KeyName
			if (lstFiltros != null && lstFiltros.size() > 0)
				throw new Exception("No se permite usar keyName y filtros WHERE al mismo tiempo");
			lstEntidades = new ArrayList<Entity>();
			if (keyName.startsWith("IN")) {
				cursorStr = null;
				entidadesLeidas = 0;
				while (keyName.startsWith("IN "))
					keyName = "IN" + keyName.substring(3);
				if (!keyName.startsWith("IN(") || !keyName.endsWith(")"))
					throw new Exception("La estructura IN no es válida: '" + keyName + "'");
				String valores = keyName.substring(3, keyName.length() - 1);
				String[] arrValores = valores.split(",");
				for (String keyNam : arrValores) {
					keyNam = keyNam.substring(1, keyNam.length() - 1).replace("&SiNubeApostrofo;", "'");
					try {
						lstEntidades.add(ClsEntidad.obtenerEntidad(datastore, entidadCns.getEntidad(), keyNam));
						entidadesLeidas++;
					} catch (EntityNotFoundException e) {
						// throw new Exception("No existe la entidad '" + entidadCns.getEntidad() + "' con keyName IN '" + keyNam + "'");
					}
				}
			} else {
				try {
					lstEntidades.add(ClsEntidad.obtenerEntidad(datastore, entidadCns.getEntidad(), keyName));
					cursorStr = null;
					entidadesLeidas = 1;
				} catch (EntityNotFoundException e) {
					// throw new Exception("No existe la entidad '" + entidadCns.getEntidad() + "' con keyName '" + keyName + "'");
				}
			}
		} else {
			lstEntidades = ClsEntidad.ejecutarConsulta(datastore, entidadCns.getEntidad(), lstFiltros, tamPag, cursorStr);
			cursorStr = ClsEntidad.getStrCursor();
			entidadesLeidas = lstEntidades.size();
		}

		// Genero el blob con los datos
		ClsBlobWriter blob = new ClsBlobWriter("¬");
		blob.agregarStr("");
		blob.agregarStr(cursorStr);
		generarEncabezado = true;
		procesarEntidadesSql(datastore, empresa, entidadCns, lstCamposCns, lstEntidades, blob, usuarioAdmin, entidadCns.getFiltrosOn());

		blob.insertarAlInicioStr(String.valueOf(entidadesLeidas));
		return blob.getString();
	}

	private void procesarEntidadesSql(DatastoreService datastore, String empresa, ClsEntidadCns entidadCns, List<ClsCampoCns> lstCamposCns, List<Entity> lstEntidades, ClsBlobWriter blob, boolean usuarioAdmin, List<ClsCampoCns> lstFiltrosWhere) throws Exception {
		if (lstEntidades.size() == 0 && !entidadCns.getInnerJoin() && entidadCns.getEntidadPadre() != null)
			lstEntidades.add(new Entity(KeyFactory.createKey(entidadCns.getEntidad(), "Null")));
		Map<String, ClsCampo> mapCamposBd = entidadCns.getMapCamposBd();
		entidad: for (Entity entity : lstEntidades) {
			entidadCns.setEntidadActual(entity);
			EntidadTemporal dbEntidadTemp = new EntidadTemporal();
			dbEntidadTemp.setEntidad(entity);
			dbEntidadTemp.asignarValoresDefault(mapCamposBd);
			// Reviso los filtros WHERE en memoria '$'
			for (ClsCampoCns filtro : lstFiltrosWhere) {
				if (filtro.getEnMemoria() && filtro.getEntidadAlias().equals(entidadCns.getAlias())) {
					if (!mapCamposBd.containsKey(filtro.getCampo()))
						throw new ExcepcionControlada("No puede filtrar en memoria el campo " + filtro.getCampo() + " pues no existe en " + entidadCns.getEntidad());
					if (filtro.getValor().equals("Null")) {
						if (dbEntidadTemp.getEntidad().getProperty(filtro.getCampo()) != null)
							continue entidad;
					} else {
						ClsCampo campo = mapCamposBd.get(filtro.getCampo());
						switch (campo.getTipo()) {
						case String:
							if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
							if (!ClsUtil.esIgualConNulo(dbEntidadTemp.getString(campo), filtro.getValor().substring(1, filtro.getValor().length() - 1).replace("&SiNubeApostrofo;", "'")))
								continue entidad;
							break;
						case Boolean:
							Boolean valBool = null;
							if (!ClsUtil.esNulo(filtro.getValor())) {
								if (filtro.getValor().equalsIgnoreCase("true")) {
									valBool = true;
								} else if (filtro.getValor().equalsIgnoreCase("false")) {
									valBool = false;
								} else {
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean (true o false) : " + filtro.getValor());
								}
								// try {
								// valBool = Boolean.parseBoolean(filtro.getValor());
								// } catch (Exception e) {
								// throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean : " + filtro.getValor());
								// }
							}
							if (!ClsUtil.esIgualConNulo(dbEntidadTemp.getBoolean(campo), valBool))
								continue entidad;
							break;
						case Long:
							Long valLong;
							try {
								valLong = Long.parseLong(filtro.getValor());
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 3");
							}
							if (!ClsUtil.esIgualConNulo(dbEntidadTemp.getLong(campo), valLong))
								continue entidad;
							break;
						case Double:
							Double valDouble;
							try {
								valDouble = Double.parseDouble(filtro.getValor());
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un double : " + filtro.getValor());
							}
							if (!ClsUtil.esIgualConNulo(dbEntidadTemp.getDouble(campo), valDouble))
								continue entidad;
							break;
						case Date:
							throw new ExcepcionControlada("No puede filtrar en campos Date: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
						case Rating:
							Integer valInt;
							try {
								valInt = Integer.parseInt(filtro.getValor());
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un int : " + filtro.getValor());
							}
							if (valInt < 0 || valInt > 99)
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un entero entre 0 y 99 : " + filtro.getValor());
							if (!ClsUtil.esIgualConNulo(dbEntidadTemp.getRating(campo), valInt))
								continue entidad;
							break;
						case Text:
							throw new ExcepcionControlada("No puede filtrar en memoria campos Text: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
						case Blob:
							throw new ExcepcionControlada("No puede filtrar en memoria campos Blob: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
						case ArrayString:
							throw new ExcepcionControlada("No puede filtrar en memoria campos ArrayList: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
						case ArrayLong:
							throw new ExcepcionControlada("No puede filtrar en memoria campos ArrayLong: " + filtro.getCampo() + " - " + entidadCns.getEntidad());
						case Email:
							if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
							if (!ClsUtil.esIgualConNulo(dbEntidadTemp.getEmail(campo), filtro.getValor().substring(1, filtro.getValor().length() - 1)))
								continue entidad;
							break;
						}
					}
				}
			}

			// Revisar que la entidad corresponda con la empresa sucursal
			if (!usuarioAdmin && (dbEntidadTemp.getKey().getName() == null || !dbEntidadTemp.getKey().getName().equals("Null")) && mapCamposBd.containsKey("empresa"))
				if (!dbEntidadTemp.getKey().getKind().equals("DbReporte") || !dbEntidadTemp.getString(mapCamposBd.get("empresa")).equals("neoreportes"))
					if (!dbEntidadTemp.getString(mapCamposBd.get("empresa")).equals(empresa))
						throw new ExcepcionControlada("La empresa de la entidad no coincide con la de la firma - " + dbEntidadTemp.getString(mapCamposBd.get("empresa")));
			// if (!usuarioAdmin && entidadCns.getDbEntidad().getValidarSucursal() && (dbEntidadTemp.getKey().getName() == null || !dbEntidadTemp.getKey().getName().equals("Null")) && mapCamposBd.containsKey("temporada"))
			// if (dbEntidadTemp.getLong(mapCamposBd.get("temporada")).longValue() != temporada)
			// // if (!(dbEntidadTemp.getKey().getKind().equals("DbParametro") && ClsUtil.esNulo(dbEntidadTemp.getString(mapCamposBd.get("temporada"))))) // Si es un parámetro por empresa no se valida la sucursal
			// throw new ExcepcionControlada("La temporada de la entidad no coincide con la de la firma - " + dbEntidadTemp.getLong(mapCamposBd.get("temporada")));
			// Reviso si hay sub entidades y las obtengo
			if (entidadCns.getSubEntidad() != null) {
				boolean ejecutarConsulta = true;
				boolean filtroPorKeyAncestor = false;
				Key keyAncestor = null;
				// proceso los filtros de la sub entidad
				ClsEntidadCns subEntidadCns = entidadCns.getSubEntidad();
				List<Filter> lstFiltros = new ArrayList<Filter>();
				for (ClsCampoCns filtro : subEntidadCns.getFiltrosOn()) {
					if (filtro.getCampo().equals("keyAncestor")) {
						if (lstFiltros.size() > 0)
							throw new ExcepcionControlada("En consultas por Ancestro no puede haber más filtros ON - A");
						if (!filtroPorKeyAncestor) {
							filtroPorKeyAncestor = true;
							ClsCampoCns campoValor = new ClsCampoCns(filtro.getValor());
							if (!campoValor.getCampo().equals("key"))
								throw new ExcepcionControlada("En consultas por Ancestro la igualdad debe ser contra un key - " + filtro.getValor());
						} else {
							throw new ExcepcionControlada("El filtro por keyAncestor existe más de una vez-" + filtro.getValor());
						}
					} else {
						if (filtroPorKeyAncestor)
							throw new ExcepcionControlada("En consultas por Ancestro no puede haber más filtros ON - D");
					}
					// if (keyAncestor)
					// throw new Exception("Es consulta por keyAncestor: '" + filtro.getCampo() + "' - '" + filtro.getValor() + "'");
					if (!filtroPorKeyAncestor && !subEntidadCns.getMapCamposBd().containsKey(filtro.getCampo()))
						throw new ExcepcionControlada("No puede filtrar el campo " + filtro.getEntidadAlias() + "." + filtro.getCampo() + " pues no existe en " + subEntidadCns.getEntidad() + " (FILTRO-JOIN)");
					// Obtengo el valor de las entidades padre
					ClsCampoCns campoValor = new ClsCampoCns(filtro.getValor());
					if (campoValor.getEntidadAlias().equals(subEntidadCns.getAlias()))
						throw new ExcepcionControlada("No puede filtrar el campo " + filtro.getCampo() + " con un campo en la misma entidad (JOIN)");
					ClsEntidadCns entidadPadre = entidadCns;
					boolean encontrado = false;
					String valor = null;
					// Filtro keyParent
					while (!encontrado && entidadPadre != null) {
						if (entidadPadre.getAlias().equals(campoValor.getEntidadAlias())) {
							encontrado = true;
							if (filtroPorKeyAncestor) {
								keyAncestor = entidadPadre.getEntidadActual().getKey();
							} else {
								if (!entidadPadre.getMapCamposBd().containsKey(campoValor.getCampo()))
									throw new ExcepcionControlada("No puede filtrar el campo '" + filtro.getEntidadAlias() + "." + filtro.getCampo() + "' con el campo '" + campoValor.getCampo() + "' pues no existe (ON)");
								if (!campoValor.getEsFormula()) {
									valor = String.valueOf(entidadPadre.getEntidadActual().getProperty(campoValor.getCampo()));
								} else {
									if (campoValor.getFormula().equals("LOTE")) {
										try {
											@SuppressWarnings("unchecked")
											ArrayList<String> lstLotes = (ArrayList<String>) entidadPadre.getEntidadActual().getProperty(campoValor.getCampo());
											Collections.sort(lstLotes);
											valor = "";
											if (lstLotes != null && lstLotes.size() >= campoValor.getNivel()) {
												for (int i = 0; i < campoValor.getNivel(); i++)
													valor += lstLotes.get(i);
											}
										} catch (Exception e) {
											throw new Exception("El campo en la función LOTE debe ser un ArrayString : " + campoValor.getCampo() + " : " + entidadPadre.getEntidadActual().getKind() + " : " + e.getMessage());
										}
									} else {
										throw new Exception("la fórmula '" + campoValor.getFormula() + "' no es válida para una condición ON");
									}
								}
							}
						} else {
							entidadPadre = entidadPadre.getEntidadPadre();
						}
					}
					if (!encontrado)
						throw new ExcepcionControlada("No encontró el valor para el campo '" + filtro.getValor() + "' (ON)");

					if (!filtroPorKeyAncestor) {
						ClsCampo campo = subEntidadCns.getMapCamposBd().get(filtro.getCampo());
						if (!campo.getIndexado() && subEntidadCns.getDbEntidad().getEndpoint())
							throw new ExcepcionControlada("El campo " + filtro.getCampo() + " no está indexado en " + subEntidadCns.getEntidad());
						switch (campo.getTipo()) {
						case String:
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, valor));
							break;
						case Boolean:
							boolean valBool;
							try {
								valBool = Boolean.parseBoolean(valor);
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean : " + valor);
							}
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, valBool));
							break;
						case Long:
							long valLong = 0;
							try {
								valLong = Long.parseLong(valor);
							} catch (Exception e) {
								ejecutarConsulta = false;
								// throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + valor + ". 4");
							}
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, valLong));
							break;
						case Double:
							throw new ExcepcionControlada("No puede filtrar en campos Double: " + filtro.getCampo() + " - " + subEntidadCns.getEntidad());
						case Date:
							try {
								valLong = Long.parseLong(filtro.getValor());
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 1");
							}
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Date(valLong)));
						case Rating:
							int valInt;
							try {
								valInt = Integer.parseInt(valor);
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un int : " + valor);
							}
							if (valInt < 0 || valInt > 99)
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un entero entre 0 y 99 : " + valor);
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, new Rating(valInt)));
							break;
						case Text:
							throw new ExcepcionControlada("No puede filtrar en campos Text: " + filtro.getCampo() + " - " + subEntidadCns.getEntidad());
						case Blob:
							throw new ExcepcionControlada("No puede filtrar en campos Blob: " + filtro.getCampo() + " - " + subEntidadCns.getEntidad());
						case ArrayString:
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, valor));
							break;
						case ArrayLong:
							try {
								valLong = Long.parseLong(valor);
							} catch (Exception e) {
								throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + valor + ". 5");
							}
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, valLong));
							break;
						case Email:
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), FilterOperator.EQUAL, new Email(valor)));
							break;
						}
					}
				}

				// Proceso los filtro WHERE para la sub-entidad
				if (ejecutarConsulta) {
					for (ClsCampoCns filtro : lstFiltrosWhere) {
						if (filtro.getEnMemoria() || !filtro.getEntidadAlias().equals(subEntidadCns.getAlias()))
							continue;
						if (!subEntidadCns.getMapCamposBd().containsKey(filtro.getCampo()))
							throw new ExcepcionControlada("No puede filtrar el campo " + filtro.getCampo() + " pues no existe en " + subEntidadCns.getEntidad() + " (FILTRO-JOIN-WHERE)");
						ClsCampo campo = subEntidadCns.getMapCamposBd().get(filtro.getCampo());
						if (!campo.getIndexado() && subEntidadCns.getDbEntidad().getEndpoint())
							throw new ExcepcionControlada("El campo " + filtro.getCampo() + " no está indexado en " + subEntidadCns.getEntidad());
						if (filtro.getOperador() == null)
							throw new Exception("El filtro '" + filtro.getCampo() + " - " + filtro.getValor() + "' no tiene operador");
						if (filtro.getValor().equals("Null")) {
							lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), null));
						} else {
							switch (campo.getTipo()) {
							case String:
								if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), filtro.getValor().substring(1, filtro.getValor().length() - 1).replace("&SiNubeApostrofo;", "'")));
								break;
							case Boolean:
								boolean valBool;
								if (filtro.getValor().equalsIgnoreCase("true")) {
									valBool = true;
								} else if (filtro.getValor().equalsIgnoreCase("false")) {
									valBool = false;
								} else {
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean (true o false) : " + filtro.getValor());
								}
								// try {
								// valBool = Boolean.parseBoolean(filtro.getValor());
								// } catch (Exception e) {
								// throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un boolean : " + filtro.getValor());
								// }
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), valBool));
								break;
							case Long:
								long valLong;
								try {
									valLong = Long.parseLong(filtro.getValor());
								} catch (Exception e) {
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 6");
								}
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), valLong));
								break;
							case Double:
								throw new ExcepcionControlada("No puede filtrar en campos Double: " + filtro.getCampo() + " - " + subEntidadCns.getEntidad());
							case Date:
								try {
									valLong = Long.parseLong(filtro.getValor());
								} catch (Exception e) {
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 1");
								}
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Date(valLong)));
							case Rating:
								int valInt;
								try {
									valInt = Integer.parseInt(filtro.getValor());
								} catch (Exception e) {
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un int : " + filtro.getValor());
								}
								if (valInt < 0 || valInt > 99)
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un entero entre 0 y 99 : " + filtro.getValor());
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Rating(valInt)));
								break;
							case Text:
								throw new ExcepcionControlada("No puede filtrar en campos Text: " + filtro.getCampo() + " - " + subEntidadCns.getEntidad());
							case Blob:
								throw new ExcepcionControlada("No puede filtrar en campos Blob: " + filtro.getCampo() + " - " + subEntidadCns.getEntidad());
							case ArrayString:
								if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), filtro.getValor().substring(1, filtro.getValor().length() - 1).replace("&SiNubeApostrofo;", "'")));
								break;
							case ArrayLong:
								try {
									valLong = Long.parseLong(filtro.getValor());
								} catch (Exception e) {
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser un long : " + filtro.getValor() + ". 7");
								}
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), valLong));
								break;
							case Email:
								if (!filtro.getValor().startsWith("'") || !filtro.getValor().endsWith("'"))
									throw new Exception("El valor del filtro para " + filtro.getCampo() + " debe ser una cadena : " + filtro.getValor());
								lstFiltros.add(new FilterPredicate(filtro.getCampo(), filtro.getOperador(), new Email(filtro.getValor().substring(1, filtro.getValor().length() - 1))));
								break;
							}
						}
					}
				}

				if (filtroPorKeyAncestor && lstFiltros.size() > 0)
					throw new Exception("En consultas por Ancestro no se permiten filtros WHERE");

				// Obtengo las entidades
				if (ejecutarConsulta) {
					lstEntidades = dameSubConsulta(subEntidadCns.getEntidad(), lstFiltros);
					if (lstEntidades == null) {
						if (!filtroPorKeyAncestor) {
							lstEntidades = ClsEntidad.ejecutarConsulta(datastore, subEntidadCns.getEntidad(), lstFiltros, 500, null);
							agregarSubConsulta(subEntidadCns.getEntidad(), lstFiltros, lstEntidades);
							entidadesLeidas += lstEntidades.size();
						} else {
							// Ejecutar consulta por Ancestor
							if (keyAncestor == null)
								throw new Exception("Es consulta por Ancestro pero keyAncestor en Null");
							lstEntidades = ClsEntidad.ejecutarConsulta(datastore, null, subEntidadCns.getEntidad(), keyAncestor);
							entidadesLeidas += lstEntidades.size();
						}
					}
				} else {
					// Se omite la consulta porque hay un long = null en el WHERE (ON)
					lstEntidades = new ArrayList<Entity>(0);
				}
				procesarEntidadesSql(datastore, empresa, subEntidadCns, lstCamposCns, lstEntidades, blob, usuarioAdmin, lstFiltrosWhere);
			} else {
				// Si es la primer fila se generan los encabezados con los nombres de columna y tipo de datos
				if (generarEncabezado) {
					generarEncabezado = false;
					for (ClsCampoCns campoCns : lstCamposCns) {
						if (campoCns.esConstante) {
							blob.agregarStr(campoCns.getAlias());
							blob.agregarStr(campoCns.getTipoConstante());
						} else {
							// Busco la entidad que corresponde con el campo
							boolean encontrado = false;
							ClsEntidadCns entidadCnsTemp = entidadCns;
							while (!encontrado && entidadCnsTemp != null) {
								if (entidadCnsTemp.getAlias().equals(campoCns.getEntidadAlias())) {
									encontrado = true;
									if (!campoCns.getCampo().equals("key") && !campoCns.getCampo().equals("keyPath") && !entidadCnsTemp.getMapCamposBd().containsKey(campoCns.getCampo()))
										throw new ExcepcionControlada("No puede usar el campo '" + campoCns.getEntidadAlias() + "." + campoCns.getCampo() + "' pues no existe en '" + entidadCnsTemp.getEntidad() + "' (BlobW)");
									blob.agregarStr(campoCns.getAlias());
									if (campoCns.getEsFormula() && campoCns.getFormula().equals("RATING")) {
										blob.agregarStr("String");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("ETI_CLI")) {
										blob.agregarStr("String");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("AÑO")) {
										blob.agregarStr("Long");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("MES")) {
										blob.agregarStr("Long");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("SEMANA")) {
										blob.agregarStr("Long");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("DIASEMANA")) {
										blob.agregarStr("String");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("CAMPOADICIONAL")) {
										blob.agregarStr("String");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("VALORLOTE")) {
										blob.agregarStr("String");
									} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("LLAVELOTE")) {
										blob.agregarStr("String");
									} else if (campoCns.getCampo().equals("key") || campoCns.getCampo().equals("keyPath")) {
										blob.agregarStr("String");
									} else {
										blob.agregarStr(entidadCnsTemp.getMapCamposBd().get(campoCns.getCampo()).getTipo().toString());
									}
								} else {
									entidadCnsTemp = entidadCnsTemp.getEntidadPadre();
								}
							}
							if (!encontrado)
								throw new ExcepcionControlada("No encontró el valor para el campo '" + campoCns.getEntidadAlias() + "." + campoCns.getCampo() + "' (BlobW)");
						}
					}
				}
				// Es la última subEntidad, se generan los renglones
				blob.nuevaFila();
				// blob.agregarStr(entidadCns.getEntidad());
				// Agrego los campos al blob
				for (ClsCampoCns campoCns : lstCamposCns) {
					// Si es una constante solo se pone
					if (campoCns.getEsConstante()) {
						blob.agregarStr(campoCns.getCampo().replace("&NumFilaSiNube;", String.valueOf(blob.getNumFilas())));
						continue;
					}
					// Busco la entidad que corresponde con el campo
					boolean encontrado = false;
					ClsEntidadCns entidadCnsTemp = entidadCns;
					while (!encontrado && entidadCnsTemp != null) {
						if (entidadCnsTemp.getAlias().equals(campoCns.getEntidadAlias())) {
							encontrado = true;
							if (!campoCns.getCampo().equals("key") && !campoCns.getCampo().equals("keyPath") && !entidadCnsTemp.getMapCamposBd().containsKey(campoCns.getCampo()))
								throw new ExcepcionControlada("No puede usar el campo '" + campoCns.getEntidadAlias() + "." + campoCns.getCampo() + "' pues no existe en '" + entidadCnsTemp.getEntidad() + "' (BlobW2)");
							dbEntidadTemp.setEntidad(entidadCnsTemp.getEntidadActual());
						} else {
							entidadCnsTemp = entidadCnsTemp.getEntidadPadre();
						}
					}
					if (!encontrado)
						throw new ExcepcionControlada("No encontró el valor para el campo '" + campoCns.getEntidadAlias() + "." + campoCns.getCampo() + "' (BlobW)");

					if (dbEntidadTemp.getKey().getName() != null && dbEntidadTemp.getKey().getName().equals("Null")) {
						blob.agregarStr(null);
					} else if (campoCns.getCampo().equals("key")) {
						blob.agregarStr(KeyFactory.keyToString(dbEntidadTemp.getKey()));
					} else if (campoCns.getCampo().equals("keyPath")) {
						Key key = dbEntidadTemp.getKey();
						String keyPath = null;
						while (key != null) {
							keyPath = key.getKind() + "," + (key.getName() == null ? key.getId() : "'" + key.getName() + "'") + (keyPath == null ? "" : "," + keyPath);
							key = key.getParent();
						}
						blob.agregarStr("KEY(" + keyPath + ")");
					} else {
						ClsCampo campo = entidadCnsTemp.getMapCamposBd().get(campoCns.getCampo());
						switch (campo.getTipo()) {
						case String:
							// if (campoCns.getEsFormula() && campoCns.getFormula().equals("CAMPOADICIONAL")) {
							// blob.agregarStr(dbEntidadTemp.getString(campo) == null ? null : "CAMPOADICIONAL(" + campoCns.getValores().get(0) + ";<<<--!" + dbEntidadTemp.getString(campo) + "!-->>>)");
							// } else {
							blob.agregarStr(dbEntidadTemp.getString(campo));
							// }
							break;
						case Boolean:
							blob.agregarBool(dbEntidadTemp.getBoolean(campo));
							break;
						case Long:
							if (campoCns.getEsFormula() && campoCns.getFormula().equals("ETI_CLI")) {
								try {
									// Obtengo los valores de la etiqueta
									List<Filter> lstFiltros = new ArrayList<Filter>();
									lstFiltros.add(new FilterPredicate("empresa", FilterOperator.EQUAL, empresa));
									lstFiltros.add(new FilterPredicate("etiqueta", FilterOperator.EQUAL, campoCns.getValores().get(0)));
									lstEntidades = ClsEntidad.ejecutarConsulta(datastore, "DbClienteEtiquetaDet", lstFiltros, 500, null);

									if (lstEntidades != null && lstEntidades.size() > 0) {
										entidadesLeidas += lstEntidades.size();
										Map<Long, String> mapEtiquetas = new HashMap<Long, String>();
										for (Entity entidad : lstEntidades)
											mapEtiquetas.put((Long) entidad.getProperty("id"), (String) entidad.getProperty("nombre"));

										// Obtengo las etiquetas del cliente
										Key keyp = KeyFactory.createKey("DbCliente", empresa + "-" + dbEntidadTemp.getLong(campo));
										Key key = KeyFactory.createKey(keyp, "DbCliente_A", empresa + "-" + dbEntidadTemp.getLong(campo));
										Entity dbFactura_A;
										try {
											dbFactura_A = datastore.get(key);
											entidadesLeidas++;
										} catch (EntityNotFoundException e) {
											throw new Exception("No existe el Cliente_A: " + empresa + "-" + dbEntidadTemp.getLong(campo));
										}
										@SuppressWarnings("unchecked")
										List<Long> etiquetas = (List<Long>) dbFactura_A.getProperty("etiquetasDet");

										if (etiquetas != null && etiquetas.size() > 0) {
											boolean okEti = false;
											for (Long etiqueta : etiquetas) {
												if (mapEtiquetas.containsKey(etiqueta)) {
													blob.agregarStr(mapEtiquetas.get(etiqueta));
													okEti = true;
													break;
												}
											}
											if (!okEti)
												blob.agregarStr(null);
										} else {
											blob.agregarStr(null);
										}
									} else {
										blob.agregarStr(null);
									}
								} catch (Exception e) {
									throw new Exception("Error al calcular ENT_CLI: " + e.getMessage());
								}
							} else {
								blob.agregarLong(dbEntidadTemp.getLong(campo));
							}
							break;
						case Double:
							blob.agregarDbl(dbEntidadTemp.getDouble(campo));
							break;
						case Date:
							if (campoCns.getEsFormula() && campoCns.getFormula().equals("AÑO")) {
								blob.agregarStr(dbEntidadTemp.getDate(campo) == null ? null : "AÑO(" + dbEntidadTemp.getDate(campo).getTime() + ")");
							} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("MES")) {
								blob.agregarStr(dbEntidadTemp.getDate(campo) == null ? null : "MES(" + dbEntidadTemp.getDate(campo).getTime() + ")");
							} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("SEMANA")) {
								blob.agregarStr(dbEntidadTemp.getDate(campo) == null ? null : "SEMANA(" + dbEntidadTemp.getDate(campo).getTime() + ")");
							} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("DIASEMANA")) {
								blob.agregarStr(dbEntidadTemp.getDate(campo) == null ? null : "DIASEMANA(" + dbEntidadTemp.getDate(campo).getTime() + ")");
							} else {
								blob.agregarLong(dbEntidadTemp.getDate(campo) == null ? null : dbEntidadTemp.getDate(campo).getTime());
							}
							break;
						case Rating:
							if (campoCns.getEsFormula() && campoCns.getFormula().equals("RATING")) {
								try {
									blob.agregarStr(campoCns.getValores().get(dbEntidadTemp.getRating(campo)));
								} catch (IndexOutOfBoundsException e) {
									throw new Exception("El valor del campo '" + campoCns.getCampo() + "' es mayor que el número de elementos proporcionados en la función RATING: " + campoCns.getValores().size());
								}
							} else {
								blob.agregarInt(dbEntidadTemp.getRating(campo));
							}
							break;
						case Text:
							if (campoCns.getEsFormula() && campoCns.getFormula().equals("CAMPOADICIONAL")) {
								blob.agregarStr(dbEntidadTemp.getText(campo) == null ? null : "CAMPOADICIONAL(" + campoCns.getValores().get(0) + ";<<<--!" + dbEntidadTemp.getText(campo) + "!-->>>)");
							} else {
								blob.agregarStr(dbEntidadTemp.getText(campo));
							}
							break;
						case Blob:
							StringBuilder sb = null;
							byte[] arr = dbEntidadTemp.getBlob(campo);
							if (arr != null && arr.length > 0) {
								sb = new StringBuilder(4096);
								for (byte val : arr) {
									if (sb.length() > 0)
										sb.append(",");
									sb.append(val);
								}
							}
							blob.agregarStr(sb == null ? null : sb.toString());
							break;
						case ArrayString:
							if (campoCns.getEsFormula() && campoCns.getFormula().equals("VALORLOTE")) {
								String valorLote = null;
								ArrayList<String> lstString = dbEntidadTemp.getArrayString(campo);
								if (lstString != null && lstString.size() > 0) {
									for (String val : lstString) {
										if (val.length() >= campoCns.getValores().get(0).length() && val.startsWith(campoCns.getValores().get(0))) {
											valorLote = val.substring(1);
											break;
										}
									}
								}
								blob.agregarStr(valorLote);
							} else if (campoCns.getEsFormula() && campoCns.getFormula().equals("LLAVELOTE")) {
								String valorLote = null;
								ArrayList<String> lstString = dbEntidadTemp.getArrayString(campo);
								if (lstString == null || lstString.size() == 0 || (lstString.size() == 1 && lstString.get(0).length() == 0)) {
									blob.agregarStr("");
								} else {
									int loteNivel = 0;
									try {
										loteNivel = Integer.parseInt(campoCns.getValores().get(0));
									} catch (Exception e) {
										throw new Exception("El segundo parámetro de la funcion LLAVELOTE no es válido: " + campoCns.getValores().get(0));
									}
									String llaveLote = "";
									for (int i = 1; i <= loteNivel; i++) {
										for (String lote : lstString) {
											if (lote.startsWith(String.valueOf(i))) {
												llaveLote += lote;
												break;
											}
										}
										blob.agregarStr(llaveLote);
									}
								}
							} else {
								sb = null;
								ArrayList<String> lstString = dbEntidadTemp.getArrayString(campo);
								if (lstString != null && lstString.size() > 0) {
									sb = new StringBuilder(4096);
									for (String val : lstString) {
										if (sb.length() > 0)
											sb.append(",");
										sb.append(val.replace(",", "&ComaSiNube;"));
									}
								}
								blob.agregarStr(sb == null ? null : sb.toString());
							}
							break;
						case ArrayLong:
							sb = null;
							ArrayList<Long> lstLong = dbEntidadTemp.getArrayLong(campo);
							if (lstLong != null && lstLong.size() > 0) {
								sb = new StringBuilder(4096);
								for (Long val : lstLong) {
									if (sb.length() > 0)
										sb.append(",");
									sb.append(val);
								}
							}
							blob.agregarStr(sb == null ? null : sb.toString());
							break;
						case Email:
							blob.agregarStr(dbEntidadTemp.getEmail(campo));
							break;
						}
					}
				}
			}
		}
	}

	private void agregarSubConsulta(String entidad, List<Filter> lstFiltros, List<Entity> lstEntidades) {
		if (lstSubConsulta == null)
			lstSubConsulta = new ArrayList<ClsSubConsulta>();
		lstSubConsulta.add(new ClsSubConsulta(entidad, lstFiltros, lstEntidades));
	}

	private List<Entity> dameSubConsulta(String entidad, List<Filter> lstFiltros) throws Exception {
		if (lstSubConsulta == null || lstSubConsulta.size() == 0 || lstFiltros.size() == 0)
			return null;
		subcns: for (ClsSubConsulta clsSubConsulta : lstSubConsulta) {
			if (!entidad.equals(clsSubConsulta.getEntidad()))
				break;
			if (lstFiltros.size() != clsSubConsulta.getLstFiltros().size())
				break;
			for (Filter filtro : lstFiltros) {
				boolean encontrado = false;
				for (Filter filtro2 : clsSubConsulta.getLstFiltros()) {
					if (filtro.equals(filtro2)) {
						encontrado = true;
						break;
					}
				}
				if (!encontrado)
					break subcns;
			}
			// Es igual
			return clsSubConsulta.getLstEntidades();
		}
		return null;
	}

	private int damePos(String cns, boolean espacio, boolean coma, boolean esperarApostrofo) throws Exception {
		int pos = -1;
		if (espacio) {
			int posEspacio = cns.indexOf(" ");
			if (posEspacio >= 0)
				pos = posEspacio;
		}
		if (coma) {
			int posComa = cns.indexOf(",");
			if (posComa >= 0 && posComa < pos)
				pos = posComa;
		}
		if (esperarApostrofo && pos > 0 && cns.substring(0, pos).contains("='")) {
			int pos2 = cns.indexOf("='");
			pos2 = cns.indexOf("'", pos2 + 2);
			if (pos2 < 0)
				throw new Exception("Falta el apóstrofo de cierre para: " + cns.substring(0, pos));
			pos2++;
			if (pos2 > pos)
				pos = pos2;
		}
		return pos;
	}

	public Respuesta guardarEstructura(String entidad) throws Exception {
		String pos = "ini";
		Respuesta resp = new Respuesta("Correcto");
		try {
			// ClsUtil.validaToken(token);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			// Obtengo los campos de la Entidad
			pos = "campos";
			List<ClsCampo> lstCampos;
			try {
				lstCampos = dameCampos(entidad);
			} catch (ExcepcionControlada e1) {
				resp.setCadena(e1.getMessage());
				return resp;
			}
			pos = "BD";
			// Creo la entidad en BD
			Key keyp = KeyFactory.createKey("DbEntidad", entidad);
			DbEntidad dbEntidad;
			try {
				dbEntidad = new DbEntidad(datastore.get(keyp));
				if (!dbEntidad.getEndpoint())
					dbEntidad.setEndpoint(true);
			} catch (EntityNotFoundException e) {
				dbEntidad = new DbEntidad(entidad, true, false, false, true);
			}
			pos = "agrCampos";
			// Agrego los campos
			List<Entity> lstEntidadCampo = ClsEntidad.ejecutarConsulta(datastore, null, "DbEntidadCampo", dbEntidad.getKey());
			Map<String, DbEntidadCampo> mapCampos = new HashMap<String, DbEntidadCampo>();
			for (Entity campo : lstEntidadCampo) {
				DbEntidadCampo dbCampo = new DbEntidadCampo(campo);
				mapCampos.put(dbCampo.getCampo(), dbCampo);
			}
			pos = "forCampos";
			for (ClsCampo campo : lstCampos) {
				DbEntidadCampo dbCampo;
				pos = "for1-" + campo.getNombre();
				if (mapCampos.containsKey(campo.getNombre())) {
					pos = "for1A-" + campo.getNombre();
					dbCampo = mapCampos.get(campo.getNombre());
				} else {
					pos = "for1B-" + campo.getNombre();
					dbCampo = new DbEntidadCampo(entidad, campo);
				}
				pos = "for:Tipo-" + campo.getNombre();
				String tipo = campo.getTipo().name();
				if (!tipo.equals(dbCampo.getTipo()))
					dbCampo.setTipo(tipo);

				pos = "for:Indexado-" + campo.getNombre();
				if (dbCampo.getIndexado() != campo.getIndexado())
					dbCampo.setIndexado(campo.getIndexado());

				pos = "for:permiteNull-" + campo.getNombre();
				if (dbCampo.getPermiteNull() != campo.getPermiteNull())
					dbCampo.setPermiteNull(campo.getPermiteNull());

				pos = "for:LargoMinimo-" + campo.getNombre();
				if (dbCampo.getLargoMinimo() != campo.getLargoMinimo())
					dbCampo.setLargoMinimo(campo.getLargoMinimo());

				pos = "for:LargoMaximo-" + campo.getNombre();
				if (dbCampo.getLargoMaximo() != campo.getLargoMaximo())
					dbCampo.setLargoMaximo(campo.getLargoMaximo());

				pos = "for:LargoMaximo-" + campo.getNombre();
				if (dbCampo.getLargoMaximo() != campo.getLargoMaximo())
					dbCampo.setLargoMaximo(campo.getLargoMaximo());

				pos = "for:EntidadGrande-" + campo.getNombre();
				if (dbCampo.getEntidadGrande() != campo.getEntidadGrande())
					dbCampo.setEntidadGrande(campo.getEntidadGrande());

				pos = "for:ValorDefault-" + campo.getNombre();
				if (!ClsUtil.esIgualConNulo(dbCampo.getValorDefault(), campo.getValorDefault()))
					dbCampo.setValorDefault(campo.getValorDefault());

				// pos = "for:Descripcion-" + campo.getNombre();
				// if (!ClsUtil.esIgualConNulo(dbCampo.getDescripcion(), campo.getDescripcion()))
				// dbCampo.setDescripcion(campo.getDescripcion());

				pos = "for:PosLlave-" + campo.getNombre();
				if (dbCampo.getPosLlave() != campo.getPosLlave())
					dbCampo.setPosLlave(campo.getPosLlave());

				pos = "for:SustituirNull-" + campo.getNombre();
				if (dbCampo.getSustituirNull() != campo.getSustituirNull())
					dbCampo.setSustituirNull(campo.getSustituirNull());

				pos = "for4-" + campo.getNombre();
				dbCampo.guardar(datastore);

				pos = "for5-" + campo.getNombre();
				if (mapCampos.containsKey(campo.getNombre())) {
					mapCampos.remove(campo.getNombre());
				}
			}

			pos = "elimCampos";
			// Elimino los campos que ya no están en la definición
			for (DbEntidadCampo campo : mapCampos.values())
				campo.eliminar(datastore);

			pos = "guardar";
			dbEntidad.guardar(datastore);

			return resp;
		} catch (Exception e) {
			resp.setCadena(entidad + " : " + pos + " : " + e.getMessage());
			return resp;
		}
	}

	private List<ClsCampo> dameCampos(String entidad) throws Exception {
		Entity entity = new Entity(KeyFactory.createKey("Entidad", "Prueba"));
		List<ClsCampo> lstCampos;
		if (entidad.equals("DbAlmacen")) {
			DbAlmacen db = new DbAlmacen(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbAlmEntrada")) {
			DbAlmEntrada db = new DbAlmEntrada(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbAlmEntradaDet")) {
			DbAlmEntradaDet db = new DbAlmEntradaDet(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbAlmSalida")) {
			DbAlmSalida db = new DbAlmSalida(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbAlmSalidaDet")) {
			DbAlmSalidaDet db = new DbAlmSalidaDet(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbCodigoDeBarras_A")) {
			DbCodigoDeBarras_A db = new DbCodigoDeBarras_A(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbCodigoDeBarras")) {
			DbCodigoDeBarras db = new DbCodigoDeBarras(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbColor")) {
			DbColor db = new DbColor(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbConsecutivo")) {
			DbConsecutivo db = new DbConsecutivo(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbDepartamento")) {
			DbDepartamento db = new DbDepartamento(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbEmpresa")) {
			DbEmpresa db = new DbEmpresa(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbEntidad")) {
			DbEntidad db = new DbEntidad(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbEntidadCampo")) {
			DbEntidadCampo db = new DbEntidadCampo(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbInvModeloDet")) {
			DbInvModeloDet db = new DbInvModeloDet(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbListaPrecios")) {
			DbListaPrecios db = new DbListaPrecios(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbListaPreciosDet")) {
			DbListaPreciosDet db = new DbListaPreciosDet(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbModelo")) {
			DbModelo db = new DbModelo(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbModeloHabilitacion")) {
			DbModeloHabilitacion db = new DbModeloHabilitacion(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbModeloImagen")) {
			DbModeloImagen db = new DbModeloImagen(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbModeloProceso")) {
			DbModeloProceso db = new DbModeloProceso(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbModeloProducido")) {
			DbModeloProducido db = new DbModeloProducido(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbParametro")) {
			DbParametro db = new DbParametro(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbPedido")) {
			DbPedido db = new DbPedido(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbPedidoDet")) {
			DbPedidoDet db = new DbPedidoDet(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbProceso")) {
			DbProceso db = new DbProceso(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbProcesoProduccion")) {
			DbOrden db = new DbOrden(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbProduccion")) {
			DbProduccion db = new DbProduccion(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbTalla")) {
			DbTalla db = new DbTalla(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbTallas")) {
			DbTallas db = new DbTallas(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbTelaHabilitacion")) {
			DbTelaHabilitacion db = new DbTelaHabilitacion(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbTemporada")) {
			DbTemporada db = new DbTemporada(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbUsuario")) {
			DbUsuario db = new DbUsuario(entity);
			lstCampos = db.getCampos();
		} else if (entidad.equals("DbVista")) {
			DbVista db = new DbVista(entity);
			lstCampos = db.getCampos();
		} else {
			throw new ExcepcionControlada("La entidad '" + entidad + "' no existe en EP > PmEntidad.dameCampos");
		}
		if (lstCampos == null)
			throw new ExcepcionControlada("La entidad " + entidad + " getCampos es Null");
		if (lstCampos.size() == 0)
			throw new ExcepcionControlada("La entidad " + entidad + " getCampos.size == 0");
		return lstCampos;
	}

	class ClsCampoCns {
		private String entidadAlias;
		private String campo;
		private String valor;
		private String alias = null;
		private boolean enMemoria = false;
		private boolean esFormula = false;
		private String formula = null;
		private List<String> valores = null;
		private boolean esConstante = false;
		private FilterOperator operador = FilterOperator.EQUAL;
		private int nivel = 0;
		private String tipoConstante = null;

		public ClsCampoCns(String entidadAlias, String campo) {
			setEntidadAlias(entidadAlias);
			setCampo(campo);
		}

		public ClsCampoCns(String valor) {
			if (valor.startsWith("'") && valor.endsWith("'")) {
				// Constante de tipo Cadena
				setEsConstante(true);
				setEntidadAlias("");
				if (valor.startsWith("'"))
					setCampo(valor.substring(1, valor.length() - 1));
				else
					setCampo(valor);
				tipoConstante = "String";
			} else if (valor.matches("\\d*\\.?\\d+")) {
				// Constante de tipo Long o Double
				setEsConstante(true);
				setEntidadAlias("");
				if (valor.startsWith("'"))
					setCampo(valor.substring(1, valor.length() - 1));
				else
					setCampo(valor);
				if (valor.contains("."))
					tipoConstante = "Double";
				else
					tipoConstante = "Long";
			} else {
				if (valor.startsWith("$")) {
					valor = valor.substring(1);
					setEnMemoria(true);
				} else if (valor.startsWith("LOTE(") && valor.endsWith(")")) {
					nivel = Integer.parseInt(valor.substring(valor.length() - 2, valor.length() - 1));
					valor = valor.substring(5, valor.length() - 3);
					setEsFormula(true);
					setFormula("LOTE");
				}
				int pos = valor.indexOf(".");
				if (pos < 0) {
					setEntidadAlias("");
					setCampo(valor);
				} else {
					String val = valor.substring(0, pos);
					setEntidadAlias(val);
					setCampo(valor.substring(pos + 1));
				}
			}
		}

		public String getTipoConstante() {
			if (esConstante) {
				return tipoConstante;
			} else {
				return null;
			}
		}

		public String getEntidadAlias() {
			return entidadAlias;
		}

		public void setEntidadAlias(String entidadAlias) {
			this.entidadAlias = entidadAlias;
		}

		public String getCampo() {
			return campo;
		}

		public void setCampo(String campo) {
			this.campo = campo;
		}

		public String getValor() {
			return valor;
		}

		public void setValor(String valor) {
			this.valor = valor.replace("&SiNubeEspacio&", " ");
		}

		public String getAlias() {
			if (alias == null)
				return campo;
			else
				return alias;
		}

		public void setAlias(String alias) {
			if (alias.startsWith("[") && alias.endsWith("]"))
				this.alias = alias.substring(1, alias.length() - 1).replace("&SiNubeEspacio&", " ");
			else
				this.alias = alias;
		}

		public boolean getEnMemoria() {
			return enMemoria;
		}

		public void setEnMemoria(boolean enMemoria) {
			this.enMemoria = enMemoria;
		}

		public boolean getEsFormula() {
			return esFormula;
		}

		public void setEsFormula(boolean esFormula) {
			this.esFormula = esFormula;
		}

		public String getFormula() {
			return formula;
		}

		public void setFormula(String formula) {
			this.formula = formula;
		}

		public List<String> getValores() {
			return valores;
		}

		public void setValores(List<String> valores) {
			this.valores = valores;
		}

		public void agregarValor(String valor) {
			if (this.valores == null)
				this.valores = new ArrayList<String>();
			this.valores.add(valor);
		}

		public boolean getEsConstante() {
			return esConstante;
		}

		public void setEsConstante(boolean esConstante) {
			this.esConstante = esConstante;
		}

		public FilterOperator getOperador() {
			return operador;
		}

		public void setOperador(FilterOperator operador) {
			this.operador = operador;
		}

		public int getNivel() {
			return nivel;
		}

		public void setNivel(int nivel) {
			this.nivel = nivel;
		}

	}

	class ClsEntidadCns {
		private String alias;
		private String entidad;
		private DbEntidad dbEntidad;
		private Map<String, ClsCampo> mapCamposBd;
		private boolean innerJoin;
		private ClsEntidadCns subEntidad;
		private ClsEntidadCns entidadPadre;
		private List<ClsCampoCns> lstFiltrosOn;
		private Entity entidadActual;

		public ClsEntidadCns(String alias, String entidad) {
			setAlias(alias);
			setEntidad(entidad);
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public String getEntidad() {
			return entidad;
		}

		public void setEntidad(String entidad) {
			this.entidad = entidad;
		}

		public DbEntidad getDbEntidad() {
			return dbEntidad;
		}

		public void setDbEntidad(DbEntidad dbEntidad) {
			this.dbEntidad = dbEntidad;
		}

		public Map<String, ClsCampo> getMapCamposBd() {
			return mapCamposBd;
		}

		public void setMapCamposBd(Map<String, ClsCampo> mapCamposBd) {
			this.mapCamposBd = mapCamposBd;
		}

		public boolean getInnerJoin() {
			return innerJoin;
		}

		public void setInnerJoin(boolean innerJoin) {
			this.innerJoin = innerJoin;
		}

		public ClsEntidadCns getSubEntidad() {
			return subEntidad;
		}

		public void setSubEntidad(ClsEntidadCns subEntidad) {
			this.subEntidad = subEntidad;
		}

		public ClsEntidadCns getEntidadPadre() {
			return entidadPadre;
		}

		public void setEntidadPadre(ClsEntidadCns entidadPadre) {
			this.entidadPadre = entidadPadre;
		}

		public List<ClsCampoCns> getFiltrosOn() {
			return lstFiltrosOn;
		}

		public void setFiltrosOn(List<ClsCampoCns> lstFiltrosOn) {
			this.lstFiltrosOn = lstFiltrosOn;
		}

		public Entity getEntidadActual() {
			return entidadActual;
		}

		public void setEntidadActual(Entity entidadActual) {
			this.entidadActual = entidadActual;
		}
	}

	class ClsSubConsulta {
		private String entidad;
		private List<Filter> lstFiltros;
		private List<Entity> lstEntidades;

		public ClsSubConsulta(String entidad, List<Filter> lstFiltros, List<Entity> lstEntidades) {
			this.entidad = entidad;
			this.lstFiltros = lstFiltros;
			this.lstEntidades = lstEntidades;
		}

		public String getEntidad() {
			return entidad;
		}

		public void setEntidad(String entidad) {
			this.entidad = entidad;
		}

		public List<Filter> getLstFiltros() {
			return lstFiltros;
		}

		public void setLstFiltros(List<Filter> lstFiltros) {
			this.lstFiltros = lstFiltros;
		}

		public List<Entity> getLstEntidades() {
			return lstEntidades;
		}

		public void setLstEntidades(List<Entity> lstEntidades) {
			this.lstEntidades = lstEntidades;
		}
	}

}