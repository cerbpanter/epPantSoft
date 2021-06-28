package com.pantsoft.eppantsoft.serializable;

public class SerModeloImagen {
	private String empresa;
	private long temporada;
	private String modelo;
	private String referencia;
	private long renglon;
	private long altoImagen;
	private long anchoImagen;
	private byte[] imagen;
	private byte[] imagenMini;

	public SerModeloImagen() {
	}

	public SerModeloImagen(String empresa, long temporada, String modelo, String referencia, long renglon, long altoImagen, long anchoImagen, byte[] imagen, byte[] imagenMini) {
		this.empresa = empresa;
		this.temporada = temporada;
		this.modelo = modelo;
		this.referencia = referencia;
		this.renglon = renglon;
		this.altoImagen = altoImagen;
		this.anchoImagen = anchoImagen;
		this.setImagen(imagen);
		this.setImagenMini(imagenMini);
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public long getTemporada() {
		return temporada;
	}

	public void setTemporada(long temporada) {
		this.temporada = temporada;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public long getRenglon() {
		return renglon;
	}

	public void setRenglon(long renglon) {
		this.renglon = renglon;
	}

	public long getAltoImagen() {
		return altoImagen;
	}

	public void setAltoImagen(long altoImagen) {
		this.altoImagen = altoImagen;
	}

	public long getAnchoImagen() {
		return anchoImagen;
	}

	public void setAnchoImagen(long anchoImagen) {
		this.anchoImagen = anchoImagen;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public byte[] getImagenMini() {
		return imagenMini;
	}

	public void setImagenMini(byte[] imagenMini) {
		this.imagenMini = imagenMini;
	}

}
