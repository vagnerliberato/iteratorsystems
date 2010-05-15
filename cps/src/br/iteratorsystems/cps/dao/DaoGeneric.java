package br.iteratorsystems.cps.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


public class DaoGeneric<T, ID extends Serializable> implements InterfaceDao<T, ID> {

	protected Class<T> persistenClass;
	protected String idColumn;
	Session session = null;
	
	public DaoGeneric(final Class<T> persistentClass, final Session session) {
		this.persistenClass = persistentClass;
		this.session = session;
	}
	
	public void excluir(T entity) {
		getSession().delete(entity);
	}

	public void excluirLista(final Collection<T> lista) {
		for (final T entity : lista) {
			excluir(entity);
		}
	}

	public Class<T> getPersistentClass() {
		return persistenClass;
	}
	@SuppressWarnings("unchecked")
	public List<T> listarPorIds(final Collection<ID> ids) {
		final Criteria criteria = getSession().createCriteria(getPersistentClass());
		
		criteria.add(Restrictions.in(idColumn, ids));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> listarTodos() {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> listarTodosOrdenados(Order... order) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		
		for (Order ordem : order) {
			criteria.addOrder(ordem);
		}
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public T obter(ID id) {
		return (T) getSession().get(getPersistentClass(), id);
	}

	public void salvar(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void salvarLista(Collection<T> lista) {
		for (T entity : lista) {
			salvar(entity);
		}
	}

	public T salvarOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void update(T entity) {
		getSession().update(entity);
	}
	
	public Session getSession(){
		return session;
	}

}
