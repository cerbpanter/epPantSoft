package com.pantsoft.eppantsoft.entidades;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pantsoft.eppantsoft.serializable.SerModeloImagen;
import com.pantsoft.eppantsoft.util.ClsCampo;
import com.pantsoft.eppantsoft.util.ClsCampo.Tipo;
import com.pantsoft.eppantsoft.util.ClsEntidad;
import com.pantsoft.eppantsoft.util.ExcepcionControlada;

public class DbModeloImagen extends ClsEntidad {
	private final ClsCampo empresa = new ClsCampo("empresa", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 1, NO_SUSTITUIR_NULL);
	private final ClsCampo temporada = new ClsCampo("temporada", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 2, NO_SUSTITUIR_NULL);
	private final ClsCampo modelo = new ClsCampo("modelo", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_MISSING, 3, NO_SUSTITUIR_NULL);
	private final ClsCampo referencia = new ClsCampo("referencia", Tipo.String, INDEXADO, NO_PERMITIR_NULL, 0, 20, TAM_NORMAL, VAL_MISSING, 4, NO_SUSTITUIR_NULL);
	private final ClsCampo renglon = new ClsCampo("renglon", Tipo.Long, INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 5, NO_SUSTITUIR_NULL);
	private final ClsCampo altoImagen = new ClsCampo("altoImagen", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo anchoImagen = new ClsCampo("anchoImagen", Tipo.Long, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo imagen = new ClsCampo("imagen", Tipo.Blob, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);
	private final ClsCampo imagenMini = new ClsCampo("imagenMini", Tipo.Blob, NO_INDEXADO, NO_PERMITIR_NULL, 0, 0, TAM_NORMAL, VAL_MISSING, 0, NO_SUSTITUIR_NULL);

	public DbModeloImagen(SerModeloImagen ser) throws ExcepcionControlada {
		Key keyP = KeyFactory.createKey("DbModelo", ser.getEmpresa() + "-" + ser.getTemporada() + "-" + ser.getModelo() + "-" + ser.getReferencia());
		Key key = KeyFactory.createKey(keyP, "DbModeloImagen", ser.getEmpresa() + "-" + ser.getTemporada() + "-" + ser.getModelo() + "-" + ser.getReferencia() + "-" + ser.getRenglon());
		entidad = new Entity(key);
		asignarValoresDefault();

		setString(this.empresa, ser.getEmpresa());
		setLong(this.temporada, ser.getTemporada());
		setString(this.modelo, ser.getModelo());
		setString(this.referencia, ser.getReferencia());
		setLong(this.renglon, ser.getRenglon());
		setImagen(ser.getAltoImagen(), ser.getAnchoImagen(), ser.getImagen());
		setImagenMini(ser.getImagenMini());
	}

	public DbModeloImagen(Entity entidad) throws ExcepcionControlada {
		this.entidad = entidad;
		asignarValoresDefault();
	}

	@Override
	public List<ClsCampo> getCampos() {
		return Arrays.asList(empresa, temporada, modelo, referencia, renglon, altoImagen, anchoImagen, imagen, imagenMini);
	}

	public String getEmpresa() throws ExcepcionControlada {
		return getString(empresa);
	}

	public long getTemporada() throws ExcepcionControlada {
		return getLong(temporada);
	}

	public String getModelo() throws ExcepcionControlada {
		return getString(modelo);
	}

	public String getReferencia() throws ExcepcionControlada {
		return getString(referencia);
	}

	public long getRenglon() throws ExcepcionControlada {
		return getLong(renglon);
	}

	public long getAltoImagen() throws ExcepcionControlada {
		return getLong(altoImagen);
	}

	private void setAltoImagen(long altoImagen) throws ExcepcionControlada {
		setLong(this.altoImagen, altoImagen);
	}

	public long getAnchoImagen() throws ExcepcionControlada {
		return getLong(anchoImagen);
	}

	private void setAnchoImagen(long anchoImagen) throws ExcepcionControlada {
		setLong(this.anchoImagen, anchoImagen);
	}

	public byte[] getImagen() throws ExcepcionControlada {
		return getBlob(imagen);
	}

	public void setImagen(long altoImagen, long anchoImagen, byte[] imagen) throws ExcepcionControlada {
		if (imagen == null || imagen.length == 0)
			throw new ExcepcionControlada("La imagen no puede estar vacía");
		if (imagen.length > 819200)
			throw new ExcepcionControlada("La imagen no puede mediar más de 800 KB");
		this.setAltoImagen(altoImagen);
		this.setAnchoImagen(anchoImagen);
		setBlob(this.imagen, imagen);
	}

	public byte[] getImagenMini() throws ExcepcionControlada {
		return getBlob(imagenMini);
	}

	public void setImagenMini(byte[] imagenMini) throws ExcepcionControlada {
		if (imagenMini == null || imagenMini.length == 0)
			throw new ExcepcionControlada("La imagen mini no puede estar vacía");
		if (imagenMini.length > 102400)
			throw new ExcepcionControlada("La imagen mini no puede mediar más de 100 KB");
		setBlob(this.imagenMini, imagenMini);
	}

	@Override
	public boolean getLiberado() {
		return true;
	}
}