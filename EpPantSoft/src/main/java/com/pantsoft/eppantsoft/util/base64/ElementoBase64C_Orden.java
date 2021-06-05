package com.pantsoft.eppantsoft.util.base64;

import java.util.Comparator;

public class ElementoBase64C_Orden implements Comparator<ElementoBase64> {

	@Override
	public int compare(ElementoBase64 o1, ElementoBase64 o2) {
		return o1.getIndice() - o2.getIndice();
	}

	// public boolean Equals(Object o){
	// return this ==o;
	// }
}
