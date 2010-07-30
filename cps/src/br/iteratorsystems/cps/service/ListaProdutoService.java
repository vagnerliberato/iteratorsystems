package br.iteratorsystems.cps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ListaProdutoDao;
import br.iteratorsystems.cps.entities.LISTAPRODUTO;
import br.iteratorsystems.cps.entities.LISTAPRODUTOITEM;
import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ListaProdutoService {

	private Session session = HibernateConfig.getSession();
	private ListaProdutoDao listaProdutoDao = new ListaProdutoDao(LISTAPRODUTO.class, session);
	
	public List<LISTAPRODUTOITEM> popularItemDaListaDeProduto(Integer idItensLista, LISTAPRODUTO listaProduto, List<PRODUTOGERAL> produtosGerais, HashMap<String, Integer> quantidadePorProduto){
		
		List<LISTAPRODUTOITEM> listaProdutosItens = new ArrayList<LISTAPRODUTOITEM>();
		for (PRODUTOGERAL produtogeral : produtosGerais) {
			LISTAPRODUTOITEM itemProduto = new LISTAPRODUTOITEM();
			itemProduto.setIdItensLista(idItensLista);
			itemProduto.setListaProduto(listaProduto);
			itemProduto.setProdutogeral(produtogeral);
			itemProduto.setQuantidade(quantidadePorProduto.get(produtogeral.getCodigoBarras()));

			listaProdutosItens.add(itemProduto);
		}
		
		return listaProdutosItens;
	}
	
	public LISTAPRODUTO salvarListaDeProdutos(Integer idItensLista, LISTAPRODUTO listaProduto, List<PRODUTOGERAL> produtosGerais, HashMap<String, Integer> quantidadePorProduto) throws CpsDaoException{
		List<LISTAPRODUTOITEM> produtoItens = popularItemDaListaDeProduto(idItensLista, listaProduto, produtosGerais, quantidadePorProduto);
		Set<LISTAPRODUTOITEM> listaProdutoItems = new HashSet<LISTAPRODUTOITEM>();
		
		for (LISTAPRODUTOITEM listaprodutoitem : produtoItens) {
			listaProdutoItems.add(listaprodutoitem);
			listaProduto.setListaProdutoItems(listaProdutoItems);
		}

		listaProdutoDao.salvar(listaProduto);
		return listaProduto;
	}

	public void setListaProdutoDao(ListaProdutoDao listaProdutoDao2) {
		this.listaProdutoDao = listaProdutoDao2;
	}
}
