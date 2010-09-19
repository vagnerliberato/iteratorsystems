package br.iteratorsystems.cps.beans;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import br.iteratorsystems.cps.common.CommonOperations;
import br.iteratorsystems.cps.common.EnviarEmail;
import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.Resources;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
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
	private String entradaRecuperacao;
	private String mensagemRecuperacao;
	private boolean logado = false;
	private boolean firstAccess = false;
	private boolean displayTelaCadastro;
	
	private static final String REGEX = "[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z\\.a-zA-Z]+";
	
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
	 * Limpa a mensagem de recuperação de acesso.
	 */
	public void limparMensagemRecuperacao() {
		mensagemRecuperacao = "";
		entradaRecuperacao = "";
	}
	
	/**
	 * Recupera o acesso
	 * @throws CpsHandlerException Se ocorrer algum erro
	 */
	public void recuperarAcesso() throws CpsHandlerException {
		loginHandler = new LoginUserHandler();
		Usuario usuario = null;
		if(entradaRecuperacao.matches(REGEX)) {
			usuario = loginHandler.getLoginBy(entradaRecuperacao, null);
		}else{
			usuario = loginHandler.getLoginBy(null,entradaRecuperacao);
		}
		if(usuario != null) {
			if(enviarEmail(usuario)) {
				mensagemRecuperacao = "Um email foi enviado para você com seus dados de acesso!";
			}else{
				mensagemRecuperacao = "Houve um erro ao enviar o email";
			}
		}else{
			mensagemRecuperacao = "Seus dados não foram encontrados no servidor.";
		}
	}
	
	/**
	 * Envia o email
	 * @param usuario - usuario
	 * @return Se o email foi enviado
	 */
	private boolean enviarEmail(Usuario usuario) {
		boolean sucesso = true;
		EnviarEmail email = new EnviarEmail();
		String titulo = "Sistema CPS - Recuperação de Acesso";
		String corpo = obterCorpoMensagem(usuario);
		try {
			if (!email.enviarEmail(titulo, corpo, usuario.getEmail())) {
				sucesso = false;
			}
		} catch (Exception e) {
			sucesso = false;
			e.printStackTrace();
		}
		return sucesso;
	}

	/**
	 * Obtem o corpo da mensage para recuperação do acesso
	 * @param usuario - usuario.
	 * @return Mensagem
	 */
	private String obterCorpoMensagem(Usuario usuario) {
		StringBuilder mensagem = new StringBuilder();
		Calendar calendar = GregorianCalendar.getInstance();
		mensagem.append("Olá, ");
		mensagem.append(usuario.getNomeUsuario());
		mensagem.append(" você solicitou o envio de seus dados de acesso às ");
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS"); 
		mensagem.append(formatador.format(calendar.getTime())+", do sistema CPS.");
		mensagem.append("\n");
		mensagem.append("\n");
		mensagem.append("Seguem seus dados: ");
		mensagem.append("\n");
		
		Login loginUsuarioRecuperacao = null;
		for(Login loginRec : usuario.getLogins()) {
			loginUsuarioRecuperacao = loginRec;
			break;
		}
		
		mensagem.append("Nome de usuário: "+loginUsuarioRecuperacao.getNomeLogin());
		mensagem.append("\n");
		mensagem.append("Senha: "+loginUsuarioRecuperacao.getSenha());
		mensagem.append("\n");
		mensagem.append("\n");
		mensagem.append("Qualquer dúvida entre em contato com a central de relacionamento.");
		mensagem.append("\n");
		mensagem.append("Obrigado,");
		mensagem.append("\n");
		mensagem.append("Sistema CPS - Porquê aqui a sua compra tem valor!");
		mensagem.append("\n");
		mensagem.append("\n");
		mensagem.append("\n");
		mensagem.append("ATENÇÃO: ISTO NÃO É UM SPAM, E SIM UMA SOLICITAÇÃO DE RECUPERAÇÃO DE ACESSO.");
		mensagem.append("\n");
		mensagem.append("Se você não solicitou a recuperação, entre IMEDIATAMENTE em contato com a central de relacionamento.");
		mensagem.append("\n");
		mensagem.append("\n");
		mensagem.append("Iterator Systems - Todos os direitos reservados. @Copyright "+calendar.get(Calendar.YEAR)+".");
		return mensagem.toString();
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
		if (this.getEmail() == null || this.getCep() == null || "".equals(this.getEmail()) || "".equals(this.getCep())) {
			return "";
		}

		if (this.getEmail().matches(REGEX)) {
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

	/**
	 * @param entradaRecuperacao the entradaRecuperacao to set
	 */
	public void setEntradaRecuperacao(String entradaRecuperacao) {
		this.entradaRecuperacao = entradaRecuperacao;
	}

	/**
	 * @return the entradaRecuperacao
	 */
	public String getEntradaRecuperacao() {
		return entradaRecuperacao;
	}

	/**
	 * @param mensagemRecuperacao the mensagemRecuperacao to set
	 */
	public void setMensagemRecuperacao(String mensagemRecuperacao) {
		this.mensagemRecuperacao = mensagemRecuperacao;
	}

	/**
	 * @return the mensagemRecuperacao
	 */
	public String getMensagemRecuperacao() {
		return mensagemRecuperacao;
	}
}
