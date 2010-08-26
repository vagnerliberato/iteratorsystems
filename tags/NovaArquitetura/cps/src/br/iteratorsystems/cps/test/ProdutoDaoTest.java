package br.iteratorsystems.cps.test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ProdutoDao;
import br.iteratorsystems.cps.entities.Preco;
import br.iteratorsystems.cps.entities.PrecoId;
import br.iteratorsystems.cps.entities.Produto;
import br.iteratorsystems.cps.entities.ProdutoId;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoDaoTest {

	@Test
	public void deveRetornarOMesmoProdutoSalvo() throws CpsDaoException{
		
		Session session = HibernateConfig.getSession();
		ProdutoDao produtoDao = new ProdutoDao(Produto.class, session);
		
		Produto produto = new Produto();
		produto.setDescricao("CPS");
		produto.setEmbalagem("10");
		ProdutoId produtoId = new ProdutoId("111122223333", 3, 5);
		produto.setId(produtoId);
		Set<Preco> precos = new HashSet<Preco>();
		PrecoId precoId = new PrecoId(5, 3, "111122223333");
		String precoVarejo1 = "2.55";
		Preco preco1= new Preco(precoId, produto, precoVarejo1);
		PrecoId precoId2 = new PrecoId(5, 3, "111122224444");
		String precoVarejo2 = "3.55";
		Preco preco2= new Preco(precoId2, produto, precoVarejo2);
		precos.add(preco1);
		precos.add(preco2);
		produto.setPrecos(precos);
		
		produtoDao.salvar(produto);
		
		Produto produtoTest = produtoDao.obter(produtoId);
		
		assertEquals("CPS", produtoTest.getDescricao());
		assertEquals(2, produtoTest.getPrecos().size());
		assertSame(produto, produtoTest);
		assertFalse(produtoTest.getPrecos().isEmpty());
		assertEquals(produtoId, produtoTest.getPrecos().iterator().next().getProduto().getId());
	}
	
}
