package com.pantsoft.eppantsoft;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmAdministracion;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpAdministracion", urlPatterns = { "/epAdministracion/*" })
public class EpAdministracion extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		ClsEpUtil ep = new ClsEpUtil();
		try {
			ep.setParametros(request, response);

			// Entidades ///////////////////////////////////////////////////////////
			if (ep.esMetodo("eliminarEntidades") && ep.esVersion("v1")) {
				ep.addPar("entidad", "String").addPar("filtros", "String").addPar("tamPag", "Int").addPar("cursor", "String");
				Respuesta resp = new PmAdministracion().eliminarEntidades(ep.dameParametroString("entidad"), ep.dameParametroString("filtros"), ep.dameParametroInt("tamPag"), ep.dameParametroString("cursor"));
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
