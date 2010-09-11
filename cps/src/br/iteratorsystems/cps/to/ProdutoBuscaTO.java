package br.iteratorsystems.cps.to;

/**
 * Classe TO de produto busca
 * @author André
 *
 */
public class ProdutoBuscaTO {

	private Long codigo;
	private String descricao;
	private Double valor;
	private Integer quantidade;
	
	/**
	 * Construtor full
	 * @param codigo - codigo
	 * @param descricao - descricao
	 * @param quantidade - quantidade
	 */
	public ProdutoBuscaTO(Long codigo,String descricao,Integer quantidade) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.quantidade = quantidade;
	}
	
	/**
	 * Construtor default
	 */
	public ProdutoBuscaTO() {
		super();
	}
	
	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the valor
	 */
	public Double getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}
	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}
	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	/**
	 * To String
	 * @return String da classe
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.codigo);
		builder.append(", ");
		builder.append(this.descricao);
		builder.append(", ");
		builder.append(this.valor);
		builder.append(", ");
		builder.append(this.quantidade);
		return builder.toString();
	}
}
