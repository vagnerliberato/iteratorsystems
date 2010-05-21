package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.REDE;

public class RedeDao extends DaoGeneric<REDE, Integer> {

	public RedeDao(Class<REDE> persistentClass, Session session) {
		super(persistentClass, session);
	}

}
