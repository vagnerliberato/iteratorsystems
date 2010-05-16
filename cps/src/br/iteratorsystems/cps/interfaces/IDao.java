package br.iteratorsystems.cps.interfaces;

import java.util.Collection;
import java.util.List;

import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.REDE;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsConstraintException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public interface IDao<T extends EntityAble> {

	Integer save(T instance) throws CpsDaoException, CpsConstraintException;

	T get(T instance) throws CpsDaoException;
	
	Integer getIdUsuario(USUARIO instance) throws CpsDaoException;
	
	Integer getLastIdFrom(EntityAble entity) throws CpsDaoException;
	
	Integer getLastIdFromModel(EntityAble entity) throws CpsDaoException;

	Collection<T> getAll(Object type) throws CpsDaoException;
	
	List<LOGIN> getAllLogin(String username) throws CpsDaoException;

	void update(T instance) throws CpsDaoException, CpsConstraintException;
	
	void updateUsuarioHQL(USUARIO instance) throws CpsDaoException,CpsConstraintException;

	void delete(T instance) throws CpsDaoException, CpsConstraintException;
	 
	REDE getRede(String nome) throws CpsDaoException;

}
