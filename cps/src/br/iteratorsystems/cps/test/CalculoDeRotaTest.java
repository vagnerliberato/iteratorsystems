package br.iteratorsystems.cps.test;

import static org.junit.Assert.*;
import java.math.BigDecimal;
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
		BigDecimal resultado = CalculoDeRota.calcularDistancia(latitudeA, longitudeA, latitudeB, longitudeB,TipoResultado.KILOMETROS);
		
		assertNotNull(resultado);
		assertEquals(5.70d, resultado.doubleValue(), 0.01);
	}
	
	@Test
	public void calcularDistanciaMI() {
		BigDecimal resultado = CalculoDeRota.calcularDistancia(latitudeA, longitudeA, latitudeB, longitudeB,TipoResultado.MILHAS);
		
		assertNotNull(resultado);
		assertEquals(3.54d, resultado.doubleValue(), 0.01);
	}
}
