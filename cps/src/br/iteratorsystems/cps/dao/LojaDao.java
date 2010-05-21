package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.LOJA;
import br.iteratorsystems.cps.entities.LOJAID;

public class LojaDao extends DaoGeneric<LOJA, LOJAID> {

	public LojaDao(Class<LOJA> persistentClass, Session session) {
		super(persistentClass, session);
	}

}
