package com.pantsoft.eppantsoft.util.base64;

public class ElementoBase64 {
	private int indice;
	private String binario;

	//
	public ElementoBase64(int i, String bin) {
		setOrden(i);
		setBinario(bin);
	}

	//
	public int getIndice() {
		return indice;
	}

	public void setOrden(int i) {
		indice = i;
	}

	public String getBinario() {
		return binario;
	}

	public void setBinario(String binario) {
		this.binario = binario;
	}

}
