package br.iteratorsystems.cps.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.entities.Loja;
import br.iteratorsystems.cps.entities.LojaId;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public class LojaDao extends DaoGeneric<Loja, LojaId> {

	public LojaDao(Class<Loja> persistentClass, Session session) {
		super(persistentClass, session);
	}

	@SuppressWarnings("unchecked")
	public List<Loja> obterLojaPorNomeFantasia(String nomefantasia) throws HibernateException, CpsDaoException {
		return getSession().createCriteria(Loja.class).add(Restrictions.ilike("nomefantasia", nomefantasia)).list();
	}

}
