/**
 * 
 */
package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.Parametrizacao;

/**
 * @author Robson
 *
 */
public class ParametrizacaoDao extends DaoGeneric<Parametrizacao, Integer> {
	public ParametrizacaoDao(Class<Parametrizacao> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
