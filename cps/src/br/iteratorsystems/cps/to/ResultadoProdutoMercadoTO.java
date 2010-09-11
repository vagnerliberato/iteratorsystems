package br.iteratorsystems.cps.to;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe TO de resultado de busca de produto
 * @author André
 *
 */
public class ResultadoProdutoMercadoTO implements Comparable<ResultadoProdutoMercadoTO>{

	private Integer codigoMercado;
	private Integer codigoRede;
	private List<ProdutoBuscaTO> listaEncontrados;
	private List<ProdutoBuscaTO> listaNaoEncontrados;
	private Double valorTotalLista;
	
	/**
	 * Construtor
	 */
	public ResultadoProdutoMercadoTO() {
		listaEncontrados = new ArrayList<ProdutoBuscaTO>(1);
		listaNaoEncontrados = new ArrayList<ProdutoBuscaTO>(1);
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
	 * @param valorTotalLista the valorTotalLista to set
	 */
	public void setValorTotalLista(Double valorTotalLista) {
		this.valorTotalLista = valorTotalLista;
	}

	/**
	 * @return the valorTotalLista
	 */
	public Double getValorTotalLista() {
		return valorTotalLista;
	}

	/**
	 * to String
	 * @return classe em string
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lista Encontrados: ");
		builder.append("\n");
		builder.append(listaEncontrados);
		builder.append("Lista de Não Encontrados: ");
		builder.append("\n");
		builder.append(listaNaoEncontrados);
		return builder.toString();
	}

	/**
	 * Compara com outro objeto semelhante
	 * @param other - Outro objeto semelhante
	 * @return Valor da comparacao
	 */
	public int compareTo(ResultadoProdutoMercadoTO other) {
		return this.valorTotalLista.compareTo(other.valorTotalLista);
	}
}
