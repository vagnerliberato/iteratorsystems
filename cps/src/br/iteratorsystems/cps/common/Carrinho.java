package br.iteratorsystems.cps.common;

public class Carrinho {

	private String codigoDeBarras;
	private Integer quantidade;

	public Carrinho(String codigoDeBarras, Integer quantidade) {
		this.codigoDeBarras = codigoDeBarras;
		this.quantidade = quantidade;
	}

	public Carrinho() {
		super();
	}
	
	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}
	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Integer getQuantidade() {
		return quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoDeBarras == null) ? 0 : codigoDeBarras.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Carrinho)) {
			return false;
		}
		Carrinho other = (Carrinho) obj;
		if (codigoDeBarras == null) {
			if (other.codigoDeBarras != null) {
				return false;
			}
		} else if (!codigoDeBarras.equals(other.codigoDeBarras)) {
			return false;
		}
		if (quantidade == null) {
			if (other.quantidade != null) {
				return false;
			}
		} else if (!quantidade.equals(other.quantidade)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Carrinho [codigoDeBarras=" + codigoDeBarras + ", quantidade="
				+ quantidade + "]";
	}
}
