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

	// booleanos para controle da tela
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
	private SelectItem[] tipoVendas = { new SelectItem(0, "Varejo") };
	private SelectItem[] items = { new SelectItem(0, "Selecione"),
			new SelectItem(1, "Cadastrar Nova Loja/Rede"),
			new SelectItem(2, "Gerenciar Lojas Cadastradas"),
			new SelectItem(3, "Gerenciar Redes Cadastradas"), };

	Session session = HibernateConfig.getSession();
	RedeDao redeDao = new RedeDao(Tabelas_Rede.class, session);
	LojaDao lojaDao = new LojaDao(Tabelas_Loja.class, session);

	/**
	 * Construtor
	 */

	public AdministrationBean() {
	}

	/**
	 * Cadastra Rede
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void cadastrarRede() throws CpsGeneralExceptions {
		if (this.getNomeRede() == null || this.getNomeRede().equals("")) {
			return;
		}

		administrationHandler = new AdministrationHandler();
		redeEntity = new Tabelas_Rede();

		try {
			redeEntity.setNome(this.getNomeRede());
			administrationHandler.saveNewRede(redeEntity);
			this.setNomeRede("");
			this.getAllRedes();
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Atualiza a Tela
	 */

	public void atualizaTela() {
		this.setAtualizarLoja(false);
		this.setMostrarLoja(false);
		this.setMostrarLojaUpd(true);
		try {
			this.setLojaEntity(this.getListLojas().get(
					this.getLojasDataTable().getRowIndex()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pega Redes
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void getRedesByName() throws CpsGeneralExceptions {
		if (this.getNomeRede() == null || "".equals(this.getNomeRede())) {
			return;
		}
		// listRedes = redeDao.obterPorNome(this.getNomeRede());
		administrationHandler = new AdministrationHandler();
		try {
			listRedes = administrationHandler.getAllRedes(this.getNomeRede());
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Pega Lojas
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void getLojasByName() throws CpsGeneralExceptions {
		if (this.getNomeLoja() == null || "".equals(this.getNomeLoja())) {
			return;
		}
		// listLojas = lojaDao.obterLojaPorNomeFantasia(this.getNomeLoja());
		administrationHandler = new AdministrationHandler();
		try {
			listLojas = administrationHandler.getAllLojas(this.getNomeLoja());
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Valida CNPJ
	 */

	public void cnpjOk() {
		try {
			if (CommonOperations.cnpjExists(this.getLojaEntity().getCnpj(),
					this.getLojaEntity())) {
				this.setCnpj_valido(false);
				return;
			}
			this.setCnpj_valido(true);
		} catch (CpsGeneralExceptions e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */

	public void openLoja() {
		if (Integer.parseInt(this.getRedeSelecionada()) == 0) {
			this.setMostrarLoja(false);
			this.setMostrarCadastroRede(true);
			return;
		}
		this.setMostrarLoja(true);
		this.setMostrarCadastroRede(false);
	}

	/**
	 * 
	 * @return
	 * @throws CpsGeneralExceptions
	 */

	public SelectItem[] getAllRedes() throws CpsGeneralExceptions {
		int index = 0;
		// List<Tabelas_Rede> todasRedes = redeDao.listarTodos();
		// redes = new SelectItem[todasRedes.size()+1];
		// redes[index] = new SelectItem(0,"Selecione...");;
		// for(Tabelas_Rede rede: todasRedes){
		// index ++;
		// redes[index] = new SelectItem(rede.getId(),rede.getNome());
		// }
		// return redes;
		try {
			administrationHandler = new AdministrationHandler();
			List<Tabelas_Rede> list = administrationHandler.getAllRedes();
			redes = new SelectItem[list.size() + 1];
			redes[index] = new SelectItem(0, "Selecione...");
			;
			for (Tabelas_Rede rede : list) {
				index++;
				redes[index] = new SelectItem(rede.getId(), rede.getNome());
			}
			return redes;
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Seleciona Operação
	 */

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
		case 1: // Cadastrar Nova Loja
			this.setNomeRede("");
			this.setCadastrarLoja(true);
			this.setAtualizarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			this.setMostrarLojaUpd(false);
			this.setLojaEntity(null);
			this.setRedeEntity(null);
			break;
		case 2: // Gerenciar Lojas Cadastradas
			this.setNomeLoja("");
			this.setListLojas(null);
			this.setAtualizarLoja(true);
			this.setCadastrarLoja(false);
			this.setAtualizarRede(false);
			this.setMostrarLoja(false);
			this.setLojaEntity(null);
			this.setRedeEntity(null);
			break;
		case 3: // Gerenciar Redes Cadastradas
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

	/**
	 * Pega Usuarios
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void findUsers() throws CpsGeneralExceptions {
		try {
			administrationHandler = new AdministrationHandler();
			allLogins = administrationHandler.getAllLogins(this
					.getLoginEntity().getNomeLogin());
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Exclui Login
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void deleteLogin() throws CpsGeneralExceptions {
		try {
			Tabelas_Login newLogin = (Tabelas_Login) this.getRichDataTable()
					.getRowData();
			administrationHandler = new AdministrationHandler();
			this.allLogins.remove(newLogin);
			administrationHandler.deleteLogin(newLogin);
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * 
	 */

	public void find() {
		FindAddress findAddress = new FindAddress();
		try {
			findAddress.find(this.getLojaEntity().getCep());
			this.getLojaEntity().setEstado(findAddress.getEstadoSigla());
			this.getLojaEntity().setCidade(findAddress.getCidade());
			this.getLojaEntity().setBairro(findAddress.getBairro());
			this.getLojaEntity().setLogradouro(findAddress.getLogradouro());
			this.getLojaEntity().setPais(findAddress.getPais());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void convertToObj() throws CpsGeneralExceptions {
		administrationHandler = new AdministrationHandler();
		Integer index = new Integer(this.getRedeSelecionada());
		try {
			for (SelectItem item : redes) {
				if (item.getValue().equals(index)) {
					this.setRedeEntity(administrationHandler.getRede(item
							.getLabel()));
					this.setLojaEntity(new Tabelas_Loja());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cadastra Loja
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void cadastraLoja() throws CpsGeneralExceptions {
		if (!this.isCnpj_valido()) {
			return;
		}
		administrationHandler = new AdministrationHandler();
		try {
			administrationHandler.saveNewLoja(this.getLojaEntity(), this
					.getRedeEntity());
			this.limpaCampos();
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Atualiza Rede
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void atualizaRede() throws CpsGeneralExceptions {
		this
				.setRedeEntity((Tabelas_Rede) this.getRedesDataTable()
						.getRowData());
		if (this.getRedeEntity().getNome() == null
				|| this.getRedeEntity().getNome().equals("")) {
			FacesUtil.errorMessage("", Resources.getErrorProperties()
					.getString("rede_invalid_name"), "nome de rede vazio");
			return;
		}
		// redeDao.update(this.getRedeEntity());
		// this.setNomeRede("");
		try {
			administrationHandler = new AdministrationHandler();
			administrationHandler.updateRede(this.getRedeEntity());
			this.setNomeRede("");
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Atualiza Loja
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void atualizaLoja() throws CpsGeneralExceptions {
		if (!this.isCnpj_valido()) {
			return;
		}
		// lojaDao.update(this.getLojaEntity());
		// this.limpaCampos();
		// this.setMostrarLojaUpd(false);
		administrationHandler = new AdministrationHandler();
		try {
			administrationHandler.updateLoja(this.getLojaEntity());
			this.limpaCampos();
			this.setMostrarLojaUpd(false);

		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Exclui Rede
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void excluirRede() throws CpsGeneralExceptions {
		// this.setRedeEntity((Tabelas_Rede)
		// this.getRedesDataTable().getRowData());
		// this.listRedes.remove(this.getRedeEntity());
		// redeDao.excluir(this.getRedeEntity());
		administrationHandler = new AdministrationHandler();
		this
				.setRedeEntity((Tabelas_Rede) this.getRedesDataTable()
						.getRowData());
		try {
			this.listRedes.remove(this.getRedeEntity());
			administrationHandler.excluirRede(this.getRedeEntity());
		} catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Exclui Loja
	 * 
	 * @throws CpsGeneralExceptions
	 */

	public void excluirLoja() throws CpsGeneralExceptions {
//		this.setLojaEntity((Tabelas_Loja) this.getLojasDataTable().getRowData());
//		this.listLojas.remove(this.getLojaEntity());
//		lojaDao.excluir(this.getLojaEntity());
		administrationHandler = new AdministrationHandler();
		this.setLojaEntity((Tabelas_Loja) this.getLojasDataTable().getRowData());
		try {
			this.listLojas.remove(this.getLojaEntity());
			administrationHandler.excluirLoja(this.getLojaEntity());
		}catch (CpsHandlerException e) {		
			throw new CpsGeneralExceptions(e);
		}
	}

	/**
	 * Limpa Campos
	 */

	private void limpaCampos() {
		this.setRedeEntity(null);
		this.setLojaEntity(null);
	}

	/**
	 * 
	 * @param loginEntity
	 */

	public void setLoginEntity(Tabelas_Login loginEntity) {
		this.loginEntity = loginEntity;
	}

	/**
	 * 
	 * @return loginEntity
	 */

	public Tabelas_Login getLoginEntity() {
		return loginEntity;
	}

	/**
	 * 
	 * @param allLogins
	 */

	public void setAllLogins(List<Tabelas_Login> allLogins) {
		this.allLogins = allLogins;
	}

	/**
	 * 
	 * @return allLogins
	 */

	public List<Tabelas_Login> getAllLogins() {
		return allLogins;
	}

	/**
	 * 
	 * @param richDataTable
	 */

	public void setRichDataTable(HtmlDataTable richDataTable) {
		this.richDataTable = richDataTable;
	}

	/**
	 * 
	 * @return richDataTable
	 */

	public HtmlDataTable getRichDataTable() {
		return richDataTable;
	}

	/**
	 * @return the cadastrarLoja
	 */
	public boolean isCadastrarLoja() {
		return cadastrarLoja;
	}

	/**
	 * @param cadastrar
	 *            the cadastrarLoja to set
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
	 * @param items
	 *            the items to set
	 */
	public void setItems(SelectItem[] items) {
		this.items = items;
	}

	/**
	 * 
	 * @param itemSelecionado
	 */

	public void setItemSelecionado(String itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	/**
	 * 
	 * @return itemSelecionado
	 */

	public String getItemSelecionado() {
		return itemSelecionado;
	}

	/**
	 * 
	 * @param redeSelecionada
	 */

	public void setRedeSelecionada(String redeSelecionada) {
		this.redeSelecionada = redeSelecionada;
	}

	/**
	 * 
	 * @return redeSelecionada
	 */

	public String getRedeSelecionada() {
		return redeSelecionada;
	}

	/**
	 * 
	 * @param mostrarLoja
	 */

	public void setMostrarLoja(boolean mostrarLoja) {
		this.mostrarLoja = mostrarLoja;
	}

	/**
	 * 
	 * @return mostrarLoja
	 */

	public boolean isMostrarLoja() {
		return mostrarLoja;
	}

	/**
	 * 
	 * @param mostrarCadastroRede
	 */

	public void setMostrarCadastroRede(boolean mostrarCadastroRede) {
		this.mostrarCadastroRede = mostrarCadastroRede;
	}

	/**
	 * 
	 * @return mostrarCadastroRede
	 */

	public boolean isMostrarCadastroRede() {
		return mostrarCadastroRede;
	}

	/**
	 * 
	 * @param tipoVendaSelecionada
	 */

	public void setTipoVendaSelecionada(String tipoVendaSelecionada) {
		this.tipoVendaSelecionada = tipoVendaSelecionada;
	}

	/**
	 * 
	 * @return tipoVendaSelecionada
	 */

	public String getTipoVendaSelecionada() {
		return tipoVendaSelecionada;
	}

	/**
	 * 
	 * @param tipoVendas
	 */

	public void setTipoVendas(SelectItem[] tipoVendas) {
		this.tipoVendas = tipoVendas;
	}

	/**
	 * 
	 * @return tipoVendas
	 */

	public SelectItem[] getTipoVendas() {
		return tipoVendas;
	}

	/**
	 * 
	 * @param lojaEntity
	 */

	public void setLojaEntity(Tabelas_Loja lojaEntity) {
		this.lojaEntity = lojaEntity;
	}

	/**
	 * 
	 * @return lojaEntity
	 */

	public Tabelas_Loja getLojaEntity() {
		return lojaEntity;
	}

	/**
	 * 
	 * @param mensagemCampoObrigatorio
	 */

	public void setMensagemCampoObrigatorio(String mensagemCampoObrigatorio) {
		this.mensagemCampoObrigatorio = mensagemCampoObrigatorio;
	}

	/**
	 * 
	 * @return mensagemCampoObrigatorio
	 */

	public String getMensagemCampoObrigatorio() {
		return mensagemCampoObrigatorio;
	}

	/**
	 * 
	 * @param nomeRede
	 */

	public void setNomeRede(String nomeRede) {
		this.nomeRede = nomeRede;
	}

	/**
	 * 
	 * @return nomeRede
	 */

	public String getNomeRede() {
		return nomeRede;
	}

	/**
	 * 
	 * @param mensagem_cnpj
	 */

	public void setMensagem_cnpj(String mensagem_cnpj) {
		this.mensagem_cnpj = mensagem_cnpj;
	}

	/**
	 * 
	 * @return mensagem_cnpj
	 */

	public String getMensagem_cnpj() {
		return mensagem_cnpj;
	}

	/**
	 * 
	 * @param cnpj_valido
	 */

	public void setCnpj_valido(boolean cnpj_valido) {
		this.cnpj_valido = cnpj_valido;
	}

	/**
	 * 
	 * @return cnpj_valido
	 */

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
	 * @param redeEntity
	 *            the redeEntity to set
	 */
	public void setRedeEntity(Tabelas_Rede redeEntity) {
		this.redeEntity = redeEntity;
	}

	/**
	 * 
	 * @param listRedes
	 */

	public void setListRedes(List<Tabelas_Rede> listRedes) {
		this.listRedes = listRedes;
	}

	/**
	 * 
	 * @return listRedes
	 */

	public List<Tabelas_Rede> getListRedes() {
		return listRedes;
	}

	/**
	 * 
	 * @param redesDataTable
	 */

	public void setRedesDataTable(HtmlDataTable redesDataTable) {
		this.redesDataTable = redesDataTable;
	}

	/**
	 * 
	 * @return redesDataTable
	 */

	public HtmlDataTable getRedesDataTable() {
		return redesDataTable;
	}

	/**
	 * 
	 * @param atualizarLoja
	 */

	public void setAtualizarLoja(boolean atualizarLoja) {
		this.atualizarLoja = atualizarLoja;
	}

	/**
	 * 
	 * @return atualizarLoja
	 */

	public boolean isAtualizarLoja() {
		return atualizarLoja;
	}

	/**
	 * 
	 * @param atualizarRede
	 */

	public void setAtualizarRede(boolean atualizarRede) {
		this.atualizarRede = atualizarRede;
	}

	/**
	 * 
	 * @return atualizarRede
	 */

	public boolean isAtualizarRede() {
		return atualizarRede;
	}

	/**
	 * 
	 * @param nomeLoja
	 */

	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}

	/**
	 * 
	 * @return nomeLoja
	 */

	public String getNomeLoja() {
		return nomeLoja;
	}

	/**
	 * 
	 * @param listLojas
	 */

	public void setListLojas(List<Tabelas_Loja> listLojas) {
		this.listLojas = listLojas;
	}

	/**
	 * 
	 * @return listLojas
	 */

	public List<Tabelas_Loja> getListLojas() {
		return listLojas;
	}

	/**
	 * 
	 * @param lojasDataTable
	 */

	public void setLojasDataTable(HtmlDataTable lojasDataTable) {
		this.lojasDataTable = lojasDataTable;
	}

	/**
	 * 
	 * @return lojasDataTable
	 */

	public HtmlDataTable getLojasDataTable() {
		return lojasDataTable;
	}

	/**
	 * 
	 * @param mostrarLojaUpd
	 */

	public void setMostrarLojaUpd(boolean mostrarLojaUpd) {
		this.mostrarLojaUpd = mostrarLojaUpd;
	}

	/**
	 * 
	 * @return mostrarLojaUpd
	 */

	public boolean isMostrarLojaUpd() {
		return mostrarLojaUpd;
	}
}
