package br.iteratorsystems.cps.to;

import br.iteratorsystems.cps.entities.PRODUTOGERAL;

/**
 * TO para os produtos da base de dados.
 * @author André
 *
 */
public class ProdutoTO {

	private PRODUTOGERAL produtoGeral;
	private Integer quantidadeSelecionada;
	
	
	/**
	 * @param produtoGeral
	 * @param quantidadeSelecionada
	 */
	public ProdutoTO(PRODUTOGERAL produtoGeral, Integer quantidadeSelecionada) {
		this.produtoGeral = produtoGeral;
		this.quantidadeSelecionada = quantidadeSelecionada;
	}
	
	/**
	 * @return the produtoGeral
	 */
	public PRODUTOGERAL getProdutoGeral() {
		return produtoGeral;
	}
	/**
	 * @param produtoGeral the produtoGeral to set
	 */
	public void setProdutoGeral(PRODUTOGERAL produtoGeral) {
		this.produtoGeral = produtoGeral;
	}
	/**
	 * @return the quantidadeSelecionada
	 */
	public Integer getQuantidadeSelecionada() {
		return quantidadeSelecionada;
	}
	/**
	 * @param quantidadeSelecionada the quantidadeSelecionada to set
	 */
	public void setQuantidadeSelecionada(Integer quantidadeSelecionada) {
		this.quantidadeSelecionada = quantidadeSelecionada;
	}

	/**
	 * hash code
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		return Integer.parseInt(produtoGeral.getCodigoBarras()) *  hash 
			   + quantidadeSelecionada * hash;
	}
	
	/**
	 * Equals
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equals = true;
		if (!(obj instanceof ProdutoTO)) {
			equals = false;
		} else if (obj == null) {
			equals = false;
		}
		ProdutoTO another = (ProdutoTO) obj;
		if (another.getProdutoGeral().getCodigoBarras().compareTo(
				this.getProdutoGeral().getCodigoBarras()) != 0) {
			equals = false;
		}
		return equals;
	}
}
