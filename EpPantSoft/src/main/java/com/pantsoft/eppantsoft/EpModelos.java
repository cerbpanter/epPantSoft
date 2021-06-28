package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmModelo;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerModeloImagen;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpModelos", urlPatterns = { "/epModelos/*" })
public class EpModelos extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			if (ep.esMetodo("modeloImagen") && ep.esVersion("v1")) {
				// Se agregar los parámetros en el orden en que deben venir en la url
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("modelo", "String").addPar("referencia", "String").addPar("renglon", "Long").addPar("mini", "Int");

				// ClsParametrosUrl parametros = new ClsParametrosUrl(ep.dameParametroString("parametrosUrl"));
				// int tipo = Integer.parseInt(parametros.dameParametro("tipo"));

				new PmModelo().modeloImagen_dameImagen(request, response, ep);
				return;
			}

			// Modelo Imagen ///////////////////////////////////////////////////////////////////////////
			if (ep.esMetodo("modeloImagen_agregar") && ep.esVersion("v1")) {
				SerModeloImagen serModeloImagen = ep.getObjetFromBody(SerModeloImagen.class);
				new PmModelo().modeloImagen_agregar(serModeloImagen);
				ep.voidEnBody();
				return;
			}

			ep.notFoundEnBody();
		} catch (Exception e) {
			ep.exceptionEnBody(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			if (ep.esMetodo("prueba") && ep.esVersion("v1")) {
				// Se agregar los parámetros en el orden en que deben venir en la url
				ep.addPar("mensaje", "String");
				Respuesta respuesta = new Respuesta();
				respuesta.setCadena("El mensaje es: " + ep.dameParametroString("mensaje"));
				ep.objectEnBody(respuesta);
				return;
			}

			// Modelos /////////////////////////////////////////////
			// if (ep.esMetodo("produccion_agregar") && ep.esVersion("v1")) {
			// SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
			// new PmProduccion().agregar(serProduccion);
			// ep.voidEnBody();
			// return;
			// }
			// if (ep.esMetodo("produccion_actualizar") && ep.esVersion("v1")) {
			// SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
			// serProduccion = new PmProduccion().actualizar(serProduccion);
			// ep.objectEnBody(serProduccion);
			// return;
			// }
			// if (ep.esMetodo("produccion_actualizarEstatus") && ep.esVersion("v1")) {
			// ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("estatus", "Int");
			// Respuesta resp = ep.getObjetFromBody(Respuesta.class);
			// new PmProduccion().actualizarEstatus(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroInt("estatus"), resp.lstLong);
			// ep.voidEnBody();
			// return;
			// }
			// if (ep.esMetodo("produccion_cambiarTemporada") && ep.esVersion("v1")) {
			// ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("nuevaTemporada", "Long");
			// Respuesta resp = ep.getObjetFromBody(Respuesta.class);
			// new PmProduccion().cambiarTemporada(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("nuevaTemporada"), resp.lstLong);
			// ep.voidEnBody();
			// return;
			// }
			// if (ep.esMetodo("produccion_dameProducciones") && ep.esVersion("v1")) {
			// ep.addPar("empresa", "String").addPar("temporada", "Long");
			// SerProduccion[] lstSer = new PmProduccion().dameProducciones(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
			// ep.objectEnBody(lstSer);
			// return;
			// }
			// if (ep.esMetodo("produccion_eliminar") && ep.esVersion("v1")) {
			// ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("numOrden", "Long");
			// new PmProduccion().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("numOrden"));
			// ep.voidEnBody();
			// return;
			// }

			// Modelo Imagen ///////////////////////////////////////////////////////////////////////////
			if (ep.esMetodo("modeloImagen_agregar") && ep.esVersion("v1")) {
				SerModeloImagen serModeloImagen = ep.getObjetFromBody(SerModeloImagen.class);
				new PmModelo().modeloImagen_agregar(serModeloImagen);
				ep.voidEnBody();
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
