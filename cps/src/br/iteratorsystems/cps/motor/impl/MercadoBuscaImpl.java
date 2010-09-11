package br.iteratorsystems.cps.motor.impl;

import java.io.Serializable;
import java.util.List;

import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.service.MercadoBuscaServiceImpl;
import br.iteratorsystems.cps.to.MercadoTO;

/**
 * Classe de busca de mercados
 * @author André
 *
 */
public class MercadoBuscaImpl implements Serializable{

	/**
	 * serial
	 */
	private static final long serialVersionUID = -7493866334593937779L;

	private MercadoBuscaServiceImpl mercadoBuscaServiceImpl;
	
	/**
	 * Construtor
	 */
	public MercadoBuscaImpl() {
		mercadoBuscaServiceImpl = new MercadoBuscaServiceImpl();
	}
	
	/**
	 * Obtem a lista de mercados mais proxima do usuario
	 * @param cepUsuario - cep do usuario
	 * @return Lista de mercados.
	 * @throws CpsDaoException - Se ocorrer algum erro
	 */
	public List<MercadoTO> obterListaMercadoMaisProximo(String ufUsuario) throws CpsDaoException {
		return mercadoBuscaServiceImpl.obterListaMercadoMaisProximo(ufUsuario);
	}

	/**
	 * Obtem o lista de mercados com base no filtro
	 * @param listaMercado - Lista de mercado
	 * @param quantidadeMaximaMercados - Quantidade maxima de mercados a pesquisar
	 * @param cepUsuario - Cep do usuario
	 * @return Lista de mercado - Lista de mercados
	 * @throws CpsDaoException - Se ocorrer algum erro
	 */
	public List<MercadoTO> obterFiltroMercadoMaisProximo(List<MercadoTO> listaMercado,Integer quantidadeMaximaMercados,String cepUsuario) throws CpsDaoException {
		return mercadoBuscaServiceImpl.obterFiltroMercadoMaisProximo(listaMercado,quantidadeMaximaMercados,cepUsuario);
	}
}
