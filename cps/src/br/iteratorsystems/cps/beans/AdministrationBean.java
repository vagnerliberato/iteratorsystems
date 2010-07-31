package br.iteratorsystems.cps.beans;

import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Session;
import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.LojaDao;
import br.iteratorsystems.cps.dao.RedeDao;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_Rede;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.AdministrationHandler;

public class AdministrationBean {

	private Tabelas_Login loginEntity = new Tabelas_Login();
	private Tabelas_Rede redeEntity;
	private Tabelas_Loja lojaEntity;
	private AdministrationHandler administrationHandler;

	//booleanos para controle da tela
	private boolean cadastrarLoja;
	private boolean mostrarLoja;
	private boolean mostrarLojaUpd;
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
	
	private List<Tabelas_Login> allLogins;
	private List<Tabelas_Rede> listRedes;
	private List<Tabelas_Loja> listLojas;
	
	private HtmlDataTable richDataTable;
	private HtmlDataTable redesDataTable;
	private HtmlDataTable lojasDataTable;
	
	private SelectItem[] redes;
	private SelectItem[] tipoVendas = {
			new SelectItem(0,"Varejo")};
	private SelectItem[] items = {
			new SelectItem(0,"Selecione"),
			new SelectItem(1,"Cadastrar Nova Loja/Rede"),
			new SelectItem(2,"Gerenciar Lojas Cadastradas"),
			new SelectItem(3,"Gerenciar Redes Cadastradas"),
	};

	Session session = HibernateConfig.getSession();
	RedeDao redeDao = new RedeDao(Tabelas_Rede.class, session);
	LojaDao lojaDao = new LojaDao(Tabelas_Loja.class, session);
	
	public AdministrationBean() {}
	
	public void cadastrarRede() throws CpsGeneralExceptions{
		if(this.getNomeRede() == null || this.getNomeRede().equals("")){ 
			return;
		}
		
		administrationHandler  = new AdministrationHandler();
		redeEntity = new Tabelas_Rede();
		
		try{
			redeEntity.setNome(this.getNomeRede());
			administrationHandler.saveNewRede(redeEntity);
			this.setNomeRede("");
			this.getAllRedes();
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void atualizaTela(){
		this.setAtualizarLoja(false);
		this.setMostrarLoja(false);
		this.setMostrarLojaUpd(true);
		try{
			this.setLojaEntity(this.getListLojas().get(this.getLojasDataTable().getRowIndex()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getRedesByName() throws CpsGeneralExceptions{
		if(this.getNomeRede() == null || "".equals(this.getNomeRede())){
			return;
		}
		listRedes = redeDao.obterPorNome(this.getNomeRede());
	}
	
	public void getLojasByName() throws CpsGeneralExceptions{
		if(this.getNomeLoja() == null || "".equals(this.getNomeLoja())){
			return;
		}
		listLojas = lojaDao.obterLojaPorNomeFantasia(this.getNomeLoja());
	}
	
	public void cnpjOk(){
		try{
			if(CommonOperations.cnpjExists(this.getLojaEntity().getCnpj(),this.getLojaEntity())){
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
		List<Tabelas_Rede> todasRedes = redeDao.listarTodos();
		redes = new SelectItem[todasRedes.size()+1];
		redes[index] = new SelectItem(0,"Selecione...");;
		for(Tabelas_Rede rede: todasRedes){
			index ++;
			redes[index] = new SelectItem(rede.getId(),rede.getNome());
		}
		return redes;
	}

	public void selectOperation() {
		int i = Integer.parseInt(this.getItemSelecionado());
		switch (i) {
		case 0: // Selecione
			this.setCadastrarLoja(false);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			this.setMostrarLojaUpd(false);
			this.setLojaEntity(null);
			this.setRedeEntity(null);
			break;
		case 1: //Cadastrar Nova Loja
			this.setNomeRede("");
			this.setCadastrarLoja(true);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			this.setMostrarLojaUpd(false);
			this.setLojaEntity(null);
			this.setRedeEntity(null);
			break;
		case 2: //Gerenciar Lojas Cadastradas
			this.setNomeLoja("");
			this.setListLojas(null);
			this.setAtualizarLoja(true);
			this.setCadastrarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			this.setLojaEntity(null);
			this.setRedeEntity(null);
			break;
		case 3: //Gerenciar Redes Cadastradas
			this.setAtualizarRede(true);
			this.setListRedes(null);
			this.setCadastrarLoja(false);
			this.setAtualizarLoja(false);
			this.setMostrarLoja(false);
			this.setMostrarLojaUpd(false);
			this.setLojaEntity(null);
			this.setRedeEntity(null);
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
			Tabelas_Login newLogin = (Tabelas_Login) this.getRichDataTable().getRowData();
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
					this.setLojaEntity(new Tabelas_Loja());
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
			administrationHandler.saveNewLoja(this.getLojaEntity(),this.getRedeEntity());
			this.limpaCampos();
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void atualizaRede() throws CpsGeneralExceptions{
		this.setRedeEntity((Tabelas_Rede)this.getRedesDataTable().getRowData());
		if(this.getRedeEntity().getNome() == null || this.getRedeEntity().getNome().equals("")) {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("rede_invalid_name"),"nome de rede vazio");
			return;
		}
		redeDao.update(this.getRedeEntity());
		this.setNomeRede("");
	}
	
	public void atualizaLoja() throws CpsGeneralExceptions{
		if(!this.isCnpj_valido()){
			return;
		}
		lojaDao.update(this.getLojaEntity());
		this.limpaCampos();
		this.setMostrarLojaUpd(false);
	}
	public void excluirRede() throws CpsGeneralExceptions{
		this.setRedeEntity((Tabelas_Rede) this.getRedesDataTable().getRowData());
		this.listRedes.remove(this.getRedeEntity());
		
		redeDao.excluir(this.getRedeEntity());
	}

	public void excluirLoja() throws CpsGeneralExceptions{
		this.setLojaEntity((Tabelas_Loja) this.getLojasDataTable().getRowData());
		this.listLojas.remove(this.getLojaEntity());
		
		lojaDao.excluir(this.getLojaEntity());
	}
	
	private void limpaCampos() {
		this.setRedeEntity(null);
		this.setLojaEntity(null);
	}

	public void setLoginEntity(Tabelas_Login loginEntity) {
		this.loginEntity = loginEntity;
	}

	public Tabelas_Login getLoginEntity() {
		return loginEntity;
	}

	public void setAllLogins(List<Tabelas_Login> allLogins) {
		this.allLogins = allLogins;
	}

	public List<Tabelas_Login> getAllLogins() {
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

	public void setLojaEntity(Tabelas_Loja lojaEntity) {
		this.lojaEntity = lojaEntity;
	}

	public Tabelas_Loja getLojaEntity() {
		return lojaEntity;
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
	public Tabelas_Rede getRedeEntity() {
		return redeEntity;
	}

	/**
	 * @param redeEntity the redeEntity to set
	 */
	public void setRedeEntity(Tabelas_Rede redeEntity) {
		this.redeEntity = redeEntity;
	}

	public void setListRedes(List<Tabelas_Rede> listRedes) {
		this.listRedes = listRedes;
	}

	public List<Tabelas_Rede> getListRedes() {
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

	public void setListLojas(List<Tabelas_Loja> listLojas) {
		this.listLojas = listLojas;
	}

	public List<Tabelas_Loja> getListLojas() {
		return listLojas;
	}

	public void setLojasDataTable(HtmlDataTable lojasDataTable) {
		this.lojasDataTable = lojasDataTable;
	}

	public HtmlDataTable getLojasDataTable() {
		return lojasDataTable;
	}

	public void setMostrarLojaUpd(boolean mostrarLojaUpd) {
		this.mostrarLojaUpd = mostrarLojaUpd;
	}

	public boolean isMostrarLojaUpd() {
		return mostrarLojaUpd;
	}
}
