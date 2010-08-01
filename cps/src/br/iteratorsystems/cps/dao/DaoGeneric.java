package br.iteratorsystems.cps.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.exceptions.CpsDaoException;


public class DaoGeneric<T, ID extends Serializable> implements InterfaceDao<T, ID> {

	protected Class<T> persistenClass;
	protected String idColumn;
	Session session = null;
	
	/**
	 * 
	 * @param persistentClass
	 * @param session
	 */
	
	public DaoGeneric(final Class<T> persistentClass, final Session session) {
		this.persistenClass = persistentClass;
		this.session = session;
	}
	
	/**
	 * Metodo Excluir
	 */
	
	public void excluir(T entity) throws CpsDaoException{
		getSession().delete(entity);
	}
	
	/**
	 * Metodo Excluir Lista
	 */

	public void excluirLista(final Collection<T> lista) throws CpsDaoException{
		for (final T entity : lista) {
			excluir(entity);
		}
	}
	
	/**
	 * 
	 */

	public Class<T> getPersistentClass() throws CpsDaoException{
		return persistenClass;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<T> listarPorIds(final Collection<ID> ids) throws CpsDaoException{
		final Criteria criteria = getSession().createCriteria(getPersistentClass());
		
		criteria.add(Restrictions.in(idColumn, ids));
		return criteria.list();
	}
	
	/**
	 * 
	 */

	@SuppressWarnings("unchecked")
	public List<T> listarTodos() throws CpsDaoException{
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		return criteria.list();
	}
	
	/**
	 * 
	 */

	@SuppressWarnings("unchecked")
	public List<T> listarTodosOrdenados(Order... order) throws CpsDaoException {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		
		for (Order ordem : order) {
			criteria.addOrder(ordem);
		}
		
		return criteria.list();
	}
	
	/**
	 * 
	 */

	@SuppressWarnings("unchecked")
	public T obter(ID id) throws CpsDaoException{
		return (T) getSession().get(getPersistentClass(), id);
	}
	
	/**
	 * Metodo
	 */

	public void salvar(T entity) throws CpsDaoException{
		getSession().saveOrUpdate(entity);
	}
	
	/**
	 * Metodo Salvar Lista
	 */

	public void salvarLista(Collection<T> lista) throws CpsDaoException{
		for (T entity : lista) {
			salvar(entity);
		}
	}
	
	/**
	 * 
	 */

	public T salvarOrUpdate(T entity) throws CpsDaoException{
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	/**
	 * Metodo Atualiza
	 */

	public void update(T entity) throws CpsDaoException{
		getSession().merge(entity);
	}
	
	/**
	 * 
	 * @return
	 * @throws CpsDaoException
	 */
	
	public Session getSession() throws CpsDaoException{
		return session;
	}

}
