package br.iteratorsystems.cps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ListaProdutoDao;
import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoGeral;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ListaProdutoService {

	private Session session = HibernateConfig.getSession();
	private ListaProdutoDao listaProdutoDao = new ListaProdutoDao(Tabelas_ListaProduto.class, session);
	
	public List<Tabelas_ListaProdutoItem> popularItemDaListaDeProduto(Integer idItensLista, Tabelas_ListaProduto listaProduto, List<Tabelas_ProdutoGeral> produtosGerais, HashMap<String, Integer> quantidadePorProduto){
		
		List<Tabelas_ListaProdutoItem> listaProdutosItens = new ArrayList<Tabelas_ListaProdutoItem>();
		for (Tabelas_ProdutoGeral produtogeral : produtosGerais) {
			Tabelas_ListaProdutoItem itemProduto = new Tabelas_ListaProdutoItem();
			itemProduto.setIdItensLista(idItensLista);
			itemProduto.setListaProduto(listaProduto);
			itemProduto.setProdutogeral(produtogeral);
			itemProduto.setQuantidade(quantidadePorProduto.get(produtogeral.getCodigoBarras()));

			listaProdutosItens.add(itemProduto);
		}
		
		return listaProdutosItens;
	}
	
	public Tabelas_ListaProduto salvarListaDeProdutos(Integer idItensLista, Tabelas_ListaProduto listaProduto, List<Tabelas_ProdutoGeral> produtosGerais, HashMap<String, Integer> quantidadePorProduto) throws CpsDaoException{
		List<Tabelas_ListaProdutoItem> produtoItens = popularItemDaListaDeProduto(idItensLista, listaProduto, produtosGerais, quantidadePorProduto);
		Set<Tabelas_ListaProdutoItem> listaProdutoItems = new HashSet<Tabelas_ListaProdutoItem>();
		
		for (Tabelas_ListaProdutoItem listaprodutoitem : produtoItens) {
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
