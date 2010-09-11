package br.iteratorsystems.cps.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.common.CalculoDeRota;
import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.enums.TipoResultado;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.helper.MercadoTOHelper;
import br.iteratorsystems.cps.helper.ProcedureHelper;
import br.iteratorsystems.cps.to.DadosGeograficosTO;
import br.iteratorsystems.cps.to.MercadoTO;

/**
 * Classe DAO de busca de mercado
 * @author André
 *
 */
public class MercadoBuscaDAOImpl {

	private static final Log log = LogFactory.getLog(MercadoBuscaDAOImpl.class);
	
	/**
	 * Obtem a lista de mercados mais proxima do usuario
	 * @param ufUsuario - uf do usuario
	 * @return Lista de mercados.
	 * @throws CpsDaoException - Se ocorrer algum erro.
	 */
	public List<MercadoTO> obterListaMercadoMaisProximo(String ufUsuario) throws CpsDaoException {
		List<String> listaProcedure = executarProcedure(ufUsuario);
		List<MercadoTO> listaMercadoTO = new ArrayList<MercadoTO>();
		
		for(String string : listaProcedure) {
			listaMercadoTO.add(
					MercadoTOHelper.obterMercadoTOProcedure(
							ProcedureHelper.limparDadosString(string)));
		}
		
		return listaMercadoTO;
	}
	
	/**
	 * Executa a procedure de busca de mercados.
	 * @param ufUsuario - Uf do usuario
	 * @return Lista de mercados encontrado
	 * @throws CpsDaoException Se ocorrer algum erro.
	 */
	private List<String> executarProcedure(String ufUsuario) throws CpsDaoException{
		log.debug("buscando mercado mais proximo "+ufUsuario);
		List<String> listaMercado = new ArrayList<String>();
		Connection con = HibernateConfig.getConnection();
		
		try{
			con.setAutoCommit(false);
			CallableStatement cs = con.prepareCall("select tabelas.sp_listar_lojas (?)");
			cs.setString(1,ufUsuario.trim());
			ResultSet rs = cs.executeQuery();

			while(rs.next()) {
				listaMercado.add(rs.getString(1));
			}
		}catch (SQLException e) {
			log.error("erro ao buscar o cep "+e);
			throw new CpsDaoException(e);
		}
		
		return listaMercado;
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
		log.debug("aplicando filtro de mercados mais proximo");
		List<MercadoTO> listaMercadoFiltro = new ArrayList<MercadoTO>();
		DadosGeograficosTO dadosGeograficosUsuario = 
									this.obterLatitudeLongitude(cepUsuario.replace("-",""));
		
		for(MercadoTO mercadoTO : listaMercado) {
			DadosGeograficosTO dadosTemp = 
			 		this.obterLatitudeLongitude(mercadoTO.getCep());
			mercadoTO.setLatitude(dadosTemp.getLatitude());
			mercadoTO.setLongitude(dadosTemp.getLongitude());
			listaMercadoFiltro.add(mercadoTO);
		}
		
		listaMercado.clear();
		listaMercado = aplicarFiltro(dadosGeograficosUsuario, listaMercadoFiltro, quantidadeMaximaMercados);
		return listaMercado;
	}

	/**
	 * Aplica o filtro de mercado
	 * @param dadosGeograficosTO - dados geograficos TO
	 * @param listaMercado - lista de mercados
	 * @param quantidadeMaxima - quantidade maixma de mercados
	 * @return Lista de mercado TO filtrada
	 */
	private List<MercadoTO> aplicarFiltro(DadosGeograficosTO dadosGeograficosTO,
										List<MercadoTO> listaMercado,
										Integer quantidadeMaxima) {
		
		List<MercadoTO> listaFiltrada = new ArrayList<MercadoTO>();
		Map<BigDecimal,MercadoTO> listaDistancia = new TreeMap<BigDecimal, MercadoTO>();
		
		for(MercadoTO mercadoTO : listaMercado) {
			if(mercadoTO.getLatitude() != null && 
					mercadoTO.getLongitude() != null) {
				
				listaDistancia.put(CalculoDeRota.calcularDistancia(
						Double.parseDouble(mercadoTO.getLatitude()),
						Double.parseDouble(mercadoTO.getLongitude()),
						Double.parseDouble(dadosGeograficosTO.getLatitude()),
						Double.parseDouble(dadosGeograficosTO.getLongitude()),
						TipoResultado.KILOMETROS),mercadoTO);
			}
		}
		
		BigDecimal[] menores = new BigDecimal[listaDistancia.size()];
		int index = 0;
		for(BigDecimal valorDistancia : listaDistancia.keySet()) {
			log.debug("valor distancia: "+valorDistancia);
			menores[index] = valorDistancia;
			index ++;
		}
		
		Arrays.sort(menores);
		
		for(int i = 0; i < quantidadeMaxima; i++) {
			listaFiltrada.add(listaDistancia.get(menores[i]));
		}
		
		return listaFiltrada;
	}
	
	/**
	 * Obtem a latitude e longitude com base em um cep.
	 * @param cep - cep
	 * @return Latitude e longitude
	 * @throws CpsDaoException - Se ocorrer algum erro.
	 */
	private DadosGeograficosTO obterLatitudeLongitude(String cep) throws CpsDaoException{
		log.debug("obtendo latitude e longitude com base no cep "+cep);
		DadosGeograficosTO dados = new DadosGeograficosTO();
		Connection con = HibernateConfig.getConnection();
		try{
			con.setAutoCommit(false);
			CallableStatement cs = con.prepareCall("select tabelas.sp_localizacao_geo (?)");
			cs.setString(1,cep.trim());
			ResultSet rs = cs.executeQuery();
			
			if(rs.next()) {
				dados = 
					obterLatitudeLongitudeTO(
							ProcedureHelper.limparDadosString(rs.getString(1)));
			}
		}catch (SQLException e) {
			log.error("erro ao buscar o cep "+e);
			throw new CpsDaoException(e);
		}
		return dados;
	}
	
	/**
	 * Obtem os dados geograficos TO com base na procedure
	 * @param entrada - entrada
	 * @return Dados geograficos TO.
	 */
	private DadosGeograficosTO obterLatitudeLongitudeTO(String entrada) {
		DadosGeograficosTO dadosTO = new DadosGeograficosTO();
		String [] split = entrada.split("[,]");
		dadosTO.setLatitude(split[1]);
		dadosTO.setLongitude(split[2]);
		return dadosTO;
	}
}
