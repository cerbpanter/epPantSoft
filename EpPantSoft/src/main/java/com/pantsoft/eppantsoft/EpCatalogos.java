package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmProceso;
import com.pantsoft.eppantsoft.pm.PmTelaHabilitacion;
import com.pantsoft.eppantsoft.pm.PmTemporada;
import com.pantsoft.eppantsoft.pm.PmUsuario;
import com.pantsoft.eppantsoft.pm.PmVista;
import com.pantsoft.eppantsoft.serializable.SerProceso;
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
