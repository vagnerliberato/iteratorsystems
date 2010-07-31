package br.iteratorsystems.cps.interfaces;

import java.util.Collection;
import java.util.List;

import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_Rede;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsConstraintException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

public interface IDao<T extends EntityAble> {

	Integer save(T instance) throws CpsDaoException, CpsConstraintException;

	T get(T instance) throws CpsDaoException;
	
	Integer getIdUsuario(Tabelas_Usuario instance) throws CpsDaoException;
	
	Integer getLastIdFrom(EntityAble entity) throws CpsDaoException;
	
	Integer getLastIdFromModel(EntityAble entity) throws CpsDaoException;

	Collection<T> getAll(Object type) throws CpsDaoException;
	
	List<Tabelas_Login> getAllLogin(String username) throws CpsDaoException;
	
	List<Tabelas_Rede> getRedesByName(String nome) throws CpsDaoException;
	
	List<Tabelas_Loja> getLojasByName(String nome) throws CpsDaoException;

	void update(T instance) throws CpsDaoException, CpsConstraintException;
	
	void delete(T instance) throws CpsDaoException, CpsConstraintException;
	 
	Tabelas_Rede getRede(String nome) throws CpsDaoException;

}
