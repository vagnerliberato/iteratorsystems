package br.iteratorsystems.cps.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.entities.Cep;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.exceptions.FindAddressException;
import br.iteratorsystems.cps.handler.BuscarCepBaseServiceImpl;
import br.iteratorsystems.cps.helper.FormatadorEstadorHelper;
import br.iteratorsystems.cps.helper.FormatadorPadraoStringHelper;

/**
 * Classe que faz a comunicação com o webService de Cep. No momento está feita para interagir com 
 * 2 tipos de webServices diferentes, um do site www.buscarcep.com.br e o outro do site cep.republicavirtual.com.br
 * Por enquanto ficará assim com os dois. Assim que as chamadas de cep á nossa base de dados estiver funcionando,
 * o www.buscarcep.com.br será retirado.
 * @author André
 *
 */
public class FindAddress {

	protected String logradouro;
	protected String bairro;
	protected String cidade;
	protected String pais;
	protected String estado;
	protected String estadoSigla;

	private static final Log log = LogFactory.getLog(FindAddress.class);
	
	private static final String baseUrl = "http://cep.republicavirtual.com.br/web_cep.php";
	private static final String PAIS = "BRASIL";

	/**
	 * Busca os dados de um cep no web service, com base no cep digitado pelo usuário.
	 * @param cep - Digitado pelo usuário
	 * @throws FindAddressException - Endereço não encontrado ou web service indisponível.
	 */
	private void findByWebService(String cep) throws FindAddressException{
		HttpURLConnection connection = null;
		StringBuilder incomingData = null;
		StringBuilder finalUrl = new StringBuilder(baseUrl);
		finalUrl.append("?cep=" + cep);
		finalUrl.append("&formato=xml");

		try {
			URL url = new URL(finalUrl.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Request-Method", "GET");

			connection.setDoInput(true);
			connection.setDoOutput(false);

			new ObterProxyHelper().createProxy(connection);

			connection.connect();
		} catch (Exception e) {
			throw new FindAddressException(e);
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			incomingData = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null) {
				incomingData.append(s);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String semHtml = "";
		if(incomingData != null){
			Pattern p = Pattern.compile("<.*?>");
			Matcher m = p.matcher(incomingData.toString());
			semHtml = m.replaceAll(" ");
		}
		
		String[] resultado = semHtml.split("  ");
		
		if(resultado.length!=8){
			throw new FindAddressException("Cep não encontrado no Web Service");
		}else {
			this.setEstadoSigla(resultado[3]);
			this.setEstado(FormatadorEstadorHelper.recuperaEstado(this.getEstadoSigla()));
			this.setCidade(resultado[4]);
			this.setBairro(resultado[5]);
			this.setLogradouro(resultado[6] + " " + resultado[7]);
			this.setPais(PAIS);
		}
	}

	/**
	 * Encontra o Cep, ou no web service, ou na base local.
	 * A prioridade é o web service por ser mais rápido, mas se o mesmo não estiver disponível ou ainda
	 * o cep não for encontrado, faz a busca na base local.
	 * @param cep - Cep
	 * @return Se houve sucesso na busca
	 */
	public boolean find(String cep) {
		boolean sucesso = true;
		final String cepModificado = cep.replace("-","");
		try{
			this.findByDataBase(cepModificado);
			log.debug("cep encontrado na base "+cepModificado);
		}catch (CpsHandlerException e) {
			try {
				this.findByWebService(cepModificado);
				log.debug("cep encontrado no web service " + cepModificado);
			} catch (FindAddressException e1) {
				log.error("erro ao buscar cep" + e1.getMessage());
				sucesso = false;
			}
		}
		return sucesso;
	}
	
	/**
	 * Busca os dados de cep na base de dados se o serviço web estiver indisponível,
	 * ou o cep não for encontrado no mesmo.
	 * @param cep - Cep a procurar.
	 * @throws CpsHandlerException 
	 */
	private void findByDataBase(String cep) throws CpsHandlerException {
		BuscarCepBaseServiceImpl cepService = new BuscarCepBaseServiceImpl();
		Cep tabelasCep = null;
		tabelasCep = cepService.buscarCep(cep);

		if (tabelasCep != null) {
			this.setBairro(FormatadorPadraoStringHelper
					.formataStringEntrada(tabelasCep.getBairro1()));
			this.setCidade(FormatadorPadraoStringHelper
					.formataStringEntrada(tabelasCep.getLocalidade()
							.getLocalidade()));
			this.setLogradouro(FormatadorPadraoStringHelper
					.formataStringEntrada(tabelasCep.getLogradouro()));
			this.setEstadoSigla(tabelasCep.getUf());
			this.setEstado(FormatadorEstadorHelper.recuperaEstado(tabelasCep
					.getUf()));
			this.setPais(PAIS);
		}
	}

	/**
	 * @return the rua
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param rua
	 *            the rua to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the estadoSigla
	 */
	public String getEstadoSigla() {
		return estadoSigla;
	}

	/**
	 * @param estadoSigla the estadoSigla to set
	 */
	public void setEstadoSigla(String estadoSigla) {
		this.estadoSigla = estadoSigla;
	}
}
