package br.iteratorsystems.cps.test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ProdutoDao;
import br.iteratorsystems.cps.entities.Tabelas_Preco;
import br.iteratorsystems.cps.entities.Tabelas_PrecoId;
import br.iteratorsystems.cps.entities.Tabelas_Produto;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoId;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoDaoTest {

	@Test
	public void deveRetornarOMesmoProdutoSalvo() throws CpsDaoException{
		
		Session session = HibernateConfig.getSession();
		ProdutoDao produtoDao = new ProdutoDao(Tabelas_Produto.class, session);
		
		Tabelas_Produto produto = new Tabelas_Produto();
		produto.setDescricao("CPS");
		produto.setEmbalagem("10");
		Tabelas_ProdutoId produtoId = new Tabelas_ProdutoId("111122223333", 3, 5);
		produto.setId(produtoId);
		Set<Tabelas_Preco> precos = new HashSet<Tabelas_Preco>();
		Tabelas_PrecoId precoId = new Tabelas_PrecoId(5, 3, "111122223333");
		String precoVarejo1 = "2.55";
		Tabelas_Preco preco1= new Tabelas_Preco(precoId, produto, precoVarejo1);
		Tabelas_PrecoId precoId2 = new Tabelas_PrecoId(5, 3, "111122224444");
		String precoVarejo2 = "3.55";
		Tabelas_Preco preco2= new Tabelas_Preco(precoId2, produto, precoVarejo2);
		precos.add(preco1);
		precos.add(preco2);
		produto.setPrecos(precos);
		
		produtoDao.salvar(produto);
		
		Tabelas_Produto produtoTest = produtoDao.obter(produtoId);
		
		assertEquals("CPS", produtoTest.getDescricao());
		assertEquals(2, produtoTest.getPrecos().size());
		assertSame(produto, produtoTest);
		assertFalse(produtoTest.getPrecos().isEmpty());
		assertEquals(produtoId, produtoTest.getPrecos().iterator().next().getProduto().getId());
	}
	
}
