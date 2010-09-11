package br.iteratorsystems.cps.common;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.acos;

import java.math.BigDecimal;
import br.iteratorsystems.cps.enums.TipoResultado;

/**
 * Classe de calculo de rota com base em latitude e longitude.
 * @author André
 *
 */
public final class CalculoDeRota {
	
	private static final Double CIRCUNFERENCIA_TERRA_KM = 6371d;
	private static final Double CIRCUNFERENCIA_TERRA_MI = 3959d;
	private static final Integer AUX = 180;
	
	private static Double latitudeA;
	private static Double latitudeB;
	private static Double longitudeA;
	private static Double longitudeB;
	
	/**
	 * Calcula a distancia em milhas ou kilometros 
	 * @param latitudeA - latitude de A
	 * @param longitudeA - longitude de A
	 * @param latitudeB - latitude de B
	 * @param longitudeB - longitude de B
	 * @param tipoResultado - Tipo de resultado(MI ou KM)
	 * @return Retorna o resultado da distancia
	 */
	public static BigDecimal calcularDistancia(Double latitudeA, Double longitudeA,
			Double latitudeB, Double longitudeB, TipoResultado tipoResultado) {
		calcularLtdLngAB(latitudeA, longitudeA, latitudeB, longitudeB);

		BigDecimal resultado = null;
		if (tipoResultado == TipoResultado.KILOMETROS) {
			resultado = aplicarFormula(CIRCUNFERENCIA_TERRA_KM);
		} else {
			resultado = aplicarFormula(CIRCUNFERENCIA_TERRA_MI);
		}
		return resultado;
	}

	/**
	 * Aplica a formula para o calculo de distancia.
	 * @param tipoResultado - tipo de resultado.
	 * @return Valor da distancia
	 */
	private static BigDecimal aplicarFormula(Double tipoResultado) {
		Double resultado = new Double(0);
		resultado = (Double)
			tipoResultado * acos(cos(latitudeA) * cos(latitudeB) * cos(longitudeB - longitudeA) + sin(latitudeA) * sin(latitudeB));
		return new BigDecimal(resultado);
	}

	/**
	 * Calcula a latitude e longitude de cada ponto.
	 * @param latitudeEA - latitude
	 * @param longitudeEA - longitude
	 * @param latitudeEB - latitude 
	 * @param longitudeEB - longitude
	 */
	private static void calcularLtdLngAB(Double latitudeEA, Double longitudeEA,
			Double latitudeEB, Double longitudeEB) {
		
		CalculoDeRota.latitudeA = (Double) (latitudeEA * PI) / AUX.intValue();
		CalculoDeRota.latitudeB = (Double) (latitudeEB * PI) / AUX.intValue();
		CalculoDeRota.longitudeA = (Double) (longitudeEA * PI) / AUX.intValue();
		CalculoDeRota.longitudeB = (Double) (longitudeEB * PI) / AUX.intValue();
	}
}
