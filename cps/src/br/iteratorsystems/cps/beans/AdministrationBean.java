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
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Loja;
import br.iteratorsystems.cps.entities.Rede;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.AdministrationHandler;

public class AdministrationBean {

	private Login loginEntity = new Login();
	private Rede rede;
	private Loja lojaEntity;
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

	private List<Login> allLogins;
	private List<Rede> listRedes;
	private List<Loja> listLojas;

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
	RedeDao redeDao = new RedeDao(Rede.class, session);
	LojaDao lojaDao = new LojaDao(Loja.class, session);

	/**
	 * Construtor
	 */

	public AdministrationBean() {
	}

	/**
	 * Cadastra Rede
	 * 
	 * @throws CpsExceptions
	 */

	public void cadastrarRede() throws CpsExceptions {
		if (this.getNomeRede() == null || this.getNomeRede().equals("")) {
			return;
		}

		administrationHandler = new AdministrationHandler();
		rede = new Rede();

		try {
			rede.setNome(this.getNomeRede());
			administrationHandler.saveNewRede(rede);
			this.setNomeRede("");
			this.getAllRedes();
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
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
	 * @throws CpsExceptions
	 */

	public void getRedesByName() throws CpsExceptions {
		if (this.getNomeRede() == null || "".equals(this.getNomeRede())) {
			return;
		}
		// listRedes = redeDao.obterPorNome(this.getNomeRede());
		administrationHandler = new AdministrationHandler();
		try {
			listRedes = administrationHandler.getAllRedes(this.getNomeRede());
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
		}
	}

	/**
	 * Pega Lojas
	 * 
	 * @throws CpsExceptions
	 */

	public void getLojasByName() throws CpsExceptions {
		if (this.getNomeLoja() == null || "".equals(this.getNomeLoja())) {
			return;
		}
		// listLojas = lojaDao.obterLojaPorNomeFantasia(this.getNomeLoja());
		administrationHandler = new AdministrationHandler();
		try {
			listLojas = administrationHandler.getAllLojas(this.getNomeLoja());
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
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
		} catch (CpsExceptions e) {
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
	 * @throws CpsExceptions
	 */

	public SelectItem[] getAllRedes() throws CpsExceptions {
		int index = 0;
		// List<Rede> todasRedes = redeDao.listarTodos();
		// redes = new SelectItem[todasRedes.size()+1];
		// redes[index] = new SelectItem(0,"Selecione...");;
		// for(Rede rede: todasRedes){
		// index ++;
		// redes[index] = new SelectItem(rede.getId(),rede.getNome());
		// }
		// return redes;
		try {
			administrationHandler = new AdministrationHandler();
			List<Rede> list = administrationHandler.getAllRedes();
			redes = new SelectItem[list.size() + 1];
			redes[index] = new SelectItem(0, "Selecione...");
			;
			for (Rede rede : list) {
				index++;
				redes[index] = new SelectItem(rede.getId(), rede.getNome());
			}
			return redes;
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
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
	 * @throws CpsExceptions
	 */

	public void findUsers() throws CpsExceptions {
		try {
			administrationHandler = new AdministrationHandler();
			allLogins = administrationHandler.getAllLogins(this
					.getLoginEntity().getNomeLogin());
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
		}
	}

	/**
	 * Exclui Login
	 * 
	 * @throws CpsExceptions
	 */

	public void deleteLogin() throws CpsExceptions {
		try {
			Login newLogin = (Login) this.getRichDataTable()
					.getRowData();
			administrationHandler = new AdministrationHandler();
			this.allLogins.remove(newLogin);
			administrationHandler.deleteLogin(newLogin);
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
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
	 * @throws CpsExceptions
	 */

	public void convertToObj() throws CpsExceptions {
		administrationHandler = new AdministrationHandler();
		Integer index = new Integer(this.getRedeSelecionada());
		try {
			for (SelectItem item : redes) {
				if (item.getValue().equals(index)) {
					this.setRedeEntity(administrationHandler.getRede(item
							.getLabel()));
					this.setLojaEntity(new Loja());
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
	 * @throws CpsExceptions
	 */

	public void cadastraLoja() throws CpsExceptions {
		if (!this.isCnpj_valido()) {
			return;
		}
		administrationHandler = new AdministrationHandler();
		try {
			administrationHandler.saveNewLoja(this.getLojaEntity(), this
					.getRedeEntity());
			this.limpaCampos();
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
		}
	}

	/**
	 * Atualiza Rede
	 * 
	 * @throws CpsExceptions
	 */

	public void atualizaRede() throws CpsExceptions {
		this
				.setRedeEntity((Rede) this.getRedesDataTable()
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
			throw new CpsExceptions(e);
		}
	}

	/**
	 * Atualiza Loja
	 * 
	 * @throws CpsExceptions
	 */

	public void atualizaLoja() throws CpsExceptions {
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
			throw new CpsExceptions(e);
		}
	}

	/**
	 * Exclui Rede
	 * 
	 * @throws CpsExceptions
	 */

	public void excluirRede() throws CpsExceptions {
		// this.setRedeEntity((Rede)
		// this.getRedesDataTable().getRowData());
		// this.listRedes.remove(this.getRedeEntity());
		// redeDao.excluir(this.getRedeEntity());
		administrationHandler = new AdministrationHandler();
		this
				.setRedeEntity((Rede) this.getRedesDataTable()
						.getRowData());
		try {
			this.listRedes.remove(this.getRedeEntity());
			administrationHandler.excluirRede(this.getRedeEntity());
		} catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
		}
	}

	/**
	 * Exclui Loja
	 * 
	 * @throws CpsExceptions
	 */

	public void excluirLoja() throws CpsExceptions {
//		this.setLojaEntity((Loja) this.getLojasDataTable().getRowData());
//		this.listLojas.remove(this.getLojaEntity());
//		lojaDao.excluir(this.getLojaEntity());
		administrationHandler = new AdministrationHandler();
		this.setLojaEntity((Loja) this.getLojasDataTable().getRowData());
		try {
			this.listLojas.remove(this.getLojaEntity());
			administrationHandler.excluirLoja(this.getLojaEntity());
		}catch (CpsHandlerException e) {		
			throw new CpsExceptions(e);
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

	public void setLoginEntity(Login loginEntity) {
		this.loginEntity = loginEntity;
	}

	/**
	 * 
	 * @return loginEntity
	 */

	public Login getLoginEntity() {
		return loginEntity;
	}

	/**
	 * 
	 * @param allLogins
	 */

	public void setAllLogins(List<Login> allLogins) {
		this.allLogins = allLogins;
	}

	/**
	 * 
	 * @return allLogins
	 */

	public List<Login> getAllLogins() {
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

	public void setLojaEntity(Loja lojaEntity) {
		this.lojaEntity = lojaEntity;
	}

	/**
	 * 
	 * @return lojaEntity
	 */

	public Loja getLojaEntity() {
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
	 * @return the rede
	 */
	public Rede getRedeEntity() {
		return rede;
	}

	/**
	 * @param rede
	 *            the rede to set
	 */
	public void setRedeEntity(Rede rede) {
		this.rede = rede;
	}

	/**
	 * 
	 * @param listRedes
	 */

	public void setListRedes(List<Rede> listRedes) {
		this.listRedes = listRedes;
	}

	/**
	 * 
	 * @return listRedes
	 */

	public List<Rede> getListRedes() {
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

	public void setListLojas(List<Loja> listLojas) {
		this.listLojas = listLojas;
	}

	/**
	 * 
	 * @return listLojas
	 */

	public List<Loja> getListLojas() {
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
