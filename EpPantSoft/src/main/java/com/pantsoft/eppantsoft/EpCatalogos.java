package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmTemporada;
import com.pantsoft.eppantsoft.serializable.SerTemporada;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpCatalogos", urlPatterns = { "/epCatalogos/*" })
public class EpCatalogos extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			// Temporadas
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
			// if (ep.esMetodo("produccion_actualizarEstatus") && ep.esVersion("v1")) {
			// ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("estatus", "Int");
			// List<Long> lstOrdenes = ep.getObjetFromBody(List.class);
			// new PmProduccion().actualizarEstatus(ep.dameParametroString("empresa"), ep.dameParametroLong("temporada"), ep.dameParametroInt("estatus"), lstOrdenes);
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
