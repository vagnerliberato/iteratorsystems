package br.iteratorsystems.cps.helper;

import br.iteratorsystems.cps.enums.EEstados;

/**
 * Classe helper para formatar estados de acordo com o Enum EEstados
 * @author André
 *
 */
public final class FormatadorEstadorHelper {

	/**
	 * Construtor privado
	 */
	private FormatadorEstadorHelper() {
		super();
	}
	
	/**
	 * Verifica o Enum de estados e recupera o correspondete com base na sigla.
	 * @param estado - Sigal do estado á encontrar.
	 * @return Estado encontrado.
	 */
	public static String recuperaEstado(String... estado) {
		String result = null;
		try {
			EEstados e = EEstados.valueOf(estado[0].toString().toUpperCase());
			result = e.getNome();
		} catch (IllegalArgumentException e) {
			result = "erro: " + e;
		}
		result = formataEstado(result);
		return result;
	}

	/**
	 * Obtem a sigla do estado com base no nome.
	 * @param nomeEstado - nome do estado
	 * @return sigla do estado
	 */
	public static String obterSiglaEstado(String nomeEstado) {
		String result = null;
		
		if(nomeEstado == null) {
			throw new IllegalArgumentException("nome do estado é nulo!");
		}
		
		for(EEstados estado : EEstados.values()) {
			if(estado.getNome().equals(nomeEstado)) {
				result = estado.name();
				break;
			}
		}
		return result;
	}
	
	/**
	 * Formata o estado.
	 * @param antigo - Estado não formatado.
	 * @return Estado formatado.
	 */
	private static String formataEstado(String antigo) {
		String[] formatado = antigo.split("_");
		String result = null;

		if (formatado.length == 1)
			return formatado[0];

		result = formatado.length == 2 ? formatado[0] + " " + formatado[1]
				: result;
		result = formatado.length == 3 ? formatado[0] + " " + formatado[1]
				+ " " + formatado[2] : result;
		result = formatado.length == 4 ? formatado[0] + " " + formatado[1]
				+ " " + formatado[2] + " " + formatado[3] : result;

		return result;
	}
}
