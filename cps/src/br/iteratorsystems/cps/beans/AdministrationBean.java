package br.iteratorsystems.cps.beans;

import java.util.List;

import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.common.Resources;
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
	private boolean cadastrarLoja;
	private boolean mostrarLoja;
	private boolean atualizarLoja;
	private boolean atualizarRede;
	private boolean mostrarCadastroRede = true;
	private boolean cnpj_valido = true;
	
	private String itemSelecionado;
	private String redeSelecionada;
	private String tipoVendaSelecionada;
	private String nomeRede;
	private String nomeLoja;
	private String mensagemCampoObrigatorio = "Campo de preenchimento obrigatório!";
	private String mensagem_cnpj = "CNPJ já cadastrado na base de dados!";
	
	private List<LOGIN> allLogins;
	private List<REDE> listRedes;
	private List<LOJA> listLojas;
	private HtmlDataTable richDataTable;
	private HtmlDataTable redesDataTable;
	private HtmlDataTable lojasDataTable;
	
	private SelectItem[] redes;
	private SelectItem[] items = {
			new SelectItem(0,"Selecione"),
			new SelectItem(1,"Cadastrar Nova Loja/Rede"),
			new SelectItem(2,"Consultar Lojas Cadastradas"),
			new SelectItem(3,"Atualizar Dados da Loja"),
			new SelectItem(4,"Excluir uma Loja do Sistema"),
			new SelectItem(5,"Gerenciar Redes Cadastradas"),
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
	
	public void getRedesByName() throws CpsGeneralExceptions{
		if(this.getNomeRede() == null || "".equals(this.getNomeRede())){
			return;
		}
		administrationHandler = new AdministrationHandler();
		try{
			listRedes = administrationHandler.getAllRedes(this.getNomeRede());
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void getLojasByName() throws CpsGeneralExceptions{
		if(this.getNomeLoja() == null || "".equals(this.getNomeLoja())){
			return;
		}
		administrationHandler = new AdministrationHandler();
		try{
			listLojas = administrationHandler.getAllLojas(this.getNomeLoja());
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
		case 0: // Selecione
			this.setCadastrarLoja(false);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			break;
		case 1: //Cadastrar Nova Loja
			this.setNomeRede("");
			this.setCadastrarLoja(true);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			break;
		case 2: //Consultar Lojas Cadastradas
			this.setCadastrarLoja(false);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			break;
		case 3: //Atualizar Dados da Loja
			this.setNomeRede("");
			this.setAtualizarLoja(true);
			this.setCadastrarLoja(false);
			this.setAtualizarRede(false);
			break;
		case 4: //Excluir uma Loja do Sistema
			this.setCadastrarLoja(false);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			break;
		case 5: //Atualizar Redes Cadastradas
			this.setAtualizarRede(true);
			this.setCadastrarLoja(false);
			this.setAtualizarLoja(false);
			this.setMostrarLoja(false);
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
	
	public void atualizaRede() throws CpsGeneralExceptions{
		this.setRedeEntity((REDE)this.getRedesDataTable().getRowData());
		if(this.getRedeEntity().getNome() == null || this.getRedeEntity().getNome().equals("")) {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("rede_invalid_name"),"nome de rede vazio");
			return;
		}
		try{
			administrationHandler = new AdministrationHandler();
			administrationHandler.updateRede(this.getRedeEntity());
			this.setNomeRede("");
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	//TODO
	public void excluirRede() throws CpsGeneralExceptions{
		administrationHandler = new AdministrationHandler();
		try{
			this.setRedeEntity((REDE) this.getRedesDataTable().getRowData());
			administrationHandler.deleteLogin(new LOGIN());
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
	public boolean isCadastrarLoja() {
		return cadastrarLoja;
	}

	/**
	 * @param cadastrar the cadastrar to set
	 */
	public void setCadastrarLoja(boolean cadastrar) {
		this.cadastrarLoja = cadastrar;
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

	public void setListRedes(List<REDE> listRedes) {
		this.listRedes = listRedes;
	}

	public List<REDE> getListRedes() {
		return listRedes;
	}

	public void setRedesDataTable(HtmlDataTable redesDataTable) {
		this.redesDataTable = redesDataTable;
	}

	public HtmlDataTable getRedesDataTable() {
		return redesDataTable;
	}

	public void setAtualizarLoja(boolean atualizarLoja) {
		this.atualizarLoja = atualizarLoja;
	}

	public boolean isAtualizarLoja() {
		return atualizarLoja;
	}

	public void setAtualizarRede(boolean atualizarRede) {
		this.atualizarRede = atualizarRede;
	}

	public boolean isAtualizarRede() {
		return atualizarRede;
	}

	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}

	public String getNomeLoja() {
		return nomeLoja;
	}

	public void setListLojas(List<LOJA> listLojas) {
		this.listLojas = listLojas;
	}

	public List<LOJA> getListLojas() {
		return listLojas;
	}

	public void setLojasDataTable(HtmlDataTable lojasDataTable) {
		this.lojasDataTable = lojasDataTable;
	}

	public HtmlDataTable getLojasDataTable() {
		return lojasDataTable;
	}
}
