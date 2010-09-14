package br.iteratorsystems.cps.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.helper.ProcedureHelper;
import br.iteratorsystems.cps.to.ProdutoBuscaTO;
import br.iteratorsystems.cps.to.ProdutoTO;
import br.iteratorsystems.cps.to.ResultadoProdutoMercadoTO;

/**
 * Classe DAO de busca de produtos
 * @author André
 *
 */
public class ProdutoBuscaDAOImpl {

	/**
	 * Log
	 */
	private static final Log log = LogFactory.getLog(ProdutoBuscaDAOImpl.class);
	
	/**
	 * Obtem a lista de produtos encontrados e/ou não encontrados de produto de 
	 * acordo com o id do mercado e da rede.
	 * @param codigoMercado - codigo do mercado
	 * @param codigoRede - codigo da rede
	 * @param listaDeProdutosTO - lista de produtos TO
	 * @return Resultado da busca
	 * @throws CpsDaoException Se ocorrer algum erro
	 */
	public ResultadoProdutoMercadoTO obterListaDeProdutosPorMercado(
			Integer codigoMercado, Integer codigoRede,
			List<ProdutoTO> listaDeProdutosTO) throws CpsDaoException {
		
		ResultadoProdutoMercadoTO resultado = new ResultadoProdutoMercadoTO();

		for (ProdutoTO produtoTO : listaDeProdutosTO) {
			ProdutoBuscaTO buscaTO = obterProduto(codigoMercado, codigoRede,produtoTO);
			resultado.setCodigoMercado(codigoMercado);
			resultado.setCodigoRede(codigoRede);
			
			if (buscaTO == null) {
				resultado.getListaNaoEncontrados().add(
						new ProdutoBuscaTO(Long.parseLong(produtoTO
								.getProdutoGeral().getCodigoBarras().trim()),
								produtoTO.getProdutoGeral().getDescricao(),
								produtoTO.getQuantidadeSelecionada()));
			}else{
				resultado.getListaEncontrados().add(buscaTO);
			}
		}
		return resultado;
	}
	
	/**
	 * Obtem um produto da procedure
	 * @param codigoMercado - codigo do mercado
	 * @param codigoRede - codigo da rede
	 * @param produto - produto
	 * @return Resultado da busca
	 * @throws CpsDaoException Se ocorrer algum erro
	 */
	private ProdutoBuscaTO obterProduto(Integer codigoMercado,Integer codigoRede,ProdutoTO produto) throws CpsDaoException {
		log.debug("obtendo produtos do mercado "+codigoMercado);
		ProdutoBuscaTO produtoBuscaTO = null;
		String resultado = "";
		Connection con = HibernateConfig.getConnection();
		
		try{
			con.setAutoCommit(false);
			CallableStatement cs = con.prepareCall("select tabelas.sp_listar_produto (?,?,?)");
			cs.setInt(1,codigoRede);
			cs.setInt(2,codigoMercado);
			cs.setString(3,produto.getProdutoGeral().getCodigoBarras().trim());
			ResultSet rs = cs.executeQuery();
			
			while(rs.next()) {
				resultado = ProcedureHelper.limparDadosString(rs.getString(1));
			}
			
			produtoBuscaTO = obterProdutoBuscaTO(resultado);
			
			if(produtoBuscaTO != null) {
				produtoBuscaTO.setQuantidade(produto.getQuantidadeSelecionada());
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new CpsDaoException(e);
		}
		return produtoBuscaTO;
	}

	/**
	 * Obtem o TO de busca de produto
	 * @param resultado - resultado da procedure
	 * @return Produto Busca TO
	 */
	private ProdutoBuscaTO obterProdutoBuscaTO(String resultado) {
		ProdutoBuscaTO produtoBuscaTO = null;
		if(resultado != null && !resultado.isEmpty()) {
			produtoBuscaTO = new ProdutoBuscaTO();
			String [] split = resultado.split("[,]");
			produtoBuscaTO.setCodigo(Long.parseLong(split[2].trim()));
			produtoBuscaTO.setDescricao(split[3]);
			produtoBuscaTO.setValor(Double.parseDouble(split[4]));
		}
		return produtoBuscaTO;
	}
}
