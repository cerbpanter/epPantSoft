package com.pantsoft.eppantsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmPedido;
import com.pantsoft.eppantsoft.pm.PmProduccion;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerPedido;
import com.pantsoft.eppantsoft.serializable.SerProduccion;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpProduccion", urlPatterns = { "/epProduccion/*" })
public class EpProduccion extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			if (ep.esMetodo("prueba") && ep.esVersion("v1")) {
				// Se agregar los parámetros en el orden en que deben venir en la url
				ep.addPar("mensaje", "String").addPar("tam", "Long");
				Respuesta respuesta = ep.getObjetFromBody(Respuesta.class);
				respuesta.setCadena("El mensaje es: " + ep.dameParametroString("mensaje") + ", tam: " + ep.dameParametroLong("tam"));
				List<Long> lst = new ArrayList<Long>();
				lst.add(25L);
				lst.add(87L);
				lst.add(38L);
				ep.objectEnBody(lst);
				return;
			}

			// PRODUCCION
			if (ep.esMetodo("produccion_agregar") && ep.esVersion("v1")) {
				SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
				serProduccion = new PmProduccion().agregar(serProduccion);
				ep.objectEnBody(serProduccion);
				return;
			}
			if (ep.esMetodo("produccion_actualizar") && ep.esVersion("v1")) {
				SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
				serProduccion = new PmProduccion().actualizar(serProduccion);
				ep.objectEnBody(serProduccion);
				return;
			}
			if (ep.esMetodo("produccion_actualizarEstatus") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("estatus", "Int");
				Respuesta resp = ep.getObjetFromBody(Respuesta.class);
				new PmProduccion().actualizarEstatus(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroInt("estatus"), resp.lstLong);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("produccion_actualizarEntrega") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("numOrden", "Long").addPar("cantidadEntrega", "Long");
				new PmProduccion().actualizarEntrega(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("numOrden"), ep.dameParametroLong("cantidadEntrega"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("produccion_cambiarTemporada") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("nuevaTemporada", "Long");
				Respuesta resp = ep.getObjetFromBody(Respuesta.class);
				new PmProduccion().cambiarTemporada(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("nuevaTemporada"), resp.lstLong);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("produccion_dameProducciones") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				SerProduccion[] lstSer = new PmProduccion().dameProducciones(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"));
				ep.objectEnBody(lstSer);
				return;
			}
			if (ep.esMetodo("produccion_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("numOrden", "Long");
				new PmProduccion().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroLong("numOrden"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("produccion_actualizarCostura") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long");
				Respuesta resp = ep.getObjetFromBody(Respuesta.class);
				new PmProduccion().actualizarCostura(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), resp.getCadena());
				ep.voidEnBody();
				return;
			}

			// PEDIDOS
			if (ep.esMetodo("pedido_agregar") && ep.esVersion("v1")) {
				SerPedido serPedido = ep.getObjetFromBody(SerPedido.class);
				serPedido = new PmPedido().agregar(serPedido);
				ep.objectEnBody(serPedido);
				return;
			}
			if (ep.esMetodo("pedido_actualizar") && ep.esVersion("v1")) {
				SerPedido serPedido = ep.getObjetFromBody(SerPedido.class);
				serPedido = new PmPedido().actualizar(serPedido);
				ep.objectEnBody(serPedido);
				return;
			}
			if (ep.esMetodo("pedido_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("folioPedido", "Long");
				new PmPedido().eliminar(ep.dameParametroString("empresa"), ep.dameParametroLong("folioPedido"));
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
