package br.iteratorsystems.cps.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Cep;
import br.iteratorsystems.cps.entities.Localidade;
import br.iteratorsystems.cps.exceptions.CpsDaoException;

/**
 * Classe de busca de Cep na base de dados do Cps. 
 * @author Andr�
 *
 */
public class BuscarCepBaseDao {

	private static final Log log = LogFactory.getLog(BuscarCepBaseDao.class);
	
	/**
	 * Busca um cep na base de dados.
	 * @param cep - Cep
	 * @return - Objeto da Tabela de Cep populado.
	 * @throws CpsDaoException - Se ocorrer alguma exce��o.
	 */
	public Cep buscarCep(final String cep) throws CpsDaoException {
		log.debug("buscando cep no dao "+cep);
		String dadosCep = null;
		Connection con = HibernateConfig.getConnection();
		try{
			CallableStatement cs = con.prepareCall("select tabelas.sp_listarcep (?)");
			cs.setString(1,cep);
			ResultSet rs = cs.executeQuery();

			if(rs.next()) {
				dadosCep = rs.getString(1);
			}else{
				throw new CpsDaoException("cep n�o encontrado!");
			}
		}catch (SQLException e) {
			log.error("erro ao buscar o cep "+e);
			throw new CpsDaoException(e);
		}
		
		return converteObjetoCep(dadosCep);
	}
	
	/**
	 * Converte o cep da base em um objeto Cep
	 * @param dadosCep - Dados do Cep retornado da base.
	 * @return Objeto Cep populado.
	 */
	private Cep converteObjetoCep(String dadosCep) {
		String [] partesCep = dadosCep.split(",");
		
		Cep tabelasCep = new Cep();
		Localidade localidade = new Localidade();
		
		tabelasCep.setLogradouro(limparStringDados(partesCep[0]));
		tabelasCep.setBairro1(limparStringDados(partesCep[1]));
		localidade.setLocalidade(limparStringDados(partesCep[2]));
		tabelasCep.setLocalidade(localidade);
		tabelasCep.setUf(limparStringDados(partesCep[3]));
		tabelasCep.setCep(limparStringDados(partesCep[4]));
		
		return tabelasCep;
	}
	
	/**
	 * Limpa os dados n�o utilizados da string.
	 * @param partesCep - dados do cep.
	 * @return String limpa.
	 */
	private String limparStringDados(String partesCep) {
		StringBuilder builder = new StringBuilder();
		builder.append(partesCep.replaceAll("[\"^(^)]",""));
		return builder.toString();
	}
}