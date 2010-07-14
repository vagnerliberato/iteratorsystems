package br.iteratorsystems.cps.test;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.Test;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.ProdutoGeralDao;
import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class ProdutoDaoTest {

	@Test
	public void deveRetornaOMesmoProdutoSalvo() throws CpsDaoException{
		
		PRODUTOGERAL produtogeral = new PRODUTOGERAL();
		produtogeral.setCodigoBarras("000011112222");
		produtogeral.setDescricao("Bolha");
		produtogeral.setEmbalagem("5");
		produtogeral.setUnidadeMedida("KG");
		
		Session session = HibernateConfig.getSession();
		ProdutoGeralDao produtoGeralDao = new ProdutoGeralDao(PRODUTOGERAL.class, session);
		produtoGeralDao.salvar(produtogeral);
		
		PRODUTOGERAL produto = produtoGeralDao.obter("000011112222");
		
		assertEquals("Bolha", produto.getDescricao());
		assertEquals("5", produto.getEmbalagem());
		assertEquals("KG", produto.getUnidadeMedida());
	}
}
