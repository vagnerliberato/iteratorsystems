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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((produtoGeral == null) ? 0 : produtoGeral.hashCode());
		result = prime
				* result
				+ ((quantidadeSelecionada == null) ? 0 : quantidadeSelecionada
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProdutoTO)) {
			return false;
		}
		ProdutoTO other = (ProdutoTO) obj;
		if (produtoGeral == null) {
			if (other.produtoGeral != null) {
				return false;
			}
		} else if (!produtoGeral.equals(other.produtoGeral)) {
			return false;
		}
		if (quantidadeSelecionada == null) {
			if (other.quantidadeSelecionada != null) {
				return false;
			}
		} else if (!quantidadeSelecionada.equals(other.quantidadeSelecionada)) {
			return false;
		}
		return true;
	}
}
