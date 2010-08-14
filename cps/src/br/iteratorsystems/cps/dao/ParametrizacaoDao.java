/**
 * 
 */
package br.iteratorsystems.cps.dao;

import org.hibernate.Session;

import br.iteratorsystems.cps.entities.Tabelas_Parametrizacao;

/**
 * @author Robson
 *
 */
public class ParametrizacaoDao extends DaoGeneric<Tabelas_Parametrizacao, Integer> {
	public ParametrizacaoDao(Class<Tabelas_Parametrizacao> persistentClass, Session session) {
		super(persistentClass, session);
	}
}
