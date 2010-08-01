package br.iteratorsystems.cps.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.entities.Tabelas_Cep;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;

/**
 * Classe de serviço que busca um cep na base de dados do cps.
 * @author André
 *
 */
public class BuscarCepBaseServiceImpl {
	
	private static final Log log = LogFactory.getLog(BuscarCepBaseServiceImpl.class);
	
	public Tabelas_Cep buscarCep(String cep) throws CpsHandlerException{
		log.debug("buscando na base o cep "+cep);
		Tabelas_Cep cepObj = null;
//		try{
//			
//			
//		}catch (CpsHandlerException e) {
//			log.error("erro ao buscar cep na base "+e);
//			throw new CpsHandlerException(e);
//		}
		return cepObj;
	}
}
