package com.pantsoft.eppantsoft.serializable;

public class SerBitacoras {
	private SerBitacora[] bitacoras;

	public SerBitacoras() {
	}

	public SerBitacoras(SerBitacora[] bitacoras) {
		setBitacoras(bitacoras);
	}

	public SerBitacora[] getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(SerBitacora[] bitacoras) {
		this.bitacoras = bitacoras;
	}

}
