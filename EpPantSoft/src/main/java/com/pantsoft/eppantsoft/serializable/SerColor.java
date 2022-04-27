package com.pantsoft.eppantsoft.serializable;

public class SerColor {
	private String empresa;
	private String color;

	public SerColor() {
	}

	public SerColor(String empresa, String color) {
		this.empresa = empresa;
		this.setColor(color);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
