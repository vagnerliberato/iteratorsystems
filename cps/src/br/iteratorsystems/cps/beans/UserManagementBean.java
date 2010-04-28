package br.iteratorsystems.cps.beans;

import java.util.Collection;
import java.util.Date;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.CEP;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.handler.UserManagementHandler;

public class UserManagementBean {

	private String nome;
	private String sobrenome;
	private Date aniversario;
	private String cpf;
	private String rg;
	private String orgao_expeditor;
	private String email;
	private String cep;
	private String logradouro;
	private String numero_res;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String estadoSigla;
	private String ddd_tel_res;
	private String tel_res;
	private String ddd_tel_cel;
	private String tel_cel;
	private String username;
	private String senha_antiga;
	private String newSenhaAntiga;
	private String nova_senha;
	private String confirma_senha;
	private String mensagem_username;
	private String mensagem_password;

	private FindAddress findAddress;
	private UserManagementHandler userHandler;
	private USUARIO usuario;
	private CEP cepEntity;
	private ENDERECO endereco;
	private LOGIN login;
	
	private boolean firstAccess;
	private boolean validUsername = true;
	private boolean validPassword = true;
	
	private static final char tipoUsuario = 'P';

	public UserManagementBean() {}

	public static final String[] msgs_informacao = {
			"Nome de usuário já cadastrado na base de dados",
			"E-mail já cadastrado na base de dados",
			"A confirmação da senha não confere!"};
	
	public boolean validatePassword(){
		boolean valid = false;
		if(this.firstAccess){
			if(!this.getNova_senha().equals(this.getConfirma_senha())){
				this.setValidPassword(false);
				this.setMensagem_password(msgs_informacao[2]);
			}else {
				valid = true;
			}
		}
		return valid;
	}
	
	public void find() {
		findAddress = new FindAddress();
		findAddress.find(this.getCep());

		this.setLogradouro(findAddress.getLogradouro());
		this.setBairro(findAddress.getBairro());
		this.setCidade(findAddress.getCidade());
		this.setEstado(findAddress.getEstado());
		this.setEstadoSigla(findAddress.getEstadoSigla());
		findAddress = null;
	}

	public String novo() throws CpsGeneralExceptions {
		String regex = "[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z\\.a-zA-Z]+";

		if (this.getEmail() == null || this.getCep() == null
				|| "".equals(this.getEmail()) || "".equals(this.getCep())) {
			return "";
		}

		if (this.getEmail().matches(regex)) {
			if(this.mailExists()){
				FacesUtil.errorMessage("", Resources.getErrorProperties().getString("email_exists"), "email já cadastrado");
				return "";
			}
			this.find();
			this.setFirstAccess(true);
			return "toCadUser";
		} else {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("invalid_email"), "email invalido");
			return "";
		}
	}

	// TODO Fazer limpar o formulario!
	public String limpa() {
		this.cep = "";
		this.nome = "";
		this.setComplemento("");
		this.nova_senha = "";
		return null;
	}

	public boolean mailExists() throws CpsGeneralExceptions{
		userHandler  = new UserManagementHandler();
		Collection<USUARIO> list =  userHandler.getAllUser();
		
		for(USUARIO user: list){
			if(user.getEmail().equalsIgnoreCase(this.getEmail()))
				return true;
		}
		return false;
	}
	
	public boolean userExists() throws CpsGeneralExceptions {
		final String nomeusuario = this.getUsername();
		
		userHandler = new UserManagementHandler();
		Collection<LOGIN> list = userHandler.getAllLogin();
		
		for(LOGIN lo : list){
			if(lo.getNomeLogin().equalsIgnoreCase(nomeusuario)) {
				mensagem_username = msgs_informacao[0];
				this.setValidUsername(false);
				return true;
			}
		}
		this.setValidUsername(true);
		return false;
	}

	public void salva() throws CpsGeneralExceptions {
		
		if(!this.validatePassword())
			return;
		
		if(!this.validUsername)
			return;
		
		usuario = new USUARIO();
		endereco = new ENDERECO();
		login = new LOGIN();
		cepEntity = new CEP();
		
		usuario.setNomeUsuario(this.getNome());
		usuario.setSobrenomeUsuario(this.getSobrenome());
		usuario.setCpfUsuario(this.getCpf().replace(".","").replace("-",""));
		usuario.setRgUsuario(this.getRg());
		usuario.setOrgaoEspedidorUsu(this.getOrgao_expeditor());
		usuario.setDddRes(this.getDdd_tel_res());
		usuario.setTelRes(this.getTel_res().replace("-",""));
		usuario.setDddCel(this.getDdd_tel_cel());
		usuario.setTelCel(this.getTel_cel().replace("-",""));
		usuario.setEmail(this.getEmail().toLowerCase());
		usuario.setDataultimamodificacao(new Date());
		
		login.setNomeLogin(this.getUsername());
		login.setSenha(this.getNova_senha());
		login.setTipoUsuario(tipoUsuario);
		
		cepEntity.setCep(this.getCep().replace("-",""));
		
		endereco.setEstado(this.getEstadoSigla());
		endereco.setCidade(this.getCidade());
		endereco.setBairro(this.getBairro());
		endereco.setLogradouro(this.getLogradouro());
		endereco.setNumero(this.getNumero_res());
		endereco.setComplemeto(this.getComplemento());
		endereco.setPais("BRASIL");
		endereco.setDataultimamodificacao(new Date());
		endereco.setCep(cepEntity);
		
		userHandler = new UserManagementHandler();
		userHandler.save(usuario,login,endereco);
	}

	public void atualiza() {
		
	}

	public void getDadosUsuario(LOGIN newLogin,USUARIO newUsuario,ENDERECO newEndereco){
		this.login = newLogin;
		this.usuario = newUsuario;
		this.endereco = newEndereco;
	}
	
	public void atualizaCampos(){
		//recebe do faces config o parametro da classe de login, com suas respectivas propiedades
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver el = context.getApplication().getELResolver();
		LoginUserBean newLoginUserInstance = (LoginUserBean) el.getValue(context.getELContext(),null,"loginUserBean");
		
		//inserindo os valores da classe de login nos campos apropiados para o usuario
		this.setNome(newLoginUserInstance.getUsuario().getNomeUsuario());
		this.setSobrenome(newLoginUserInstance.getUsuario().getSobrenomeUsuario());
		//this.setAniversario(newLoginUserInstance.getUsuario().get)
		this.setCpf(newLoginUserInstance.getUsuario().getCpfUsuario());
		this.setRg(newLoginUserInstance.getUsuario().getRgUsuario());
		this.setOrgao_expeditor(newLoginUserInstance.getUsuario().getOrgaoEspedidorUsu());
		//inserindo os valores da classe de login nos campos apropiados para o contato
		this.setEmail(newLoginUserInstance.getUsuario().getEmail());
		this.setCep(newLoginUserInstance.getEndereco().getCep().getCep());
		this.find();
		this.setNumero_res(newLoginUserInstance.getEndereco().getNumero());
		this.setDdd_tel_res(newLoginUserInstance.getUsuario().getDddRes());
		this.setTel_res(newLoginUserInstance.getUsuario().getTelRes());
		this.setDdd_tel_cel(newLoginUserInstance.getUsuario().getDddCel());
		this.setTel_cel(newLoginUserInstance.getUsuario().getTelCel());
		this.setUsername(newLoginUserInstance.getLogin().getNomeLogin());
		this.setNewSenhaAntiga(newLoginUserInstance.getLogin().getSenha());
	}
	
	public boolean validateForm() {
		if (this.firstAccess) {
			if (!this.validUsername) {
				return false;
			}
			if (this.getNova_senha() == null
					|| this.getConfirma_senha() == null
					|| "".equals(this.getNova_senha())
					|| "".equals(this.getConfirma_senha())) {
				FacesUtil.errorMessage("", Resources.getErrorProperties().getString("empty_password_field"),
						"preencha campo senha");
				return false;
			}
			if (!this.getConfirma_senha().equals(this.getNova_senha())) {
				FacesUtil.errorMessage("", Resources.getErrorProperties().getString("wrong_password_confirmation"),"confirmaçao incorreta");
				return false;
			}
			return true;
		}
		if (!this.firstAccess) {
			if (this.getSenha_antiga() != null
					|| !"".equals(this.getSenha_antiga())) {
				if (this.getNova_senha() != null
						&& this.getConfirma_senha() != null) {
					if (!this.getNova_senha().equals(this.getConfirma_senha())) {
						FacesUtil.errorMessage("", Resources.getErrorProperties().getString("wrong_password_confirmation"),"confirmaçao incorreta");
						return false;
					}
				}
				return true;
			}
		}
		return true;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * @param sobrenome
	 *            the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/**
	 * @return the aniversario
	 */
	public Date getAniversario() {
		return aniversario;
	}

	/**
	 * @param aniversario
	 *            the aniversario to set
	 */
	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg
	 *            the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the orgao_expeditor
	 */
	public String getOrgao_expeditor() {
		return orgao_expeditor;
	}

	/**
	 * @param orgaoExpeditor
	 *            the orgao_expeditor to set
	 */
	public void setOrgao_expeditor(String orgaoExpeditor) {
		orgao_expeditor = orgaoExpeditor;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the ddd_tel_res
	 */
	public String getDdd_tel_res() {
		return ddd_tel_res;
	}

	/**
	 * @param dddTelRes
	 *            the ddd_tel_res to set
	 */
	public void setDdd_tel_res(String dddTelRes) {
		ddd_tel_res = dddTelRes;
	}

	/**
	 * @return the tel_res
	 */
	public String getTel_res() {
		return tel_res;
	}

	/**
	 * @param telRes
	 *            the tel_res to set
	 */
	public void setTel_res(String telRes) {
		tel_res = telRes;
	}

	/**
	 * @return the ddd_tel_cel
	 */
	public String getDdd_tel_cel() {
		return ddd_tel_cel;
	}

	/**
	 * @param dddTelCel
	 *            the ddd_tel_cel to set
	 */
	public void setDdd_tel_cel(String dddTelCel) {
		ddd_tel_cel = dddTelCel;
	}

	/**
	 * @return the tel_cel
	 */
	public String getTel_cel() {
		return tel_cel;
	}

	/**
	 * @param telCel
	 *            the tel_cel to set
	 */
	public void setTel_cel(String telCel) {
		tel_cel = telCel;
	}

	/**
	 * @return the senha_antiga
	 */
	public String getSenha_antiga() {
		return senha_antiga;
	}

	/**
	 * @param senhaAntiga
	 *            the senha_antiga to set
	 */
	public void setSenha_antiga(String senhaAntiga) {
		senha_antiga = senhaAntiga;
	}

	/**
	 * @return the nova_senha
	 */
	public String getNova_senha() {
		return nova_senha;
	}

	/**
	 * @param novaSenha
	 *            the nova_senha to set
	 */
	public void setNova_senha(String novaSenha) {
		nova_senha = novaSenha;
	}

	/**
	 * @return the confirma_senha
	 */
	public String getConfirma_senha() {
		return confirma_senha;
	}

	/**
	 * @param confirmaSenha
	 *            the confirma_senha to set
	 */
	public void setConfirma_senha(String confirmaSenha) {
		confirma_senha = confirmaSenha;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro
	 *            the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setFirstAccess(boolean firstAccess) {
		this.firstAccess = firstAccess;
	}

	public boolean isFirstAccess() {
		return firstAccess;
	}

	public void setNumero_res(String numero_res) {
		this.numero_res = numero_res;
	}

	public String getNumero_res() {
		return numero_res;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setValidUsername(boolean validUsername) {
		this.validUsername = validUsername;
	}

	public boolean isValidUsername() {
		return validUsername;
	}

	public void setMensagem_username(String mensagem_username) {
		this.mensagem_username = mensagem_username;
	}

	public String getMensagem_username() {
		return mensagem_username;
	}

	public void setEstadoSigla(String estadoSigla) {
		this.estadoSigla = estadoSigla;
	}

	public String getEstadoSigla() {
		return estadoSigla;
	}

	public void setValidPassword(boolean validPassword) {
		this.validPassword = validPassword;
	}

	public boolean isValidPassword() {
		return validPassword;
	}

	public void setMensagem_password(String mensagem_password) {
		this.mensagem_password = mensagem_password;
	}

	public String getMensagem_password() {
		return mensagem_password;
	}

	public void setNewSenhaAntiga(String newSenhaAntiga) {
		this.newSenhaAntiga = newSenhaAntiga;
	}

	public String getNewSenhaAntiga() {
		return newSenhaAntiga;
	}
}
