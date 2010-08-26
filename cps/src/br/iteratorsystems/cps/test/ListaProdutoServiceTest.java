package br.iteratorsystems.cps.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.iteratorsystems.cps.dao.ListaProdutoDao;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.ListaProduto;
import br.iteratorsystems.cps.entities.ListaProdutoItem;
import br.iteratorsystems.cps.entities.ProdutoGeral;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.service.ListaProdutoService;

public class ListaProdutoServiceTest {

	private ListaProdutoService listaProdutoService;
	
	@Mock
	private ListaProdutoDao listaProdutoDao = mock(ListaProdutoDao.class);
	
	@Before
	public void setUp(){
		listaProdutoService = new ListaProdutoService();
		//listaProdutoService.setListaProdutoDao(listaProdutoDao);
	}
	
	@Test
	public void devePopularListaProdutoItemEhSalvarListaDeProduto() throws Exception, CpsDaoException{
		Set<ListaProdutoItem> listaProdutoItens = new HashSet<ListaProdutoItem>();
		Set<ListaProduto> listaProdutos = new HashSet<ListaProduto>();
		
		Usuario usuario = popularUsuario(listaProdutos);
		ListaProduto listaProduto = popularListaProduto(usuario, listaProdutoItens);
		ProdutoGeral produtoGeral1 = popularProdutoGeral(listaProdutoItens, "121231", "Vagner mané", "12", "KG");
		ProdutoGeral produtoGeral2 = popularProdutoGeral(listaProdutoItens, "777777", "Ronald sonso", "24", "LT");
		
		List<ProdutoGeral> produtosGerais = new ArrayList<ProdutoGeral>();
		produtosGerais.add(produtoGeral1);
		produtosGerais.add(produtoGeral2);
		
		HashMap<String, Integer> quantidadePorProduto = popularQuantidadesPorProduto(produtosGerais);
		
//		ListaProduto listaDeProdutos = listaProdutoService.salvarListaDeProdutos(1, listaProduto, produtosGerais, quantidadePorProduto);
//		
//		assertEquals("listaTeste", listaDeProdutos.getNomeLista());
//		//assertEquals(1, listaDeProdutos.getIdLista(), 0.0001);
//		assertEquals(2, listaDeProdutos.getListaProdutoItems().size());
//		assertNull(listaDeProdutos.getUsuario().getLogins());
//		assertEquals("11", listaDeProdutos.getUsuario().getDddCel());
//		assertSame(usuario, listaDeProdutos.getUsuario());
		
		Integer quantidadeTotalDeProdutosHaSerSalvo = obterQuantidadeTotalDosProdutosHaSeremSalvos(null);
		assertEquals(36, quantidadeTotalDeProdutosHaSerSalvo, 0.0001);
	}

	private Integer obterQuantidadeTotalDosProdutosHaSeremSalvos(
			ListaProduto listaDeProdutos) {
		Integer quantidadeTotalDeProdutosHaSerSalvo = 0;
		for (ListaProdutoItem listaProdutoItem : listaDeProdutos.getListaProdutoItems()) {
			quantidadeTotalDeProdutosHaSerSalvo += listaProdutoItem.getQuantidade();
		}
		return quantidadeTotalDeProdutosHaSerSalvo;
	}

	private HashMap<String, Integer> popularQuantidadesPorProduto(
			List<ProdutoGeral> produtosGerais) {
		HashMap<String, Integer> quantidadePorProduto = new HashMap<String, Integer>();
		for (ProdutoGeral produtogeral : produtosGerais) {
			quantidadePorProduto.put(produtogeral.getCodigoBarras(), Integer.parseInt(produtogeral.getEmbalagem()));
		}
		return quantidadePorProduto;
	}
	
	private ListaProduto popularListaProduto(final Usuario usuario, final Set<ListaProdutoItem> listaProdutoItems){
		ListaProduto listaProduto = new ListaProduto();
		listaProduto.setNomeLista("listaTeste");
		//listaProduto.setIdLista(1);
		listaProduto.setDataCriacao(new Date());
		listaProduto.setUsuario(usuario);
		listaProduto.setListaProdutoItems(listaProdutoItems);
		
		return listaProduto;
	}

	private Usuario popularUsuario(final Set<ListaProduto> listaProdutos) {
		Usuario usuario = new Usuario();
		usuario.setCpfUsuario("11111111111");
		usuario.setDataNascimento(new Date());
		usuario.setDataultimamodificacao(new Date());
		usuario.setDddCel("11");
		usuario.setDddRes("35");
		usuario.setEmail("cpsiteratorsystems@iteratrsystems.com.br");
		usuario.setEnderecos(new HashSet<Endereco>());
		usuario.setIdUsuario(10);
		usuario.setListaProdutos(listaProdutos);
		usuario.setLogins(null);
		usuario.setNomeUsuario("testeCps");
		usuario.setOrgaoEspedidorUsu("ssp");
		usuario.setRgUsuario("22222222");
		usuario.setSobrenomeUsuario("cps");
		usuario.setTelCel("15");
		usuario.setTelRes("11112222");
		return usuario;
	}

	private ProdutoGeral popularProdutoGeral(final Set<ListaProdutoItem> listaProdutoItems, String codigoBarras, String descricao, String embalagem, String unidadeMedida) {
		ProdutoGeral produtogeral = new ProdutoGeral();
		produtogeral.setCodigoBarras(codigoBarras);
		produtogeral.setDescricao(descricao);
		produtogeral.setEmbalagem(embalagem );
		produtogeral.setImagem('N');
		produtogeral.setListaProdutoItems(listaProdutoItems);
		produtogeral.setStatus('A');
		produtogeral.setUnidadeMedida(unidadeMedida );
		
		return produtogeral;
	}
}
