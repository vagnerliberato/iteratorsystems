package br.iteratorsystems.cps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ItensListaProdutoDao;
import br.iteratorsystems.cps.dao.ListaProdutoDao;
import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoGeral;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ListaProdutoService {

	private Session session = HibernateConfig.getSession();
	private ListaProdutoDao listaProdutoDao = new ListaProdutoDao(Tabelas_ListaProduto.class, session);
	private ItensListaProdutoDao itemListaDao = new ItensListaProdutoDao(Tabelas_ListaProdutoItem.class, session) ;
	
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
	
	public void excluirListaDeProdutos(final Tabelas_ListaProduto listaProduto) throws CpsDaoException {
		Session session = HibernateConfig.getSession();
		listaProdutoDao.excluir(listaProduto);
		session.flush();
	}

	public void incluirListaDeProdutos(final Tabelas_ListaProduto listaProduto) throws CpsDaoException {
		Transaction transaction = HibernateConfig.getSession().getTransaction();
		transaction.begin();
		listaProdutoDao.salvar(listaProduto);
		transaction.commit();
	}
	
	public void incluirItensListaDeProdutos(final Set<Tabelas_ListaProdutoItem> itemLista) throws CpsDaoException {
		Transaction transaction = HibernateConfig.getSession().getTransaction();
		Tabelas_ListaProduto listaProduto = obterLista();
		transaction.begin();
		for(Tabelas_ListaProdutoItem item : itemLista) {
			item.setListaProduto(listaProduto);
		}
		itemListaDao.salvarLista(itemLista);
		transaction.commit();
	}
	
	private Tabelas_ListaProduto obterLista() {
		Tabelas_ListaProduto lista = new Tabelas_ListaProduto();
		try {
			Integer id =
					itemListaDao.getLastIdFromModel(new Tabelas_ListaProduto());
			lista.setId(id);
		} catch (CpsDaoException e) {
			e.printStackTrace();
		}
		return lista;
	}
}
