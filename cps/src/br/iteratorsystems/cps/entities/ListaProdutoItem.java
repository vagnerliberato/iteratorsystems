package br.iteratorsystems.cps.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.iteratorsystems.cps.interfaces.EntityAble;

/**
 * ListaProdutoItem generated by hbm2java
 */
@Entity
@Table(name = "lista_produto_item", schema = "tabelas")
@SequenceGenerator(name = "generatorListaItem", sequenceName = "lista_produto_item_id_itens_lista_seq")
public class ListaProdutoItem implements java.io.Serializable,
		EntityAble {

	private static final long serialVersionUID = 5768174124919155293L;
	private Integer idItensLista;
	private ListaProduto listaProduto;
	private ProdutoGeral produtogeral;
	private Integer quantidade;

	public ListaProdutoItem() {
	}

	public ListaProdutoItem(Integer idItensLista,
			ListaProduto listaProduto,
			ProdutoGeral produtogeral, Integer quantidade) {
		this.idItensLista = idItensLista;
		this.listaProduto = listaProduto;
		this.produtogeral = produtogeral;
		this.quantidade = quantidade;
	}

	@Id
	@Column(name = "id_itens_lista", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generatorListaItem")
	public Integer getIdItensLista() {
		return this.idItensLista;
	}

	public void setIdItensLista(Integer idItensLista) {
		this.idItensLista = idItensLista;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lista", nullable = false)
	public ListaProduto getListaProduto() {
		return this.listaProduto;
	}

	public void setListaProduto(ListaProduto listaProduto) {
		this.listaProduto = listaProduto;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_barras", nullable = false)
	public ProdutoGeral getProdutogeral() {
		return this.produtogeral;
	}

	public void setProdutogeral(ProdutoGeral produtogeral) {
		this.produtogeral = produtogeral;
	}

	@Column(name = "quantidade", nullable = false)
	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idItensLista == null) ? 0 : idItensLista.hashCode());
		result = prime * result
				+ ((listaProduto == null) ? 0 : listaProduto.hashCode());
		result = prime * result
				+ ((produtogeral == null) ? 0 : produtogeral.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ListaProdutoItem))
			return false;
		ListaProdutoItem other = (ListaProdutoItem) obj;
		if (idItensLista == null) {
			if (other.idItensLista != null)
				return false;
		} else if (!idItensLista.equals(other.idItensLista))
			return false;
		if (listaProduto == null) {
			if (other.listaProduto != null)
				return false;
		} else if (!listaProduto.equals(other.listaProduto))
			return false;
		if (produtogeral == null) {
			if (other.produtogeral != null)
				return false;
		} else if (!produtogeral.equals(other.produtogeral))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListaProdutoItem [idItensLista=" + idItensLista
				+ ", listaProduto=" + listaProduto.getId() + ", produtogeral="
				+ produtogeral + ", quantidade=" + quantidade + "]";
	}
}