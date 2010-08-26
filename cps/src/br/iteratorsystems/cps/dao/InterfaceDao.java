package br.iteratorsystems.cps.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Order;

import br.iteratorsystems.cps.exceptions.CpsDaoException;

public interface InterfaceDao<T, ID extends Serializable> {

	public abstract Class<T> getPersistentClass() throws CpsDaoException;
	
	public abstract T obter(ID id) throws CpsDaoException;
	
	public abstract List<T> listarTodos() throws CpsDaoException;
	
	public abstract List<T> listarTodosOrdenados(Order... order) throws CpsDaoException;
	
	public abstract void salvar(T entity) throws CpsDaoException;
	
	public abstract T salvarOrUpdate(T entity) throws CpsDaoException;
	
	public abstract void salvarLista(Collection<T> lista) throws CpsDaoException;
	
	public abstract void excluir(T entity) throws CpsDaoException;
	
	public abstract void excluirLista(Collection<T> lista) throws CpsDaoException;
	
	public abstract List<T> listarPorIds(Collection<ID> ids) throws CpsDaoException;
	
	public abstract void update(T entity) throws CpsDaoException;
}
