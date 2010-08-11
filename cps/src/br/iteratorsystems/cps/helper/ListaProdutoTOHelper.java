package br.iteratorsystems.cps.helper;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;
import br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.to.ProdutoTO;

/**
 * Classe helper para operações com a lista de produtos
 * @author Robson
 *
 */
public final class ListaProdutoTOHelper {

	/**
	 * Construtor padrão
	 */
	private ListaProdutoTOHelper() {
		super();
	}
	
	/**
	 * Método responsável por converter produto TO em Lista produto Item
	 * @param produtoTO - Produto TO da lista de produtos.
	 * @return Lista Produto Item
	 */
	public static Tabelas_ListaProdutoItem converteProdutoTO(ProdutoTO produtoTO) {
		Tabelas_ListaProdutoItem produtoItem = new Tabelas_ListaProdutoItem();
		produtoItem.setProdutogeral(produtoTO.getProdutoGeral());
		produtoItem.setQuantidade(produtoTO.getQuantidadeSelecionada());
		return produtoItem;
	}

	/**
	 * Método responsavel por converter uma listaProdutoTO em uma listaProdutoItem
	 * @param listaComprasUsuario - ListaProdutoTo do usuario
	 * @return Uma lista de ListaProdutoItem
	 */
	public static Set<Tabelas_ListaProdutoItem> converteListaProdutoTO(Collection<ProdutoTO> listaComprasUsuario) {
		Set<Tabelas_ListaProdutoItem> listaProdutoItem = new HashSet<Tabelas_ListaProdutoItem>();
		for(ProdutoTO produtoTO : listaComprasUsuario) {
			listaProdutoItem.add(converteProdutoTO(produtoTO));
		}
		return listaProdutoItem;
	}

	
	/**
	 * Método responsavel por popular uma lista de produto
	 * @param listaDeProdutoItens
	 * @return Lista de Produto
	 */
	public static Tabelas_ListaProduto popularUmaListaDeProduto(Set<Tabelas_ListaProdutoItem> listaDeProdutoItens){
		Tabelas_ListaProduto listaProduto = new Tabelas_ListaProduto();

		listaProduto.setListaProdutoItems(listaDeProdutoItens);
		listaProduto.setDataCriacao(new Date());
		listaProduto.setNomeLista("Ronald");
		
		 //FacesContext context = FacesContext.getCurrentInstance();
		 //ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		 //login = (Tabelas_Login) servletContext.getAttribute("usuarioLogado");
		
		Set<Tabelas_ListaProduto> x = new HashSet<Tabelas_ListaProduto>();
		x.add(listaProduto);
		Tabelas_Usuario usuario = popularUsuario(x);
		listaProduto.setUsuario(usuario);
		
		for (Tabelas_ListaProdutoItem listaprodutoitem : listaDeProdutoItens) {
			listaprodutoitem.setListaProduto(listaProduto);
			listaprodutoitem.getProdutogeral().setListaProdutoItems(listaDeProdutoItens);
		}
		
		//for (Tabelas_ListaProdutoItem item : listaDeProdutoItens) {
			//System.out.println(item.getProdutogeral().toString());
			//System.out.println(item.getListaProduto().getListaProdutoItems().iterator().next().getProdutogeral().toString());
		//}
		
		return listaProduto;
	}
	
	private static Tabelas_Usuario popularUsuario(final Set<Tabelas_ListaProduto> listaProdutos) {
		Tabelas_Usuario usuario = new Tabelas_Usuario();
		usuario.setCpfUsuario("11111111111");
		usuario.setDataNascimento(new Date());
		usuario.setDataultimamodificacao(new Date());
		usuario.setDddCel("11");
		usuario.setDddRes("35");
		usuario.setEmail("cpsiteratorsystems@iteratrsystems.com.br");
		usuario.setEnderecos(new HashSet<Tabelas_Endereco>());
		usuario.setIdUsuario(1);
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
}