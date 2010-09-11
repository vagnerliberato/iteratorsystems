package br.iteratorsystems.cps.beans;


import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.handler.LoginUserHandler;

public class LoginUserBean {

	private Login login;
	private Usuario usuario;
	private Endereco endereco;
	private LoginUserHandler loginHandler;
	
	private String nomeLogin;
	private String senha;
	private String email;
	private String cep;
	private boolean logado = false;
	private boolean firstAccess = false;
	private boolean displayTelaCadastro;
	
	/**
	 * Método que valida o login do usuario
	 * @return
	 * @throws CpsExceptions
	 */

	public String validaLogin() throws CpsExceptions {

		if ("".equals(this.getNomeLogin()) || "".equals(this.getSenha()))
			return "";

		loginHandler = new LoginUserHandler();
		this.setLogin(loginHandler.doLogin(this.getNomeLogin(), this.getSenha()));
		
		if (this.getLogin() != null) {
			
			FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
			servletContext.setAttribute("usuarioLogado", this.getLogin());
			
			this.setLogado(true);
			limparObjetoPaginaInicio(context);
			return "toDefaultPage";
		} else {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("incorrect_user_or_pass"),"usuario ou senha incorretos");
			return "";
		}
	}
	
	/**
	 * Limpa o objeto bean da página inicial
	 * @param context - Faces context
	 */
	private void limparObjetoPaginaInicio(FacesContext context) {
		ELResolver el = context.getApplication().getELResolver();
		DefaultBean defaultBean = (DefaultBean) 
					el.getValue(context.getELContext(),null,"defaultBean");
		defaultBean.limparTudo();
	}
	
	/**
	 * 
	 * @return
	 * @throws CpsExceptions
	 */
	
	public String novo() throws CpsExceptions {
		String regex = "[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z\\.a-zA-Z]+";

		if (this.getEmail() == null || this.getCep() == null || "".equals(this.getEmail()) || "".equals(this.getCep())) {
			return "";
		}

		if (this.getEmail().matches(regex)) {
			if(CommonOperations.mailExists(this.getEmail())){
				FacesUtil.errorMessage("", Resources.getErrorProperties().getString("email_exists"), "email já cadastrado");
				return "";
			}
			this.setFirstAccess(true);
			return "toCadUser";
		} else {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("invalid_email"), "email invalido");
			return "";
		}
	}
	
	/**
	 * 
	 * @return toCadUser
	 */
	
	public String toUserManagerPage() {
		if (!this.isLogado()) {
			return NavigationBean.toUserAccess();
		}
		try {
			getUserData();
			FacesContext context = FacesContext.getCurrentInstance();
			ELResolver el = context.getApplication().getELResolver();
			UserManagementBean temporaryUserBean = (UserManagementBean) el.getValue(context.getELContext(), null,"userManagementBean");
			temporaryUserBean.atualizaCampos(this.getUsuario());
		} catch (CpsExceptions e) {
			e.printStackTrace();
		}
		return "toCadUser";
	}
	
	/**
	 * 
	 * @throws CpsExceptions
	 */

	public void getUserData() throws CpsExceptions {
		loginHandler = new LoginUserHandler();
		setUsuario(loginHandler.getUserRelated(login.getIdLogin()));
	}
	
	/**
	 * Faz o logout da aplicação.
	 * @return toLoginPage
	 */
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.invalidate();
		removerObjetosDaSessao(facesContext);
		restaurarDados();
		return "toDefaultPage";
	}
	
	/**
	 * Remove os objetos da sessão.
	 * @param context - Faces context
	 */
	private void removerObjetosDaSessao(FacesContext context) {
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		servletContext.removeAttribute("usuarioLogado");
	}
	
	/**
	 * Restaura os dados padrões do bean.
	 */
	private void restaurarDados() {
		this.setCep(null);
		this.setEmail(null);
		this.setEndereco(null);
		this.setFirstAccess(false);
		this.setLogado(false);
		this.setLogin(null);
		this.setNomeLogin(null);
		this.setSenha(null);
		this.setUsuario(null);
	}
	
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * 
	 * @param nomeLogin
	 */

	public void setNomeLogin(String nomeLogin) {
		this.nomeLogin = nomeLogin;
	}
	
	/**
	 * 
	 * @return nomeLogin
	 */

	public String getNomeLogin() {
		return nomeLogin;
	}
	
	/**
	 * 
	 * @param logado
	 */

	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	
	/**
	 * 
	 * @return logado
	 */

	public boolean isLogado() {
		return logado;
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}
	
	/**
	 * 
	 * @param usuario
	 */

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * 
	 * @return usuario
	 */

	public Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * 
	 * @param endereco
	 */

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	/**
	 * 
	 * @return endereco
	 */

	public Endereco getEndereco() {
		return endereco;
	}
	
	/**
	 * 
	 * @param email
	 */

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @return email
	 */

	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @param cep
	 */

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**
	 * 
	 * @return cep
	 */

	public String getCep() {
		return cep;
	}
	
	/**
	 * 
	 * @param firstAccess
	 */

	public void setFirstAccess(boolean firstAccess) {
		this.firstAccess = firstAccess;
	}
	
	/**
	 * 
	 * @return firstAccess
	 */

	public boolean isFirstAccess() {
		return firstAccess;
	}

	/**
	 * @param displayTelaCadastro the displayTelaCadastro to set
	 */
	public void setDisplayTelaCadastro(boolean displayTelaCadastro) {
		this.displayTelaCadastro = displayTelaCadastro;
	}

	/**
	 * @return the displayTelaCadastro
	 */
	public boolean isDisplayTelaCadastro() {
		return displayTelaCadastro;
	}
}
