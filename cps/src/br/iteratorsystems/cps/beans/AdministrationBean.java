package br.iteratorsystems.cps.beans;

import java.util.List;

import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.entities.CONTATOLOJA;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.LOJA;
import br.iteratorsystems.cps.entities.REDE;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.AdministrationHandler;

public class AdministrationBean {

	private LOGIN loginEntity = new LOGIN();
	private REDE redeEntity;
	private LOJA lojaEntity = new LOJA();
	private CONTATOLOJA contatoLojaEntity = new CONTATOLOJA();
	private AdministrationHandler administrationHandler;

	//booleanos para controle da tela
	private boolean cadastrar;
	private boolean alterar;
	private boolean excluir;
	private boolean consultar;
	private boolean mostrarLoja;
	private boolean mostrarCadastroRede = true;
	private boolean cnpj_valido = true;
	
	private String itemSelecionado;
	private String redeSelecionada;
	private String tipoVendaSelecionada;
	private String nomeRede;
	private String mensagemCampoObrigatorio = "Campo de preenchimento obrigatório!";
	private String mensagem_cnpj = "CNPJ já cadastrado na base de dados!";
	
	private List<LOGIN> allLogins;
	private HtmlDataTable richDataTable;
	
	private SelectItem[] redes;
	private SelectItem[] items = {
			new SelectItem(0,"Selecione"),
			new SelectItem(1,"Cadastrar Nova Loja"),
			new SelectItem(2,"Consultar Lojas Cadastradas"),
			new SelectItem(3,"Atualizar Dados da Loja"),
			new SelectItem(4,"Excluir uma Loja do Sistema"),
	};
	private SelectItem[] tipoVendas = {new SelectItem(0,"Varejo")};

	public AdministrationBean() {}
	
	public void cadastrarRede() throws CpsGeneralExceptions{
		if(this.getNomeRede() == null || this.getNomeRede().equals("")) return;
		administrationHandler  = new AdministrationHandler();
		redeEntity = new REDE();
		try{
			redeEntity.setNome(this.getNomeRede());
			administrationHandler.saveNewRede(redeEntity);
			this.setNomeRede("");
			this.getAllRedes();
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void cnpjOk(){
		try{
			if(CommonOperations.cnpjExists(this.getLojaEntity().getCnpj())){
				this.setCnpj_valido(false);
				return;
			}
			this.setCnpj_valido(true);
		}catch (CpsGeneralExceptions e) {
			e.printStackTrace();
		}
	}
	
	public void openLoja(){
		if(Integer.parseInt(this.getRedeSelecionada()) == 0){
			this.setMostrarLoja(false);
			this.setMostrarCadastroRede(true);
			return;
		}
		this.setMostrarLoja(true);
		this.setMostrarCadastroRede(false);
	}

	public SelectItem[] getAllRedes() throws CpsGeneralExceptions{
		int index = 0;
		try{
			administrationHandler = new AdministrationHandler();
			List<REDE> list = administrationHandler.getAllRedes();
			redes = new SelectItem[list.size()+1];
			redes[index] = new SelectItem(0,"Selecione...");;
			for(REDE rede: list){
				index ++;
				redes[index] = new SelectItem(rede.getId(),rede.getNome());
			}
			return redes;
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	public void selectOperation() {
		int i = Integer.parseInt(this.getItemSelecionado());
		switch (i) {
		case 0:
			this.setCadastrar(false);
			this.setAlterar(false);
			this.setConsultar(false);
			this.setExcluir(false);
			break;
		case 1:
			this.setCadastrar(true);
			this.setAlterar(false);
			this.setConsultar(false);
			this.setExcluir(false);
			break;
		case 2:
			this.setConsultar(true);
			this.setCadastrar(false);
			this.setAlterar(false);
			this.setExcluir(false);
			break;
		case 3:
			this.setAlterar(true);
			this.setCadastrar(false);
			this.setConsultar(false);
			this.setExcluir(false);
			break;
		case 4:
			this.setExcluir(true);
			this.setCadastrar(false);
			this.setAlterar(false);
			this.setConsultar(false);
			break;
		}
	}
	
	public void findUsers() throws CpsGeneralExceptions{
		try{
			administrationHandler = new AdministrationHandler();
			allLogins = administrationHandler.getAllLogins(this.getLoginEntity().getNomeLogin());
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void deleteLogin() throws CpsGeneralExceptions {
		try{
			LOGIN newLogin = (LOGIN) this.getRichDataTable().getRowData();
			administrationHandler = new AdministrationHandler();
			this.allLogins.remove(newLogin);
			administrationHandler.deleteLogin(newLogin);
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void find() {
		FindAddress findAddress = new FindAddress();
		try{
			findAddress.find(this.getLojaEntity().getCep());
			this.getLojaEntity().setEstado(findAddress.getEstadoSigla());
			this.getLojaEntity().setCidade(findAddress.getCidade());
			this.getLojaEntity().setBairro(findAddress.getBairro());
			this.getLojaEntity().setLogradouro(findAddress.getLogradouro());
			this.getLojaEntity().setPais(findAddress.getPais());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertToObj() throws CpsGeneralExceptions{
		administrationHandler = new AdministrationHandler();
		Integer index = new Integer(this.getRedeSelecionada());
		try{
			for(SelectItem item : redes){
				if(item.getValue().equals(index)){
					this.setRedeEntity(administrationHandler.getRede(item.getLabel()));
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cadastraLoja() throws CpsGeneralExceptions{
		if(!this.isCnpj_valido()){
			return;
		}
		
		administrationHandler = new AdministrationHandler();
		try{
			this.getLojaEntity().setTipo_venda("1");
			administrationHandler.saveNewLoja(this.getLojaEntity(),this.getRedeEntity());
			this.limpaCampos();
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	private void limpaCampos() {
		this.setRedeEntity(null);
		this.setLojaEntity(null);
		this.setContatoLojaEntity(null);
	}

	public void setLoginEntity(LOGIN loginEntity) {
		this.loginEntity = loginEntity;
	}

	public LOGIN getLoginEntity() {
		return loginEntity;
	}

	public void setAllLogins(List<LOGIN> allLogins) {
		this.allLogins = allLogins;
	}

	public List<LOGIN> getAllLogins() {
		return allLogins;
	}

	public void setRichDataTable(HtmlDataTable richDataTable) {
		this.richDataTable = richDataTable;
	}

	public HtmlDataTable getRichDataTable() {
		return richDataTable;
	}

	/**
	 * @return the cadastrar
	 */
	public boolean isCadastrar() {
		return cadastrar;
	}

	/**
	 * @param cadastrar the cadastrar to set
	 */
	public void setCadastrar(boolean cadastrar) {
		this.cadastrar = cadastrar;
	}

	/**
	 * @return the atualizar
	 */
	public boolean isAlterar() {
		return alterar;
	}

	/**
	 * @param atualizar the atualizar to set
	 */
	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

	/**
	 * @return the excluir
	 */
	public boolean isExcluir() {
		return excluir;
	}

	/**
	 * @param excluir the excluir to set
	 */
	public void setExcluir(boolean excluir) {
		this.excluir = excluir;
	}

	/**
	 * @return the consultar
	 */
	public boolean isConsultar() {
		return consultar;
	}

	/**
	 * @param consultar the consultar to set
	 */
	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}

	/**
	 * @return the items
	 */
	public SelectItem[] getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(SelectItem[] items) {
		this.items = items;
	}

	public void setItemSelecionado(String itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public String getItemSelecionado() {
		return itemSelecionado;
	}

	public void setRedeSelecionada(String redeSelecionada) {
		this.redeSelecionada = redeSelecionada;
	}

	public String getRedeSelecionada() {
		return redeSelecionada;
	}

	public void setMostrarLoja(boolean mostrarLoja) {
		this.mostrarLoja = mostrarLoja;
	}

	public boolean isMostrarLoja() {
		return mostrarLoja;
	}

	public void setMostrarCadastroRede(boolean mostrarCadastroRede) {
		this.mostrarCadastroRede = mostrarCadastroRede;
	}

	public boolean isMostrarCadastroRede() {
		return mostrarCadastroRede;
	}

	public void setTipoVendaSelecionada(String tipoVendaSelecionada) {
		this.tipoVendaSelecionada = tipoVendaSelecionada;
	}

	public String getTipoVendaSelecionada() {
		return tipoVendaSelecionada;
	}

	public void setTipoVendas(SelectItem[] tipoVendas) {
		this.tipoVendas = tipoVendas;
	}

	public SelectItem[] getTipoVendas() {
		return tipoVendas;
	}

	public void setLojaEntity(LOJA lojaEntity) {
		this.lojaEntity = lojaEntity;
	}

	public LOJA getLojaEntity() {
		return lojaEntity;
	}

	public void setContatoLojaEntity(CONTATOLOJA contatoLojaEntity) {
		this.contatoLojaEntity = contatoLojaEntity;
	}

	public CONTATOLOJA getContatoLojaEntity() {
		return contatoLojaEntity;
	}

	public void setMensagemCampoObrigatorio(String mensagemCampoObrigatorio) {
		this.mensagemCampoObrigatorio = mensagemCampoObrigatorio;
	}

	public String getMensagemCampoObrigatorio() {
		return mensagemCampoObrigatorio;
	}

	public void setNomeRede(String nomeRede) {
		this.nomeRede = nomeRede;
	}

	public String getNomeRede() {
		return nomeRede;
	}

	public void setMensagem_cnpj(String mensagem_cnpj) {
		this.mensagem_cnpj = mensagem_cnpj;
	}

	public String getMensagem_cnpj() {
		return mensagem_cnpj;
	}

	public void setCnpj_valido(boolean cnpj_valido) {
		this.cnpj_valido = cnpj_valido;
	}

	public boolean isCnpj_valido() {
		return cnpj_valido;
	}

	/**
	 * @return the redeEntity
	 */
	public REDE getRedeEntity() {
		return redeEntity;
	}

	/**
	 * @param redeEntity the redeEntity to set
	 */
	public void setRedeEntity(REDE redeEntity) {
		this.redeEntity = redeEntity;
	}
}
