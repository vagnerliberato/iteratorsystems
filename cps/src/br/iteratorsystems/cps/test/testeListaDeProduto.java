package br.iteratorsystems.cps.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.LockMode;
import org.hibernate.Session;

import br.iteratorsystems.cps.beans.ListaDeProdutoBean;
import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ListaProdutoDao;
import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoGeral;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.helper.ListaProdutoTOHelper;
import br.iteratorsystems.cps.service.ListaProdutoService;
import br.iteratorsystems.cps.to.ProdutoTO;

public class testeListaDeProduto {

	public static void main(String[] args) throws CpsGeneralExceptions {
		
		/*Set<ProdutoTO> listaProdutoTO = new HashSet<ProdutoTO>();
		ProdutoTO produtoTO = new ProdutoTO(popular(),12);
		listaProdutoTO.add(produtoTO); 
		ListaDeProdutoBean bean = new ListaDeProdutoBean();
		
		bean.setListaComprasUsuario(listaProdutoTO);
		bean.incluirListaDeProdutos();*/
		
		Session session = HibernateConfig.getSession();
		ListaProdutoDao listaProdutoDao = new ListaProdutoDao(Tabelas_ListaProduto.class, session);
		Tabelas_ListaProduto listaProduto = listaProdutoDao.obter(1);
		
		ListaProdutoService listaProdutoService = new ListaProdutoService();
		listaProdutoService.excluirListaDeProdutos(listaProduto);
		
		
		/*ListaProdutoTOHelper helper = new ListaProdutoTOHelper(); 
		tabelas'
		Tabelas_Usuario usuario = ListaProdutoTOHelper.popularUsuario(null);
		*/
	}
	
	private static Tabelas_ProdutoGeral popular() {
		Tabelas_ProdutoGeral prod  = new Tabelas_ProdutoGeral();
		Set<Tabelas_ListaProdutoItem> lista = new HashSet<Tabelas_ListaProdutoItem>();
		prod.setCodigoBarras("789100000943");
		prod.setDescricao("teste");
		prod.setEmbalagem("sei la");
		prod.setImagem('N');
		prod.setUnidadeMedida("KG");
		prod.setStatus('A');
		lista.add(new Tabelas_ListaProdutoItem(null, null, prod, 1));
		prod.setListaProdutoItems(lista);
		return prod;
	}
}
