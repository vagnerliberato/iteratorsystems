package br.iteratorsystems.cps.test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ProdutoDao;
import br.iteratorsystems.cps.entities.PRECO;
import br.iteratorsystems.cps.entities.PRECOID;
import br.iteratorsystems.cps.entities.PRODUTO;
import br.iteratorsystems.cps.entities.PRODUTOID;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoDaoTest {

	@Test
	public void deveRetornarOMesmoProdutoSalvo() throws CpsDaoException{
		
		Session session = HibernateConfig.getSession();
		ProdutoDao produtoDao = new ProdutoDao(PRODUTO.class, session);
		
		PRODUTO produto = new PRODUTO();
		produto.setDescricao("CPS");
		produto.setEmbalagem("10");
		PRODUTOID produtoId = new PRODUTOID("111122223333", 3, 5);
		produto.setId(produtoId);
		Set<PRECO> precos = new HashSet<PRECO>();
		PRECOID precoId = new PRECOID(5, 3, "111122223333");
		String precoVarejo1 = "2.55";
		PRECO preco1= new PRECO(precoId, produto, precoVarejo1);
		PRECOID precoId2 = new PRECOID(5, 3, "111122224444");
		String precoVarejo2 = "3.55";
		PRECO preco2= new PRECO(precoId2, produto, precoVarejo2);
		precos.add(preco1);
		precos.add(preco2);
		produto.setPrecos(precos);
		
		produtoDao.salvar(produto);
		
		PRODUTO produtoTest = produtoDao.obter(produtoId);
		
		assertEquals("CPS", produtoTest.getDescricao());
		assertEquals(2, produtoTest.getPrecos().size());
		assertSame(produto, produtoTest);
		assertFalse(produtoTest.getPrecos().isEmpty());
		assertEquals(produtoId, produtoTest.getPrecos().iterator().next().getProduto().getId());
	}
	
}
