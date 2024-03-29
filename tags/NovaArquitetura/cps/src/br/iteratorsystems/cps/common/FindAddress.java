package br.iteratorsystems.cps.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.iteratorsystems.cps.entities.Cep;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.exceptions.FindAddressException;
import br.iteratorsystems.cps.handler.BuscarCepBaseServiceImpl;
import br.iteratorsystems.cps.helper.FormatadorEstadorHelper;
import br.iteratorsystems.cps.helper.FormatadorPadraoStringHelper;

/**
 * Classe que faz a comunica��o com o webService de Cep. No momento est� feita para interagir com 
 * 2 tipos de webServices diferentes, um do site www.buscarcep.com.br e o outro do site cep.republicavirtual.com.br
 * Por enquanto ficar� assim com os dois. Assim que as chamadas de cep � nossa base de dados estiver funcionando,
 * o www.buscarcep.com.br ser� retirado.
 * @author Andr�
 *
 */
public class FindAddress {

	protected String logradouro;
	protected String bairro;
	protected String cidade;
	protected String pais;
	protected String estado;
	protected String estadoSigla;

	//private static final String baseUrl2 = "http://www.buscarcep.com.br/index.php";
	//private static final String CHAVE = "1Maco/svVvWvqJ1sNk5prmVd9kbaK7";
	private static final String baseUrl = "http://cep.republicavirtual.com.br/web_cep.php";
	private static final String PAIS = "BRASIL";

	/**
	 * Busca os dados de um cep no web service, com base no cep digitado pelo usu�rio.
	 * @param cep - Digitado pelo usu�rio
	 * @throws FindAddressException - Endere�o n�o encontrado ou web service indispon�vel.
	 */
	private void findByWebService(String cep) throws FindAddressException{
		HttpURLConnection connection = null;
		StringBuilder incomingData = null;
		StringBuilder finalUrl = new StringBuilder(baseUrl);
		finalUrl.append("?cep=" + cep);
		finalUrl.append("&formato=xml");
		//finalUrl.append("&chave=" + CHAVE);

		try {
			URL url = new URL(finalUrl.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Request-Method", "GET");

			connection.setDoInput(true);
			connection.setDoOutput(false);

			//this.createProxy(connection);

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

		Pattern p = Pattern.compile("<.*?>");
		Matcher m = p.matcher(incomingData.toString());
		String semHtml = m.replaceAll(" ");
		String[] resultado = semHtml.split("  ");
		
		if(resultado.length!=8){
			throw new FindAddressException("Cep n�o encontrado no Web Service");
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
	 * A prioridade � o web service por ser mais r�pido, mas se o mesmo n�o estiver dispon�vel ou ainda
	 * o cep n�o for encontrado, faz a busca na base local.
	 * @param cep - Cep
	 */
	public void find(String cep) {
		final String cepModificado = cep.replace("-","");
		try{
			this.findByWebService(cepModificado);
			return;
		}catch (FindAddressException e) {
			e.printStackTrace();
			this.findByDataBase(cepModificado);
		}
	}
	
	/**
	 * Busca os dados de cep na base de dados se o servi�o web estiver indispon�vel,
	 * ou o cep n�o for encontrado no mesmo.
	 * @param cep - Cep a procurar.
	 */
	private void findByDataBase(String cep) {
		BuscarCepBaseServiceImpl cepService = new BuscarCepBaseServiceImpl();
		Cep tabelasCep = null;
		try {
			tabelasCep = cepService.buscarCep(cep);
			
			if(tabelasCep != null) {
				this.setBairro(FormatadorPadraoStringHelper.formataStringEntrada(tabelasCep.getBairro1()));
				this.setCidade(FormatadorPadraoStringHelper.formataStringEntrada(tabelasCep.getLocalidade().getLocalidade()));
				this.setLogradouro(FormatadorPadraoStringHelper.formataStringEntrada(tabelasCep.getLogradouro()));
				this.setEstadoSigla(tabelasCep.getUf());
				this.setEstado(FormatadorEstadorHelper.recuperaEstado(tabelasCep.getUf()));
				this.setPais(PAIS);
			}
		} catch (CpsHandlerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se houver proxy na rede, este m�todo recebe o usu�rio e senha, e
	 * os adiciona � conec��o para usar a rede normalmente.
	 * @param connection - Conec��o.
	 */
	private void createProxy(HttpURLConnection connection) {
		// Configure proxy ...
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "10.0.0.1");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("http.proxyType", "4");
		String proxyUser = "200711190";
		String proxyPassword = "7104";

		// adiciona o usu�rio e senha
		connection.setRequestProperty("Proxy-Authorization", "Basic "
				+ new sun.misc.BASE64Encoder()
						.encode((proxyUser + ":" + proxyPassword).getBytes()));
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
