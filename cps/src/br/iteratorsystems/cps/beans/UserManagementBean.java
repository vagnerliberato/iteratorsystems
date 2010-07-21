package br.iteratorsystems.cps.beans;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.CEP;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.PARAMETRIZACAO_CPS;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.LoginUserHandler;
import br.iteratorsystems.cps.handler.UserManagementHandler;

public class UserManagementBean {

	private UserManagementHandler userHandler = null;
	private USUARIO usuarioEntity = new USUARIO();
	private CEP cepEntity = new CEP();
	private ENDERECO enderecoEntity = new ENDERECO();
	private LOGIN loginEntity = new LOGIN();
	private PARAMETRIZACAO_CPS parametrizacao;
	
	private String estadoSigla;
	private String put_senha_antiga;
	private String nova_senha;
	private String confirma_nova_senha;
	
	private String mensagem_username;
	private String mensagem_password;
	private String mensagem_cpf;
	private String mensagem_senha_antiga;
	
	private boolean firstAccess = false;
	private boolean validUsername = true;
	private boolean validCpf = true;
	private boolean validPassword = true;
	private boolean validOldPassword = true;
	private boolean administrador = false;

	protected FacesContext context = FacesContext.getCurrentInstance();
	protected ELResolver el = context.getApplication().getELResolver();
	
	//o faces ainda não possui um PERFEITO mecanismo de mensagens, então vai na mão!
	public static final String[] MENSAGENS_JSF = 
			{"Nome de usuário já cadastrado na base de dados",
			"E-mail já cadastrado na base de dados",
			"A confirmação da senha não confere!",
			"CPF já cadastrado em nossa base de dados",
			"A senha antiga está incorreta!"};
	
	/**
	 * Bean default.
	 */
	public UserManagementBean() {
		this.parametrizacao = obtemParametrizacao();
		passaOuRepassa();
	}

	/**
	 * Obtém a parametrização do sistema.
	 * @return  Classe de parametrização.
	 */
	private PARAMETRIZACAO_CPS obtemParametrizacao() {
		PARAMETRIZACAO_CPS parametrizacao = (PARAMETRIZACAO_CPS) context.getExternalContext().getApplicationMap().get("parametrizacao");
		return parametrizacao;
	}
	
	//nome criativo não? kkkk
	public void passaOuRepassa(){
		LoginUserBean newLoginUserInstance = null;
		
		try{
			newLoginUserInstance = (LoginUserBean) el.getValue(context.getELContext(),null,"loginUserBean");
			this.setFirstAccess(newLoginUserInstance.isFirstAccess());
			
			if(this.firstAccess){
				this.getEnderecoEntity().setCep(newLoginUserInstance.getCep());
				//this.getEnderecoEntity().getCep().setCep();
				this.getUsuarioEntity().setEmail(newLoginUserInstance.getEmail());
				
				newLoginUserInstance.setEmail(null);
				newLoginUserInstance.setCep(null);
				
				this.find();
			} else{
				this.atualizaCampos();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validatePassword(){
		boolean valid = false;
		if(this.firstAccess){
			if(!this.getNova_senha().equals(this.getLoginEntity().getSenha())){
				this.setValidPassword(false);
				this.setMensagem_password(MENSAGENS_JSF[2]);
			}else {
				valid = true;
				this.setValidPassword(true);
			}
		}else{
			if(!this.getNova_senha().equals(this.getConfirma_nova_senha())){
				this.setValidPassword(false);
				this.setMensagem_password(MENSAGENS_JSF[2]);
			}else{
				valid = true;
				this.setValidPassword(true);
			}
		}
		return valid;
	}
	
	public boolean validateOldPassword() {
		LoginUserHandler newHandler = new LoginUserHandler();
		boolean newValid = false;
		try {
			newValid = newHandler.checkPassword(this.getPut_senha_antiga());
		} catch (CpsHandlerException e) {
			e.printStackTrace();
		}
		return newValid;
	}
	
	public void find() {
		FindAddress findAddress = new FindAddress();
		try{
			findAddress.find(this.getEnderecoEntity().getCep());
			this.enderecoEntity.setLogradouro(findAddress.getLogradouro());
			this.enderecoEntity.setBairro(findAddress.getBairro());
			this.enderecoEntity.setCidade(findAddress.getCidade());
			this.enderecoEntity.setEstado(findAddress.getEstado());
			this.enderecoEntity.setPais(findAddress.getPais());
			this.setEstadoSigla(findAddress.getEstadoSigla());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean userExists() throws CpsGeneralExceptions{
		if(CommonOperations.userExists(this.getLoginEntity().getNomeLogin())){
			this.setValidUsername(false);
			this.setMensagem_username(MENSAGENS_JSF[0]);
			return false;
		}else{
			this.setValidUsername(true);
			return true;
		}
	}
	
	public boolean cpfExists() throws CpsGeneralExceptions{
		if(CommonOperations.cpfExists(this.getUsuarioEntity().getCpfUsuario().replace(".","").replace("-",""))){
			this.setMensagem_cpf(MENSAGENS_JSF[3]);
			this.setValidCpf(false);
			return true;
		}
		else{
			this.setValidCpf(true);
			return false;
		}
	}
	
	public boolean cpfExists(String cpf,String username) throws CpsGeneralExceptions{
		if(CommonOperations.cpfExists(cpf,username)){
			this.setMensagem_cpf(MENSAGENS_JSF[3]);
			this.setValidCpf(false);
			return true;
		}
		else{
			this.setValidCpf(true);
			return false;
		}
	}
	
	// TODO Fazer limpar o formulario!
	public void limpa() {
		this.setLoginEntity(null);
		this.setUsuarioEntity(null);
		this.setEnderecoEntity(null);
	}
	
	public String salva() throws CpsGeneralExceptions {
		if(!this.validatePassword())
			return "";
		
		if(!this.validUsername)
			return "";
		
		if(this.cpfExists(this.getUsuarioEntity().getCpfUsuario().replace(".","").replace("-",""),this.getLoginEntity().getNomeLogin()))
			return "";
		
		userHandler = new UserManagementHandler();
		enderecoEntity.setEstado(this.getEstadoSigla());

		try{
			userHandler.save(usuarioEntity,loginEntity,enderecoEntity);
			
			this.limpa();
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("user_registered"),"usuario cadastrado");
			return "toLoginPage";
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}

	public void atualiza() throws CpsGeneralExceptions{

		if(!"".equals(this.getPut_senha_antiga())){
			if(!this.validateOldPassword()) {
				this.setValidOldPassword(false);
				this.setMensagem_senha_antiga(MENSAGENS_JSF[4]);
				return;
			}else{
				if(!this.validatePassword()) {
					return;
				}
				this.setValidOldPassword(true);
				this.getLoginEntity().setSenha(this.getConfirma_nova_senha());
			}
		}

		if(!this.validUsername)
			return;
		
		if(this.cpfExists(usuarioEntity.getCpfUsuario(),loginEntity.getNomeLogin()))
			return;
		
		userHandler = new UserManagementHandler();
		try{
			userHandler.update(this.getUsuarioEntity(),this.getLoginEntity(),this.getEnderecoEntity());
		}catch (CpsGeneralExceptions e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void atualizaCampos(){
		//recebe do faces config o parametro da classe de login, com suas respectivas propiedades
		LoginUserBean newLoginUserInstance = (LoginUserBean) el.getValue(context.getELContext(),null,"loginUserBean");
		this.usuarioEntity = newLoginUserInstance.getUsuario();
		if(this.usuarioEntity == null) return;
		//solução paliativa
		for(ENDERECO endereco : this.getUsuarioEntity().getEnderecos()){
			this.enderecoEntity = endereco;
		}
		for(LOGIN login : this.getUsuarioEntity().getLogins()){
			this.loginEntity = login;
		}
		this.setAdministrador(this.getLoginEntity().getTipoUsuario() == 'A' ? true : false);
	}
	
	public void atualizaCampos(USUARIO paramUser){
		this.usuarioEntity = paramUser;
		//solução paliativa
		for(ENDERECO endereco : this.getUsuarioEntity().getEnderecos()){
			this.enderecoEntity = endereco;
		}
		for(LOGIN login : this.getUsuarioEntity().getLogins()){
			this.loginEntity = login;
		}
		this.setFirstAccess(false);
		this.setAdministrador(this.getLoginEntity().getTipoUsuario() == 'A' ? true : false);
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

	public void setFirstAccess(boolean firstAccess) {
		this.firstAccess = firstAccess;
	}

	public boolean isFirstAccess() {
		return firstAccess;
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

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setMensagem_cpf(String mensagem_cpf) {
		this.mensagem_cpf = mensagem_cpf;
	}

	public String getMensagem_cpf() {
		return mensagem_cpf;
	}

	public void setValidCpf(boolean validCpf) {
		this.validCpf = validCpf;
	}

	public boolean isValidCpf() {
		return validCpf;
	}

	public void setMensagem_senha_antiga(String mensagem_senha_antiga) {
		this.mensagem_senha_antiga = mensagem_senha_antiga;
	}

	public String getMensagem_senha_antiga() {
		return mensagem_senha_antiga;
	}

	/**
	 * @return the validOldPassword
	 */
	public boolean isValidOldPassword() {
		return validOldPassword;
	}

	/**
	 * @param validOldPassword the validOldPassword to set
	 */
	public void setValidOldPassword(boolean validOldPassword) {
		this.validOldPassword = validOldPassword;
	}

	/**
	 * @return the usuarioEntity
	 */
	public USUARIO getUsuarioEntity() {
		return usuarioEntity;
	}

	/**
	 * @param usuarioEntity the usuarioEntity to set
	 */
	public void setUsuarioEntity(USUARIO usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

	/**
	 * @return the cepEntity
	 */
	public CEP getCepEntity() {
		return cepEntity;
	}

	/**
	 * @param cepEntity the cepEntity to set
	 */
	public void setCepEntity(CEP cepEntity) {
		this.cepEntity = cepEntity;
	}

	/**
	 * @return the enderecoEntity
	 */
	public ENDERECO getEnderecoEntity() {
		return enderecoEntity;
	}

	/**
	 * @param enderecoEntity the enderecoEntity to set
	 */
	public void setEnderecoEntity(ENDERECO enderecoEntity) {
		this.enderecoEntity = enderecoEntity;
	}

	/**
	 * @return the loginEntity
	 */
	public LOGIN getLoginEntity() {
		return loginEntity;
	}

	/**
	 * @param loginEntity the loginEntity to set
	 */
	public void setLoginEntity(LOGIN loginEntity) {
		this.loginEntity = loginEntity;
	}

	public void setPut_senha_antiga(String put_senha_antiga) {
		this.put_senha_antiga = put_senha_antiga;
	}

	public String getPut_senha_antiga() {
		return put_senha_antiga;
	}

	public void setConfirma_nova_senha(String confirma_nova_senha) {
		this.confirma_nova_senha = confirma_nova_senha;
	}

	public String getConfirma_nova_senha() {
		return confirma_nova_senha;
	}

	/**
	 * @param parametrizacao the parametrizacao to set
	 */
	public void setParametrizacao(PARAMETRIZACAO_CPS parametrizacao) {
		this.parametrizacao = parametrizacao;
	}

	/**
	 * @return the parametrizacao
	 */
	public PARAMETRIZACAO_CPS getParametrizacao() {
		return parametrizacao;
	}
}
