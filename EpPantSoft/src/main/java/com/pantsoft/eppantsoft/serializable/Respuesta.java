package com.pantsoft.eppantsoft.serializable;

import java.util.Date;
import java.util.List;

public class Respuesta {
	public double doble;
	public long largo;
	public Date fecha;
	public String cadena;
	public boolean booleano;
	public List<String> lstString;
	public List<Long> lstLong;
	public List<Short> lstShort;

	public Respuesta() {
	}

	public Respuesta(String cadena) {
		setCadena(cadena);
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public boolean getBooleano() {
		return booleano;
	}

	public void setBooleano(boolean booleano) {
		this.booleano = booleano;
	}

	public List<String> getLstString() {
		return lstString;
	}

	public void setLstString(List<String> lst) {
		this.lstString = lst;
	}

	public long getLargo() {
		return largo;
	}

	public void setLargo(long largo) {
		this.largo = largo;
	}

	public double getDoble() {
		return doble;
	}

	public void setDoble(double doble) {
		this.doble = doble;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Long> getLstLong() {
		return lstLong;
	}

	public void setLstLong(List<Long> lstLong) {
		this.lstLong = lstLong;
	}

	public List<Short> getLstShort() {
		return lstShort;
	}

	public void setLstShort(List<Short> lstShort) {
		this.lstShort = lstShort;
	}
}
