package br.iteratorsystems.cps.test;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.Test;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ProdutoGeralDao;
import br.iteratorsystems.cps.entities.ProdutoGeral;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoGeralDaoTest {

	@Test
	public void deveRetornaOMesmoProdutoGeralSalvo() throws CpsDaoException{
		
		ProdutoGeral produtogeral = new ProdutoGeral();
		produtogeral.setCodigoBarras("000011112222");
		produtogeral.setDescricao("Bolha");
		produtogeral.setEmbalagem("5");
		produtogeral.setUnidadeMedida("KG");
		
		Session session = HibernateConfig.getSession();
		ProdutoGeralDao produtoGeralDao = new ProdutoGeralDao(ProdutoGeral.class, session);
		produtoGeralDao.salvar(produtogeral);
		
		ProdutoGeral produto = produtoGeralDao.obter("000011112222");
		
		assertEquals("Bolha", produto.getDescricao());
		assertEquals("5", produto.getEmbalagem());
		assertEquals("KG", produto.getUnidadeMedida());
	}
}
