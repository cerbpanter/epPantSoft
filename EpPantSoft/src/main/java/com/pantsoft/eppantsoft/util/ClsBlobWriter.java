package com.pantsoft.eppantsoft.util;

import java.util.ArrayList;
import java.util.Date;

public class ClsBlobWriter {
	StringBuilder sb;
	boolean pipe;
	String separadorFila = "\n";
	int numFilas = 1;

	public ClsBlobWriter() {
		sb = new StringBuilder(1048576); // 1MB
		pipe = false;
	}

	public ClsBlobWriter(String separadorFila) {
		sb = new StringBuilder(1048576); // 1MB
		pipe = false;
		this.separadorFila = separadorFila;
	}

	public void cargarBlobStr(String blobStr) {
		sb.append(blobStr);
	}

	public void nuevaFila() {
		sb.append(separadorFila);
		pipe = false;
		numFilas++;
	}

	public int getNumFilas() {
		return numFilas;
	}

	public int length() {
		return sb.length();
	}

	private void agregarPipe() {
		if (pipe)
			sb.append("|");
		else
			pipe = true;
	}

	public void agregarInt(Integer valor) {
		agregarPipe();
		if (valor == null)
			sb.append("&NullSiNube;");
		else
			sb.append(valor);
	}

	public void agregarLong(Long valor) {
		agregarPipe();
		if (valor == null)
			sb.append("&NullSiNube;");
		else
			sb.append(valor);
	}

	/***
	 * Devuelve el contenido del blob en una cadena
	 * 
	 * @return
	 * 				La cadena que representa el blob
	 */
	public String getString() {
		return sb.toString();
	}

	public void agregarDbl(Double valor) {
		agregarPipe();
		if (valor == null)
			sb.append("&NullSiNube;");
		else
			sb.append(valor);
	}

	public void agregarShort(Short valor) {
		agregarPipe();
		if (valor == null)
			sb.append("&NullSiNube;");
		else
			sb.append(valor);
	}

	public void agregarDate(Date valor) {
		agregarPipe();
		if (valor == null)
			sb.append("&NullSiNube;");
		else
			sb.append(valor.getTime());
	}

	public void agregarBool(Boolean valor) {
		agregarPipe();
		if (valor == null)
			sb.append("&NullSiNube;");
		else
			sb.append(valor);
	}

	public void agregarStr(String valor) {
		agregarPipe();
		if (valor == null) {
			sb.append("&NullSiNube;");
		} else {
			valor = valor.replace("&Pipe2SiNube;", "&Pipe3SiNube;").replace("&Enter2SiNube;", "&Enter3SiNube;").replace("&Separador2SiNube;", "&Separador3SiNube;");
			valor = valor.replace("&PipeSiNube;", "&Pipe2SiNube;").replace("&EnterSiNube;", "&Enter2SiNube;").replace("&SeparadorSiNube;", "&Separador2SiNube;");
			sb.append(valor.replace("|", "&PipeSiNube;").replace("\n", "&EnterSiNube;").replace(separadorFila, "&SeparadorSiNube;"));
		}
	}

	public void agregarArrayString(ArrayList<String> lstValores) {
		StringBuilder sb = null;
		if (lstValores != null && lstValores.size() > 0) {
			sb = new StringBuilder(4096);
			for (String val : lstValores) {
				if (sb.length() > 0)
					sb.append(",");
				sb.append(val.replace(",", "&ComaSiNube;"));
			}
		}
		agregarStr(sb == null ? null : sb.toString());
	}

	public void agregarArrayString(String[] lstValores) {
		StringBuilder sbt = null;
		if (lstValores != null && lstValores.length > 0) {
			sbt = new StringBuilder(4096);
			for (String val : lstValores) {
				if (sbt.length() > 0)
					sbt.append(",");
				sbt.append(val.replace(",", "&ComaSiNube;"));
			}
		}
		agregarStr(sbt == null ? null : sbt.toString());
	}

	public void agregarArrayLong(ArrayList<Long> lstValores) {
		StringBuilder sbt = null;
		if (lstValores != null && lstValores.size() > 0) {
			sbt = new StringBuilder(4096);
			for (Long val : lstValores) {
				if (sbt.length() > 0)
					sbt.append(",");
				sbt.append(val);
			}
		}
		agregarStr(sbt == null ? null : sbt.toString());
	}

	public void agregarArrayLong(Long[] lstValores) {
		StringBuilder sbt = null;
		if (lstValores != null && lstValores.length > 0) {
			sbt = new StringBuilder(4096);
			for (Long val : lstValores) {
				if (sbt.length() > 0)
					sbt.append(",");
				sbt.append(val);
			}
		}
		agregarStr(sbt == null ? null : sbt.toString());
	}

	public void insertarAlInicioStr(String valor) {
		if (valor == null) {
			sb.insert(0, "&NullSiNube;");
		} else {
			valor = valor.replace("&Pipe2SiNube;", "&Pipe3SiNube;").replace("&Enter2SiNube;", "&Enter3SiNube;").replace("&Separador2SiNube;", "&Separador3SiNube;");
			valor = valor.replace("&PipeSiNube;", "&Pipe2SiNube;").replace("&EnterSiNube;", "&Enter2SiNube;").replace("&SeparadorSiNube;", "&Separador2SiNube;");
			sb.insert(0, valor.replace("|", "&PipeSiNube;").replace("\n", "&EnterSiNube;"));
		}
	}

	public void agregarLongAlInicio(Long valor) {

		if (valor == null)
			sb.insert(0, "&NullSiNube;|");
		else
			sb.insert(0, valor + "|");

	}

	public void insertarAlInicioBlobStr(String blobStr) {
		if (blobStr != null)
			sb.insert(0, blobStr);
	}

	public String getSeparadorFila() {
		return separadorFila;
	}

	public void setSeparadorFila(String separadorFila) {
		this.separadorFila = separadorFila;
	}
}