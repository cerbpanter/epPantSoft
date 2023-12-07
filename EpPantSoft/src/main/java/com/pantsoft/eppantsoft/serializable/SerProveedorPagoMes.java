package com.pantsoft.eppantsoft.serializable;

public class SerProveedorPagoMes {
	private String empresa;
	private long mes;
	private boolean autorizado1;
	private boolean autorizado2;
	private boolean autorizado3;
	private boolean autorizado4;
	private boolean autorizado5;
	private boolean terminado;

	public SerProveedorPagoMes() {
	}

	public SerProveedorPagoMes(String empresa, long mes, boolean autorizado1, boolean autorizado2, boolean autorizado3, boolean autorizado4, boolean autorizado5, boolean terminado) {
		setEmpresa(empresa);
		setMes(mes);
		setAutorizado1(autorizado1);
		setAutorizado1(autorizado1);
		setAutorizado1(autorizado1);
		setAutorizado1(autorizado1);
		setAutorizado1(autorizado1);
		setTerminado(terminado);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getMes() {
		return mes;
	}

	public void setMes(long mes) {
		this.mes = mes;
	}

	public boolean getAutorizado1() {
		return autorizado1;
	}

	public void setAutorizado1(boolean autorizado1) {
		this.autorizado1 = autorizado1;
	}

	public boolean getAutorizado2() {
		return autorizado2;
	}

	public void setAutorizado2(boolean autorizado2) {
		this.autorizado2 = autorizado2;
	}

	public boolean getAutorizado3() {
		return autorizado3;
	}

	public void setAutorizado3(boolean autorizado3) {
		this.autorizado3 = autorizado3;
	}

	public boolean getAutorizado4() {
		return autorizado4;
	}

	public void setAutorizado4(boolean autorizado4) {
		this.autorizado4 = autorizado4;
	}

	public boolean getAutorizado5() {
		return autorizado5;
	}

	public void setAutorizado5(boolean autorizado5) {
		this.autorizado5 = autorizado5;
	}

	public boolean getTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

}
