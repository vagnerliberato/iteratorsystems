package br.iteratorsystems.cps.motor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.enums.TipoDeComparacao;
import br.iteratorsystems.cps.exceptions.CpsComparacaoException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.interfaces.MotorComparacao;
import br.iteratorsystems.cps.motor.impl.MercadoBuscaImpl;
import br.iteratorsystems.cps.motor.impl.ProdutoBuscaImpl;
import br.iteratorsystems.cps.to.DadosGeograficosTO;
import br.iteratorsystems.cps.to.MercadoTO;
import br.iteratorsystems.cps.to.ProdutoTO;
import br.iteratorsystems.cps.to.ResultadoComparacaoTO;
import br.iteratorsystems.cps.to.ResultadoProdutoMercadoTO;

/**
 * Classe Core do sistema, responsável por fazer todo o tipo de buscas.
 * @author André
 *
 */
public final class MotorComparacaoImpl implements MotorComparacao{

	/**
	 * serial
	 */
	private static final long serialVersionUID = 5831446769272766501L;
	
	/**
	 * log
	 */
	private static final Log log = LogFactory.getLog(MotorComparacaoImpl.class);

	final TipoDeComparacao tipoDeComparacao;
	final Integer quantidadeMaximaMercados;
	final List<ProdutoTO> listaDeProdutos;
	final MercadoBuscaImpl mercadoBuscaImpl;
	final ProdutoBuscaImpl produtoBuscaImpl;
	final String ufUsuario;
	final String cepUsuario;
	final Double valorCombustivel;
	final Double rendimentoCombustivel;
	
	/**
	 * Construtor
	 * @param listaDeProdutos - Lista de produtos TO.
	 * @param tipoDeComparacao - tipo de comparacao
	 * @param ufUsuario
	 * @param quantidadeMaximaMercados
	 * @param valorCombustivel
	 * @param rendimentoCombustivel
	 * @param cepUsuario
	 */
	public MotorComparacaoImpl(List<ProdutoTO>listaDeProdutos ,
							  TipoDeComparacao tipoDeComparacao,
							  String ufUsuario,
							  Integer quantidadeMaximaMercados,
							  Double valorCombustivel,
							  Double rendimentoCombustivel,
							  String cepUsuario) {
		log.trace("construtor");
		this.listaDeProdutos = listaDeProdutos;
		this.tipoDeComparacao = tipoDeComparacao;
		this.ufUsuario = ufUsuario;
		this.quantidadeMaximaMercados = quantidadeMaximaMercados;
		this.valorCombustivel = valorCombustivel;
		log.debug("valorCombustivel: "+this.valorCombustivel);
		this.rendimentoCombustivel = rendimentoCombustivel;
		log.debug("rendimentoCombustivel: "+this.rendimentoCombustivel);
		this.cepUsuario = cepUsuario;
		mercadoBuscaImpl = new MercadoBuscaImpl();
		produtoBuscaImpl  = new ProdutoBuscaImpl();
	}
	
	private List<MercadoTO> buscarProduto(List<Integer> listaCodigoProduto) {
		return null;
	}

	/**
	 * Calcula o menor preço da lista de compras com base nos mercados selecionados.
	 * @param listaMercadoTO - lista de mercado TO
	 * @param listaProdutos - lista de produtos
	 * @return Resultado comparacao TO
	 * @throws CpsExceptions Se ocorrer algum erro nas camadas abaixo
	 */
	private List<ResultadoComparacaoTO> calcularMenorPreco(
			List<MercadoTO> listaMercadoTO, List<ProdutoTO> listaProdutos)
			throws CpsExceptions {
		log.trace("calcular Menor preço iniciado");
		
		List<ResultadoComparacaoTO> listaComparacao = new ArrayList<ResultadoComparacaoTO>();
		List<ResultadoProdutoMercadoTO> listaResultadoTO = produtoBuscaImpl
							.obterListaDeProdutosPorMercado(listaMercadoTO, listaProdutos);
		DadosGeograficosTO dadosTO = 
				mercadoBuscaImpl.obterLatitudeLongitude(this.cepUsuario);
		
		int index = 0;
		for(ResultadoProdutoMercadoTO resultadoTO : listaResultadoTO) {
			for(MercadoTO mercadoTO : listaMercadoTO) {
				if(mercadoTO.getCodigo().compareTo(resultadoTO.getCodigoMercado()) == 0) {
					ResultadoComparacaoTO resultadoComparacao = new ResultadoComparacaoTO(); 
					resultadoComparacao.setPosicao(++index);
					resultadoComparacao.setCodigoMercado(resultadoTO.getCodigoMercado());
					resultadoComparacao.setCodigoRede(resultadoTO.getCodigoRede());
					resultadoComparacao.setCep(mercadoTO.getCep());
					resultadoComparacao.setLatitude(mercadoTO.getLatitude());
					resultadoComparacao.setLongitude(mercadoTO.getLongitude());
					resultadoComparacao.setListaEncontrados(resultadoTO.getListaEncontrados());
					resultadoComparacao.setListaNaoEncontrados(resultadoTO.getListaNaoEncontrados());
					resultadoComparacao.setValorDeslocamento(
							obterValorDeslocamento(mercadoTO.getDistanciaAproximada(),
									this.valorCombustivel,
									this.rendimentoCombustivel));
					resultadoComparacao.setDistanciaUsuario(
							Double.parseDouble(mercadoTO.getDistanciaAproximada().toString()));
					resultadoComparacao.setValorTotalLista(resultadoTO.getValorTotalLista());
					resultadoComparacao.setNomeMercado(mercadoTO.getNome());
					resultadoComparacao.setLongitudeUsusario(dadosTO.getLongitude());
					resultadoComparacao.setLatitudeUsusario(dadosTO.getLatitude());
					listaComparacao.add(resultadoComparacao);
				}
			}
		}
		log.trace("calcular Menor preço finalizado");
		return listaComparacao;
	}

	/**
	 * Obtém o valor do deslocamento do usuario ao mercado
	 * @param distanciaAproximada - distancia aproximada do mercado ao usuario
	 * @param valorCombustivel - valor combustivel
	 * @param rendimentoCombustivel - rendimento do combustivel
	 * @return Valor do deslocamento
	 */
	private Double obterValorDeslocamento(BigDecimal distanciaAproximada,
			Double valorCombustivel, Double rendimentoCombustivel) {
		log.trace("obtendo valor do deslocamento");
		Double valorDeslocamento = null;
		final Double distanciaAproximadaDouble = Double
				.parseDouble(distanciaAproximada.toString());
		valorDeslocamento = (distanciaAproximadaDouble * valorCombustivel)
				/ rendimentoCombustivel;
		log.debug(valorDeslocamento);
		return valorDeslocamento;
	}

	/**
	 * Obtem a lista de mercados mais proxima do usuario
	 * @param cepUsuario - uf do usuario
	 * @return Lista de mercados.
	 */
	private List<MercadoTO> obterListaMercadoMaisProximo(String ufUsuario) throws CpsComparacaoException{
		log.trace("obterListaMercadoMaisProximo");
		List<MercadoTO> listaMercado = null;
		try {
			 listaMercado = 
				 	mercadoBuscaImpl.obterListaMercadoMaisProximo(ufUsuario);
			 log.debug(listaMercado);
		} catch (CpsDaoException e) {
			log.error(e.getMessage());
			throw new CpsComparacaoException(e);
		}
		return listaMercado;
	}

	private List<ResultadoComparacaoTO> obterMenorCustoBeneficio() {
		return null;
	}

	/**
	 * Método responsável por efetuar a comparação dos produtos e mercados.
	 * @return Lista Com o resultado da comparação
	 * @throws Se ocorrer algum erro.
	 */
	public List<ResultadoComparacaoTO> comparar() throws CpsComparacaoException {
		log.trace("Comparar!");
		List<ResultadoComparacaoTO> resultadoComparacao = null;
		List<MercadoTO> listaMercado = obterListaMercadoMaisProximo(ufUsuario);
		List<MercadoTO> listaFiltrada = new ArrayList<MercadoTO>(this
				.obterFiltroMercadoMaisProximo(listaMercado));

		if (tipoDeComparacao == TipoDeComparacao.MENOR_PRECO) {
			try {
				resultadoComparacao = this.calcularMenorPreco(listaFiltrada,
						listaDeProdutos);
				log.debug("resultado por menor preço "+resultadoComparacao);
			} catch (CpsExceptions e) {
				log.error(e.getMessage());
				throw new CpsComparacaoException(e);
			}
		} else if (tipoDeComparacao == TipoDeComparacao.MENOR_DISTANCIA) {
			try{
				resultadoComparacao = this.calcularMenorPreco(listaFiltrada, listaDeProdutos);
				Collections.sort(resultadoComparacao);
				int index = 0;
				for(ResultadoComparacaoTO resultado : resultadoComparacao) {
					resultado.setPosicao(++index);
				}
			}catch (CpsExceptions e) {
				log.error(e.getMessage());
				throw new CpsComparacaoException(e);
			}
		} else {
			// TODO
		}
		log.trace("Fim Comparação");
		return resultadoComparacao;
	}

	/**
	 * Obtem o filtro dos mercados mais próximos do usuário
	 * @param listaMercado - lista de mercado
	 * @return Lista de mercado TO.
	 * @throws CpsComparacaoException Se ocorrer algum erro. 
	 */
	private List<MercadoTO> obterFiltroMercadoMaisProximo(List<MercadoTO> listaMercado) throws CpsComparacaoException{
		List<MercadoTO> listaMercadoFiltro = new ArrayList<MercadoTO>();
		log.trace("obterFiltroMercadoMaisProximo");
		try {
			listaMercadoFiltro =
					mercadoBuscaImpl.obterFiltroMercadoMaisProximo(listaMercado,quantidadeMaximaMercados,cepUsuario);
		} catch (CpsDaoException e) {
			log.error(e.getMessage());
			throw new CpsComparacaoException(e);
		}
		log.debug(listaMercadoFiltro);
		return listaMercadoFiltro;
	}
}
