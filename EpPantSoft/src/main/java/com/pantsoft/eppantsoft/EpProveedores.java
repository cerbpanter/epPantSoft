package com.pantsoft.eppantsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmProveedores;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerProveedorPago;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpProduccion", urlPatterns = { "/epProduccion/*" })
public class EpProveedores extends HttpServlet {

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

			// ProveedorPago
			if (ep.esMetodo("proovedorPago_agregar") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().agregarProveedorPago(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proovedorPago_marcarRevisado") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().marcarRevisado(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proovedorPago_marcarAutorizado") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().marcarAutorizado(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proovedorPago_marcarPagado") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().marcarPagado(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proovedorPago_marcarTerminado") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().marcarTerminado(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proovedorPago_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("uuid", "String");
				new PmProveedores().eliminarProveedorPago(ep.dameParametroString("empresa"), ep.dameParametroString("uuid"));
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
