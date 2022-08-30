package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmAlmacen;
import com.pantsoft.eppantsoft.pm.PmModelo;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerAlmEntrada;
import com.pantsoft.eppantsoft.serializable.SerAlmSalida;
import com.pantsoft.eppantsoft.serializable.SerAlmacen;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpAlmacen", urlPatterns = { "/epAlmacen/*" })
public class EpAlmacen extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			// Modelo Imagen ///////////////////////////////////////////////////////////////////////////
			if (ep.esMetodo("modeloImagen") && ep.esVersion("v1")) {
				// Se agregar los parámetros en el orden en que deben venir en la url
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("modelo", "String").addPar("referencia", "String").addPar("renglon", "Long").addPar("mini", "Int");

				// ClsParametrosUrl parametros = new ClsParametrosUrl(ep.dameParametroString("parametrosUrl"));
				// int tipo = Integer.parseInt(parametros.dameParametro("tipo"));

				new PmModelo().modeloImagen_dameImagen(request, response, ep);
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

			// AlmEntrada /////////////////////////////////////////////
			if (ep.esMetodo("almEntrada_agregar") && ep.esVersion("v1")) {
				SerAlmEntrada serAlmEntrada = ep.getObjetFromBody(SerAlmEntrada.class);
				serAlmEntrada = new PmAlmacen().almEntrada_agregar(serAlmEntrada, null, null);
				ep.objectEnBody(serAlmEntrada);
				return;
			}
			if (ep.esMetodo("almEntrada_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("folioAlmEntrada", "Long");
				new PmAlmacen().almEntrada_eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("folioAlmEntrada"));
				ep.voidEnBody();
				return;
			}

			// AlmSalida /////////////////////////////////////////////
			if (ep.esMetodo("almSalida_agregar") && ep.esVersion("v1")) {
				SerAlmSalida serAlmSalida = ep.getObjetFromBody(SerAlmSalida.class);
				serAlmSalida = new PmAlmacen().almSalida_agregar(serAlmSalida);
				ep.objectEnBody(serAlmSalida);
				return;
			}
			if (ep.esMetodo("almSalida_actualizarConError") && ep.esVersion("v1")) {
				SerAlmSalida serAlmSalida = ep.getObjetFromBody(SerAlmSalida.class);
				serAlmSalida = new PmAlmacen().almSalida_actualizarConError(serAlmSalida);
				ep.objectEnBody(serAlmSalida);
				return;
			}
			if (ep.esMetodo("almSalida_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("folioAlmSalida", "Long");
				new PmAlmacen().almSalida_eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("folioAlmSalida"));
				ep.voidEnBody();
				return;
			}

			// Catálogo de Almacén ///////////////////////////////////////////////////////////////////////////
			if (ep.esMetodo("catAlmacen_agregar") && ep.esVersion("v1")) {
				SerAlmacen serAlmacen = ep.getObjetFromBody(SerAlmacen.class);
				new PmAlmacen().catAlmacen_agregar(serAlmacen);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("catAlmacen_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("almacen", "String");
				new PmAlmacen().catAlmacen_eliminar(ep.dameParametroString("empresa"), ep.dameParametroString("almacen"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("catAlmacen_dameAlmacenes") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String");
				SerAlmacen[] lstSer = new PmAlmacen().catAlmacen_dameAlmacenes(ep.dameParametroString("empresa"));
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
