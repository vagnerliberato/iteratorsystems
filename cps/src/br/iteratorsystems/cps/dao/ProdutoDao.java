package br.iteratorsystems.cps.dao;

import org.hibernate.Session;


import br.iteratorsystems.cps.entities.PRODUTO;
import br.iteratorsystems.cps.entities.PRODUTOID;

public class ProdutoDao extends DaoGeneric<PRODUTO, PRODUTOID> {

	public ProdutoDao(Class<PRODUTO> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
