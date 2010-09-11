package br.iteratorsystems.cps.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.LockMode;
import org.hibernate.Session;

import br.iteratorsystems.cps.beans.ListaDeProdutoBean;
import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ListaProdutoDao;
import br.iteratorsystems.cps.entities.ListaProduto;
import br.iteratorsystems.cps.entities.ListaProdutoItem;
import br.iteratorsystems.cps.entities.ProdutoGeral;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.helper.ListaProdutoTOHelper;
import br.iteratorsystems.cps.service.ListaProdutoService;
import br.iteratorsystems.cps.to.ProdutoTO;

public class testeListaDeProduto {

	public static void main(String[] args) throws CpsExceptions {
		
		/*Set<ProdutoTO> listaProdutoTO = new HashSet<ProdutoTO>();
		ProdutoTO produtoTO = new ProdutoTO(popular(),12);
		listaProdutoTO.add(produtoTO); 
		ListaDeProdutoBean bean = new ListaDeProdutoBean();
		
		bean.setListaComprasUsuario(listaProdutoTO);
		bean.incluirListaDeProdutos();*/
		
		Session session = HibernateConfig.getSession();
		ListaProdutoDao listaProdutoDao = new ListaProdutoDao(ListaProduto.class, session);
		ListaProduto listaProduto = listaProdutoDao.obter(1);
		
		ListaProdutoService listaProdutoService = new ListaProdutoService();
		listaProdutoService.excluirListaDeProdutos(listaProduto);
		
		
		/*ListaProdutoTOHelper helper = new ListaProdutoTOHelper(); 
		tabelas'
		Usuario usuario = ListaProdutoTOHelper.popularUsuario(null);
		*/
	}
	
	private static ProdutoGeral popular() {
		ProdutoGeral prod  = new ProdutoGeral();
		Set<ListaProdutoItem> lista = new HashSet<ListaProdutoItem>();
		prod.setCodigoBarras("789100000943");
		prod.setDescricao("teste");
		prod.setEmbalagem("sei la");
		prod.setImagem('N');
		prod.setUnidadeMedida("KG");
		prod.setStatus('A');
		lista.add(new ListaProdutoItem(null, null, prod, 1));
		prod.setListaProdutoItems(lista);
		return prod;
	}
}
