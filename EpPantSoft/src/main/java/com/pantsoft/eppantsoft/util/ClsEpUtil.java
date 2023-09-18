package com.pantsoft.eppantsoft.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClsEpUtil {
	// Servlet
	private Gson gson;
	private HttpServletRequest req;
	private HttpServletResponse res;

	// Parámetros
	private String parametros[];
	Map<String, Integer> mapPos = new HashMap<String, Integer>();
	Map<String, String> mapTipo = new HashMap<String, String>();
	int tam = 3;

	public ClsEpUtil() {
		// Servlet
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		gson = builder.create();
	}

	public void setParametros(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Servlet
		req = request;
		res = response;

		// Parámetros
		// "/epCatalogos/v1/lineaDeProducto_Actualizar/F0aZoMISccvAVMyWgIsS%2BpOzRTLDHlRQs4gm1kSCDOwV9kK27Q%2FhsuIBYNXVSS%2BS44TAo1nHRYZydov0mehyMQ%3D%3D"
		String requestURI = request.getRequestURI();
		if (requestURI.startsWith("/"))
			requestURI = requestURI.substring(1);
		parametros = requestURI.split("/");

		// String requestURI = req.getRequestURI();
		// String requestURL = req.getRequestURL().toString();
		// String contextPath = req.getContextPath();
		// String servletPath = req.getServletPath();

		// "/v1/lineaDeProducto_Actualizar/F0aZoMISccvAVMyWgIsS%2BpOzRTLDHlRQs4gm1kSCDOwV9kK27Q%2FhsuIBYNXVSS%2BS44TAo1nHRYZydov0mehyMQ%3D%3D"
		// String pathInfo = request.getPathInfo();
		// if (ClsUtil.esNulo(pathInfo))
		// throw new Exception("La url no está completa - No hay parámetros");
		// if (pathInfo.startsWith("/"))
		// pathInfo = pathInfo.substring(1);
		// parametros = pathInfo.split("/");
		if (parametros.length < 3)
			throw new Exception("La url no está completa - faltan parámetros");
	}

	// Servlet
	private String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public byte[] getBytesFromInputStream(InputStream is) throws IOException {
		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	private String getJsonFromBody() throws Exception {
		InputStream in = req.getInputStream();
		byte[] datos = getBytesFromInputStream(in);
		if (datos == null || datos.length == 0)
			throw new Exception("Falta el objecto en body");
		String jsonStr = new String(datos, "UTF-8");
		return jsonStr;
	}

	public <T> T getObjetFromBody(Class<T> classOfT) throws Exception {
		return gson.fromJson(getJsonFromBody(), classOfT);
	}

	public void objectEnBody(Object obj) throws Exception {
		String json = gson.toJson(obj);

		OutputStream out = res.getOutputStream();
		byte[] arr = json.getBytes("UTF-8");
		res.setContentLength(arr.length);
		res.setHeader("content-type", "application/json;charset=utf-8");
		// res.setHeader("content-disposition", "attachment; filename=data.json");

		agregarEncabezadosCORS();

		out.write(arr);
		out.close();
	}

	public void imagenEnBody(byte[] imagen) throws Exception {

		OutputStream out = res.getOutputStream();

		res.setContentLength(imagen.length);
		res.setHeader("content-type", "image/png");

		agregarEncabezadosCORS();

		out.write(imagen);
		out.close();
	}

	public void stringEnBody(String cadena) throws Exception {
		OutputStream out = res.getOutputStream();
		byte[] arr = cadena.getBytes("UTF-8");
		res.setContentLength(arr.length);
		res.setHeader("content-type", "application/json;charset=utf-8");

		agregarEncabezadosCORS();

		out.write(arr);
		out.close();

	}

	public void voidEnBody() throws Exception {
		res.setStatus(HttpServletResponse.SC_NO_CONTENT);

		agregarEncabezadosCORS();
	}

	public void notFoundEnBody() throws Exception {
		res.setStatus(HttpServletResponse.SC_NOT_FOUND);

		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print("El método no existe en EP");

		agregarEncabezadosCORS();
	}

	public void exceptionEnBody(Exception e) {
		try {
			res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

			String json = "{" +
					" \"error\": {" +
					" \"errors\": [" +
					" {" +
					" \"domain\": \"global\"," +
					" \"reason\": \"backendError\"," +
					" \"message\": \"" + e.getMessage() + "\"" +
					" }" +
					" ]," +
					" \"code\": 503," +
					" \"message\": \"" + e.getMessage() + "\"" +
					" }" +
					"}";

			// Al agregar el stackTrace el Json se descompone y no di por qué
			// " \"stackTrace\": \"" + getStackTrace(e).replace("\n", "").replace("\r", "") + "\"" +
			OutputStream out = res.getOutputStream();
			byte[] arr = json.getBytes("UTF-8");
			res.setContentLength(arr.length);
			res.setHeader("content-type", "application/json;charset=utf-8");

			agregarEncabezadosCORS();

			out.write(arr);
			out.close();
		} catch (Exception e1) {
			Logger rootLogger = Logger.getLogger("EpCatalogos");
			rootLogger.severe(getStackTrace(e1));
		}
	}

	private void agregarEncabezadosCORS() {
		agregarEncabezadosCORS(null);
	}

	private void agregarEncabezadosCORS(String accessControlAllowHeaders) {
		if (accessControlAllowHeaders == null)
			accessControlAllowHeaders = "Content-Type";
		// The following are CORS headers. Max age informs the
		// browser to keep the results of this call for 1 day.
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "GET, POST");
		res.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);
		res.setHeader("Access-Control-Max-Age", "86400");
		// Tell the browser what requests we allow.
		res.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");

	}

	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		// Servlet
		req = request;
		res = response;

		// Busco el encabezado access-control-request-headers para copiar su valor en access-control-allow-headers en la respuesta
		// Esto porque es una validación del CORS, al parecer solo es necesario en OPTION, en POST ya no
		String accessControlAllowHeaders = null;
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				if (headerName.equalsIgnoreCase("access-control-request-headers")) {
					accessControlAllowHeaders = request.getHeader(headerName);
				}
			}
		}
		agregarEncabezadosCORS(accessControlAllowHeaders);
	}

	// Parámetros
	public boolean esVersion(String version) {
		return !ClsUtil.esNulo(parametros[1]) && parametros[1].equals(version);
	}

	public boolean esMetodo(String metodo) {
		return !ClsUtil.esNulo(parametros[2]) && parametros[2].equals(metodo);
	}

	public String getMetodo() {
		return parametros[2];
	}

	public ClsEpUtil addPar(String nombre, String tipo) throws Exception {
		if (parametros.length < (tam + 1))
			throw new Exception("La url no está completa - faltan parámetros");
		mapPos.put(nombre, tam);
		tam++;
		if (!tipo.equals("String") && !tipo.equals("Long") && !tipo.equals("Int"))
			throw new Exception("El tipo de parámetro no se soporta: " + tipo);
		mapTipo.put(nombre, tipo);
		return this;
	}

	public String dameParametroString(String nombre) throws Exception {
		if (!mapTipo.containsKey(nombre))
			throw new Exception("El parámetro no se ha declarado: " + nombre);
		if (!mapTipo.get(nombre).equals("String"))
			throw new Exception("El parámetro no es de tipo String: " + nombre);
		return URLDecoder.decode(parametros[mapPos.get(nombre)], "UTF-8");
	}

	public long dameParametroLong(String nombre) throws Exception {
		if (!mapTipo.containsKey(nombre))
			throw new Exception("El parámetro no se ha declarado: " + nombre);
		if (!mapTipo.get(nombre).equals("Long"))
			throw new Exception("El parámetro no es de tipo Long: " + nombre);
		try {
			long val = Long.parseLong(URLDecoder.decode(parametros[mapPos.get(nombre)], "UTF-8"));
			return val;
		} catch (NumberFormatException e) {
			throw new Exception("El valor del parámetro no es de tipo Long: " + nombre + " : " + parametros[mapPos.get(nombre)]);
		}
	}

	public int dameParametroInt(String nombre) throws Exception {
		if (!mapTipo.containsKey(nombre))
			throw new Exception("El parámetro no se ha declarado: " + nombre);
		if (!mapTipo.get(nombre).equals("Int"))
			throw new Exception("El parámetro no es de tipo Int: " + nombre);
		try {
			int val = Integer.parseInt(URLDecoder.decode(parametros[mapPos.get(nombre)], "UTF-8"));
			return val;
		} catch (NumberFormatException e) {
			throw new Exception("El valor del parámetro no es de tipo Int: " + nombre + " : " + parametros[mapPos.get(nombre)]);
		}
	}

}
