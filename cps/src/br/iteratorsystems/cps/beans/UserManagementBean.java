package br.iteratorsystems.cps.beans;

import java.util.HashSet;
import java.util.Set;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.Cep;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Parametrizacao;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.LoginUserHandler;
import br.iteratorsystems.cps.handler.UserManagementHandler;
import br.iteratorsystems.cps.helper.FormatadorEstadorHelper;

public class UserManagementBean {

	private UserManagementHandler userHandler = null;
	private Usuario usuarioEntity = new Usuario();
	private Cep cepEntity = new Cep();
	private Endereco enderecoEntity = new Endereco();
	private Login loginEntity = new Login();
	private Parametrizacao parametrizacao;
	
	private String estadoSigla;
	private String put_senha_antiga;
	private String nova_senha;
	private String confirma_nova_senha;
	
	private String mensagem_username;
	private String mensagem_password;
	private String mensagem_cpf;
	private String mensagem_senha_antiga;
	private String acaoBotaoIncluirNovoCadastro;
	
	private boolean firstAccess = false;
	private boolean validUsername = true;
	private boolean validCpf = true;
	private boolean validPassword = true;
	private boolean validOldPassword = true;
	private boolean administrador = false;
	
	protected FacesContext context = FacesContext.getCurrentInstance();
	protected ELResolver el = context.getApplication().getELResolver();
	private String nomeModalCepIncorreto;
	
	//o faces ainda não possui um PERFEITO mecanismo de mensagens, então vai na mão!
	public static final String[] MENSAGENS_JSF = 
			{"Nome de usuário já cadastrado na base de dados",
			"E-mail já cadastrado na base de dados",
			"A confirmação da senha não confere!",
			"CPF já cadastrado em nossa base de dados",
			"A senha antiga está incorreta!",
			"Não é permitido senha em branco."};
	
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
	private Parametrizacao obtemParametrizacao() {
		Parametrizacao parametrizacao = (Parametrizacao) context.getExternalContext().getApplicationMap().get("parametrizacao");
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
			boolean isSenhaEmBranco = this.getNova_senha().isEmpty();
			boolean isSenhaConfirmaEmBranco = this.getConfirma_nova_senha().isEmpty();
			boolean isSenhasDiferentes = !this.getNova_senha().equals( this.getConfirma_nova_senha());
			
			if(isSenhaEmBranco || isSenhaConfirmaEmBranco){
				this.setValidPassword(false);
				this.setMensagem_password(MENSAGENS_JSF[5]);
			}else if (isSenhasDiferentes) {
				this.setValidPassword(false);
				this.setMensagem_password(MENSAGENS_JSF[2]);
			} else {
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
	
	public void validarCampos(){
		acaoBotaoIncluirNovoCadastro = "";
		Usuario usuario = this.getUsuarioEntity();
		boolean isCamposPreenchidos = isDadosDoUsuarioPreenchido(usuario);
		
		if(isCamposPreenchidos) {
			acaoBotaoIncluirNovoCadastro = "Richfaces.showModalPanel('modalGravarDadossSave');";
		}
	}

	private boolean isDadosDoUsuarioPreenchido(Usuario usuario) {
		boolean valido = true;
		if (usuario.isNomeVazioOuNulo() || usuario.isSobreNomeVazioOuNulo() || usuario.isDataNascimentoVazioOuNulo()) {
			valido = false;
		}else if(usuario.isCPFVazioOuNulo() || usuario.isRGVazioOuNulo() || usuario.isEmailVazioOuNulo()){
			valido = false;
		}
		
		Set<Endereco> enderecos = usuario.getEnderecos();
		for (Endereco endereco : enderecos) {
			if(endereco.isCEPVazioOuNulo()){
				valido = false;
			}
		}
		
		return valido;
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
			
			if (findAddress.getLogradouro() == null
					|| findAddress.getBairro() == null
					|| findAddress.getCidade() == null) {
				setNomeModalCepIncorreto("Richfaces.showModalPanel('modalCepIncorreto');");
			}else{
				setNomeModalCepIncorreto("submit();");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean userExists() throws CpsExceptions{
		if(CommonOperations.userExists(this.getLoginEntity().getNomeLogin())){
			this.setValidUsername(false);
			this.setMensagem_username(MENSAGENS_JSF[0]);
			return false;
		}else{
			this.setValidUsername(true);
			return true;
		}
	}
	
	public boolean cpfExists() throws CpsExceptions{
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
	
	public boolean cpfExists(String cpf,String username) throws CpsExceptions{
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
	
	public String limpa() {
		this.setLoginEntity(null);
		this.setUsuarioEntity(null);
		this.setEnderecoEntity(null);
		return "toCadUser";
	}
	
	public String salva() throws CpsExceptions {
		this.acaoBotaoIncluirNovoCadastro = "";
		
		
		if(!this.validatePassword())
			return "";
		
		if(!this.validUsername)
			return "";
		
		if(this.cpfExists(this.getUsuarioEntity().getCpfUsuario().replace(".","").replace("-",""),this.getLoginEntity().getNomeLogin()))
			return "";
		
		if(!this.validarEnderecoInformado()) {
			return "";
		}
		
		userHandler = new UserManagementHandler();
		enderecoEntity.setEstado(this.getEstadoSigla());

		try{
			userHandler.save(usuarioEntity,loginEntity,enderecoEntity);
			return "";
		}catch (CpsHandlerException e) {
			throw new CpsExceptions(e);
		}
	}


	public String retorna() {
		this.limpa();
		FacesUtil.errorMessage("", Resources.getErrorProperties().getString("user_registered"),"usuario cadastrado");
		return "toLoginPage";
	}

	
	/**
	 * Valida se um endereço informado é valido
	 * @return Se é valido ou não.
	 */
	private boolean validarEnderecoInformado() {
		boolean ok = true;
		
		if(getEnderecoEntity() == null) {
			ok = false;
		}else if (getEnderecoEntity().getCep() == null || "".equals(getEnderecoEntity().getCep())) {
			ok = false;
		}else if (getEnderecoEntity().getBairro() == null || "".equals(getEnderecoEntity().getBairro())) {
			ok = false;
		}else if (getEnderecoEntity().getCidade() == null || "".equals(getEnderecoEntity().getCidade())) {
			ok = false;
		}else if (getEnderecoEntity().getLogradouro() == null || "".equals(getEnderecoEntity().getLogradouro())) {
			ok = false;
		}else if (getEnderecoEntity().getEstado() == null || "".equals(getEnderecoEntity().getEstado())) {
			ok = false;
		}
		return ok;
	}

	public void atualiza() throws CpsExceptions{

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
		
		if(enderecoEntity.getEstado()!= null && enderecoEntity.getEstado().length() > 2){
			enderecoEntity.setEstado(FormatadorEstadorHelper.obterSiglaEstado(enderecoEntity.getEstado()));
		}

		userHandler = new UserManagementHandler();
		try{
			userHandler.update(this.getUsuarioEntity());
		}catch (CpsExceptions e) {
			throw new CpsExceptions(e);
		}
	}
	
	public void atualizaCampos(){
		//recebe do faces config o parametro da classe de login, com suas respectivas propiedades
		LoginUserBean newLoginUserInstance = (LoginUserBean) el.getValue(context.getELContext(),null,"loginUserBean");
		this.usuarioEntity = newLoginUserInstance.getUsuario();
		if(this.usuarioEntity == null) return;
		//solução paliativa
		for(Endereco endereco : this.getUsuarioEntity().getEnderecos()){
			this.enderecoEntity = endereco;
		}
		for(Login login : this.getUsuarioEntity().getLogins()){
			this.loginEntity = login;
		}
		this.setAdministrador(this.getLoginEntity().getTipoUsuario() == 'A' ? true : false);
	}
	
	public void atualizaCampos(Usuario paramUser){
		this.usuarioEntity = paramUser;
		LoginUserHandler loginHandler = new LoginUserHandler();
		
		if(usuarioEntity.getEnderecos() == null || 
				usuarioEntity.getEnderecos().isEmpty()) {
			Set<Endereco> endereco = new HashSet<Endereco>();
			try {
				endereco.add(loginHandler.getEnderecoRelated(usuarioEntity.getIdUsuario()));
				usuarioEntity.setEnderecos(endereco);
				
				for(Endereco enderecoEntity : usuarioEntity.getEnderecos()) {
					this.enderecoEntity = enderecoEntity;
				}
				
			} catch (CpsHandlerException e) {
				e.printStackTrace();
			}
		}
		
		if(usuarioEntity.getLogins() == null || 
				usuarioEntity.getLogins().isEmpty() ||
				this.getLoginEntity() == null) {
			
			Set<Login> login = new HashSet<Login>();
			try {
				login.add(loginHandler.getLogin(usuarioEntity.getIdUsuario()));
				usuarioEntity.setLogins(login);
				
				for(Login loginEntity : usuarioEntity.getLogins()) {
					this.setLoginEntity(loginEntity);
				}
				
			} catch (CpsHandlerException e) {
				e.printStackTrace();
			}
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
	public Usuario getUsuarioEntity() {
		return usuarioEntity;
	}

	/**
	 * @param usuarioEntity the usuarioEntity to set
	 */
	public void setUsuarioEntity(Usuario usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

	/**
	 * @return the cepEntity
	 */
	public Cep getCepEntity() {
		return cepEntity;
	}

	/**
	 * @param cepEntity the cepEntity to set
	 */
	public void setCepEntity(Cep cepEntity) {
		this.cepEntity = cepEntity;
	}

	/**
	 * @return the enderecoEntity
	 */
	public Endereco getEnderecoEntity() {
		return enderecoEntity;
	}

	/**
	 * @param enderecoEntity the enderecoEntity to set
	 */
	public void setEnderecoEntity(Endereco enderecoEntity) {
		this.enderecoEntity = enderecoEntity;
	}

	/**
	 * @return the loginEntity
	 */
	public Login getLoginEntity() {
		return loginEntity;
	}

	/**
	 * @param loginEntity the loginEntity to set
	 */
	public void setLoginEntity(Login loginEntity) {
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
	public void setParametrizacao(Parametrizacao parametrizacao) {
		this.parametrizacao = parametrizacao;
	}

	/**
	 * @return the parametrizacao
	 */
	public Parametrizacao getParametrizacao() {
		return parametrizacao;
	}

	/**
	 * @param nomeModalCepIncorreto the nomeModalCepIncorreto to set
	 */
	public void setNomeModalCepIncorreto(String nomeModalCepIncorreto) {
		this.nomeModalCepIncorreto = nomeModalCepIncorreto;
	}

	/**
	 * @return the nomeModalCepIncorreto
	 */
	public String getNomeModalCepIncorreto() {
		return nomeModalCepIncorreto;
	}

	/**
	 * @return the acaoBotaoIncluirNovoCadastro
	 */
	public String getAcaoBotaoIncluirNovoCadastro() {
		return acaoBotaoIncluirNovoCadastro;
	}

	/**
	 * @param acaoBotaoIncluirNovoCadastro the acaoBotaoIncluirNovoCadastro to set
	 */
	public void setAcaoBotaoIncluirNovoCadastro(String acaoBotaoIncluirNovoCadastro) {
		this.acaoBotaoIncluirNovoCadastro = acaoBotaoIncluirNovoCadastro;
	}
}
