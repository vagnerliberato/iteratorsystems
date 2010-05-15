package br.iteratorsystems.cps.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Order;

public interface InterfaceDao<T, ID extends Serializable> {

	public abstract Class<T> getPersistentClass();
	
	public abstract T obter(ID id);
	
	public abstract List<T> listarTodos();
	
	public abstract List<T> listarTodosOrdenados(Order... order);
	
	public abstract void salvar(T entity);
	
	public abstract T salvarOrUpdate(T entity);
	
	public abstract void salvarLista(Collection<T> lista);
	
	public abstract void excluir(T entity);
	
	public abstract void excluirLista(Collection<T> lista);
	
	public abstract List<T> listarPorIds(Collection<ID> ids);
	
	public abstract void update(T entity);
}
