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
@WebServlet(name = "EpProveedores", urlPatterns = { "/epProveedores/*" })
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

			// ProveedorPagoMes
			// if (ep.esMetodo("proveedorPagoMes_agregar") && ep.esVersion("v1")) {
			// SerProveedorPagoMes serProveedorPagoMes = ep.getObjetFromBody(SerProveedorPagoMes.class);
			// serProveedorPagoMes = new PmProveedores().agregarProveedorPagoMes(serProveedorPagoMes);
			// ep.objectEnBody(serProveedorPagoMes);
			// return;
			// }
			if (ep.esMetodo("proveedorPagoMes_autorizarSemana") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("mesVencimiento", "Long").addPar("semana", "Long");
				new PmProveedores().autorizarSemana(ep.dameParametroString("empresa"), ep.dameParametroLong("mesVencimiento"), ep.dameParametroLong("semana"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("proveedorPagoMes_terminarMes") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("mesVencimiento", "Long");
				new PmProveedores().terminarMes(ep.dameParametroString("empresa"), ep.dameParametroLong("mesVencimiento"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("proveedorPagoMes_actualizarTitulo") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("mesVencimiento", "Long").addPar("semana", "Long").addPar("titulo", "String");
				new PmProveedores().actualizarTitulo(ep.dameParametroString("empresa"), ep.dameParametroLong("mesVencimiento"), ep.dameParametroLong("semana"), ep.dameParametroString("titulo"));
				ep.voidEnBody();
				return;
			}

			// ProveedorPago
			if (ep.esMetodo("proveedorPago_agregar") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().agregarProveedorPago(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proveedorPago_marcarRevisado") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().marcarRevisado(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proveedorPago_actualizarFechaVencimiento") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().actualizarFechaVencimiento(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			// if (ep.esMetodo("proveedorPago_marcarAutorizado") && ep.esVersion("v1")) {
			// ep.addPar("empresa", "String").addPar("mesVencimiento", "Long").addPar("semana", "Long");
			// new PmProveedores().marcarAutorizado(ep.dameParametroString("empresa"), ep.dameParametroLong("mesVencimiento"), ep.dameParametroLong("semana"));
			// ep.voidEnBody();
			// return;
			// }
			if (ep.esMetodo("proveedorPago_actualizarPagado") && ep.esVersion("v1")) {
				SerProveedorPago serProveedorPago = ep.getObjetFromBody(SerProveedorPago.class);
				serProveedorPago = new PmProveedores().actualizarPagado(serProveedorPago);
				ep.objectEnBody(serProveedorPago);
				return;
			}
			if (ep.esMetodo("proveedorPago_marcarTerminado") && ep.esVersion("v1")) {
				Respuesta resp = ep.getObjetFromBody(Respuesta.class);

				new PmProveedores().marcarTerminado(resp.cadena);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("proveedorPago_asignarSemana") && ep.esVersion("v1")) {
				Respuesta resp = ep.getObjetFromBody(Respuesta.class);

				new PmProveedores().asignarSemana(resp.cadena, resp.largo);
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("proveedorPago_eliminar") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("mes", "Long").addPar("uuid", "String");
				new PmProveedores().eliminarProveedorPago(ep.dameParametroString("empresa"), ep.dameParametroLong("mes"), ep.dameParametroString("uuid"));
				ep.voidEnBody();
				return;
			}
			if (ep.esMetodo("proveedorPago_corregir") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("mes", "Long").addPar("cursor", "String");
				Respuesta resp = new PmProveedores().corregirProveedorPago(ep.dameParametroString("empresa"), ep.dameParametroLong("mes"), ep.dameParametroString("cursor"));
				ep.objectEnBody(resp);
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
