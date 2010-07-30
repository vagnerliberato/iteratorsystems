package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.LISTAPRODUTO;

public class ListaProdutoDao extends DaoGeneric<LISTAPRODUTO, Integer> {

	public ListaProdutoDao(Class<LISTAPRODUTO> persistentClass, Session session) {
		super(persistentClass, session);
	}

}
