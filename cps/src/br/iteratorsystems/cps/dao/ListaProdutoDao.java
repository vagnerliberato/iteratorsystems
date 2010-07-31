package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;

public class ListaProdutoDao extends DaoGeneric<Tabelas_ListaProduto, Integer> {

	public ListaProdutoDao(Class<Tabelas_ListaProduto> persistentClass, Session session) {
		super(persistentClass, session);
	}

}
