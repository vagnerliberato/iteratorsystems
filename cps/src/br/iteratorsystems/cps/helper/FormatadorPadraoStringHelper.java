package br.iteratorsystems.cps.helper;

/**
 * Classe Helper de formato de strings.
 * @author André
 *
 */
public final class FormatadorPadraoStringHelper {

	/**
	 * Construtor padrão.
	 */
	private FormatadorPadraoStringHelper() {
		super();
	}

	/**
	 * Formata uma string de entrada adequadamente.
	 * @param entrada - String de entrada
	 * @return String formatada
	 */
	public static String formataStringEntrada(String entrada) {
		StringBuilder saida = new StringBuilder();
		saida.append(entrada.toLowerCase());
		String[] espacosDivididos = saida.toString().split("\\s");
		saida.delete(0, saida.length());

		for (int i = 0; i < espacosDivididos.length; i++) {
			String aux = espacosDivididos[i].substring(0, 1).toUpperCase();
			espacosDivididos[i] = espacosDivididos[i].substring(1,espacosDivididos[i].length());
			espacosDivididos[i] = aux + espacosDivididos[i];

			saida.append(espacosDivididos[i]);
			saida.append(" ");
		}
		return saida.toString();
	}
}
