package br.iteratorsystems.cps.helper;

/**
 * Classe helper de procedure
 * @author André
 *
 */
public final class ProcedureHelper {

	/**
	 * Construtor privado
	 */
	private ProcedureHelper() {
		super();
	}
	
	/**
	 * Limpa os dados não utilizados da string.
	 * @param partesCep - dados do cep.
	 * @return String limpa.
	 */
	public static String limparDadosString(String partesCep) {
		StringBuilder builder = new StringBuilder();
		builder.append(partesCep.replaceAll("[\"^(^)]",""));
		return builder.toString();
	}
}
