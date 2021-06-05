package com.pantsoft.eppantsoft;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmProduccion;
import com.pantsoft.eppantsoft.serializable.Respuesta;
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
				ep.objectEnBody(respuesta);
				return;
			}
			if (ep.esMetodo("produccion_agregar") && ep.esVersion("v1")) {
				SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
				new PmProduccion().agregar(serProduccion);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("produccion_actualizar") && ep.esVersion("v1")) {
				SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
				new PmProduccion().actualizar(serProduccion);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("produccion_actualizarEstatus") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("estatus", "Int");
				List<Long> lstOrdenes = ep.getObjetFromBody(List.class);
				new PmProduccion().actualizarEstatus(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroInt("estatus"), lstOrdenes);
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
