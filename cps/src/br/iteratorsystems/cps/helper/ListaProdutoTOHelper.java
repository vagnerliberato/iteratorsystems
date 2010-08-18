package br.iteratorsystems.cps.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.to.ListaProdutoTO;
import br.iteratorsystems.cps.to.ProdutoTO;

/**
 * Classe helper para operações com a lista de produtos
 * @author Robson
 *
 */
public final class ListaProdutoTOHelper {

	/**
	 * Construtor padrão
	 */
	private ListaProdutoTOHelper() {
		super();
	}
	
	/**
	 * Método responsável por converter produto TO em Lista produto Item
	 * @param produtoTO - Produto TO da lista de produtos.
	 * @return Lista Produto Item
	 */
	private static Tabelas_ListaProdutoItem converteProdutoTO(ProdutoTO produtoTO) {
		Tabelas_ListaProdutoItem produtoItem = new Tabelas_ListaProdutoItem();
		produtoItem.setProdutogeral(produtoTO.getProdutoGeral());
		produtoItem.setQuantidade(produtoTO.getQuantidadeSelecionada());
		return produtoItem;
	}

	/**
	 * Método responsavel por converter uma listaProdutoTO em uma listaProdutoItem
	 * @param listaComprasUsuario - ListaProdutoTo do usuario
	 * @return Uma lista de ListaProdutoItem
	 */
	public static Set<Tabelas_ListaProdutoItem> converteListaProdutoTO(Collection<ProdutoTO> listaComprasUsuario) {
		Set<Tabelas_ListaProdutoItem> listaProdutoItem = new HashSet<Tabelas_ListaProdutoItem>();
		for(ProdutoTO produtoTO : listaComprasUsuario) {
			listaProdutoItem.add(converteProdutoTO(produtoTO));
		}
		return listaProdutoItem;
	}

	
	/**
	 * Método responsavel por popular uma lista de produto
	 * @param listaDeProdutoItens
	 * @return Lista de Produto
	 */
	public static Tabelas_ListaProduto popularUmaListaDeProduto(String nomeLista,Tabelas_Usuario usuario){
		Tabelas_ListaProduto listaProduto = new Tabelas_ListaProduto();
		listaProduto.setNomeLista(nomeLista);
		listaProduto.setDataCriacao(new Date());
		listaProduto.setUsuario(usuario);
		return listaProduto;
	}
	
	/**
	 * Obtem uma lista de produto TO com base em uma entidade.
	 * @param listaProduto - Entidade com a lista de produto.
	 * @return Lista de Produto TO preenchida.
	 */
	public static ListaProdutoTO obterListaProdutoTO(Tabelas_ListaProduto listaProduto) {
		ListaProdutoTO listaProdutoTO = new ListaProdutoTO();
		listaProdutoTO.setNomeLista(listaProduto.getNomeLista());
		listaProdutoTO.setListaProdutos(
				(List<ProdutoTO>)converteItemLista(listaProduto.getListaProdutoItems()));
		return listaProdutoTO;
	}
	
	/**
	 * Converte uma lista de items em uma lista de produto TO.
	 * @param listaItem - Lista de items.
	 * @return Lista de produto TO convertida.
	 */
	public static Collection<ProdutoTO> converteItemLista(Collection<Tabelas_ListaProdutoItem> listaItem) {
		List<ProdutoTO> listaTO = new ArrayList<ProdutoTO>();
		for(Tabelas_ListaProdutoItem item : listaItem) {
			listaTO.add(
					new ProdutoTO(item.getProdutogeral(), item.getQuantidade()));
		}
		return listaTO;
	}
}