package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.entities.Rede;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class RedeDao extends DaoGeneric<Rede, Integer> {

	public RedeDao(Class<Rede> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<Rede> obterPorNome(String nomeRede) throws HibernateException, CpsDaoException {
		return getSession().createCriteria(Rede.class).add(Restrictions.eq("nome", nomeRede)).list();
	}

}
