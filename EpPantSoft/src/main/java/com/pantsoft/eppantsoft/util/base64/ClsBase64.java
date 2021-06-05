package com.pantsoft.eppantsoft.util.base64;

import java.util.ArrayList;

public class ClsBase64 {

	private static final String base64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

			+ "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "+/";

	// private static final int splitLinesAt = 76;

	public static byte[] zeroPad(int length, byte[] bytes) {

		byte[] padded = new byte[length]; // initialized to zero by JVM

		System.arraycopy(bytes, 0, padded, 0, bytes.length);

		return padded;

	}

	public static String encode(String string) {

		byte[] stringArray;

		try {

			stringArray = string.getBytes("UTF-8"); // use appropriate encoding string!

		} catch (Exception ignored) {

			stringArray = string.getBytes(); // use locale default rather than croak

		}

		return encode(stringArray);

	}

	public static String encode(byte[] stringArray) {

		String encoded = "";

		// determine how many padding bytes to add to the output

		int paddingCount = (3 - (stringArray.length % 3)) % 3;

		// add any necessary padding to the input

		stringArray = zeroPad(stringArray.length + paddingCount, stringArray);

		// process 3 bytes at a time, churning out 4 output bytes

		// worry about CRLF insertions later

		for (int i = 0; i < stringArray.length; i += 3) {

			int j = ((stringArray[i] & 0xff) << 16) +

					((stringArray[i + 1] & 0xff) << 8) +

					(stringArray[i + 2] & 0xff);

			encoded = encoded + base64code.charAt((j >> 18) & 0x3f) +

					base64code.charAt((j >> 12) & 0x3f) +

					base64code.charAt((j >> 6) & 0x3f) +

					base64code.charAt(j & 0x3f);

		}

		// replace encoded padding nulls with "="

		// return splitLines(encoded.substring(0, encoded.length() -

		// paddingCount) + "==".substring(0, paddingCount));

		return encoded.substring(0, encoded.length() - paddingCount) + "==".substring(0, paddingCount);

	}

	// public static byte[] decodifica(String c) {
	// ArrayList<String> lista = new ArrayList<String>();
	// lista.add("prueba");
	// return null;
	// }

	// public static byte[] decodifica(String cadenaBase64) {
	// int indice = 0;
	// byte NumeroBase10;
	// int indiceArr = 0;
	// byte comodines = 0;
	//
	// byte[] arr = new byte[cadenaBase64.length() * 6 / 8 - 1];
	// Hashtable<Integer, String> ArrBinario = new Hashtable<Integer, String>();
	//
	// char caracter;
	//
	// for (;;) {
	// caracter = cadenaBase64.charAt(indice);
	// //
	// if (caracter == '=') {
	// comodines += 1;
	// NumeroBase10 = 0;
	// } else
	// NumeroBase10 = (byte) base64code.indexOf(caracter);
	// //
	// if (indice % 4 == 0) {
	// if (indice > 0)
	// indiceArr++;
	// ArrBinario.put(indiceArr, dameBinario6Bits(NumeroBase10));
	// } else {
	// ArrBinario.put(indiceArr, ArrBinario.get(indiceArr) + dameBinario6Bits(NumeroBase10));
	// }
	// indice++;
	// if (indice == cadenaBase64.length())
	// break;
	// }
	// //
	// int indiceBytes = 0;
	// for (int i = 0; i < ArrBinario.size(); i++) {
	// arr[indiceBytes] = (byte) dameBase10(ArrBinario.get(i).substring(0, 7));
	// indice++;
	// arr[indiceBytes] = (byte) dameBase10(ArrBinario.get(i).substring(8, 15));
	// indice++;
	// arr[indiceBytes] = (byte) dameBase10(ArrBinario.get(i).substring(16, 23));
	// indice++;
	// }
	// //
	// if (comodines > 0) {
	// int nvoTamanio = arr.length - comodines - 1; // chcecar esta linea
	// byte[] arr2 = new byte[nvoTamanio];
	// for (int i = 0; i < nvoTamanio; i++)
	// arr2[i] = arr[i];
	// return arr2;
	// } else
	// return arr;
	//
	// }
	public static byte[] decodifica(String cadenaBase64) {
		int indice = 0;
		int NumeroBase10;
		int indiceArr = 0;
		byte comodines = 0;

		byte[] arr = new byte[cadenaBase64.length() * 6 / 8]; // - 1
		// Hashtable<Integer, String> ArrBinario = new Hashtable<Integer, String>();
		ArrayList<ElementoBase64> ArrBinario = new ArrayList<ElementoBase64>();

		char caracter;

		for (;;) {
			caracter = cadenaBase64.charAt(indice);
			//
			if (caracter == '=') {
				comodines += 1;
				NumeroBase10 = 0;
			} else
				NumeroBase10 = base64code.indexOf(caracter);
			//
			if (indice % 4 == 0) {
				//
				if (indice > 0)
					indiceArr++;
				//
				ArrBinario.add(new ElementoBase64(indiceArr, dameBinario6Bits(NumeroBase10)));
			} else {
				// String aux = ArrBinario.get(indiceArr).getBinario();// esta linea solo para prueba
				// ArrBinario.add(new ElementoBase64(indiceArr, ArrBinario.get(indiceArr).getBinario() + dameBinario6Bits(NumeroBase10)));
				ArrBinario.get(indiceArr).setBinario(ArrBinario.get(indiceArr).getBinario() + dameBinario6Bits(NumeroBase10));

			}

			//
			indice++;
			if (indice == cadenaBase64.length())
				break;
		}

		// Barre ArrBinario ordenado por la propiedad indice
		java.util.Collections.sort(ArrBinario, new ElementoBase64C_Orden());

		//
		int indiceBytes = 0;
		for (int i = 0; i < ArrBinario.size(); i++) {
			// int aux = ArrBinario.get(i).getIndice();
			// (byte) (arr[indiceBytes] & 0xff)
			try {
				arr[indiceBytes] = (byte) dameBase10(ArrBinario.get(i).getBinario().substring(0, 8));
				indiceBytes++;
				arr[indiceBytes] = (byte) dameBase10(ArrBinario.get(i).getBinario().substring(8, 16));
				indiceBytes++;
				arr[indiceBytes] = (byte) dameBase10(ArrBinario.get(i).getBinario().substring(16, 24));
				indiceBytes++;
			} catch (Exception e) {
				// int x = 0;
			}

		}
		//
		if (comodines > 0) {
			int nvoTamanio = arr.length - comodines; // -1
			byte[] arr2 = new byte[nvoTamanio];
			for (int i = 0; i < nvoTamanio; i++)
				arr2[i] = arr[i];
			return arr2;
		} else
			return arr;

	}

	private static String dameBinario6Bits(int numero) {
		int val, res;
		String binario, aux;
		binario = aux = "";
		val = numero;
		for (;;) {
			res = val % 2;
			val = val / 2;
			aux += String.valueOf(res);
			if (val == 0)
				break;
		}
		for (int i = aux.length() - 1; i >= 0; i--)
			binario += aux.charAt(i);
		return alinea_derecha(binario, 6, '0');
	}

	public static String alinea_derecha(String s, int len, char pad) {
		if (len < 0)
			throw new IllegalArgumentException("La longitud debe ser un valor > 0");
		if (len == 0)
			return s;
		if (s == null)
			return s;
		if (s.length() == 0)
			return s;
		int sLen = s.length();
		if (len == s.length())
			return s;
		if (len < sLen)
			return s.substring(sLen - len);
		else {
			String sl = copiachar(pad, len - sLen);
			return sl + s;
		}
	}

	public static String copiachar(char c, int times) {
		if (times == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= times; i++)
			sb.append(c);
		return sb.toString();
	}

	//
	private static int dameBase10(String binario) {
		int bit;
		int potencia, base10;
		potencia = base10 = 0;
		for (int i = binario.length() - 1; i >= 0; i--) {
			bit = Integer.parseInt(String.valueOf(binario.charAt(i)));
			base10 += bit * Math.pow(2, potencia);
			potencia++;
		}

		return base10;
	}

}
