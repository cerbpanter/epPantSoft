package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmAlmacen;
import com.pantsoft.eppantsoft.pm.PmCodigoDeBarras;
import com.pantsoft.eppantsoft.pm.PmColor;
import com.pantsoft.eppantsoft.pm.PmProceso;
import com.pantsoft.eppantsoft.pm.PmTalla;
import com.pantsoft.eppantsoft.pm.PmTallas;
import com.pantsoft.eppantsoft.pm.PmTelaHabilitacion;
import com.pantsoft.eppantsoft.pm.PmTemporada;
import com.pantsoft.eppantsoft.pm.PmUsuario;
import com.pantsoft.eppantsoft.pm.PmVista;
import com.pantsoft.eppantsoft.serializable.SerAlmacen;
import com.pantsoft.eppantsoft.serializable.SerCodigoDeBarras;
import com.pantsoft.eppantsoft.serializable.SerColor;
import com.pantsoft.eppantsoft.serializable.SerProceso;
import com.pantsoft.eppantsoft.serializable.SerTalla;
import com.pantsoft.eppantsoft.serializable.SerTallas;
import com.pantsoft.eppantsoft.serializable.SerTelaHabilitacion;
import com.pantsoft.eppantsoft.serializable.SerTemporada;
import com.pantsoft.eppantsoft.serializable.SerUsuario;
import com.pantsoft.eppantsoft.serializable.SerVista;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpCatalogos", urlPatterns = { "/epCatalogos/*" })
public class EpCatalogos extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			// Temporadas ///////////////////////////////////////////////////////////
			if (ep.esMetodo("temporada_agregar") && ep.esVersion("v1")) {
				SerTemporada serTemporada = ep.getObjetFromBody(SerTemporada.class);
				new PmTemporada().agregar(serTemporada);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("temporada_actualizar") && ep.esVersion("v1")) {
				SerTemporada serTemporada = ep.getObjetFromBody(SerTemporada.class);
				new PmTemporada().actualizar(serTemporada);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("temporada_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				new PmTemporada().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("temporada_dameTemporadaSql") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporadaSql", "Long");
				SerTemporada ser = new PmTemporada().dameTemporadaSql(ep.dameParametroString("empresa"), ep.dameParametroLong("temporadaSql"));
				ep.objectEnBody(ser);
				return;
			}
			if (ep.esMetodo("temporada_dameTemporadas") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String");
				SerTemporada[] lstSer = new PmTemporada().dameTemporadas(ep.dameParametroString("empresa"));
				ep.objectEnBody(lstSer);
				return;
			}

			// Usuarios //////////////////////////////////////////////////////////
			if (ep.esMetodo("usuario_agregar") && ep.esVersion("v1")) {
				SerUsuario serUsuario = ep.getObjetFromBody(SerUsuario.class);
				new PmUsuario().agregar(serUsuario);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("usuario_actualizar") && ep.esVersion("v1")) {
				SerUsuario serUsuario = ep.getObjetFromBody(SerUsuario.class);
				new PmUsuario().actualizar(serUsuario);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("usuario_actualizarTalleres") && ep.esVersion("v1")) {
				SerUsuario serUsuario = ep.getObjetFromBody(SerUsuario.class);
				new PmUsuario().actualizarTalleres(serUsuario);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("usuario_dameUsuarios") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String");
				SerUsuario[] lstSer = new PmUsuario().dameUsuarios(ep.dameParametroString("empresa"));
				ep.objectEnBody(lstSer);
				return;
			}
			if (ep.esMetodo("usuario_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("usuario", "String");
				new PmUsuario().eliminar(ep.dameParametroString("empresa"), ep.dameParametroString("usuario"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("usuario_dameUsuario") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("usuario", "String").addPar("password", "String");
				SerUsuario serUsuario = new PmUsuario().dameUsuario(ep.dameParametroString("empresa"), ep.dameParametroString("usuario"), ep.dameParametroString("password"));
				ep.objectEnBody(serUsuario);
				return;
			}
			if (ep.esMetodo("usuario_iniciarSesion") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("usuario", "String").addPar("password", "String");
				SerUsuario serUsuario = new PmUsuario().iniciarSesion(ep.dameParametroString("empresa"), ep.dameParametroString("usuario"), ep.dameParametroString("password"));
				ep.objectEnBody(serUsuario);
				return;
			}
			if (ep.esMetodo("usuario_validarSesion") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("usuario", "String").addPar("sesion", "String");
				SerUsuario serUsuario = new PmUsuario().validarSesion(ep.dameParametroString("empresa"), ep.dameParametroString("usuario"), ep.dameParametroString("sesion"));
				ep.objectEnBody(serUsuario);
				return;
			}
			if (ep.esMetodo("usuario_dameTalleresUsuario") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("usuario", "String");
				SerUsuario serUsuario = new PmUsuario().dameTalleresUsuario(ep.dameParametroString("empresa"), ep.dameParametroString("usuario"));
				ep.objectEnBody(serUsuario);
				return;
			}

			// Vistas ///////////////////////////////////////////////////////////
			if (ep.esMetodo("vista_agregar") && ep.esVersion("v1")) {
				SerVista serVista = ep.getObjetFromBody(SerVista.class);
				new PmVista().agregar(serVista);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("vista_actualizar") && ep.esVersion("v1")) {
				SerVista serVista = ep.getObjetFromBody(SerVista.class);
				new PmVista().actualizar(serVista);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("vista_dameVistas") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String");
				SerVista[] lstSer = new PmVista().dameVistas(ep.dameParametroString("empresa"));
				ep.objectEnBody(lstSer);
				return;
			}
			if (ep.esMetodo("vista_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("vista", "String");
				new PmVista().eliminar(ep.dameParametroString("empresa"), ep.dameParametroString("vista"));
				ep.voidEnBody();
				return;
			}

			// TelaHabilitacion ///////////////////////////////////////////////////////////
			if (ep.esMetodo("telaHabilitacion_agregar") && ep.esVersion("v1")) {
				SerTelaHabilitacion serTelaHabilitacion = ep.getObjetFromBody(SerTelaHabilitacion.class);
				new PmTelaHabilitacion().agregar(serTelaHabilitacion);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("telaHabilitacion_actualizar") && ep.esVersion("v1")) {
				SerTelaHabilitacion serTelaHabilitacion = ep.getObjetFromBody(SerTelaHabilitacion.class);
				new PmTelaHabilitacion().actualizar(serTelaHabilitacion);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("telaHabilitacion_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("materia", "String");
				new PmTelaHabilitacion().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("materia"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("telaHabilitacion_dameMaterias") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerTelaHabilitacion[] lstSer = new PmTelaHabilitacion().dameMaterias(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}

			// Procesos ///////////////////////////////////////////////////////////
			if (ep.esMetodo("procesos_agregar") && ep.esVersion("v1")) {
				SerProceso serProceso = ep.getObjetFromBody(SerProceso.class);
				new PmProceso().agregar(serProceso);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("procesos_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("proceso", "String");
				new PmProceso().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("proceso"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("procesos_dameProcesos") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerProceso[] lstSer = new PmProceso().dameProcesos(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}

			// Talla ///////////////////////////////////////////////////////////
			if (ep.esMetodo("talla_agregar") && ep.esVersion("v1")) {
				SerTalla serTalla = ep.getObjetFromBody(SerTalla.class);
				new PmTalla().agregar(serTalla);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("talla_actualizar") && ep.esVersion("v1")) {
				SerTalla serTalla = ep.getObjetFromBody(SerTalla.class);
				serTalla = new PmTalla().actualizar(serTalla);
				ep.objectEnBody(serTalla);
				return;
			}
			if (ep.esMetodo("talla_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("talla", "String");
				new PmTalla().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("talla"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("talla_dameTallas") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerTalla[] lstSer = new PmTalla().dameTallas(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}

			// Tallas ///////////////////////////////////////////////////////////
			if (ep.esMetodo("tallas_agregar") && ep.esVersion("v1")) {
				SerTallas serTallas = ep.getObjetFromBody(SerTallas.class);
				new PmTallas().agregar(serTallas);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("tallas_actualizar") && ep.esVersion("v1")) {
				SerTallas serTallas = ep.getObjetFromBody(SerTallas.class);
				serTallas = new PmTallas().actualizar(serTallas);
				ep.objectEnBody(serTallas);
				return;
			}
			if (ep.esMetodo("tallas_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("talla", "String");
				new PmTallas().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("talla"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("tallas_dameTalla") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("talla", "String");
				SerTallas serTallas = new PmTallas().dameTalla(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("talla"));
				ep.objectEnBody(serTallas);
				return;
			}
			if (ep.esMetodo("tallas_dameTallas") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerTallas[] lstSer = new PmTallas().dameTallas(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}

			// Color ///////////////////////////////////////////////////////////
			if (ep.esMetodo("color_agregar") && ep.esVersion("v1")) {
				SerColor serColor = ep.getObjetFromBody(SerColor.class);
				new PmColor().agregar(serColor);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("color_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("color", "String");
				new PmColor().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("color"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("color_dameColores") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerColor[] lstSer = new PmColor().dameColores(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}

			// Almacén ///////////////////////////////////////////////////////////
			if (ep.esMetodo("almacen_agregar") && ep.esVersion("v1")) {
				SerAlmacen serAlmacen = ep.getObjetFromBody(SerAlmacen.class);
				new PmAlmacen().catAlmacen_agregar(serAlmacen);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("almacen_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("almacen", "String");
				new PmAlmacen().catAlmacen_eliminar(ep.dameParametroString("empresa"), ep.dameParametroString("almacen"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("almacen_dameColores") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String");
				SerAlmacen[] lstSer = new PmAlmacen().catAlmacen_dameAlmacenes(ep.dameParametroString("empresa"));
				ep.objectEnBody(lstSer);
				return;
			}

			// CodigoDeBarras ///////////////////////////////////////////////////////////
			if (ep.esMetodo("codigoDeBarras_agregar") && ep.esVersion("v1")) {
				SerCodigoDeBarras serCodigoDeBarras = ep.getObjetFromBody(SerCodigoDeBarras.class);
				new PmCodigoDeBarras().agregar(serCodigoDeBarras);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("codigoDeBarras_actualizar") && ep.esVersion("v1")) {
				SerCodigoDeBarras serCodigoDeBarras = ep.getObjetFromBody(SerCodigoDeBarras.class);
				serCodigoDeBarras = new PmCodigoDeBarras().actualizar(serCodigoDeBarras);
				ep.objectEnBody(serCodigoDeBarras);
				return;
			}
			if (ep.esMetodo("codigoDeBarras_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("modelo", "String").addPar("color", "String").addPar("talla", "String");
				new PmCodigoDeBarras().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("modelo"), ep.dameParametroString("color"), ep.dameParametroString("talla"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("codigoDeBarras_dameTallas") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("modelo", "String");
				SerCodigoDeBarras[] lstSer = new PmCodigoDeBarras().dameCodigosDeBarras(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("modelo"));
				ep.objectEnBody(lstSer);
				return;
			}

			ep.notFoundEnBody();
		} catch (Exception e) {
			ep.exceptionEnBody(e);
		}
	};

	@Override
	public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ClsEpUtil ep = new ClsEpUtil();
		ep.doOptions(request, response);
	}

}
