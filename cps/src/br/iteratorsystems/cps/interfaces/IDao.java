package br.iteratorsystems.cps.interfaces;

import java.util.Collection;
import java.util.List;

import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Loja;
import br.iteratorsystems.cps.entities.Rede;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsConstraintException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public interface IDao<T extends EntityAble> {

	Integer save(T instance) throws CpsDaoException, CpsConstraintException;

	T get(T instance) throws CpsDaoException;
	
	Integer getIdUsuario(Usuario instance) throws CpsDaoException;
	
	Integer getLastIdFrom(EntityAble entity) throws CpsDaoException;
	
	Integer getLastIdFromModel(EntityAble entity) throws CpsDaoException;

	Collection<T> getAll(Object type) throws CpsDaoException;
	
	List<Login> getAllLogin(String username) throws CpsDaoException;
	
	List<Rede> getRedesByName(String nome) throws CpsDaoException;
	
	List<Loja> getLojasByName(String nome) throws CpsDaoException;

	void update(T instance) throws CpsDaoException, CpsConstraintException;
	
	void delete(T instance) throws CpsDaoException, CpsConstraintException;
	 
	Rede getRede(String nome) throws CpsDaoException;

}
