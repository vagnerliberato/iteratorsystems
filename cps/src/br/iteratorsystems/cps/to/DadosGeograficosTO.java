package br.iteratorsystems.cps.to;

/**
 * Classe que representa a latitude e longitude
 * @author André
 *
 */
public class DadosGeograficosTO {
	
	private String latitude;
	private String longitude;
	
	/**
	 * Construtor default
	 */
	public DadosGeograficosTO() {
		super();
	}
	
	/**
	 * @param latitude
	 * @param longitude
	 */
	public DadosGeograficosTO(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
