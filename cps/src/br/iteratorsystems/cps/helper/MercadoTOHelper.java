package br.iteratorsystems.cps.helper;

import br.iteratorsystems.cps.to.MercadoTO;

/**
 * Classe helper de mercadoTO
 * @author André
 *
 */
public final class MercadoTOHelper {

	/**
	 * Construtor privado
	 */
	private MercadoTOHelper() {
		super();
	}
	
	/**
	 * Obtem uma mercado TO da procedure
	 * @param mercado - string de entrada da procedure
	 * @return Mercado TO
	 */
	public static MercadoTO obterMercadoTOProcedure(String mercado) {
		MercadoTO mercadoTO = new MercadoTO();
		String[] partes = mercado.split("[,]");
		
		mercadoTO.setCodigo(Integer.parseInt(partes[0]));
		mercadoTO.setCodigoRede(Integer.parseInt(partes[1]));
		mercadoTO.setNome(partes[2].trim());
		mercadoTO.setCep(partes[3].trim());
		return mercadoTO;
	}
}
