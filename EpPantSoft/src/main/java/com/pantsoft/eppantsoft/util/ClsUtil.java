package com.pantsoft.eppantsoft.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.internet.MimeUtility;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.pantsoft.eppantsoft.entidades.DbConsecutivo;

public class ClsUtil {

	public static boolean esNulo(Object objeto) {
		if (objeto == null || CadenaX(objeto).trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String CadenaX(Object val) {
		try {
			if (val == null)
				return "";
			return String.valueOf(val);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Redondea un número a los decimales especificados.
	 * 
	 * @param valor
	 *          El número que se quiere redondear
	 * @param decimales
	 *          El número de decimales requerido
	 */
	public static double Redondear(double valor, int decimales) {
		// double p = Math.pow(10, decimales);
		// valor = valor * p;
		// valor = Math.round(valor);
		// return valor / p;

		double p = (double) Math.pow(10, decimales);
		valor = (valor * p) + 0.000000001;
		valor = Math.round(valor);
		return (double) valor / p;

	}

	public static boolean esIgualConNulo(String v1, String v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Boolean v1, Boolean v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Long v1, Long v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Double v1, Double v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static boolean esIgualConNulo(Integer v1, Integer v2) {
		if (v1 == null)
			return v2 == null;
		else
			return v1.equals(v2);
	}

	public static String sustituyeCaracteresEspeciales(String html) {
		String val = html;
		// return "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> " + val;

		val = val.replace("á", "&aacute;");
		val = val.replace("é", "&eacute;");
		val = val.replace("í", "&iacute;");
		val = val.replace("ó", "&oacute;");
		val = val.replace("ú", "&uacute;");

		val = val.replace("Á", "&Aacute;");
		val = val.replace("É", "&Eacute;");
		val = val.replace("Í", "&Iacute;");
		val = val.replace("Ó", "&Oacute;");
		val = val.replace("Ú", "&Uacute;");

		val = val.replace("ñ", "&ntilde;");
		val = val.replace("Ñ", "&Ntilde;");
		val = val.replace("|", "&#124;");

		val = val.replace("“", "&#8220;");
		val = val.replace("”", "&#8221;");
		val = val.replace("„", "&#8222;");
		val = val.replace("°", "&#176;");
		val = val.replace("—", "&#8212;");
		val = val.replace("–", "&#8211;");

		// Del listado completo
		val = val.replace("€", "&#128;");
		val = val.replace("‚", "&#130;");
		val = val.replace("Œ", "&#140;");
		val = val.replace("Ž", "&#142;");
		val = val.replace("‘", "&#145;");
		val = val.replace("’", "&#146;");
		val = val.replace("•", "&#149;");
		val = val.replace("˜", "&#152;");
		val = val.replace("™", "&#153;");
		val = val.replace("š", "&#154;");
		val = val.replace("›", "&#155;");
		val = val.replace("œ", "&#156;");
		val = val.replace("Ÿ", "&#159;");
		val = val.replace("¡", "&iexcl;");
		val = val.replace("¢", "&cent;");
		val = val.replace("£", "&pound;");
		val = val.replace("¤", "&curren;");
		val = val.replace("¥", "&yen;");
		val = val.replace("¦", "&brvbar;");
		val = val.replace("§", "&sect;");
		val = val.replace("¨", "&uml;");
		val = val.replace("©", "&copy;");
		val = val.replace("ª", "&ordf;");
		val = val.replace("«", "&laquo;");
		val = val.replace("¬", "&not;");
		val = val.replace("®", "&reg;");
		val = val.replace("¯", "&macr;");
		val = val.replace("±", "&plusmn;");
		val = val.replace("²", "&sup2;");
		val = val.replace("³", "&sup3;");
		val = val.replace("´", "&acute;");
		val = val.replace("μ", "&micro;");
		val = val.replace("¶", "&para;");
		val = val.replace("·", "&middot;");
		val = val.replace("¸", "&cedil;");
		val = val.replace("¹", "&sup1;");
		val = val.replace("º", "&ordm;");
		val = val.replace("»", "&raquo;");
		val = val.replace("¼", "&frac14;");
		val = val.replace("½", "&frac12;");
		val = val.replace("¾", "&frac34;");
		val = val.replace("¿", "&iquest;");
		val = val.replace("À", "&Agrave;");
		val = val.replace("Â", "&Acirc;");
		val = val.replace("Ã", "&Atilde;");
		val = val.replace("Ä", "&Auml;");
		val = val.replace("Å", "&Aring;");
		val = val.replace("Æ", "&AElig;");
		val = val.replace("Ç", "&Ccedil;");
		val = val.replace("È", "&Egrave;");
		val = val.replace("Ê", "&Ecirc;");
		val = val.replace("Ë", "&Euml;");
		val = val.replace("Ì", "&Igrave;");
		val = val.replace("Î", "&Icirc;");
		val = val.replace("Ï", "&Iuml;");
		val = val.replace("Ð", "&ETH;");
		val = val.replace("Ò", "&Ograve");
		val = val.replace("Ô", "&Ocirc;");
		val = val.replace("Õ", "&Otilde;");
		val = val.replace("Ö", "&Ouml;");
		val = val.replace("×", "&times;");
		val = val.replace("Ø", "&Oslash;");
		val = val.replace("Ù", "&Ugrave;");
		val = val.replace("Û", "&Ucirc;");
		val = val.replace("Ü", "&Uuml;");
		val = val.replace("Ý", "&Yacute;");
		val = val.replace("Þ", "&THORN;");
		val = val.replace("ß", "&szlig;");
		val = val.replace("à", "&agrave;");
		val = val.replace("â", "&acirc;");
		val = val.replace("ã", "&atilde;");
		val = val.replace("ä", "&auml;");
		val = val.replace("å", "&aring;");
		val = val.replace("æ", "&aelig;");
		val = val.replace("ç", "&ccedil;");
		val = val.replace("è", "&egrave;");
		val = val.replace("ê", "&ecirc;");
		val = val.replace("ë", "&euml;");
		val = val.replace("ì", "&igrave;");
		val = val.replace("î", "&icirc;");
		val = val.replace("ï", "&iuml;");
		val = val.replace("ð", "&eth;");
		val = val.replace("ò", "&ograve;");
		val = val.replace("ô", "&ocirc;");
		val = val.replace("õ", "&otilde;");
		val = val.replace("ö", "&ouml;");
		val = val.replace("÷", "&divide;");
		val = val.replace("ø", "&oslash;");
		val = val.replace("ù", "&ugrave;");
		val = val.replace("û", "&ucirc;");
		val = val.replace("ü", "&uuml;");
		val = val.replace("ý", "&yacute;");
		val = val.replace("þ", "&thorn;");
		val = val.replace("ÿ", "&yuml;");
		val = val.replace("Œ", "&OElig;");
		val = val.replace("œ", "&oelig;");
		val = val.replace("Š", "&Scaron;");
		val = val.replace("š", "&scaron;");
		val = val.replace("Ÿ", "&Yuml;");
		val = val.replace("ˆ", "&circ;");
		val = val.replace("~", "&tilde;");
		val = val.replace("‘", "&lsquo;");
		val = val.replace("’", "&rsquo;");
		val = val.replace("‚", "&sbquo;");
		val = val.replace("†", "&dagger;");
		val = val.replace("‡", "&Dagger;");
		val = val.replace("‰", "&permil;");
		val = val.replace("‹", "&lsaquo;");
		val = val.replace("›", "&rsaquo;");
		val = val.replace("…", "&#8230;");
		val = val.replace("‰", "&#8240;");
		val = val.replace("€", "&euro;");
		val = val.replace("™", "&#8482;");
		// val = val.replace("\n", "<br>");
		return val;
	}

	public static String regresaCaracteresEspeciales(String cadena) {
		String val = cadena;
		// return "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> " + val;
		val = val.replace("&aacute;", "á");
		val = val.replace("&eacute;", "é");
		val = val.replace("&iacute;", "í");
		val = val.replace("&oacute;", "ó");
		val = val.replace("&uacute;", "ú");

		val = val.replace("&Aacute;", "Á");
		val = val.replace("&Eacute;", "É");
		val = val.replace("&Iacute;", "Í");
		val = val.replace("&Oacute;", "Ó");
		val = val.replace("&Uacute;", "Ú");

		val = val.replace("&ntilde;", "ñ");
		val = val.replace("&Ntilde;", "Ñ");
		val = val.replace("&#124;", "|");
		val = val.replace("&#8220;", "“");
		val = val.replace("&#8221;", "”");
		val = val.replace("&#8222;", "„");
		val = val.replace("&#176;", "°");
		val = val.replace("&#8212;", "—");
		val = val.replace("&#8211;", "–");

		// Del listado Completo
		val = val.replace("&#128;", "€");
		val = val.replace("&#130;", "‚");
		val = val.replace("&#140;", "Œ");
		val = val.replace("&#142;", "Ž");
		val = val.replace("&#145;", "‘");
		val = val.replace("&#146;", "’");
		val = val.replace("&#149;", "•");
		val = val.replace("&#152;", "˜");
		val = val.replace("&#153;", "™");
		val = val.replace("&#154;", "š");
		val = val.replace("&#155;", "›");
		val = val.replace("&#156;", "œ");
		val = val.replace("&#159;", "Ÿ");
		val = val.replace("&iexcl;", "¡");
		val = val.replace("&cent;", "¢");
		val = val.replace("&pound;", "£");
		val = val.replace("&curren;", "¤");
		val = val.replace("&yen;", "¥");
		val = val.replace("&brvbar;", "¦");
		val = val.replace("&sect;", "§");
		val = val.replace("&uml;", "¨");
		val = val.replace("&copy;", "©");
		val = val.replace("&ordf;", "ª");
		val = val.replace("&laquo;", "«");
		val = val.replace("&not;", "¬");
		val = val.replace("&reg;", "®");
		val = val.replace("&macr;", "¯");
		val = val.replace("&plusmn;", "±");
		val = val.replace("&sup2;", "²");
		val = val.replace("&sup3;", "³");
		val = val.replace("&acute;", "´");
		val = val.replace("&micro;", "μ");
		val = val.replace("&para;", "¶");
		val = val.replace("&middot;", "·");
		val = val.replace("&cedil;", "¸");
		val = val.replace("&sup1;", "¹");
		val = val.replace("&ordm;", "º");
		val = val.replace("&raquo;", "»");
		val = val.replace("&frac14;", "¼");
		val = val.replace("&frac12;", "½");
		val = val.replace("&frac34;", "¾");
		val = val.replace("&iquest;", "¿");
		val = val.replace("&Agrave;", "À");
		val = val.replace("&Acirc;", "Â");
		val = val.replace("&Atilde;", "Ã");
		val = val.replace("&Auml;", "Ä");
		val = val.replace("&Aring;", "Å");
		val = val.replace("&AElig;", "Æ");
		val = val.replace("&Ccedil;", "Ç");
		val = val.replace("&Egrave;", "È");
		val = val.replace("&Ecirc;", "Ê");
		val = val.replace("&Euml;", "Ë");
		val = val.replace("&Igrave;", "Ì");
		val = val.replace("&Icirc;", "Î");
		val = val.replace("&Iuml;", "Ï");
		val = val.replace("&ETH;", "Ð");
		val = val.replace("&Ograve", "Ò");
		val = val.replace("&Ocirc;", "Ô");
		val = val.replace("&Otilde;", "Õ");
		val = val.replace("&Ouml;", "Ö");
		val = val.replace("&times;", "×");
		val = val.replace("&Oslash;", "Ø");
		val = val.replace("&Ugrave;", "Ù");
		val = val.replace("&Ucirc;", "Û");
		val = val.replace("&Uuml;", "Ü");
		val = val.replace("&Yacute;", "Ý");
		val = val.replace("&THORN;", "Þ");
		val = val.replace("&szlig;", "ß");
		val = val.replace("&agrave;", "à");
		val = val.replace("&acirc;", "â");
		val = val.replace("&atilde;", "ã");
		val = val.replace("&auml;", "ä");
		val = val.replace("&aring;", "å");
		val = val.replace("&aelig;", "æ");
		val = val.replace("&ccedil;", "ç");
		val = val.replace("&egrave;", "è");
		val = val.replace("&ecirc;", "ê");
		val = val.replace("&euml;", "ë");
		val = val.replace("&igrave;", "ì");
		val = val.replace("&icirc;", "î");
		val = val.replace("&iuml;", "ï");
		val = val.replace("&eth;", "ð");
		val = val.replace("&ograve;", "ò");
		val = val.replace("&ocirc;", "ô");
		val = val.replace("&otilde;", "õ");
		val = val.replace("&ouml;", "ö");
		val = val.replace("&divide;", "÷");
		val = val.replace("&oslash;", "ø");
		val = val.replace("&ugrave;", "ù");
		val = val.replace("&ucirc;", "û");
		val = val.replace("&uuml;", "ü");
		val = val.replace("&yacute;", "ý");
		val = val.replace("&thorn;", "þ");
		val = val.replace("&yuml;", "ÿ");
		val = val.replace("&OElig;", "Œ");
		val = val.replace("&oelig;", "œ");
		val = val.replace("&Scaron;", "Š");
		val = val.replace("&scaron;", "š");
		val = val.replace("&Yuml;", "Ÿ");
		val = val.replace("&circ;", "ˆ");
		val = val.replace("&tilde;", "~");
		val = val.replace("&lsquo;", "‘");
		val = val.replace("&rsquo;", "’");
		val = val.replace("&sbquo;", "‚");
		val = val.replace("&dagger;", "†");
		val = val.replace("&Dagger;", "‡");
		val = val.replace("&permil;", "‰");
		val = val.replace("&lsaquo;", "‹");
		val = val.replace("&rsaquo;", "›");
		val = val.replace("&#8230;", "…");
		val = val.replace("&#8240;", "‰");
		val = val.replace("&euro;", "€");
		val = val.replace("&#8482;", "™");

		// val = val.replace("\n", "<br>");
		return val;
	}

	// Conversion Base64
	public static String codificarStrBase64(String cadena) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream b64os = MimeUtility.encode(baos, "base64");
		b64os.write(cadena.getBytes());
		b64os.close();

		String strBase64 = new String(baos.toByteArray());
		strBase64 = strBase64.replace("\n", "").replace("\r", "");
		return strBase64;
	}

	public static String codificarStrBase64(byte[] arreglo) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream b64os = MimeUtility.encode(baos, "base64");
		b64os.write(arreglo);
		b64os.close();
		// return new String(baos.toByteArray());

		String strBase64 = new String(baos.toByteArray());
		strBase64 = strBase64.replace("\n", "").replace("\r", "");
		return strBase64;

		// return ClsBase64.encode(arreglo);

	}

	public static String decodificarStrBase64(String cadena) throws Exception {
		byte[] b = cadena.getBytes("UTF-8");
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		InputStream b64is = MimeUtility.decode(bais, "base64");
		byte[] tmp = new byte[b.length];
		int n = b64is.read(tmp);
		byte[] res = new byte[n];
		System.arraycopy(tmp, 0, res, 0, n);
		return new String(res);
	}

	public static String decodificarStrBase64(byte[] arreglo) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(arreglo);
		InputStream b64is = MimeUtility.decode(bais, "base64");
		byte[] tmp = new byte[arreglo.length];
		int n = b64is.read(tmp);
		byte[] res = new byte[n];
		System.arraycopy(tmp, 0, res, 0, n);
		return new String(res);
	}

	// Consecutivos
	public static long dameIdActual(String empresa, long temporada, String tipo, DatastoreService datastore, Transaction tx) throws Exception {
		try {
			Key key = KeyFactory.createKey("DbConsecutivo", empresa + "-" + temporada + "-" + tipo);
			DbConsecutivo dbConsecutivo = new DbConsecutivo(datastore.get(tx, key));
			return dbConsecutivo.getId();
		} catch (EntityNotFoundException e) {
			return 0L;
		}
	}

	public static long dameSiguienteId(String empresa, long temporada, String tipo, DatastoreService datastore, Transaction tx) throws ExcepcionControlada {
		long id = 0;
		DbConsecutivo dbConsecutivo;
		try {
			Key key = KeyFactory.createKey("DbConsecutivo", empresa + "-" + temporada + "-" + tipo);
			dbConsecutivo = new DbConsecutivo(datastore.get(tx, key));
		} catch (EntityNotFoundException e) {
			dbConsecutivo = new DbConsecutivo(empresa, temporada, tipo);
		}
		id = dbConsecutivo.getId() + 1;
		dbConsecutivo.setId(id);
		dbConsecutivo.guardar(datastore, tx);
		return id;
	}

	public static void estableceId(String empresa, long temporada, String tipo, long id, DatastoreService datastore, Transaction tx) throws Exception {
		DbConsecutivo dbConsecutivo;
		try {
			Key key = KeyFactory.createKey("DbConsecutivo", empresa + "-" + temporada + "-" + tipo);
			dbConsecutivo = new DbConsecutivo(datastore.get(tx, key));
		} catch (EntityNotFoundException e) {
			dbConsecutivo = new DbConsecutivo(empresa, temporada, tipo);
		}
		dbConsecutivo.setId(id);
		dbConsecutivo.guardar(datastore, tx);
	}

}
