package com.pantsoft.eppantsoft.util;

import java.util.Date;
import java.util.List;

public class ClsBlobReaderParametros {
	private ClsBlobReader blobR;

	public ClsBlobReaderParametros(String blobStr) {
		blobR = new ClsBlobReader("¬", blobStr, true);
		blobR.siguienteFila();
	}

	public boolean existeCampo(String campo) {
		return blobR.existeCampo(campo);
	}

	// String, Boolean, Long, Double, Date, Rating, Text, Blob, ArrayString, ArrayLong, Email
	public Integer getValorInt(String parametro) throws Exception {
		return blobR.getValorInt(parametro);
	}

	public Long getValorLong(String parametro) throws Exception {
		return blobR.getValorLong(parametro);
	}

	public Boolean getValorBool(String parametro) throws Exception {
		return blobR.getValorBool(parametro);
	}

	public Short getValorShort(String parametro) throws Exception {
		return blobR.getValorShort(parametro);
	}

	public Double getValorDbl(String parametro) throws Exception {
		return blobR.getValorDbl(parametro);
	}

	public String getValorStr(String parametro) throws Exception {
		return blobR.getValorStr(parametro);
	}

	public Date getValorDate(String parametro) throws Exception {
		return blobR.getValorDate(parametro);
	}

	public List<String> getValorArrayString(String parametro) throws Exception {
		return blobR.getValorArrayString(parametro);
	}

	public List<Long> getValorArrayLong(String parametro) throws Exception {
		return blobR.getValorArrayLong(parametro);
	}
}
