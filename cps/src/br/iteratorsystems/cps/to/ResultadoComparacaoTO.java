package br.iteratorsystems.cps.to;

import java.util.List;

/**
 * Resultado Comparacao TO.
 * @author André
 *
 */
public class ResultadoComparacaoTO implements Comparable<ResultadoComparacaoTO>{

	private Integer posicao;
	private Integer codigoMercado;
	private Integer codigoRede;
	private String nomeMercado;
	private String latitude;
	private String longitude;
	private String latitudeUsusario;
	private String longitudeUsusario;
	private String cep;
	private List<ProdutoBuscaTO> listaEncontrados;
	private List<ProdutoBuscaTO> listaNaoEncontrados;
	private Double valorTotalLista;
	private Double valorDeslocamento;
	private Double distanciaUsuario;
	
	/**
	 * Construtor default
	 */
	public ResultadoComparacaoTO() {
		super();
	}
	
	/**
	 * @param posicao the posicao to set
	 */
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	/**
	 * @return the posicao
	 */
	public Integer getPosicao() {
		return posicao;
	}

	/**
	 * @return the codigoMercado
	 */
	public Integer getCodigoMercado() {
		return codigoMercado;
	}
	/**
	 * @param codigoMercado the codigoMercado to set
	 */
	public void setCodigoMercado(Integer codigoMercado) {
		this.codigoMercado = codigoMercado;
	}
	/**
	 * @return the codigoRede
	 */
	public Integer getCodigoRede() {
		return codigoRede;
	}
	/**
	 * @param codigoRede the codigoRede to set
	 */
	public void setCodigoRede(Integer codigoRede) {
		this.codigoRede = codigoRede;
	}
	/**
	 * @param nomeMercado the nomeMercado to set
	 */
	public void setNomeMercado(String nomeMercado) {
		this.nomeMercado = nomeMercado;
	}

	/**
	 * @return the nomeMercado
	 */
	public String getNomeMercado() {
		return nomeMercado;
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
	/**
	 * @return the latitudeUsusario
	 */
	public String getLatitudeUsusario() {
		return latitudeUsusario;
	}

	/**
	 * @param latitudeUsusario the latitudeUsusario to set
	 */
	public void setLatitudeUsusario(String latitudeUsusario) {
		this.latitudeUsusario = latitudeUsusario;
	}

	/**
	 * @return the longitudeUsusario
	 */
	public String getLongitudeUsusario() {
		return longitudeUsusario;
	}

	/**
	 * @param longitudeUsusario the longitudeUsusario to set
	 */
	public void setLongitudeUsusario(String longitudeUsusario) {
		this.longitudeUsusario = longitudeUsusario;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}
	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	/**
	 * @return the listaEncontrados
	 */
	public List<ProdutoBuscaTO> getListaEncontrados() {
		return listaEncontrados;
	}
	/**
	 * @param listaEncontrados the listaEncontrados to set
	 */
	public void setListaEncontrados(List<ProdutoBuscaTO> listaEncontrados) {
		this.listaEncontrados = listaEncontrados;
	}
	/**
	 * @return the listaNaoEncontrados
	 */
	public List<ProdutoBuscaTO> getListaNaoEncontrados() {
		return listaNaoEncontrados;
	}
	/**
	 * @param listaNaoEncontrados the listaNaoEncontrados to set
	 */
	public void setListaNaoEncontrados(List<ProdutoBuscaTO> listaNaoEncontrados) {
		this.listaNaoEncontrados = listaNaoEncontrados;
	}
	/**
	 * @return the valorTotalLista
	 */
	public Double getValorTotalLista() {
		return valorTotalLista;
	}
	/**
	 * @param valorTotalLista the valorTotalLista to set
	 */
	public void setValorTotalLista(Double valorTotalLista) {
		this.valorTotalLista = valorTotalLista;
	}
	/**
	 * @return the valorDeslocamento
	 */
	public Double getValorDeslocamento() {
		return valorDeslocamento;
	}
	/**
	 * @param valorDeslocamento the valorDeslocamento to set
	 */
	public void setValorDeslocamento(Double valorDeslocamento) {
		this.valorDeslocamento = valorDeslocamento;
	}
	/**
	 * @return the distanciaUsuario
	 */
	public Double getDistanciaUsuario() {
		return distanciaUsuario;
	}
	/**
	 * @param distanciaUsuario the distanciaUsuario to set
	 */
	public void setDistanciaUsuario(Double distanciaUsuario) {
		this.distanciaUsuario = distanciaUsuario;
	}

	/**
	 * Compare TO
	 * @param obj - Outro objeto a ser comparado
	 * @return Valor da comparação
	 */
	public int compareTo(ResultadoComparacaoTO obj) {
		return this.getDistanciaUsuario().compareTo(obj.getDistanciaUsuario());
	}
}
