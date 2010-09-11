package br.iteratorsystems.cps.motor;

import java.util.ArrayList;
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
import br.iteratorsystems.cps.to.MercadoTO;
import br.iteratorsystems.cps.to.ProdutoTO;
import br.iteratorsystems.cps.to.ResultadoComparacaoTO;

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
		this.rendimentoCombustivel = rendimentoCombustivel;
		this.cepUsuario = cepUsuario;
		mercadoBuscaImpl = new MercadoBuscaImpl();
		produtoBuscaImpl  = new ProdutoBuscaImpl();
	}
	
	private List<MercadoTO> buscarProduto(List<Integer> listaCodigoProduto) {
		return null;
	}

	private List<ResultadoComparacaoTO> calcularMenorPreco(
			List<MercadoTO> listaMercadoTO, List<ProdutoTO> listaProdutos) throws CpsExceptions {
		log.trace("calcular Menor preço iniciado");
		
		produtoBuscaImpl.obterListaDeProdutosPorMercado(
				listaMercadoTO, listaProdutos);
		
		log.trace("calcular Menor preço finalizado");
		return null;
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
		List<MercadoTO> listaMercado = 
					obterListaMercadoMaisProximo(ufUsuario);
		List<MercadoTO> listaFiltrada = 
			new ArrayList<MercadoTO>(this.obterFiltroMercadoMaisProximo(listaMercado));
		
		if(tipoDeComparacao == TipoDeComparacao.MENOR_PRECO) {
			try {
				this.calcularMenorPreco(listaFiltrada,listaDeProdutos);
			} catch (CpsExceptions e) {
				throw new CpsComparacaoException(e);
			}
		}else if(tipoDeComparacao == TipoDeComparacao.MENOR_DISTANCIA) {
			//TODO
		}else {
			//TODO
		}
		log.trace("Fim Comparação");
		return null;
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
