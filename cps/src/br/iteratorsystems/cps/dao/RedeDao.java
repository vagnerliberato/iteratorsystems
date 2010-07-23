package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.entities.REDE;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class RedeDao extends DaoGeneric<REDE, Integer> {

	public RedeDao(Class<REDE> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<REDE> obterPorNome(String nomeRede) throws HibernateException, CpsDaoException {
		return getSession().createCriteria(REDE.class).add(Restrictions.eq("nome", nomeRede)).list();
	}

}
