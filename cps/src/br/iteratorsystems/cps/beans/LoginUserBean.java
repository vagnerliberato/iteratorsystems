package br.iteratorsystems.cps.beans;

import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.handler.LoginUserHandler;

public class LoginUserBean {

	private String nomeLogin;
	private String senha;

	private boolean logado = false;

	private LOGIN login;
	private USUARIO usuario;
	private ENDERECO endereco;
	private LoginUserHandler loginHandler;

	public LoginUserBean() {
	}

	public String validaLogin() throws CpsGeneralExceptions {

		if ("".equals(this.getNomeLogin()) || "".equals(this.getSenha()))
			return "";

		loginHandler = new LoginUserHandler();
		login = loginHandler.doLogin(this.getNomeLogin(), this.getSenha());
		
		if (login != null) {
			this.setLogado(true);
			setUsuario(loginHandler.getUserRelated(login.getIdLogin()));
			setEndereco(loginHandler.getEnderecoRelated(login.getIdLogin()));
			return "toDefaultPage";
		} else {
			FacesUtil.errorMessage("", Resources.getErrorProperties().getString("incorrect_user_or_pass"),"usuario ou senha incorretos");
			return "";
		}
	}

	public String toUserManagerPage() {
		if (!this.isLogado()) {
			return NavigationBean.toUserAccess();
		}
		return "toCadUser";
	}

	public String logout() {
		return "";
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
}
