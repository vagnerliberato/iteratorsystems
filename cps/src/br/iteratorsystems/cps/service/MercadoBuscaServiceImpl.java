package br.iteratorsystems.cps.service;

import java.util.List;

import br.iteratorsystems.cps.dao.MercadoBuscaDAOImpl;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.to.DadosGeograficosTO;
import br.iteratorsystems.cps.to.MercadoTO;

/**
 * Classe de serviço de busca de mercado
 * @author André
 *
 */
public class MercadoBuscaServiceImpl {

	private MercadoBuscaDAOImpl mercadoBuscaDAOImpl;
	
	/**
	 * Construtor
	 */
	public MercadoBuscaServiceImpl() {
		mercadoBuscaDAOImpl = new MercadoBuscaDAOImpl();
	}
	
	/**
	 * Obtem a lista de mercados mais proxima do usuario
	 * @param cepUsuario - uf do usuario
	 * @return Lista de mercados.
	 * @throws CpsDaoException - Se ocorre um erro.
	 */
	public List<MercadoTO> obterListaMercadoMaisProximo(String ufUsuario) throws CpsDaoException {
		return mercadoBuscaDAOImpl.obterListaMercadoMaisProximo(ufUsuario);
	}

	/**
	 * Obtem o lista de mercados com base no filtro
	 * @param listaMercado - Lista de mercado
	 * @param quantidadeMaximaMercados - Quantidade maxima de mercados a pesquisar
	 * @param cepUsuario - Cep do usuario
	 * @return Lista de mercado - Lista de mercados
	 * @throws CpsDaoException - Se ocorrer algum erro
	 */
	public List<MercadoTO> obterFiltroMercadoMaisProximo(List<MercadoTO> listaMercado, Integer quantidadeMaximaMercados,String cepUsuario) throws CpsDaoException{
		return mercadoBuscaDAOImpl.obterFiltroMercadoMaisProximo(listaMercado,quantidadeMaximaMercados,cepUsuario);
	}
	
	/**
	 * Obtem a latitude e longitude com base em um cep.
	 * @param cep - cep
	 * @return Latitude e longitude
	 * @throws CpsDaoException - Se ocorrer algum erro.
	 */
	public DadosGeograficosTO obterLatitudeLongitude(String cep) throws CpsDaoException{
		return mercadoBuscaDAOImpl.obterLatitudeLongitude(cep);
	}
}
