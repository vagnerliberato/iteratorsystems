package br.iteratorsystems.cps.beans;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.handler.LoginUserHandler;

public class LoginUserBean {

	private LOGIN login;
	private USUARIO usuario;
	private ENDERECO endereco;
	private LoginUserHandler loginHandler;
	
	private String nomeLogin;
	private String senha;
	private String email;
	private String cep;
	private boolean logado = false;
	private boolean firstAccess = false;

	public String validaLogin() throws CpsGeneralExceptions {

		if ("".equals(this.getNomeLogin()) || "".equals(this.getSenha()))
			return "";

		loginHandler = new LoginUserHandler();
		this.setLogin(loginHandler.doLogin(this.getNomeLogin(), this.getSenha()));
		
		if (this.getLogin() != null) {
			this.setLogado(true);
			return "toDefaultPage";
		} else {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("incorrect_user_or_pass"),"usuario ou senha incorretos");
			return "";
		}
	}
	
	public String novo() throws CpsGeneralExceptions {
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
		} catch (CpsGeneralExceptions e) {
			e.printStackTrace();
		}
		return "toCadUser";
	}

	public void getUserData() throws CpsGeneralExceptions {
		loginHandler = new LoginUserHandler();
		setUsuario(loginHandler.getUserRelated(login.getIdLogin()));
	}
	
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.invalidate();
		return "toLoginPage";
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

	public void setNomeLogin(String nomeLogin) {
		this.nomeLogin = nomeLogin;
	}

	public String getNomeLogin() {
		return nomeLogin;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public boolean isLogado() {
		return logado;
	}

	/**
	 * @return the login
	 */
	public LOGIN getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(LOGIN login) {
		this.login = login;
	}

	public void setUsuario(USUARIO usuario) {
		this.usuario = usuario;
	}

	public USUARIO getUsuario() {
		return usuario;
	}

	public void setEndereco(ENDERECO endereco) {
		this.endereco = endereco;
	}

	public ENDERECO getEndereco() {
		return endereco;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCep() {
		return cep;
	}

	public void setFirstAccess(boolean firstAccess) {
		this.firstAccess = firstAccess;
	}

	public boolean isFirstAccess() {
		return firstAccess;
	}
}
