package br.iteratorsystems.cps.to;

import java.io.Serializable;

import br.iteratorsystems.cps.entities.ProdutoGeral;

/**
 * TO para os produtos da base de dados.
 * @author Andr�
 *
 */
public class ProdutoTO implements Serializable{

	private static final long serialVersionUID = 7446849227982421202L;
	
	private Integer id;
	private ProdutoGeral produtoGeral;
	private Boolean possuiImagem;
	private Integer quantidadeSelecionada;
	private String imagem;
	
	/**
	 * Construtor m�nimo
	 * @param produtoGeral
	 * @param quantidadeSelecionada
	 */
	public ProdutoTO(Integer id,ProdutoGeral produtoGeral, Integer quantidadeSelecionada) {
		this.id = id;
		this.produtoGeral = produtoGeral;
		this.quantidadeSelecionada = quantidadeSelecionada;
	}
	
	/**
	 * Construtor padr�o
	 */
	public ProdutoTO() {
		super();
	}
	
	/**
	 * @return the produtoGeral
	 */
	public ProdutoGeral getProdutoGeral() {
		return produtoGeral;
	}
	/**
	 * @param produtoGeral the produtoGeral to set
	 */
	public void setProdutoGeral(ProdutoGeral produtoGeral) {
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
		long longAux = Long.parseLong(produtoGeral.getCodigoBarras());
		
		return Integer.valueOf((int) longAux) *  hash + quantidadeSelecionada * hash;
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

	/**
	 * @param imagem the imagem to set
	 */
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	/**
	 * @return the imagem
	 */
	public String getImagem() {
		return imagem;
	}

	/**
	 * @param possuiImagem the possuiImagem to set
	 */
	public void setPossuiImagem(Boolean possuiImagem) {
		this.possuiImagem = possuiImagem;
	}

	/**
	 * @return the possuiImagem
	 */
	public Boolean getPossuiImagem() {
		return possuiImagem;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * to String
	 * @return Variaveis da classe em string
	 */
	@Override
	public String toString() {
		return "ProdutoTO [imagem=" + imagem + ", possuiImagem=" + possuiImagem
				+ ", quantidadeSelecionada=" + quantidadeSelecionada + 
				"codigoBarra="+produtoGeral.getCodigoBarras().trim()+"]";
	}
}
