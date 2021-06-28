package com.pantsoft.eppantsoft.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class ClsParametrosUrl {
	HashMap<String, String> mapParametros;
	Logger _logger = Logger.getLogger("ClsParametrosUrl");

	public ClsParametrosUrl(String parametros) throws Exception {

		// _logger.severe("1." + parametros);

		try {
			parametros = ClsUtil.decodificarStrBase64(parametros);
		} catch (Exception e) {
			throw new Exception("Error al decodificar el valor 'par' [" + parametros + "]");
		}
		// _logger.severe("2." + parametros);
		//
		parametros = ClsUtil.regresaCaracteresEspeciales(parametros);
		mapParametros = new HashMap<String, String>();

		// _logger.severe("3." + parametros);

		String[] arrPar;

		arrPar = parametros.split("\n");

		if (arrPar.length == 1) {
			arrPar = parametros.split("\r");
		} else {
			parametros = parametros.replace("\r", "");
			arrPar = parametros.split("\n");
		}

		for (String par : arrPar) {
			// _logger.severe("PAR:" + par);
			try {
				int pos = par.indexOf("=");
				if (pos < 1)
					throw new ExcepcionControlada("Número de elementos incorrecto");
				String parametro = par.substring(0, pos);
				String valor = par.substring(pos + 1);
				if (mapParametros.containsKey(parametro))
					throw new ExcepcionControlada("El parámetro '" + parametro + "' esta duplicado");
				mapParametros.put(parametro, valor);
			} catch (Exception e) {
				throw new ExcepcionControlada("Error en el parametro '" + par + "' (" + e.getMessage() + ")");
			}
		}
	}

	public ClsParametrosUrl() {
		mapParametros = new HashMap<String, String>();
	}

	public String dameParametro(String parametro) {
		if (mapParametros.containsKey(parametro))
			return mapParametros.get(parametro);
		else
			return null;
	}

	public void limpiar() {
		mapParametros.clear();
	}

	public void agregarParametro(String parametro, String valor) {
		mapParametros.put(parametro, valor);
	}

	public String dameParametros(String prefijo) throws Exception {
		Entry<String, String> entry;
		String par = "";
		Iterator<Entry<String, String>> it = mapParametros.entrySet().iterator();
		while (it.hasNext()) {
			entry = it.next();
			if (par.length() > 0)
				par += "\n";
			par += entry.getKey() + "=" + entry.getValue();
		}
		return prefijo + ClsUtil.codificarStrBase64(ClsUtil.sustituyeCaracteresEspeciales(par)); // ClsBase64.encode(ClsUtil.sustituyeCaracteresEspeciales(par));
	}
}
