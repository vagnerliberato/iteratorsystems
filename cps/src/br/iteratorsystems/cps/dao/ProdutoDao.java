package br.iteratorsystems.cps.dao;

import org.hibernate.Session;


import br.iteratorsystems.cps.entities.Produto;
import br.iteratorsystems.cps.entities.ProdutoId;

public class ProdutoDao extends DaoGeneric<Produto, ProdutoId> {

	public ProdutoDao(Class<Produto> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
