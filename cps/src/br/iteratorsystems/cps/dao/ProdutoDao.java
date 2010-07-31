package br.iteratorsystems.cps.dao;

import org.hibernate.Session;


import br.iteratorsystems.cps.entities.Tabelas_Produto;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoId;

public class ProdutoDao extends DaoGeneric<Tabelas_Produto, Tabelas_ProdutoId> {

	public ProdutoDao(Class<Tabelas_Produto> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
