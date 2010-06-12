package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.PRODUTOGERAL;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class testeRobson {

	/**
	 * @param args
	 * @throws CpsDaoException 
	 * @throws CpsDaoException 
	 * @throws HibernateException 
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws CpsDaoException {

		HibernateConfig config = new HibernateConfig() {};
		Session session = config.getSession();
		ProdutoGeralDao dao = new ProdutoGeralDao(PRODUTOGERAL.class, session);
		
		List<PRODUTOGERAL> produtogerals = dao.obterProduto("SOLUVEL MATINAL", 1, 30);
		for (PRODUTOGERAL produtogeral : produtogerals) {
			System.out.println(produtogeral.getDescricao());
		}
	}

}
