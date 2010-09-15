package br.iteratorsystems.cps.common;

import java.net.HttpURLConnection;

/**
 * Obtem o proxy
 * @author André
 *
 */
public class ObterProxyHelper {

	
	/**
	 * Se houver proxy na rede, este método recebe o usuário e senha, e
	 * os adiciona á conecção para usar a rede normalmente.
	 * @param connection - Conecção.
	 */
	public void createProxy(HttpURLConnection connection) {
		// Configure proxy ...
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "10.0.0.1");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("http.proxyType", "4");
		String proxyUser = "200711190";
		String proxyPassword = "7104";

		// adiciona o usuário e senha
		connection.setRequestProperty("Proxy-Authorization", "Basic "
				+ new sun.misc.BASE64Encoder()
						.encode((proxyUser + ":" + proxyPassword).getBytes()));
	}
}
