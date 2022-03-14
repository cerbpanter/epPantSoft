package com.pantsoft.eppantsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pantsoft.eppantsoft.pm.PmEntidad;
import com.pantsoft.eppantsoft.pm.PmProduccion;
import com.pantsoft.eppantsoft.serializable.Respuesta;
import com.pantsoft.eppantsoft.serializable.SerProduccion;
import com.pantsoft.eppantsoft.util.ClsEpUtil;

@SuppressWarnings("serial")
@WebServlet(name = "EpSql", urlPatterns = { "/epSql/*" })
public class EpSql extends HttpServlet {

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
				ep.objectEnBody(lst);
				return;
			}
			if (ep.esMetodo("ejecutarConsultaSql") && ep.esVersion("v1")) {
				ep.addPar("empresa", "String").addPar("temporada", "Long").addPar("cns", "String");
				String blob;
				String empresa = ep.dameParametroString("empresa");
				Long temporada = ep.dameParametroLong("temporada");
				String cns = ep.dameParametroString("cns");

				boolean noAdminSiNube = cns.endsWith("NOADMINSINUBE");
				// TODO Calcular usuarioAdmin
				boolean usuarioAdmin = !noAdminSiNube;
				// boolean usuarioAdmin = ClsUtil.validaUsuarioEmpresaAdmin(empresa, sucursal, usuario, password, noAdminSiNube);

				// if (cns.contains(" UNIRSINUBE ")) {
				// String[] arrCns = cns.split(" UNIRSINUBE ");
				// String blobTemp;
				// blob = null;
				// PmEntidad pmEntidad = new PmEntidad();
				// for (String cns2 : arrCns) {
				// try {
				// blobTemp = pmEntidad.ejecutarConsultaSql(empresa, sucursal, cns2, usuarioAdmin, usuario);
				// blob = ClsBlobReader.acumularBlobStr(blob, blobTemp);
				// } catch (Exception e) {
				// throw new Exception("CNS2: " + cns2 + ", ERR: " + e.getMessage());
				// }
				// }
				// } else {
				blob = new PmEntidad().ejecutarConsultaSql(empresa, temporada, cns, usuarioAdmin);
				// }

				Respuesta resp = new Respuesta(blob);
				ep.objectEnBody(resp);

				// *******************************************
				SerProduccion serProduccion = ep.getObjetFromBody(SerProduccion.class);
				serProduccion = new PmProduccion().agregar(serProduccion);
				ep.objectEnBody(serProduccion);
				return;
			}
			ep.notFoundEnBody();
		} catch (

		Exception e) {
			ep.exceptionEnBody(e);
		}
	};

	@Override
	public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ClsEpUtil ep = new ClsEpUtil();
		ep.doOptions(request, response);
	}

}
