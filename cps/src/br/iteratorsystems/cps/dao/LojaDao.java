package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.entities.LOJA;
import br.iteratorsystems.cps.entities.LOJAID;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class LojaDao extends DaoGeneric<LOJA, LOJAID> {

	public LojaDao(Class<LOJA> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<LOJA> obterLojaPorNomeFantasia(String nomefantasia) throws HibernateException, CpsDaoException {
		return getSession().createCriteria(LOJA.class).add(Restrictions.eq("nomefantasia", nomefantasia)).list();
	}

}
