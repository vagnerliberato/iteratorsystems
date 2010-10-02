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
	private String latitudeUsuario;
	private String longitudeUsuario;
	private String cep;
	private String cepUsuario;
	private List<ProdutoBuscaTO> listaEncontrados;
	private List<ProdutoBuscaTO> listaNaoEncontrados;
	private Double valorTotalLista;
	private Double valorDeslocamento;
	private Double distanciaUsuario;
	private boolean expande = true;
	private boolean expandeProdutosEncontrados;
	private boolean expandeProdutosNaoEncontrados;
	private boolean expandeGmap = true;
	
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
	 * @param cepUsuario the cepUsuario to set
	 */
	public void setCepUsuario(String cepUsuario) {
		this.cepUsuario = cepUsuario;
	}

	/**
	 * @return the cepUsuario
	 */
	public String getCepUsuario() {
		return cepUsuario;
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
	 * @param expande the expande to set
	 */
	public void setExpande(boolean expande) {
		this.expande = expande;
	}

	/**
	 * @return the expande
	 */
	public boolean isExpande() {
		return expande;
	}

	/**
	 * @return the expandeProdutosEncontrados
	 */
	public boolean isExpandeProdutosEncontrados() {
		return expandeProdutosEncontrados;
	}

	/**
	 * @param expandeProdutosEncontrados the expandeProdutosEncontrados to set
	 */
	public void setExpandeProdutosEncontrados(boolean expandeProdutosEncontrados) {
		this.expandeProdutosEncontrados = expandeProdutosEncontrados;
	}

	/**
	 * @return the expandeProdutosNaoEncontrados
	 */
	public boolean isExpandeProdutosNaoEncontrados() {
		return expandeProdutosNaoEncontrados;
	}

	/**
	 * @param expandeProdutosNaoEncontrados the expandeProdutosNaoEncontrados to set
	 */
	public void setExpandeProdutosNaoEncontrados(
			boolean expandeProdutosNaoEncontrados) {
		this.expandeProdutosNaoEncontrados = expandeProdutosNaoEncontrados;
	}

	/**
	 * @return the expandeGmap
	 */
	public boolean isExpandeGmap() {
		return expandeGmap;
	}

	/**
	 * @param expandeGmap the expandeGmap to set
	 */
	public void setExpandeGmap(boolean expandeGmap) {
		this.expandeGmap = expandeGmap;
	}

	/**
	 * Compare TO
	 * @param obj - Outro objeto a ser comparado
	 * @return Valor da comparação
	 */
	public int compareTo(ResultadoComparacaoTO obj) {
		return this.getDistanciaUsuario().compareTo(obj.getDistanciaUsuario());
	}

	/**
	 * @param latitudeUsuario the latitudeUsuario to set
	 */
	public void setLatitudeUsuario(String latitudeUsuario) {
		this.latitudeUsuario = latitudeUsuario;
	}

	/**
	 * @return the latitudeUsuario
	 */
	public String getLatitudeUsuario() {
		return latitudeUsuario;
	}

	/**
	 * @param longitudeUsuario the longitudeUsuario to set
	 */
	public void setLongitudeUsuario(String longitudeUsuario) {
		this.longitudeUsuario = longitudeUsuario;
	}

	/**
	 * @return the longitudeUsuario
	 */
	public String getLongitudeUsuario() {
		return longitudeUsuario;
	}
}
