package com.pantsoft.eppantsoft.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClsBlobReader {
	private String filas[];
	private String valores[];
	private int posFila;
	private String separadorFila = "\n";
	private Map<String, Integer> mapColumnaNombre = new HashMap<String, Integer>();
	private Map<String, String> mapColumnaTipo = new HashMap<String, String>();

	public ClsBlobReader(String blobStr) {
		filas = blobStr.split(separadorFila);
		posFila = -1;
		siguienteFila();
	}

	public ClsBlobReader(String separadorFila, String blobStr) {
		this.separadorFila = separadorFila;
		filas = blobStr.split(separadorFila);
		posFila = -1;
		siguienteFila();
	}

	public ClsBlobReader(String separadorFila, String blobStr, boolean cargarColumnas) {
		this.separadorFila = separadorFila;
		filas = blobStr.split(separadorFila);
		posFila = -1;
		siguienteFila();
		if (cargarColumnas && filas.length > 0 && getLengthValores() > 2) {
			// 1|&NullSiNube;|serieFactura|String|folioFactura|Long|fechaFactura|Date|estatus|Rating
			// A|73|1453752476869|2
			int pos = 2;
			while (pos < getLengthValores()) {
				mapColumnaNombre.put(getValorStr(pos), (pos / 2) - 1);
				mapColumnaTipo.put(getValorStr(pos), getValorStr(pos + 1));
				pos = pos + 2;
			}
		}
	}

	public boolean siguienteFila() {
		if (posFila < (filas.length - 1)) {
			posFila++;
			valores = filas[posFila].split("\\|");
			return true;
		} else {
			valores = null;
			return false;
		}
	}

	public int getPosFila() {
		return posFila;
	}

	public int getLengthFilas() {
		if (filas == null || filas.length <= 1)
			return 0;
		else
			return filas.length - 1;
	}

	public boolean setPosFila(int pos) {
		if (pos < filas.length) {
			posFila = pos;
			return true;
		} else
			return false;
	}

	public int damePosCampo(String campo) throws Exception {
		if (mapColumnaNombre.containsKey(campo))
			return mapColumnaNombre.get(campo);
		else
			throw new Exception("El campo '" + campo + "' no existe en la consulta. V");
	}

	public boolean existeCampo(String campo) {
		return mapColumnaNombre.containsKey(campo);
	}

	public String dameTipoCampo(String campo) throws Exception {
		if (mapColumnaTipo.containsKey(campo))
			return mapColumnaTipo.get(campo);
		else
			throw new Exception("El campo '" + campo + "' no existe en la consulta. T");
	}

	public void validaTipoCampo(String campo, String tipo, String tipo2) throws Exception {
		if (mapColumnaTipo.containsKey(campo)) {
			if (!mapColumnaTipo.get(campo).equals(tipo) && !mapColumnaTipo.get(campo).equals(tipo2))
				throw new Exception("El campo '" + campo + "' no es del tipo '" + tipo + "'" + (tipo2 == null ? "" : ", '" + tipo2 + "'"));
		} else {
			throw new Exception("El campo '" + campo + "' no existe en la consulta. T");
		}
	}

	public Integer getValorInt(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else
				return Integer.parseInt(valores[pos]);
		} else {
			return null;
		}
	}

	// String, Boolean, Long, Double, Date, Rating, Text, Blob, ArrayString, ArrayLong, Email
	public Integer getValorInt(String campo) throws Exception {
		validaTipoCampo(campo, "Long", "Rating");
		return getValorInt(damePosCampo(campo));
	}

	@SuppressWarnings("deprecation")
	public Long getValorLong(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else {
				String valor = valores[pos];
				if (valor.startsWith("AÑO(")) {
					long valorLong = Long.parseLong(valor.substring(4, valor.length() - 1));
					Date fecha = new Date(valorLong);
					return fecha.getYear() + 1900L;
				} else if (valor.startsWith("MES(")) {
					long valorLong = Long.parseLong(valor.substring(4, valor.length() - 1));
					Date fecha = new Date(valorLong);
					return fecha.getMonth() + 1L;
				} else if (valor.startsWith("SEMANA(")) {
					long valorLong = Long.parseLong(valor.substring(7, valor.length() - 1));
					Date fecha = new Date(valorLong);
					return (long) dameSemana(fecha);
				} else {
					return Long.parseLong(valor);
				}
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public int dameSemana(Date fecha) {
		final long MILLIS_DAY = 86400000;
		final long MILLIS_WEEK = 86400000 * 7;

		// Obtengo el día de la semana del 1 de enero porque debo restárselo a la fecha
		Date inicioAño = new Date(fecha.getYear(), 0, 1);
		long diferenciaPrimerSemana = (7 - inicioAño.getDay()) * MILLIS_DAY;
		// Genero la fecha sin Hora
		Date fechaSinHora = new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate());
		// Calculo la diferencia desde el 1 de enero - la diferencia de la primer semana
		long resto = fechaSinHora.getTime() - inicioAño.getTime() + MILLIS_DAY - diferenciaPrimerSemana;
		int semana = 1;
		// Voy restando semanas completas
		while (resto >= MILLIS_WEEK) {
			semana++;
			resto -= MILLIS_WEEK;
		}
		// Si hay restante es porque la fecha es a media semana
		if (resto > 0)
			semana++;

		return semana;
	}

	public Long getValorLong(String campo) throws Exception {
		validaTipoCampo(campo, "Long", null);
		return getValorLong(damePosCampo(campo));
	}

	public Boolean getValorBool(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else
				return Boolean.parseBoolean(valores[pos]);
		} else {
			return null;
		}
	}

	public Boolean getValorBool(String campo) throws Exception {
		validaTipoCampo(campo, "Boolean", null);
		return getValorBool(damePosCampo(campo));
	}

	public Short getValorShort(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else
				return Short.parseShort(valores[pos]);
		} else {
			return null;
		}
	}

	public Short getValorShort(String campo) throws Exception {
		validaTipoCampo(campo, "Long", null);
		return getValorShort(damePosCampo(campo));
	}

	public Double getValorDbl(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else
				return Double.parseDouble(valores[pos]);
		} else {
			return null;
		}
	}

	public Double getValorDbl(String campo) throws Exception {
		validaTipoCampo(campo, "Double", null);
		return getValorDbl(damePosCampo(campo));
	}

	@SuppressWarnings("deprecation")
	public String getValorStr(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else {
				String valor = valores[pos];
				if (valor.startsWith("DIASEMANA(")) {
					long valorLong = Long.parseLong(valor.substring(10, valor.length() - 1));
					Date fecha = new Date(valorLong);
					switch (fecha.getDay()) {
					case 0:
						return "Domingo";
					case 1:
						return "Lunes";
					case 2:
						return "Martes";
					case 3:
						return "Miércoles";
					case 4:
						return "Jueves";
					case 5:
						return "Viernes";
					case 6:
						return "Sábado";
					default:
						return "Error";
					}
				} else {
					return valores[pos].replace("&PipeSiNube;", "|").replace("&EnterSiNube;", "\n").replace("&SeparadorSiNube;", separadorFila);
				}
			}
		} else {
			return null;
		}
	}

	public String getValorStr(String campo) throws Exception {
		validaTipoCampo(campo, "String", "Text");
		return getValorStr(damePosCampo(campo));
	}

	public Date getValorDate(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;")) {
				return null;
			} else {
				return new Date(Long.parseLong(valores[pos]));
				// long fechaLong = Long.parseLong(valores[pos]);
				// int offset = TimeZone.getDefault().getOffset(fechaLong);
				// return new Date(fechaLong + offset);
			}
		} else {
			return null;
		}

		// Dim fecha As Long = getValorLong(pos)
		// Try
		// Dim base As New DateTime(1970, 1, 1, 0, 0, 0, 0)
		// base = base.AddMilliseconds(fecha)
		//
		// Dim curTimeZone As TimeZone = TimeZone.CurrentTimeZone
		// Dim currentOffset As TimeSpan = curTimeZone.GetUtcOffset(base)
		// Return base.AddMilliseconds(currentOffset.Hours * 60 * 60 * 1000)
		// Catch ex As Exception
		// Throw New Exception("Error al convertir la fecha." & ex.Message)
		// End Try
	}

	public Date getValorDate(String campo) throws Exception {
		validaTipoCampo(campo, "Date", null);
		return getValorDate(damePosCampo(campo));
	}

	public List<String> getValorArrayString(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else {
				String valores = getValorStr(pos);
				if (ClsUtil.esNulo(valores)) {
					return null;
				} else {
					List<String> lstValores = new ArrayList<String>();
					for (String valor : valores.split(",")) {
						lstValores.add(valor.replace("&ComaSiNube;", ","));
					}
					return lstValores;
				}
			}
		} else {
			return null;
		}
	}

	public List<String> getValorArrayString(String campo) throws Exception {
		validaTipoCampo(campo, "ArrayString", null);
		return getValorArrayString(damePosCampo(campo));
	}

	public List<Long> getValorArrayLong(int pos) {
		if (valores != null && pos < valores.length) {
			if (valores[pos].equals("&NullSiNube;"))
				return null;
			else {
				String valores = getValorStr(pos);
				if (ClsUtil.esNulo(valores)) {
					return null;
				} else {
					List<Long> lstValores = new ArrayList<Long>();
					for (String valor : valores.split(",")) {
						lstValores.add(Long.parseLong(valor));
					}
					return lstValores;
				}
			}
		} else {
			return null;
		}
	}

	public List<Long> getValorArrayLong(String campo) throws Exception {
		validaTipoCampo(campo, "ArrayLong", null);
		return getValorArrayLong(damePosCampo(campo));
	}

	public String getSeparadorFila() {
		return separadorFila;
	}

	public void setSeparadorFila(String separadorFila) {
		this.separadorFila = separadorFila;
	}

	public String[] getValores() {
		return valores;
	}

	public int getLengthValores() {
		if (valores == null)
			return 0;
		else
			return valores.length;
	}

}
