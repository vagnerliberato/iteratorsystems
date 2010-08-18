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
import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.entities.Tabelas_ProdutoGeral;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
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
		Set<Tabelas_ListaProdutoItem> listaProdutoItens = new HashSet<Tabelas_ListaProdutoItem>();
		Set<Tabelas_ListaProduto> listaProdutos = new HashSet<Tabelas_ListaProduto>();
		
		Tabelas_Usuario usuario = popularUsuario(listaProdutos);
		Tabelas_ListaProduto listaProduto = popularListaProduto(usuario, listaProdutoItens);
		Tabelas_ProdutoGeral produtoGeral1 = popularProdutoGeral(listaProdutoItens, "121231", "Vagner mané", "12", "KG");
		Tabelas_ProdutoGeral produtoGeral2 = popularProdutoGeral(listaProdutoItens, "777777", "Ronald sonso", "24", "LT");
		
		List<Tabelas_ProdutoGeral> produtosGerais = new ArrayList<Tabelas_ProdutoGeral>();
		produtosGerais.add(produtoGeral1);
		produtosGerais.add(produtoGeral2);
		
		HashMap<String, Integer> quantidadePorProduto = popularQuantidadesPorProduto(produtosGerais);
		
//		Tabelas_ListaProduto listaDeProdutos = listaProdutoService.salvarListaDeProdutos(1, listaProduto, produtosGerais, quantidadePorProduto);
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
			Tabelas_ListaProduto listaDeProdutos) {
		Integer quantidadeTotalDeProdutosHaSerSalvo = 0;
		for (Tabelas_ListaProdutoItem listaProdutoItem : listaDeProdutos.getListaProdutoItems()) {
			quantidadeTotalDeProdutosHaSerSalvo += listaProdutoItem.getQuantidade();
		}
		return quantidadeTotalDeProdutosHaSerSalvo;
	}

	private HashMap<String, Integer> popularQuantidadesPorProduto(
			List<Tabelas_ProdutoGeral> produtosGerais) {
		HashMap<String, Integer> quantidadePorProduto = new HashMap<String, Integer>();
		for (Tabelas_ProdutoGeral produtogeral : produtosGerais) {
			quantidadePorProduto.put(produtogeral.getCodigoBarras(), Integer.parseInt(produtogeral.getEmbalagem()));
		}
		return quantidadePorProduto;
	}
	
	private Tabelas_ListaProduto popularListaProduto(final Tabelas_Usuario usuario, final Set<Tabelas_ListaProdutoItem> listaProdutoItems){
		Tabelas_ListaProduto listaProduto = new Tabelas_ListaProduto();
		listaProduto.setNomeLista("listaTeste");
		//listaProduto.setIdLista(1);
		listaProduto.setDataCriacao(new Date());
		listaProduto.setUsuario(usuario);
		listaProduto.setListaProdutoItems(listaProdutoItems);
		
		return listaProduto;
	}

	private Tabelas_Usuario popularUsuario(final Set<Tabelas_ListaProduto> listaProdutos) {
		Tabelas_Usuario usuario = new Tabelas_Usuario();
		usuario.setCpfUsuario("11111111111");
		usuario.setDataNascimento(new Date());
		usuario.setDataultimamodificacao(new Date());
		usuario.setDddCel("11");
		usuario.setDddRes("35");
		usuario.setEmail("cpsiteratorsystems@iteratrsystems.com.br");
		usuario.setEnderecos(new HashSet<Tabelas_Endereco>());
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

	private Tabelas_ProdutoGeral popularProdutoGeral(final Set<Tabelas_ListaProdutoItem> listaProdutoItems, String codigoBarras, String descricao, String embalagem, String unidadeMedida) {
		Tabelas_ProdutoGeral produtogeral = new Tabelas_ProdutoGeral();
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
