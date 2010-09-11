package br.iteratorsystems.cps.test;

import java.math.BigDecimal;
import junit.framework.Assert;

import org.junit.Test;

import br.iteratorsystems.cps.common.CalculoDeRota;
import br.iteratorsystems.cps.enums.TipoResultado;

/**
 * Teste de calculo de rota
 * @author André
 *
 */
public class CalculoDeRotaTest{

	private static final Double latitudeA = -23.5706427;
	private static final Double latitudeB = -23.6021796;
	private static final Double longitudeA = -46.710385;
	private static final Double longitudeB = -46.7544991;
	
	@Test
	public void calcularDistanciaKM() {
		BigDecimal resultado =
				CalculoDeRota.calcularDistancia(
						latitudeA, longitudeA, latitudeB, longitudeB,TipoResultado.KILOMETROS);
		Assert.assertNotNull(resultado);
	}
	
	@Test
	public void calcularDistanciaMI() {
		BigDecimal resultado = 
				CalculoDeRota.calcularDistancia(
						latitudeA, longitudeA, latitudeB, longitudeB,TipoResultado.MILHAS);
		Assert.assertNotNull(resultado);
	}
}
