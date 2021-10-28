package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmListaPrecios;
import com.pantsoft.eppantsoft.pm.PmModelo;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerListaPrecios;
import com.pantsoft.eppantsoft.serializable.SerListaPreciosDet;
import com.pantsoft.eppantsoft.serializable.SerListaPreciosDetArr;
import com.pantsoft.eppantsoft.serializable.SerModelo;
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

			// Modelos /////////////////////////////////////////////
			if (ep.esMetodo("modelo_agregar") && ep.esVersion("v1")) {
				SerModelo serModelo = ep.getObjetFromBody(SerModelo.class);
				serModelo = new PmModelo().modelo_agregar(serModelo);
				ep.objectEnBody(serModelo);
				return;
			}
			if (ep.esMetodo("modelo_actualizar") && ep.esVersion("v1")) {
				SerModelo serModelo = ep.getObjetFromBody(SerModelo.class);
				serModelo = new PmModelo().modelo_actualizar(serModelo);
				ep.objectEnBody(serModelo);
				return;
			}
			if (ep.esMetodo("modelo_dameModelo") && ep.esVersion("v1")) {
				SerModelo serModelo = ep.getObjetFromBody(SerModelo.class);
				serModelo = new PmModelo().modelo_dameModelo(serModelo);
				ep.objectEnBody(serModelo);
				return;
			}
			if (ep.esMetodo("modelo_dameModelos") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerModelo[] lstSer = new PmModelo().modelo_dameModelos(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}
			if (ep.esMetodo("modelo_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("modelo", "String").addPar("referencia", "String");
				new PmModelo().modelo_eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("modelo"), ep.dameParametroString("referencia"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("modelo_sincronizar") && ep.esVersion("v1")) {
				SerModelo serModelo = ep.getObjetFromBody(SerModelo.class);
				Object obj = new PmModelo().modelo_sincronizar(serModelo);
				ep.objectEnBody(obj);
				return;
			}
			if (ep.esMetodo("modelo_marcarSincronizado") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("modelo", "String").addPar("referencia", "String");
				new PmModelo().modelo_marcarSincronizado(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("modelo"), ep.dameParametroString("referencia"));
				ep.voidEnBody();
				return;
			}

			// Modelo Imagen ///////////////////////////////////////////////////////////////////////////
			if (ep.esMetodo("modeloImagen_agregar") && ep.esVersion("v1")) {
				SerModeloImagen serModeloImagen = ep.getObjetFromBody(SerModeloImagen.class);
				new PmModelo().modeloImagen_agregar(serModeloImagen);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("modeloImagen_eliminar") && ep.esVersion("v1")) {
				SerModeloImagen serModeloImagen = ep.getObjetFromBody(SerModeloImagen.class);
				new PmModelo().modeloImagen_eliminar(serModeloImagen);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("modeloImagen_dameModelosImagenSinImagen") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerModeloImagen[] lstSer = new PmModelo().modeloImagen_dameModelosImagenSinImagen(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}

			// ListaPrecios /////////////////////////////////////////////
			if (ep.esMetodo("listaPrecios_agregar") && ep.esVersion("v1")) {
				SerListaPrecios serListaPreciosDet = ep.getObjetFromBody(SerListaPrecios.class);
				serListaPreciosDet = new PmListaPrecios().listaPrecios_agregar(serListaPreciosDet);
				ep.objectEnBody(serListaPreciosDet);
				return;
			}
			if (ep.esMetodo("listaPrecios_actualizar") && ep.esVersion("v1")) {
				SerListaPrecios serListaPreciosDet = ep.getObjetFromBody(SerListaPrecios.class);
				serListaPreciosDet = new PmListaPrecios().listaPrecios_actualizar(serListaPreciosDet);
				ep.objectEnBody(serListaPreciosDet);
				return;
			}
			if (ep.esMetodo("listaPrecios_dameListaPrecios") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("idListaPrecios", "Long").addPar("usuario", "String");
				SerListaPrecios serListaPreciosDet = new PmListaPrecios().listaPrecios_dameListaPrecios(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("idListaPrecios"), ep.dameParametroString("usuario"));
				ep.objectEnBody(serListaPreciosDet);
				return;
			}
			if (ep.esMetodo("listaPrecios_dameListasPrecios") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("usuario", "String");
				SerListaPrecios[] lstSer = new PmListaPrecios().listaPrecios_dameListasPrecios(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("usuario"));
				ep.objectEnBody(lstSer);
				return;
			}
			if (ep.esMetodo("listaPrecios_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("idListaPrecios", "Long");
				new PmListaPrecios().listaPrecios_eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("idListaPrecios"));
				ep.voidEnBody();
				return;
			}

			// ListaPreciosDet /////////////////////////////////////////////
			if (ep.esMetodo("listaPreciosDet_agregar") && ep.esVersion("v1")) {
				SerListaPreciosDet serListaPreciosDet = ep.getObjetFromBody(SerListaPreciosDet.class);
				serListaPreciosDet = new PmListaPrecios().listaPreciosDet_agregar(serListaPreciosDet);
				ep.objectEnBody(serListaPreciosDet);
				return;
			}
			if (ep.esMetodo("listaPreciosDet_agregarArr") && ep.esVersion("v1")) {
				SerListaPreciosDetArr serListaPreciosDetArr = ep.getObjetFromBody(SerListaPreciosDetArr.class);
				new PmListaPrecios().listaPreciosDet_agregarArr(serListaPreciosDetArr.getArr());
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("listaPreciosDet_actualizar") && ep.esVersion("v1")) {
				SerListaPreciosDet serListaPreciosDet = ep.getObjetFromBody(SerListaPreciosDet.class);
				serListaPreciosDet = new PmListaPrecios().listaPreciosDet_actualizar(serListaPreciosDet);
				ep.objectEnBody(serListaPreciosDet);
				return;
			}
			if (ep.esMetodo("listaPreciosDet_actualizarArr") && ep.esVersion("v1")) {
				SerListaPreciosDetArr serListaPreciosDetArr = ep.getObjetFromBody(SerListaPreciosDetArr.class);
				new PmListaPrecios().listaPreciosDet_actualizarArr(serListaPreciosDetArr.getArr());
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("listaPreciosDet_dameDetalles") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("idListaPrecios", "Long");
				SerListaPreciosDet[] detalles = new PmListaPrecios().listaPreciosDet_dameDetalles(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("idListaPrecios"));
				ep.objectEnBody(detalles);
				return;
			}
			if (ep.esMetodo("listaPreciosDet_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("idListaPrecios", "String").addPar("modelo", "String").addPar("referencia", "String");
				new PmListaPrecios().listaPreciosDet_eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroString("idListaPrecios"), ep.dameParametroString("modelo"), ep.dameParametroString("referencia"));
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
