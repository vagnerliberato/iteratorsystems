package br.iteratorsystems.cps.to;

import java.io.Serializable;
import java.util.List;

/**
 * Classe TO de lista.
 * @author André
 *
 */
public class ListaProdutoTO implements Serializable {

	/**
	 * Chave serial
	 */
	private static final long serialVersionUID = -4800001992250797632L;

	private Integer idLista;
	private String nomeLista;
	private List<ProdutoTO> listaProdutos;
	
	/**
	 * @param idLista
	 * @param nomeLista
	 * @param listaProdutos
	 */
	public ListaProdutoTO(Integer idLista, String nomeLista,
			List<ProdutoTO> listaProdutos) {
		super();
		this.idLista = idLista;
		this.nomeLista = nomeLista;
		this.listaProdutos = listaProdutos;
	}
	
	/**
	 * Construtor default
	 */
	public ListaProdutoTO() {
		super();
	}


	/**
	 * @return the idLista
	 */
	public Integer getIdLista() {
		return idLista;
	}
	/**
	 * @param idLista the idLista to set
	 */
	public void setIdLista(Integer idLista) {
		this.idLista = idLista;
	}
	/**
	 * @return the nomeLista
	 */
	public String getNomeLista() {
		return nomeLista;
	}
	/**
	 * @param nomeLista the nomeLista to set
	 */
	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}
	/**
	 * @return the listaProdutos
	 */
	public List<ProdutoTO> getListaProdutos() {
		return listaProdutos;
	}
	/**
	 * @param listaProdutos the listaProdutos to set
	 */
	public void setListaProdutos(List<ProdutoTO> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
}
