package br.iteratorsystems.cps.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.ListaProduto;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.service.ListaProdutoService;

public class ListaProdutoTest {

	private ListaProdutoService service = new ListaProdutoService();
	
	@Test
	public void deveAtualizarUmaListaDeProduto() throws CpsHandlerException{
		
		Set<Endereco> enderecos = new HashSet<Endereco>();
		Set<ListaProduto> listaProdutos = new HashSet<ListaProduto>();
		Usuario usuario = new Usuario(20, "Bolha", "ambastos", new Date(1l), "10203040", "1234321", "SP", "11", "12344321", "31", "32123432", "abc@dfg.com", new Date(2l), listaProdutos, null, enderecos);
		ListaProduto listaProduto = new ListaProduto(10, usuario, "Teste", new Date(1l));
		
		service.atualizarListaDeProdutos(listaProduto);
		
		assertEquals(10, listaProduto.getId().intValue());
		assertNotNull(listaProduto);
		assertEquals("Teste", listaProduto.getNomeLista());
	}
}
